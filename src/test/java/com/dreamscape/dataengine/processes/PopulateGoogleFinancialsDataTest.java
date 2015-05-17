/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.processes;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

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
    public void calculateReturnOnICDoesNotMakeDoubleNegativeDivisionPositive(){
        
    }
    
    @Test
    public void calculateMFIRatioDoesNotMakeDoubleNegativeDivisionPositive(){
        
    }
}
