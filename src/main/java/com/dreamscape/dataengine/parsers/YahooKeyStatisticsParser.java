/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.domain.Metric;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.exceptions.PageDoesNotExistException;

import java.util.regex.Pattern;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
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
public class YahooKeyStatisticsParser extends HTMLParser {
    
    private static final String startContent = "<big><b>Key Statistics";
    private static final String endContent = "Key Statistics Help";
    
    public YahooKeyStatisticsParser(){
        
    }
    public YahooKeyStatisticsParser(String textToParse){
        super(textToParse);
    }
   /*
    * TODO: Write a GoogleFinanceParser and refactor shared code back into this class, 
    * Move the rest into YahooKeyStatisticsParser. Then, have this class be inherited
    */
    public void generatePatterns(InputStream patternConfiguration) throws IOException {
        
        try {
        
        
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
 
	DefaultHandler handler = new DefaultHandler() {
 
        boolean bname = false;
	boolean bpattern = false;
        boolean btype = false;
        
        String key;
        String type;
        String pattern;
        
        StringBuilder buf = new StringBuilder();
        
 
        @Override
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) {
 
                if (qName.equalsIgnoreCase("ELEMENT")) {
                    key = attributes.getValue("id");
                    sourceMetrics.put(key, null);
		}
 
                else if (qName.equalsIgnoreCase("PATTERN")) {                     
			bpattern = true;
		}
                else if (qName.equalsIgnoreCase("TYPE")){
                        btype = true;
                }

 
	}
 
        @Override
	public void endElement(String uri, String localName,
		String qName) throws SAXException {
 
            if (qName.equalsIgnoreCase("ELEMENT")) {
                    sourceMetrics.put(key, new Metric(type, Pattern.compile(generateRegEx(type, pattern)), pattern));
		}
	}
 
        @Override
	public void characters(char ch[], int start, int length) throws SAXException {

            if (bpattern) {
                    pattern = new String(ch, start, length);
                    //System.out.println("Pattern : " + pattern);
                    bpattern = false;
            }
            if (btype){
                type = new String(ch, start, length);
                //System.out.println("Type: " + type);
                btype = false;
            }
        }
     };
 
       saxParser.parse(patternConfiguration, handler);
 
     } 
     catch (ParserConfigurationException | SAXException e) 
     {
        e.printStackTrace();
     }
    }
    
    public Security parseSecurity(String ticker, String textBlock){
        try{
            String keyStatsText = BlockReader.trimExcessContent(textBlock, startContent, endContent, true, true);
            reader = new BlockReader(keyStatsText);
        }
        catch(StringIndexOutOfBoundsException e){
            System.err.println("Error trying to access String index in content.");
            return null;
        }
        catch(PageDoesNotExistException e){
            System.err.println("Page for " + ticker + " not found!");
            return null;
        }
        Security security = new Security();
        security.setTicker(ticker);
  
        // Finds a RegEx Match in the current range
        security.setMarketCap((Double)parseValue(sourceMetrics.get("marketCap")));
        security.setEnterpriseValue((Double)parseValue(sourceMetrics.get("enterpriseValue")));
        security.setTrailingPE((Double)parseValue(sourceMetrics.get("trailingPE")));
        security.setForwardPE((Double)parseValue(sourceMetrics.get("forwardPE")));
        security.setPegRatio((Double)parseValue(sourceMetrics.get("pegRatio")));
        security.setPriceSales((Double)parseValue(sourceMetrics.get("priceSales")));
        security.setPriceBook((Double)parseValue(sourceMetrics.get("priceBook")));
        security.setEvToRevenue((Double)parseValue(sourceMetrics.get("eVToRevenue")));
        security.setEvToEBITDA((Double)parseValue(sourceMetrics.get("eVToEBITDA")));
        security.setProfitMargin((Double)parseValue(sourceMetrics.get("profitMargin")));
        security.setOperatingMargin((Double)parseValue(sourceMetrics.get("operatingMargin")));
        security.setReturnOnAssets((Double)parseValue(sourceMetrics.get("returnOnAssets")));
        security.setReturnOnEquity((Double)parseValue(sourceMetrics.get("returnOnEquity")));
        security.setRevenue((Double)parseValue(sourceMetrics.get("revenue")));
        security.setRevenuePerShare((Double)parseValue(sourceMetrics.get("revenuePerShare")));
        security.setQtrRevenueGrowth((Double)parseValue(sourceMetrics.get("qtrRevenueGrowth")));
        security.setGrossProfit((Double)parseValue(sourceMetrics.get("grossProfit")));
        security.setEbitda((Double)parseValue(sourceMetrics.get("ebitda")));
        security.setNetIncomeAvlToCommonShares((Double)parseValue(sourceMetrics.get("netIncomeAvlToCommonShares")));
        security.setDilutedEPS((Double)parseValue(sourceMetrics.get("dilutedEPS")));
        security.setQtrEarningsGrowth((Double)parseValue(sourceMetrics.get("qtrEarningsGrowth")));
        security.setTotalCash((Double)parseValue(sourceMetrics.get("totalCash")));
        security.setTotalCashPerShare((Double)parseValue(sourceMetrics.get("totalCashPerShare")));
        security.setTotalDebt((Double)parseValue(sourceMetrics.get("totalDebt")));
        security.setTotalDebtToEquity((Double)parseValue(sourceMetrics.get("totalDebtToEquity")));
        security.setCurrentRatio((Double)parseValue(sourceMetrics.get("currentRatio")));
        security.setBookValuePerShare((Double)parseValue(sourceMetrics.get("bookValuePerShare")));
        security.setOperatingCashFlow((Double)parseValue(sourceMetrics.get("operatingCashFlow")));
        security.setLeveredCashFlow((Double)parseValue(sourceMetrics.get("leveredCashFlow")));
        security.setBeta((Double)parseValue(sourceMetrics.get("beta")));
        security.setFiftyTwoWeekChange((Double)parseValue(sourceMetrics.get("fiftyTwoWeekChange")));
        security.setFiftyTwoWeekHigh((Double)parseValue(sourceMetrics.get("fiftyTwoWeekHigh")));
        security.setFiftyTwoWeekLow((Double)parseValue(sourceMetrics.get("fiftyTwoWeekLow")));
        security.setFiftyDayMovingAvg((Double)parseValue(sourceMetrics.get("fiftyDayMovingAvg")));
        security.setTwoHundredDayMovingAvg((Double)parseValue(sourceMetrics.get("twoHundredDayMovingAvg")));
        security.setAvgVolThreeMon((Double)parseValue(sourceMetrics.get("avgVolThreeMon")));
        security.setAvgVolTenDay((Double)parseValue(sourceMetrics.get("avgVolTenDay")));
        security.setSharesOutstanding((Double)parseValue(sourceMetrics.get("sharesOutstanding")));
        security.setFloatShares((Double)parseValue(sourceMetrics.get("floatShares")));
        security.setPercentHeldByInsiders((Double)parseValue(sourceMetrics.get("percentHeldByInsiders")));
        security.setPercentHeldByInstitutions((Double)parseValue(sourceMetrics.get("percentHeldByInstitutions")));
        security.setSharesShort((Double)parseValue(sourceMetrics.get("sharesShort")));
        security.setShortRatio((Double)parseValue(sourceMetrics.get("shortRatio")));
        security.setShortPercentOfFloat((Double)parseValue(sourceMetrics.get("shortPercentOfFloat")));
        security.setPriorMonthSharesShort((Double)parseValue(sourceMetrics.get("priorMonthSharesShort")));
        security.setForwardAnnualDividendRate((Double)parseValue(sourceMetrics.get("forwardAnnualDividendRate")));
        security.setForwardAnnualDividendYield((Double)parseValue(sourceMetrics.get("forwardAnnualDividendYield")));
        security.setFiveYearAvgDividendYield((Double)parseValue(sourceMetrics.get("fiveYearAvgDividendYield")));
        security.setPayoutRatio((Double)parseValue(sourceMetrics.get("payoutRatio")));
        //security.setDividendDate((DateTime)parseValue(sourceMetrics.get("")));
        //security.setExDividendDate((DateTime)parseValue(sourceMetrics.get("")));
        security.setLastSplitFactor((Double)parseValue(sourceMetrics.get("lastSplitFactor")));
        //security.setLastSplitDate((DateTime)parseValue(sourceMetrics.get("")));

        //security.setLastSplitDate((DateTime)parseValue(sourceMetrics.get("")));
        
        return security;
    }
    
    
        
    
    
    public static Map<String, Metric> getSourceMetrics() {
        return sourceMetrics;
    }

    public static void setSourceMetrics(Map<String, Metric> sourceMetrics) {
        YahooKeyStatisticsParser.sourceMetrics = sourceMetrics;
    }
    
}
