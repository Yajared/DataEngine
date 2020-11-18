/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.domain.Prospect;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The central method of this class is the tokenize method.
 * This method, given a blob of text, will create a searchable/iterable
 * list of tokens.
 * @author Jared
 */

public class FinvizParser extends HTMLParser { //Finviz sold out... so now we have to implement a screen scraper to get ticker names
    private static String startContent = "<table width=\"100%\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" bgcolor=\"#d3d3d3\">";
    private static String endContent = "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"body-table\" bgcolor=\"#ffffff\" border=\"0\">";
    
    private static String uniqueTickerContentIdentifier = "screener-link-primary\">";
    private static String tickerRegex = ".*" + uniqueTickerContentIdentifier + "([A-Z]{1,5}(\\.[A-Z]{1,5})?)<";
    
    private static String pricePrefix = "offsetx=\\[-?\\d+\\] offsety=\\[-?\\d+\\] delay=\\[-?\\d+\\]\"><a href=\"quote\\.ashx\\?t=\\w{1,5}&ty=c&p=d&b=1\" class=\"screener-link\"><span style=\"color:#.{6};\">";
    private static String priceRegex = ".*" + pricePrefix + "\\d+\\.\\d{2}<";
    private static String pricePrefixTerminator = "color:#";
    private static int fromTerminatorToPrice = 9;
    private static String tickerCountRegex = ".*<b>Total: <\\/b>\\d+\\s";
    
    private static final String numberPattern = "\\d+";
    private static String tickerCountStartPattern = "<td width=\"140\" align=\"left\" valign=\"bottom\" class=\"count-text\">";
    private static String tickerCountEndPattern = "<td align=\"center\" valign=\"bottom\" class=\"fullview-links\">";
    private static final int lengthOfJunkTextBetweenTickers = 1400;
    private static final int lengthOfExtraTextBetweenProspects = 860;
    
    
    // Given a CSV file as input we should create a Map of String -> String[]
    public static Map<String, String[]> convertToMap(File fileInCSVFormat) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        HashMap <String, String[]> map = new HashMap<>();
        try{
                br = new BufferedReader(new FileReader(fileInCSVFormat));
            
                String headers = br.readLine();
                String line = br.readLine();
                while(line != null){
                    String[] companyData = line.split(",");
                    map.put(companyData[1], companyData);
                    line = br.readLine();
            }
        }
        catch(FileNotFoundException e){
            System.err.println("Could not find a readable CSV File.");
            throw e;
        }
        catch(IOException e){
            System.err.println("Error reading from File.");
            throw e;
        }
        
        return map;
    }
    
