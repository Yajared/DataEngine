/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.parsers;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.utillibrary.connections.Domain;
import java.io.InputStream;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jared
 */
public class FinvizParserTest {
    
    public FinvizParserTest() {
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

//    @Test // extractTickerValue in FinvizParser must be made public to test
//    public void canExtractTickerValueTest() {
//        String testBlock = "onclick=\"window.location='quote.ashx?t=JKHB&";
//        
//        String tickerReturned = FinvizParser.extractTickerValue(testBlock);
//        
//        assertEquals("JKHY", tickerReturned);
//    }
//    
//    @Test // extractNextTicker in FinvizParser must be made public to test
//    public void canExtractNextTickerTest(){
//        InputStream is2 = getClass().getResourceAsStream("/FinvizExamplePage.html");
//       
//        String pageAsString = Domain.convertInputStreamContentToBlobString(is2);
//        
//        String finvizText = BlockReader.trimExcessContent(pageAsString, FinvizParser.getStartContent(), FinvizParser.getEndContent());
//        finvizText = BlockReader.removeWhiteSpace(finvizText);
//        BlockReader reader = new BlockReader(finvizText);
//        
//        FinvizParser fp = new FinvizParser();
//        fp.reader = reader;
//        
//        String ticker1 = fp.parseNextTicker();
//        String ticker2 = fp.parseNextTicker();
//        String ticker3 = fp.parseNextTicker();
//        String ticker4 = fp.parseNextTicker();
//        
//        assertEquals("JKHY", ticker1);
//        assertEquals("PAC", ticker2);
//        assertNull(ticker3);
//        assertNull(ticker4);
//    }
    @Test
    public void parseTotalTickersCountTest()
    {
        InputStream is = getClass().getResourceAsStream("/FinvizExamplePage2.html");       
        String pageAsString = Domain.convertInputStreamContentToBlobString(is);
        
        FinvizParser fp = new FinvizParser();
        int count = fp.parseTotalTickersCount(pageAsString);
        
        assertEquals(63, count);
    }
    
    @Test
    public void canParseTickerAndPrice()
    {
        InputStream is = getClass().getResourceAsStream("/FinvizExamplePage3.html");       
        String pageAsString = Domain.convertInputStreamContentToBlobString(is);
        
        FinvizParser fp = new FinvizParser();
        List<Prospect> prospects = fp.parseProspects(pageAsString);
        System.out.println("Company\t\tPrice");
        System.out.println("----------------------");
        for(Prospect prospect : prospects){
            assertNotNull(prospect.getSymbol());
            System.out.print(prospect.getSymbol() + "\t\t");
            assertNotNull(prospect.getPriceAtCreation());
            System.out.print(prospect.getPriceAtCreation() + "\n");
        }
        assertEquals(20, prospects.size());
    }
}
