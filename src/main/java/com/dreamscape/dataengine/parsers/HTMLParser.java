/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.domain.Metric;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 */
public class HTMLParser extends Parser{
    protected static final String numberPattern = "((N\\/A)|(NaN)|-?((\\d+\\.\\d+)[MBTK]?|(((\\d{1,3}(,\\d{3})+)|(\\d{2,3}))(\\.\\d+)?[MK]?)))<";
    private static final String percentPattern = ">((N\\/A)|(NaN)|(-?((\\d{1,3}(,\\d{3})(,\\d{3})?)|(\\d+))(\\.\\d+))?%)<";
    private static final String ratioPattern = ">((N\\/A)|(\\d+:\\d+))<";
    private static final String datePattern = ">((N\\/A)|([A-Z]{1}[a-z]{2} \\d{1,2}, \\d{4}))<";
    private static final String accountingNumberPattern = ">((-)|(-?\\d{1,3}(,\\d{3})*(\\.\\d+)?))<";
    private static final String listTerminationPattern = ".*<\\/tr>";
    
    protected static Map<String, Metric> sourceMetrics = new HashMap<>();
    protected BlockReader reader;
    
    public HTMLParser(){
        
    }
    public HTMLParser(String textToParse){
        reader = new BlockReader(textToParse);
    }
    
