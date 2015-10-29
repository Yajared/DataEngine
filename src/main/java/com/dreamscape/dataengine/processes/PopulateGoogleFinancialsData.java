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
import com.dreamscape.utillibrary.numeric.NumericUtils;
import com.dreamscape.dataengine.parsers.GoogleFinancialsParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author Jared
 */
public class PopulateGoogleFinancialsData {
    protected final static int qtrOverQtrGrowth = 0;
    protected final static int lastTwoQtrGrowth = 1;
    protected final static int lastNineMonthsGrowth = 2;
    protected final static int qtrVsLastYearGrowth = 3;
    protected final static int compositeGrowth = 4;
    
    protected final static int thisQtr = 0;
    protected final static int lastQtr = 1;
    protected final static int twoQtrsBack = 2;
    protected final static int nineMonthsBack = 3;
    protected final static int thisQtrLastYear = 4;
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
                
                for(int j = 500;j < /*rowOfTickers.length*/ 1000; j++){
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
        
        //columns.add("returnOnIC");
        //columns.add("magicFormulaRatio");
        columns.add("qtrOverQtrEarningsGrowth");
        columns.add("lastTwoQtrEarningsGrowth");
        columns.add("lastNineMonthsEarningsGrowth");
        columns.add("qtrVsLastYearEarningsGrowth");
        columns.add("compositeEarningsGrowth");
        
        //values.add(returnOnIC);
        //values.add(mfRatio);
        values.add(earningsGrowth[qtrOverQtrGrowth]);
        values.add(earningsGrowth[lastTwoQtrGrowth]);
        values.add(earningsGrowth[lastNineMonthsGrowth]);
        values.add(earningsGrowth[qtrVsLastYearGrowth]);
        values.add(earningsGrowth[compositeGrowth]);
                
        try{
            securityDAO.update(ticker, columns, values);
//            for(int i = 0; i < columns.size(); i++)
//                System.out.println("Column: " + columns.get(i) + " Value: " + values.get(i));
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
        Double[] earningsGrowth = new Double[4];
        Double total = 0.0;
        int nonNullValueCount = 0;
        for(int i = lastQtr; i < earnings.size(); i++)
        {
            earningsGrowth[i-1] = NumericUtils.calculateChange(earnings.get(i), earnings.get(thisQtr));
            if(earningsGrowth[i-1] != null)
            {
                total = total + earningsGrowth[i-1];
                nonNullValueCount++;
            }
        }
        
        Double average = total / nonNullValueCount;
        ArrayList<Double> earningsGrowthAsList = new ArrayList<>();
        earningsGrowthAsList.addAll((List<Double>)Arrays.asList(earningsGrowth));
        earningsGrowthAsList.add(average);
        earningsGrowth = (Double[])earningsGrowthAsList.toArray(new Double[5]);
        
        return earningsGrowth;
    }
}
