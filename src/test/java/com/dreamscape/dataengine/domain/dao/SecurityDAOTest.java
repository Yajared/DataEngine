/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.utillibrary.connections.Domain;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.parsers.YahooKeyStatisticsParser;
import java.io.IOException;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 *
 * @author Jared
 */
public class SecurityDAOTest {
    
    public SecurityDAOTest() {
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
    public void canInsertSecurityIntoDatabase() throws IOException{
       InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");

       //InputStream pageToParse = getClass().getResourceAsStream("/YahooKeyStatistics.html");

        Domain domain = new Domain("http://finance.yahoo.com/q/ks?s=JBLU+Key+Statistics");
        InputStream is2 = domain.getPageContentAsInputStream();
       
       String pageAsString = Domain.convertInputStreamContentToBlobString(is2);
       
       YahooKeyStatisticsParser p = new YahooKeyStatisticsParser(pageAsString);
       p.generatePatterns(is);                 


       Security security = p.parseSecurity("JBLU", pageAsString);
       
       SecurityDAO securityDAO = new SecurityDAOHibernateImpl();
       
       securityDAO.create(security);
    }
}
