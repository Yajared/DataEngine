/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jared
 */
public class SecurityTest {
    
    public SecurityTest() {
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
    public void populateHistoricalPricesTest(){
        Security security = new Security();
        security.setTicker("JBLU");
        //security.populateHistoricalPrices(50);
        //ArrayList<Double> historicalPrices = security.getHistoricalPrices();
        
        //for(int i = 0; i < historicalPrices.size(); i++){
        //    assertNotNull(historicalPrices.get(i));
        //}
    }
}
