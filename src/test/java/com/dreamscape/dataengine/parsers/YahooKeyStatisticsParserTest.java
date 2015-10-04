/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.domain.Metric;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.utillibrary.connections.Domain;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;


/**
 *
 * @author Jared
 */
public class YahooKeyStatisticsParserTest {
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

     
     @Ignore
     public void canParseXMLConfigurationFile() throws IOException {
 
         InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

         //HashMap<String, Pattern> map = (HashMap)KeyStatisticsKeyStatisticsParser.generatePatterns(is);

         YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
         p.generatePatterns(is);
         HashMap<String, Metric> map = (HashMap<String, Metric>)YahooKeyStatisticsParser.getSourceMetrics();
         
         assertEquals("S&P 500 52-Week Change", ((Metric)map.get("sp500fiftyTwoWeekChange")).getPattern().pattern());
     }
     
     @Test
     public void canParseNumberValue() {
         try{
            InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

            YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
            p.generatePatterns(is);
         
            HashMap<String, Metric> map = (HashMap<String, Metric>)YahooKeyStatisticsParser.getSourceMetrics();
                 
         
            String expression = "Market Cap (intraday)<font size=\"-1\"><sup>5</sup></font>:</td><td class=\"yfnc_tabledata1\"><span id=\"yfs_j10_jblu\">3.34B<";
            Pattern pattern = map.get("marketCap").getPattern();
            Matcher m = pattern.matcher(expression);
       
            //System.out.println("Expression: " + expression);
            //System.out.println("Pattern to match: " + pattern.pattern());
            assertTrue(m.matches());
         }
         catch(IOException e){
             System.err.println("Threw IO Exception!");
             e.printStackTrace();
             assertTrue(false);
         }
     }
     
     @Test
     public void canParseDecimalValue() throws IOException {
         InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

         YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
         p.generatePatterns(is);
         
         HashMap<String, Metric> map = (HashMap<String, Metric>)YahooKeyStatisticsParser.getSourceMetrics();
                 
         
         String expression = "Beta:</td><td class=\"yfnc_tabledata1\">0.73<";
         Pattern pattern = map.get("beta").getPattern();
         Matcher m = pattern.matcher(expression);
       
         //System.out.println("Expression: " + expression);
         //System.out.println("Pattern to match: " + pattern.pattern());
         assertTrue(m.matches());
         
     }
     
     @Test
     public void canParsePercentValue() throws IOException {
         InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

         YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
         p.generatePatterns(is);
         
         HashMap<String, Metric> map = (HashMap<String, Metric>)YahooKeyStatisticsParser.getSourceMetrics();
                 
         
         String expression = "Operating Margin (ttm):</td><td class=\"yfnc_tabledata1\">7.79%<";
         Pattern pattern = map.get("operatingMargin").getPattern();
         Matcher m = pattern.matcher(expression);
       
         //System.out.println("Expression: " + expression);
        // System.out.println("Pattern to match: " + pattern.pattern());
         assertTrue(m.matches());
         
         
     }
     @Test
     public void canParseRatioValue() throws IOException
     {
         InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

         YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
         p.generatePatterns(is);
         
         HashMap<String, Metric> map = (HashMap<String, Metric>)YahooKeyStatisticsParser.getSourceMetrics();
                 
         
         String expression = "Last Split Factor (new per old)<font size=\"-1\"><sup>2</sup></font>:</td><td class=\"yfnc_tabledata1\">3:2<";
         Pattern pattern = map.get("lastSplitFactor").getPattern();
         Matcher m = pattern.matcher(expression);
       
         //System.out.println("Expression: " + expression);
         //System.out.println("Pattern to match: " + pattern.pattern());
         assertTrue(m.matches());
         
         
         
     }
     
