/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.processes;

import com.dreamscape.dataengine.connections.GoogleFinancialsConnectionManager;
import com.dreamscape.dataengine.domain.Financials;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.dao.SecurityDAO;
import com.dreamscape.dataengine.domain.dao.SecurityDAOHibernateImpl;
import com.dreamscape.utillibrary.parsers.CSVParser;
import com.dreamscape.dataengine.parsers.GoogleFinancialsParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared
 */
public class PopulateGoogleFinancialsData {
    public void populateAll (File tickerNamesInCSVFormat) throws IOException{
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(tickerNamesInCSVFormat));
            ArrayList<String[]> tickers = CSVParser.convertToListOfStringArray(br, false);
            
            InputStream is = getClass().getResourceAsStream("/GoogleFinancialsPattern.xml");
            GoogleFinancialsParser p = new GoogleFinancialsParser();
            p.initializePatterns(is);
        
            String ticker = "";
            boolean tickerParsed = false;
            ArrayList<String> unparseableTickers = new ArrayList();
            int numberOfRows = tickers.size();
            for(int i = 0 ; i < numberOfRows;i++){
                String[] rowOfTickers = tickers.get(i);
                
                for(int j = 2499;j < rowOfTickers.length; j++){
                    System.out.println(i + j);
                    ticker = rowOfTickers[j];
                    tickerParsed = populateMFI(ticker, p);
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
    
    private boolean populateMFI(String ticker, GoogleFinancialsParser parser) throws IOException{
        GoogleFinancialsConnectionManager connectionManager = new GoogleFinancialsConnectionManager();
        String pageAsString = connectionManager.downloadFinancialsData(ticker);
        
        if(pageAsString != null){
            Financials financials = parser.parseFinancials(ticker, pageAsString);
            
            if(financials != null){
                SecurityDAO securityDAO = new SecurityDAOHibernateImpl();
       
                List<String> columns = new ArrayList<>();
                
                List<Object> values = new ArrayList<>();
                
                Double returnOnIC = calculateReturnOnIC(financials);
                
                Security s = securityDAO.retrieve(ticker);
                
                Double mfRatio = calculateMFRatio(returnOnIC, s.geteVToEBITDA());
                
                columns.add("returnOnIC");
                columns.add("magicFormulaRatio");
                
                values.add(returnOnIC);
                values.add(mfRatio);
                
                securityDAO.update(ticker, columns, values);
                return true;
            }
        }
        return false;
    }
    
    private static Double calculateReturnOnIC(Financials financials){
        Double ROIC = 0.0;
        
        Double investedCapital = financials.getTotalAssets() - financials.getCashAndSTInvestments() - financials.getLongTermInvestments() - financials.getOtherLongTermAssets() - financials.getTotalCurrentLiabilities();
        investedCapital -= financials.getGoodwill();
        investedCapital -= financials.getIntangibleAssets();
        
        Double operatingIncome = financials.getOperatingIncome();
        if(investedCapital.equals(new Double(0.0)) == false){
            ROIC = financials.getOperatingIncome() / investedCapital;
            if(operatingIncome < 0 && investedCapital < 0)
                ROIC *= -1;
        }
        return ROIC;
    }
    
    private static Double calculateMFRatio(Double returnOnIC, Double enterpriseValueToEbitda){
        Double MFRatio = 0.0;
        if(enterpriseValueToEbitda != null && enterpriseValueToEbitda.equals(new Double(0.0)) == false){
            
            MFRatio = returnOnIC / enterpriseValueToEbitda;
            if(returnOnIC < 0 && enterpriseValueToEbitda < 0)
                MFRatio *= -1;
        }
        return MFRatio;
    }
}