//    public ArrayList<String> parseTickers(String textBlock){
//        
//        
//        ArrayList<String> tickers = new ArrayList<>();
//        
//        String finvizText = BlockReader.trimExcessContent(textBlock, startContent, endContent, false, false);
//        finvizText = BlockReader.removeWhiteSpace(finvizText);
//        this.reader = new BlockReader(finvizText);
//        
//        do{
//            String ticker = parseNextTicker();
//            if(ticker != null)
//                tickers.add(ticker);
//            else
//                break;
//        }while(true);
//        
//        return tickers;
//    }
    
    public List<Prospect> parseProspects(String textBlock){
        List<Prospect> prospects = new ArrayList<>();
        
        String finvizText = BlockReader.trimExcessContent(textBlock, startContent, endContent, false, false);
        finvizText = BlockReader.removeWhiteSpace(finvizText);
        this.reader = new BlockReader(finvizText);
        
        do{
            Prospect prospect = parseNextProspect();
            if(prospect != null)
                prospects.add(prospect);
            else
                break;
        }while(true);
        
        return prospects;
    }
    
    private String parseNextTicker(){
        String value = null;
        final int END_OF_BLOCK_BUFFER = 10;
                
        String textToSearch = reader.getRemainingBlock();

        String currentBlock = "";
        Matcher matcher;
        boolean matchFound = false;
        int i = 1;
        int beginningIndex = 0;

        Pattern p = Pattern.compile(tickerRegex);

        //System.out.println("Searching through block: " + textToSearch);
        //System.out.println("Searching for pattern: " + tickerPattern);

        int textLength = 0;
        
        if(textToSearch != null)
            textLength = textToSearch.length();
        else
            return value;
        int searchSpan = textLength - END_OF_BLOCK_BUFFER;

        while(!matchFound && i < searchSpan)
        {
            currentBlock = (textToSearch.substring(beginningIndex, i));
            matcher = p.matcher(currentBlock);

            //System.out.println(currentBlock);

            if(matcher.matches())
            {
                value = extractTickerValue(currentBlock);
                matchFound = true;
                reader.setPosition(reader.getPosition() + i);
                reader.setPosition(reader.getPosition() + lengthOfJunkTextBetweenTickers);
            }
            i++;
        }
        return value;
    }
    
    private Prospect parseNextProspect(){
        Prospect prospect = null;
        final int END_OF_BLOCK_BUFFER = 10;
                
        String textToSearch = reader.getRemainingBlock();

        String currentBlock = "";
        Matcher matcher;
        boolean tickerFound = false;
        boolean priceFound = false;
        int i = 1;
        int beginningIndex = 0;

        Pattern tickerPattern = Pattern.compile(tickerRegex);
        Pattern pricePattern = Pattern.compile(priceRegex);
        //System.out.println("Searching through block: " + textToSearch);
        //System.out.println("Searching for pattern: " + tickerPattern);

        if(textToSearch != null){
            prospect = new Prospect();
        }
        else
            return prospect;
 
        int searchSpan = textToSearch.length() - END_OF_BLOCK_BUFFER;
        
        // Search for the ticker
        while(!tickerFound && i < searchSpan)
        {
            currentBlock = (textToSearch.substring(beginningIndex, i));
            matcher = tickerPattern.matcher(currentBlock);

            //System.out.println(currentBlock);

            if(matcher.matches())
            {
                prospect.setSymbol(extractTickerValue(currentBlock));
                tickerFound = true;
                reader.setPosition(reader.getPosition() + i);
            }
            i++;
        }
        
        // Reset text and iterator
        textToSearch = reader.getRemainingBlock();
        i = 0;
        
        // Now we search for the price
        while(!priceFound && i < searchSpan)
        {
            try {
                currentBlock = (textToSearch.substring(beginningIndex, i));
            }
            catch(Exception e) {
                System.err.println("Died on search text: " + textToSearch + " with beginning index: " + beginningIndex + " and ending index: " + i);
            }
            matcher = pricePattern.matcher(currentBlock);

            //System.out.println(currentBlock);

            if(matcher.matches())
            {
                prospect.setPriceAtCreation(extractPriceValue(currentBlock));
                priceFound = true;
                reader.setPosition(reader.getPosition() + i);
                reader.setPosition(reader.getPosition() + lengthOfExtraTextBetweenProspects);
            }
            i++;
        }
        return prospect;
    }
    
    public int parseTotalTickersCount(String content){
        int value = 0;
        try{
            String trimmedContent = BlockReader.trimExcessContent(content, tickerCountStartPattern, tickerCountEndPattern, true, true);
            this.reader = new BlockReader(trimmedContent);    
            String textToSearch = reader.getRemainingBlock();

            String currentBlock;
            Matcher matcher;
            int i = 1;

            Pattern p = Pattern.compile(tickerCountRegex);

            //System.out.println("Searching through block: " + textToSearch);
            //System.out.println("Searching for pattern: " + tickerPattern);

            int textLength = 0;

            if(textToSearch != null)
                textLength = textToSearch.length();
            else
                return 0;
            int searchSpan = textLength;

            while(i < searchSpan)
            {
                currentBlock = (textToSearch.substring(0, i));
                matcher = p.matcher(currentBlock);

                //System.out.println(currentBlock);

                if(matcher.matches())
                {
                    value = extractTickerCount(currentBlock);
                    break;
                }
                i++;
            }    
        }
        catch(RuntimeException e)
        {
            System.err.println("Runtime Exception encountered when trying to parse the page!");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        finally{
            return value;
        }
    }
    
    private static String extractTickerValue(String currentBlock){
        String ticker = currentBlock.substring(currentBlock.indexOf(uniqueTickerContentIdentifier) + uniqueTickerContentIdentifier.length(), currentBlock.length() - 1);
        
        return ticker;
    }
    
    private static Float extractPriceValue(String currentBlock){
        Float price = null;
        String priceAsString = currentBlock.substring(currentBlock.lastIndexOf(pricePrefixTerminator) + pricePrefixTerminator.length() + fromTerminatorToPrice, currentBlock.length() - 1);
        try{
            price = Float.parseFloat(priceAsString);
        }
        catch(NumberFormatException e){
            System.err.println("In block: " + currentBlock);
            System.err.println("Could not extract a price value from String: " + priceAsString);
            System.err.println("-------------------------------------------------------------------------------------------------");
        }
        
        return price;
    }
    
    private static int extractTickerCount(String currentBlock){
        StringBuilder value = new StringBuilder("");
        Pattern p = Pattern.compile(numberPattern);
        Matcher matcher = null;
        
        currentBlock = currentBlock.trim(); // trim the trailing whitespace
        int i = currentBlock.length() - 1;
        if(i < 0)
            return 0;
        do
        {
            value.insert(0, currentBlock.charAt(i));
            matcher = p.matcher(value);
            
        }while(matcher.matches() && --i > 0);
        
        return Integer.parseInt(value.substring(1));
    }

    public static String getStartContent() {
        return startContent;
    }

    public static void setStartContent(String startContent) {
        FinvizParser.startContent = startContent;
    }

    public static String getEndContent() {
        return endContent;
    }

    public static void setEndContent(String endContent) {
        FinvizParser.endContent = endContent;
    }
}
