/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.managers;

import com.dreamscape.dataengine.domain.Prospect;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void generateRandomProspects() {
        List<Prospect> prospects;
        do{
            FinvizDomainManager fdm = new FinvizDomainManager();
            prospects = fdm.generateRandomProspects();

            if(prospects.size() > 0)
            {
                System.out.println(prospects.get(0).getSignalAndFeatures());
                int i = 0;
                for(Prospect prospect : prospects){
                    System.out.println(prospect.getSymbol());
                }
                System.out.println("Number of Companies Found: " + i);
            }
            else
                System.out.println("No prospects were found with the randomly generated signal and features.");
        } while (prospects.size() < 1);
    }
}
