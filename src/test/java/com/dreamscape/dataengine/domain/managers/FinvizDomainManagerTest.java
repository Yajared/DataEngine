/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.managers;

import com.dreamscape.dataengine.domain.Prospect;
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
public class FinvizDomainManagerTest {
    
    public FinvizDomainManagerTest() {
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
    public void generateRandomProspectsTest() {
        FinvizDomainManager fdm = new FinvizDomainManager();
        Prospect[] prospects = fdm.generateRandomProspects();
        
        if(prospects.length > 0)
        {
            System.out.println(prospects[0].getSignalAndFeatures());
            int i = 0;
            for(; i < prospects.length; i++){
                System.out.println(prospects[i].getSymbol());
            }
            System.out.println("Number of Companies Found: " + (i + 1));
        }
        else
            System.out.println("No prospects were found with the randomly generated signal and features.");
    }
}
