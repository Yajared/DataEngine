/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.processes;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static com.dreamscape.dataengine.processes.PopulateGoogleFinancialsData.*;

/**
 *
 * @author Jared
 */
public class PopulateGoogleFinancialsDataTest {
    
    public PopulateGoogleFinancialsDataTest() {
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
    
    @Ignore
    public void canPopulateCompaniesInFile(){
        PopulateGoogleFinancialsData populate = new PopulateGoogleFinancialsData();
        File file = new File("C:\\companies.csv");
        try{
            populate.populateAll(file);
        }
        catch(IOException e){
            System.err.println("IO Exception when trying to populate all companies' data!");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    @Test
    public void calculateEarningsGrowthQoQMRQLargerThanLastQuarterBothPositive(){
        Double mrq = 7.0;
        Double prevQtr = 5.0;
        
        Double[] qtrEarnings = new Double[5];
        
        qtrEarnings[thisQtr] = mrq;
        qtrEarnings[lastQtr] = prevQtr;

        Double[] earningsGrowth = PopulateGoogleFinancialsData.calculateEarningsGrowth(Arrays.asList(qtrEarnings));
        
        assertEquals(0.4, earningsGrowth[qtrOverQtrGrowth], .0001);
    }
    
    @Test
    public void calculateEarningsGrowthQoQMRQSmallerThanLastQuarterBothPositive(){
        Double mrq = 2.0;
        Double prevQtr = 6.0;
        
        Double[] qtrEarnings = new Double[5];
        
        qtrEarnings[thisQtr] = mrq;
        qtrEarnings[lastQtr] = prevQtr;

        Double[] earningsGrowth = PopulateGoogleFinancialsData.calculateEarningsGrowth(Arrays.asList(qtrEarnings));
        
        assertEquals(-0.6667, earningsGrowth[qtrOverQtrGrowth], .0001);
    }
    
    @Test
    public void calculateEarningsGrowthQoQMRQLargerThanLastQuarterLastQtrNeg(){
        Double mrq = 2.0;
        Double prevQtr = -4.0;
        
        Double[] qtrEarnings = new Double[5];
        
        qtrEarnings[thisQtr] = mrq;
        qtrEarnings[lastQtr] = prevQtr;

        Double[] earningsGrowth = PopulateGoogleFinancialsData.calculateEarningsGrowth(Arrays.asList(qtrEarnings));
        
        assertEquals(1.5, earningsGrowth[qtrOverQtrGrowth], .0001);
    }
    
    @Test
    public void calculateEarningsGrowthQoQMRQSmallerThanLastQuarterMRQNeg(){
        Double mrq = -3.0;
        Double prevQtr = 5.0;
        
        Double[] qtrEarnings = new Double[5];
        
        qtrEarnings[thisQtr] = mrq;
        qtrEarnings[lastQtr] = prevQtr;

        Double[] earningsGrowth = PopulateGoogleFinancialsData.calculateEarningsGrowth(Arrays.asList(qtrEarnings));
        
        assertEquals(-1.6, earningsGrowth[qtrOverQtrGrowth], .0001);
    }
    
    @Test
    public void calculateEarningsGrowthQoQMRQLargerThanLastQuarterBothNeg(){
        Double mrq = -3.0;
        Double prevQtr = -5.0;
        
        Double[] qtrEarnings = new Double[5];
        
        qtrEarnings[thisQtr] = mrq;
        qtrEarnings[lastQtr] = prevQtr;

        Double[] earningsGrowth = PopulateGoogleFinancialsData.calculateEarningsGrowth(Arrays.asList(qtrEarnings));
        
        assertEquals(.4, earningsGrowth[qtrOverQtrGrowth], .0001);
    }
    
    @Test
    public void calculateEarningsGrowthQoQMRQSmallerThanLastQuarterBothNeg(){
        Double mrq = -7.0;
        Double prevQtr = -5.0;
        
        Double[] qtrEarnings = new Double[5];
        
        qtrEarnings[thisQtr] = mrq;
        qtrEarnings[lastQtr] = prevQtr;

        Double[] earningsGrowth = PopulateGoogleFinancialsData.calculateEarningsGrowth(Arrays.asList(qtrEarnings));
        
        assertEquals(-.4, earningsGrowth[qtrOverQtrGrowth], .0001);
    }
}
