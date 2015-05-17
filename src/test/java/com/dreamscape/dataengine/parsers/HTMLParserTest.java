/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jared
 */
public class HTMLParserTest {
    
    public HTMLParserTest() {
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
     public void numberPatternRegexDoesNotMatchSuperscriptToken() {
         String textToTest = "Market Cap (intraday)<font size=\"-1\"><sup>5</sup></font>:</td><td class=\"yfnc_tabledata1\"><span id=\"yfs_j10_jkhy\">5.20B<";
         String numberPattern = HTMLParser.numberPattern;
         String leadingText = "Market Cap \\(intraday\\)";
         String matchAll = ".*>";
         String numberType = "number";
         Double marketCap = 5_200_000_000.0;
         
         Pattern p = Pattern.compile(leadingText + matchAll + numberPattern);
         Matcher m = p.matcher(textToTest);
         //assertTrue(m.matches());
         
         if(m.matches() == false)
         {
             assertEquals(p.pattern(), textToTest);
         }
         
         Double value = (Double)HTMLParser.extractValue(textToTest, numberType);
         assertEquals(marketCap, value, .1);
     }       
}     