     @Test
     public void canParseDateValue() throws IOException
     {
         InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

         YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
         p.generatePatterns(is);
         
         HashMap<String, Metric> map = (HashMap<String, Metric>)YahooKeyStatisticsParser.getSourceMetrics();
                 
         
         String expression = "Last Split Date<font size=\"-1\"><sup>3</sup></font>:</td><td class=\"yfnc_tabledata1\">Dec 27, 2005<";
         Pattern pattern = map.get("lastSplitDate").getPattern();
         Matcher m = pattern.matcher(expression);
       
         //System.out.println("Expression: " + expression);
        // System.out.println("Pattern to match: " + pattern.pattern());
         assertTrue(m.matches());
     }
     
     @Test
     public void canParseSecurity() throws IOException {
         InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");
         
         InputStream is2 = getClass().getResourceAsStream("/YahooKeyStatistics.html");
       
         String pageAsString = Domain.convertInputStreamContentToBlobString(is2);
         
         YahooKeyStatisticsParser p = new YahooKeyStatisticsParser(pageAsString);
         p.generatePatterns(is);                 
         
         Security security = p.parseSecurity("JBLU", pageAsString);
         
         Double marketCap = new Double(3340000000.0);
         assertEquals(marketCap, security.getMarketCap());
         
         Double enterpriseValue = new Double(4940000000.0);
         assertEquals(enterpriseValue, security.getEnterpriseValue());
         
         Double trailingPE = new Double(23.68);
         assertEquals(trailingPE, security.getTrailingPE(), .01);
         
         Double forwardPE = new Double(12.46);
         assertEquals(forwardPE, security.getForwardPE(), .01);
         
         Double pegRatio = new Double(0.70);
         assertEquals(pegRatio, security.getPegRatio(), .01);
         
         Double priceSales = new Double(0.58);
         assertEquals(priceSales, security.getPriceSales(), .01);
         
         Double priceBook = new Double(1.40);
         assertEquals(priceBook, security.getPriceBook(), .01);
         
         Double eVToRevenue = new Double(0.87);
         assertEquals(eVToRevenue, security.getEvToRevenue(), .01);
         
         Double eVToEBITDA = new Double(6.64);
         assertEquals(eVToEBITDA, security.getEvToEBITDA(), .01);
         
         Double profitMargin = new Double(.0623);
         assertEquals(profitMargin, security.getProfitMargin(), .0001);
         
         Double operatingMargin = new Double(.0779);
         assertEquals(operatingMargin, security.getOperatingMargin(), .0001);
         
         Double returnOnAssets = new Double(.0366);
         assertEquals(returnOnAssets, security.getReturnOnAssets(), .0001);
         
         Double returnOnEquity = new Double(.1658);
         assertEquals(returnOnEquity, security.getReturnOnEquity(), .0001);
         
         Double revenue = new Double(5650000000L);
         assertEquals(revenue, security.getRevenue());
         
         Double revenuePerShare = new Double(19.5);
         assertEquals(revenuePerShare, security.getRevenuePerShare(), .01);
         
         Double qtrRevenueGrowth = new Double(.118);
         assertEquals(qtrRevenueGrowth, security.getQtrRevenueGrowth(), .001);
         
         Double grossProfit = new Double(2680000000L);
         assertEquals(grossProfit, security.getGrossProfit());
         
         Double ebitda = new Double(743000000);
         assertEquals(ebitda, security.getEbitda());
         
         Double netIncomeAvlToCommonShares = new Double(352000000);
         assertEquals(netIncomeAvlToCommonShares, security.getNetIncomeAvlToCommonShares());
         
         Double dilutedEPS = new Double(0.48);
         assertEquals(dilutedEPS, security.getDilutedEPS(), .01);
         
         Double qtrEarningsGrowth = new Double(5.3889);
         assertEquals(qtrEarningsGrowth, security.getQtrEarningsGrowth(), .0001);
         
         Double totalCash = new Double(797000000);
         assertEquals(totalCash, security.getTotalCash());
         
         Double totalCashPerShare = new Double(2.73);
         assertEquals(totalCashPerShare, security.getTotalCashPerShare(), .01);
         
         Double totalDebt = new Double(2390000000L);
         assertEquals(totalDebt, security.getTotalDebt());
         
         Double totalDebtToEquity = new Double(103.15);
         assertEquals(totalDebtToEquity, security.getTotalDebtToEquity(), .01);
         
         Double currentRatio = new Double(0.65);
         assertEquals(currentRatio, security.getCurrentRatio(), .01);
         
         Double bookValuePerShare = new Double(7.93);
         assertEquals(bookValuePerShare, security.getBookValuePerShare(), .01);
         
         Double operatingCashFlow = new Double(897000000);
         assertEquals(operatingCashFlow, security.getOperatingCashFlow());
         
         Double leveredCashFlow = new Double(-41120000);
         assertEquals(leveredCashFlow, security.getLeveredCashFlow());
         
         Double beta = new Double(0.73);
         assertEquals(beta, security.getBeta(), .01);
         
         Double fiftyTwoWeekChange = new Double(0.8019);
         assertEquals(fiftyTwoWeekChange, security.getFiftyTwoWeekChange(), .0001);
         
         Double fiftyTwoWeekHigh = new Double(11.59);
         assertEquals(fiftyTwoWeekHigh, security.getFiftyTwoWeekHigh(), .01);
         
         Double fiftyTwoWeekLow = new Double(6.04);
         assertEquals(fiftyTwoWeekLow, security.getFiftyTwoWeekLow(), .01);
         
         Double fiftyDayMovingAvg = new Double(10.84);
         assertEquals(fiftyDayMovingAvg, security.getFiftyDayMovingAvg(), .01);
         
         Double twoHundredDayMovingAvg = new Double(9.39);
         assertEquals(twoHundredDayMovingAvg, security.getTwoHundredDayMovingAvg(), .01);
         
         Double avgVolThreeMon = new Double(8029500);
         assertEquals(avgVolThreeMon, security.getAvgVolThreeMon());
         
         Double avgVolTenDay = new Double(8746440);
         assertEquals(avgVolTenDay, security.getAvgVolTenDay());
         
         Double sharesOutstanding = new Double(291860000);
         assertEquals(sharesOutstanding, security.getSharesOutstanding());
         
         Double floatShares = new Double(242320000);
         assertEquals(floatShares, security.getFloatShares());
         
         Double percentHeldByInsiders = new Double(0.2989);
         assertEquals(percentHeldByInsiders, security.getPercentHeldByInsiders(), .0001);
         
         Double percentHeldByInstitutions = new Double(0.8710);
         assertEquals(percentHeldByInstitutions, security.getPercentHeldByInstitutions(), .0001);
         
         Double sharesShort = new Double(49880000);
         assertEquals(sharesShort, security.getSharesShort());
         
         Double shortRatio = new Double(7.20);
         assertEquals(shortRatio, security.getShortRatio(), .01);
         
         Double shortPercentOfFloat = new Double(0.2350);
         assertEquals(shortPercentOfFloat, security.getShortPercentOfFloat(), .0001);
         
         Double priorMonthSharesShort = new Double(44070000);
         assertEquals(priorMonthSharesShort, security.getPriorMonthSharesShort());
         
         Double forwardAnnualDividendRate = null;
         assertEquals(forwardAnnualDividendRate, security.getForwardAnnualDividendRate());
         
         Double forwardAnnualDividendYield = null;
         assertEquals(forwardAnnualDividendYield, security.getForwardAnnualDividendYield());
         
         Double payoutRatio = null;
         assertEquals(payoutRatio, security.getPayoutRatio());
         
         //DateTime dividendDate = new DateTime(Dat);
         //assertEquals(dividendDate.getDayOfYear(), security.getDividendDate());
         
         //DateTime exDividendDate = new DateTime();
         //assertEquals(exDividendDate.getDayOfYear(), security.getExDividendDate());
         
         Double lastSplitFactor = new Double(3.0/2.0);
         assertEquals(lastSplitFactor, security.getLastSplitFactor());
         
         //DateTime d = new DateTime("2005-12-27T21:39:45.618-08:00");
         //assertEquals(d.getDayOfYear(), security.getLastSplitDate().getDayOfYear());
     }
}
