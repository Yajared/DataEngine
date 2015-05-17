/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.managers.FinvizDomainManager;
import java.io.IOException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
public class ProspectDAOTest {
    
    public ProspectDAOTest() {
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

    @Ignore //Test is failing, may need to revisit
    public void canInsertSecurityIntoDatabase() throws IOException{
       
       FinvizDomainManager fdm = new FinvizDomainManager();
       Prospect[] prospects = fdm.generateRandomProspects();
                       
       for (Prospect prospect : prospects)
       {
           ProspectDAO dao = new ProspectDAOHibernateImpl();
           dao.create(prospect);
       }
    }
    @Test
    public void canConvertCurrentDateToDateTime()
    {
        String dateTime = DateTime.now().toString();
        // Format for input
        
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        // Parsing the date
        
        DateTime jodatime = dtf.parseDateTime(dateTime);
        // Format for output
        
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            
        System.out.println(dtfOut.print(jodatime));
    }
}
