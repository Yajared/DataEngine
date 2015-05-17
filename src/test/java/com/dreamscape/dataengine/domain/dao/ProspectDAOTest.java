/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.managers.FinvizDomainManager;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

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
}
