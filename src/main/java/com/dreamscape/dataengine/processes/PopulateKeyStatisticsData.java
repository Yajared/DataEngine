/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.processes;

import com.dreamscape.dataengine.connections.YahooFinanceConnectionManager;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.dao.SecurityDAO;
import com.dreamscape.dataengine.domain.dao.SecurityDAOHibernateImpl;
import com.dreamscape.utillibrary.parsers.CSVParser;
import com.dreamscape.dataengine.parsers.YahooKeyStatisticsParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 *
 * @author Jared
 */
public class PopulateKeyStatisticsData {
    public void populateAll (File tickerNamesInCSVFormat) throws IOException{
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(tickerNamesInCSVFormat));
            ArrayList<String[]> tickers = CSVParser.convertToListOfStringArray(br, false);
            InputStream is = getClass().getResourceAsStream("/KeyStatisticsPattern.xml");
            YahooKeyStatisticsParser p = new YahooKeyStatisticsParser();
            p.generatePatterns(is);
        
            String ticker;
            boolean tickerParsed;
            ArrayList<String> unparseableTickers = new ArrayList();
            int numberOfRows = tickers.size();
            for(int i = 0; i<numberOfRows;i++){
                String[] rowOfTickers = tickers.get(i);
                
                for(int j = 0;j< rowOfTickers.length; j++){
                    System.out.println(i + j);
                    ticker = rowOfTickers[j].replace('.', '-');
                    tickerParsed = populate(ticker, p);
                    if(tickerParsed == false){
                        unparseableTickers.add(ticker);
                    }
                }
            }
            for(String unparsedTicker : unparseableTickers){
                System.out.println(unparsedTicker);
            }
        }
        catch(FileNotFoundException e){
            System.err.println("Could not find a readable CSV File.");
            throw e;
        }
        catch(IOException e){
            System.err.println("Error reading from File.");
            throw e;
        }
    }
    
    private boolean populate(String ticker, YahooKeyStatisticsParser parser) throws IOException{
        YahooFinanceConnectionManager connectionManager = new YahooFinanceConnectionManager();
        String pageAsString = connectionManager.downloadKeyStatistics(ticker);
        
        if(pageAsString != null){
            Security security = parser.parseSecurity(ticker, pageAsString);
            
            if(security != null){
                SecurityDAO securityDAO = new SecurityDAOHibernateImpl();
       
                securityDAO.create(security);
                return true;
            }
        }
        return false;
    }
}