    protected Object parseValue(Metric metric){
        
        Object value = null;
        
        //System.out.println(metric.getRawText());
        //System.out.println(metric.getType());

        Pattern p = metric.getPattern();
        //System.out.println(p.toString());
        
        String textToTest = reader.getRemainingBlock();
        String rawText = metric.getRawText();
        
        int beginningIndex = textToTest.indexOf(rawText);

        value = findNextValue(textToTest, beginningIndex, p, metric.getType(), this.reader, metric.isList());
        
        return value;
    }
    private static Object findNextValue(String textBlock, int startIndex, Pattern p, String metricType, BlockReader r, boolean isList)
    {
        Matcher matcher;
        int i = startIndex + 1;
        String textToTest;
        
        boolean matchFound = false;
        Object value = null;
        if(isList)
        {
            Object currentValue;
            List valueList = new ArrayList<>();
            boolean hasMoreItems = true;
            while(hasMoreItems)
            {
                textToTest = textBlock.substring(startIndex, i);
                matcher = p.matcher(textToTest);
                
                if(matcher.matches())
                {
                    currentValue = extractValue(textToTest, metricType);
                    valueList.add(currentValue);
                    r.setPosition(r.getPosition() + i);
                }
                
                if(Pattern.matches(listTerminationPattern, textToTest))
                    hasMoreItems = false;
                
                i++;
            }
            return valueList;
        }
        else
        {
            while(matchFound == false)
            {
                textToTest = textBlock.substring(startIndex, i);
                matcher = p.matcher(textToTest);
                
                if(matcher.matches())
                {
                    value = extractValue(textToTest, metricType);
                    matchFound = true;
                    
                    r.setPosition(r.getPosition() + i);
                }
                
                i++;
            }
        }
        return value;
    }
    protected static Object extractValue(String matchedText, String valueType){
        String valueAsString = matchedText.substring(matchedText.lastIndexOf(">") + 1, matchedText.lastIndexOf("<"));
        //System.out.println(valueAsString);
        //System.out.println(valueType);
        Object value = null;
        boolean percentValue = false;
        
        Double valueAsDouble = null;
        
        switch(valueType){
            
            case "date":
                //System.out.println("Found a date type!");
                int month = -1;
                if(valueAsString.equals("N/A")){
                    return null;
                }
                else{
                    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                    String monthSubString = valueAsString.substring(0, 3);
                    for(int i = 0; i< months.length; i++){
                        if(monthSubString.equals(months[i])){ 
                            month = i;
                            break;
                        }
                    }
                }
                int indexOfComma = valueAsString.indexOf(",");
            
                String daySubString = valueAsString.substring(4, indexOfComma);
                String yearSubString = valueAsString.substring(indexOfComma + 2);
            
                DateTime dt = new DateTime(yearSubString + "-" + String.valueOf(month + 1) + "-" + daySubString + "T21:39:45.618-08:00");
            
                value = dt;
            break;
            
            case "accountingNumber":
                //System.out.println("Found an accounting type!");
                if(valueAsString.indexOf("-") != -1 && valueAsString.length() < 2)
                {
                    value = new Double(0.0);
                    break;
                }
            
                //Convert to millions
                if(valueAsString.indexOf(".") != -1){
                    // Move decimal point over 2 places (since this is our precision)
                    int decimalIndex = valueAsString.indexOf(".");
                    valueAsString = valueAsString.substring(0, decimalIndex) + valueAsString.substring(decimalIndex + 1) + "0000";
                    //System.out.println(valueAsString);
                }
                else{
                    valueAsString = valueAsString + "000000";
                }
                //System.out.println(valueAsString);
                
            case "percent":
                //System.out.println("Found a percent type!");
                if(valueAsString.indexOf("%") != -1){
                    valueAsString = valueAsString.substring(0, valueAsString.length() - 1);
                    percentValue = true;
                }    
            case "number":
                //System.out.println("Found a number type!");
                
                boolean negativeNumber = false;

                if(valueAsString.indexOf("-") != -1){
                    valueAsString = valueAsString.substring(1);
                    negativeNumber = true;
                }
                while(valueAsString.indexOf(',') != -1){
                    int locationOfComma = valueAsString.indexOf(',');
                    valueAsString = valueAsString.substring(0, locationOfComma) + valueAsString.substring(locationOfComma + 1);
                }

                    if(valueAsString.indexOf("B") != -1){
                        valueAsDouble = Double.valueOf(Double.parseDouble(valueAsString.substring(0, valueAsString.length() - 1)) * 1000000000.0);
                    }
                    else if(valueAsString.indexOf("M") != -1){
                        valueAsDouble = Double.valueOf(Double.parseDouble(valueAsString.substring(0, valueAsString.length() - 1)) * 1000000.0);
                    }
                    else if(valueAsString.indexOf("T") != -1){
                        valueAsDouble = Double.valueOf(Double.parseDouble(valueAsString.substring(0, valueAsString.length() - 1)) * 1000000000000.0);
                    }
                    else if(valueAsString.indexOf("K") != -1){
                        valueAsDouble = Double.valueOf(Double.parseDouble(valueAsString.substring(0, valueAsString.length() - 1)) * 1000.0);
                    }
                    else if(valueAsString.contains("N/A") || valueAsString.contains("NaN")){
                        valueAsDouble = null;
                        break;
                    }
                    else{
                        valueAsDouble = Double.valueOf(Double.parseDouble(valueAsString));
                    }
                    
                    if(percentValue)
                        valueAsDouble /= 100;
                    if(negativeNumber)
                        valueAsDouble *= -1.0;
                    
                    value = valueAsDouble;
             break;
                
             case "ratio":
                //System.out.println("Found a ratio type!");
                int indexOfColon = valueAsString.indexOf(":");
                if(indexOfColon != -1){
                    String firstHalf = valueAsString.substring(0, indexOfColon);
                    String secondHalf = valueAsString.substring(indexOfColon + 1);
                
                    value = Double.valueOf(Double.parseDouble(firstHalf) / Double.parseDouble(secondHalf));
                }   
             break;
        }
        //if(value != null)
        //    System.out.println(value);

        return value;
    }
    
    public static String generateRegEx(String type, String pattern){
        
        pattern = escapeRegexSpecialChar(pattern);
        String regExUniversalBuffer = ".*";
        pattern += regExUniversalBuffer;
        
        switch(type){
            case "number":
                pattern += numberPattern;
                break;
            case "percent":
                pattern += percentPattern;
                break;
            case "ratio":
                pattern += ratioPattern;
                break;
            case "date":
                pattern += datePattern;
                break;
            case "accountingNumber":
                pattern += accountingNumberPattern;
                break;
            default:
                System.err.println("No Regex Pattern Specified for " + pattern);
                System.exit(1);
        }
        return pattern;
    }
    private static String escapeRegexSpecialChar(String pattern){
        StringBuilder newPattern = new StringBuilder("");
        char[] patternAsCharArray = pattern.toCharArray();
        for(int i = 0;i<patternAsCharArray.length;i++)
        {
            if(patternAsCharArray[i] == '(' || patternAsCharArray[i] == ')')
            {
                newPattern.append('\\');
            }
            newPattern.append(patternAsCharArray[i]);
        }
        return newPattern.toString();
    }
}
