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
import org.hibernate.HibernateException;

/**
 *
 * @author Jared
 */
public class PopulateGoogleFinancialsData {
    final static int qtrOverQtrGrowth = 0;
    final static int lastTwoQtrGrowth = 1;
    final static int qtrVsLastYearGrowth = 2;
    
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
                
                for(int j = 0;j < rowOfTickers.length; j++){
                    System.out.println(i + j);
                    ticker = rowOfTickers[j];
                    
                    GoogleFinancialsConnectionManager connectionManager = new GoogleFinancialsConnectionManager();
                    String pageAsString = connectionManager.downloadFinancialsData(ticker);
        
                    if(pageAsString != null){
                        Financials financials = p.parseFinancials(ticker, pageAsString);
            
                        if(financials != null){
                            
                            tickerParsed = updateCompanyFinancials(ticker, financials);
                            if(tickerParsed == false){
                                unparseableTickers.add(ticker);
                            }
                        }
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
    
    private boolean updateCompanyFinancials(String ticker, Financials financials){    
        SecurityDAO securityDAO = new SecurityDAOHibernateImpl();
        Security s = securityDAO.retrieve(ticker);
        
        Double returnOnIC = calculateReturnOnIC(financials);
        Double mfRatio = calculateMFRatio(returnOnIC, s.getEvToEBITDA());
        Double[] earningsGrowth = calculateEarningsGrowth(financials.getQuarterlyEarnings());
        
        List<String> columns = new ArrayList<>();        
        List<Double> values = new ArrayList<>();
        
        columns.add("returnOnIC");
        columns.add("magicFormulaRatio");
        columns.add("qtrOverQtrEarningsGrowth");
        columns.add("lastTwoQtrEarningsGrowth");
        columns.add("qtrVsLastYearEarningsGrowth");
        
        values.add(returnOnIC);
        values.add(mfRatio);
        values.add(earningsGrowth[qtrOverQtrGrowth]);
        values.add(earningsGrowth[lastTwoQtrGrowth]);
        values.add(earningsGrowth[qtrVsLastYearGrowth]);
        
        try{
            //securityDAO.update(ticker, columns, values);
            for(int i = 0; i < columns.size(); i++)
                System.out.println("Column: " + columns.get(i) + " Value: " + values.get(i));
        }
        catch(HibernateException e)
        {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
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
    
    protected static Double[] calculateEarningsGrowth(List<Double> earnings)
    {
        Double[] earningsGrowth = new Double[3];
        if(earnings.get(0) != null)
        {
            Double divisor = earnings.get(0);
            if(divisor < 0) // negative Divisor so we'll need to make it positive
                divisor *= -1;
            
            Double qtrOverQtr = earnings.get(1) != null ? 
                ( earnings.get(0) - earnings.get(1) ) / divisor : null;
            Double lastTwoQtr = earnings.get(1) != null && earnings.get(2) != null ?
                ( ( earnings.get(0) - earnings.get(1) ) + ( earnings.get(1) - earnings.get(2) ) ) / divisor: null;
            Double qtrVsLastYear = earnings.get(4) != null ?
                ( earnings.get(0) - earnings.get(4) ) / divisor : null;
            
            earningsGrowth[qtrOverQtrGrowth] = qtrOverQtr;
            earningsGrowth[lastTwoQtrGrowth] = lastTwoQtr;
            earningsGrowth[qtrVsLastYearGrowth] = qtrVsLastYear;
        }

        return earningsGrowth;
    }
}
