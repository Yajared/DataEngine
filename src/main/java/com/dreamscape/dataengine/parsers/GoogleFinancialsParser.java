/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.domain.Financials;
import com.dreamscape.dataengine.domain.Metric;
import com.dreamscape.dataengine.exceptions.PageDoesNotExistException;
import static com.dreamscape.dataengine.parsers.HTMLParser.generateRegEx;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Jared
 */
public class GoogleFinancialsParser extends HTMLParser {
    
    private static final String startContent = ">Income Statement</b></b></a>";
    private static final String endContent = "<td class=\"lft lm\">Cash Taxes Paid, Supplemental";
    // Change endContent to, since we are not using annual data: <div id="balannualdiv_viz" class="id-balannualdiv_viz viz_charts"></div>
    public GoogleFinancialsParser(){
        
    }
    
    public GoogleFinancialsParser(String textToParse){
        super(textToParse);
    }
    Metric metric;
    
    public void initializePatterns(InputStream xmlRegexPatterns) throws IOException{
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
 
            DefaultHandler handler = new DefaultHandler() {
    
            String name;
            String pattern;
            
            boolean bname = false;
            boolean btype = false;
            boolean bpattern = false;
            
            StringBuilder buf = new StringBuilder();
            
 
            @Override
            public void startElement(String uri, String localName,String qName, 
                Attributes attributes) {
                
		//System.out.println("Start Element :" + qName);
 
                if (qName.equalsIgnoreCase("NAME")) {
                    //System.out.println("Starting Element...");
                    bname = true;
                    metric = new Metric();
		}
 
                else if (qName.equalsIgnoreCase("TYPE")){
                    //System.out.println("Starting Type....");
                    btype = true;
                }
                
                else if (qName.equalsIgnoreCase("PATTERN")){
                    //System.out.println("Starting Pattern....");
                    bpattern = true;
                }
            }
 
            @Override
            public void endElement(String uri, String localName,
                    String qName) throws SAXException {
            }

            @Override
            public void characters(char ch[], int start, int length) throws SAXException {
                if (bname) {
                    metric.setName( new String(ch, start, length));
                    bname = false;
                }
                else if (btype){
                    metric.setType( new String(ch, start, length));
                    btype = false;
                }
                else if (bpattern){
                    metric.setRawText(new String(ch, start, length));
                    //System.out.println("Pattern Raw Text: " + metric.getRawText());
                    metric.setPattern((Pattern.compile(generateRegEx(metric.getType(), metric.getRawText()))));
                    
                    sourceMetrics.put(metric.getName(), metric);
                    
                    bpattern = false;
                }
                
            }
         };

         saxParser.parse(xmlRegexPatterns, handler);

         } 
         catch (ParserConfigurationException e) 
         {
            e.printStackTrace();
         }
         catch (SAXException e)
         {
             e.printStackTrace();
         }
    }
    public Financials parseFinancials(String ticker, String textBlock){
        try{
            String financialsText = BlockReader.trimExcessContent(textBlock, startContent, endContent, true, true);
            financialsText = BlockReader.removeWhiteSpace(financialsText);
            reader = new BlockReader(financialsText);
        }
        catch(StringIndexOutOfBoundsException e){
            System.out.println("Could not locate one of: " + startContent + " or " + endContent + " in textBlock: " + textBlock);
            return null;
        }
        catch(PageDoesNotExistException e){
            System.out.println("Page for " + ticker + " not found!");
            return null;
        }
        Financials financials = new Financials();
        financials.setTicker(ticker);
        
        financials.setOperatingIncome((Double)parseValue(sourceMetrics.get("operatingIncome")));
        financials.setCashAndSTInvestments((Double)parseValue(sourceMetrics.get("cashAndSTInvestments")));
        financials.setGoodwill((Double)parseValue(sourceMetrics.get("goodwill")));
        financials.setIntangibleAssets((Double)parseValue(sourceMetrics.get("intangibleAssets")));
        financials.setLongTermInvestments((Double)parseValue(sourceMetrics.get("longTermInvestments")));
        financials.setOtherLongTermAssets((Double)parseValue(sourceMetrics.get("otherLongTermAssets")));
        financials.setTotalAssets((Double)parseValue(sourceMetrics.get("totalAssets")));
        financials.setTotalCurrentLiabilities((Double)parseValue(sourceMetrics.get("totalCurrentLiabilities")));
        
        return financials;
    }
}
