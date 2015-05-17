/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.utillibrary.connections.Domain;
import com.dreamscape.dataengine.domain.Financials;
import java.io.IOException;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jared
 */
public class GoogleFinancialsParserTest {
    
    public GoogleFinancialsParserTest() {
    }
    
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

    @Test
    public void canParseFinancials() throws IOException{
       InputStream is = getClass().getResourceAsStream("/GoogleFinancialsPattern.xml");

       InputStream pageToParse = getClass().getResourceAsStream("/AUYGoogleFinancials.html");
       
       String pageAsString = Domain.convertInputStreamContentToBlobString(pageToParse);
       
       System.out.println("Index of Start content: " + pageAsString.indexOf(">Income Statement</b></b></a>"));
       System.out.println("Index of End content: " + pageAsString.indexOf("Google Finance Beta available in: <a href"));
       GoogleFinancialsParser p = new GoogleFinancialsParser();
       
       p.initializePatterns(is);                 

       Financials financials = p.parseFinancials("AUY", pageAsString);
       
       final Double oneMillion = new Double(1_000_000.0); 
       
       Double operatingIncome = new Double(-672.63 * oneMillion); //436
       Double cashAndSTInvestments = new Double(264.55 * oneMillion); //1230
       Double goodwill = new Double(0.0); //1320
       Double intangibleAssets = new Double(377.07 * oneMillion); //1329
       Double longTermInvestments = new Double(74.31 * oneMillion); //1338
       Double otherLongTermAssets = new Double(251.94 * oneMillion); //1347
       Double totalAssets = new Double(12_784.66 * oneMillion); //1356
       Double totalCurrentLiabilities = new Double(643.05 * oneMillion); //1410
        
       assertEquals(operatingIncome, financials.getOperatingIncome(), 0.1);
       assertEquals(cashAndSTInvestments, financials.getCashAndSTInvestments(), 0.1);
       assertEquals(goodwill, financials.getGoodwill(), 0.1);
       assertEquals(intangibleAssets, financials.getIntangibleAssets(), 0.1);
       assertEquals(longTermInvestments, financials.getLongTermInvestments(), 0.1);
       assertEquals(otherLongTermAssets, financials.getOtherLongTermAssets(), 0.1);
       assertEquals(totalAssets, financials.getTotalAssets(), 0.1);
       assertEquals(totalCurrentLiabilities, financials.getTotalCurrentLiabilities(), 0.1);
       
    }
}
