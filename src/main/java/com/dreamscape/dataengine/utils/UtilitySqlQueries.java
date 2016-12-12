/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.utils;

import com.dreamscape.dataengine.domain.Feature;
import com.dreamscape.dataengine.utils.PerformanceTimeFrame;
import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.dao.ProspectDAO;
import com.dreamscape.dataengine.domain.dao.ProspectDAOHibernateImpl;
import com.dreamscape.dataengine.domain.dao.SecurityDAO;
import com.dreamscape.dataengine.domain.dao.SecurityDAOHibernateImpl;
import com.dreamscape.dataengine.persistence.HibernateUtil;
import com.dreamscape.utillibrary.exceptions.UnexpectedValueException;
import com.dreamscape.dataengine.domain.GreaterOrLess;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jared
 */
public class UtilitySqlQueries {
    private static Map<Long, List<Security>> portfolioCache;
    static{
        portfolioCache = new HashMap<Long, List<Security>>();
    }
    public static List<Prospect> getPercentileGreaterOrLessThanPerformance(PerformanceTimeFrame timeFrame, Long portfolioId, Float decimalPercentile, GreaterOrLess greaterThanOrLessThan)
    {
        List<Prospect> ret;
        String sign;
        if(greaterThanOrLessThan.equals(GreaterOrLess.GREATER_THAN))
            sign = ">";
        else if(greaterThanOrLessThan.equals(GreaterOrLess.LESS_THAN))
            sign = "<";
        else
        {
            System.err.println("Unexpected value encountered, expecting GreaterOrLess.GREATER_THAN or GreaterOrLess.LESS_THAN.");
            throw new UnexpectedValueException();
        }
        
        if(! (decimalPercentile > 0.0 && decimalPercentile < 1.0) )
        {
            System.err.println("Unexpected value encountered, expecting a value in the range: 0 < value < 1");
            throw new UnexpectedValueException();
        }
        
        
        if(! Prospect.validateTimeFrame(timeFrame))
        {
            System.err.println("Unexpected value encountered, expecting one of a number of Performance Intervals, found: " + timeFrame.toString());
            throw new UnexpectedValueException();
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            ProspectDAO dao = new ProspectDAOHibernateImpl();
            List<Prospect> allProspects = dao.getProspectsByPortfolioId(portfolioId);
            System.out.println("Prospect Count of prospects in portfolio with ID: " + portfolioId.toString() + " is " + String.valueOf(allProspects.size()));
            
            Comparator<Prospect> comparator = Prospect.getComparatorAndInitializeListForSorting(allProspects, timeFrame);
            allProspects.sort(comparator);
            
            // Let's check and make sure they're sorted...
            /*System.out.println("In ascending performance order...");
            for(int i = 0; i < allProspects.size(); i++)
                System.out.println(i + ") " + allProspects.get(i).getSymbol() + ": " + allProspects.get(i).getPerformanceForComparison());
            */
            
            Prospect prospectAtThreshold = allProspects.get(new Double(allProspects.size() * 1.0 * decimalPercentile).intValue());
            Float thresholdProspectPerformance = prospectAtThreshold.getPerformance(timeFrame);
            
            String query = "From Prospect p where p." + timeFrame.toString().toLowerCase() + " " + sign + " " + thresholdProspectPerformance.toString();
            session.beginTransaction();
            
            System.out.println("Running query: " + query);
            Query q = session.createQuery(query);
            
            List<Prospect> resultList = q.list();
            
            if (resultList.size() < 1)
            {
                ret = new ArrayList<>();
            }
            else
                ret = resultList;
        }
        catch(HibernateException e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally{
            session.disconnect();
            session.flush();
        } 
        return ret;
    }
    public static List<Security> getSecuritiesGreaterOrLessThanValue(Feature feature, GreaterOrLess greaterOrLessThan, Float value)
    {
        List<Security> securities = new ArrayList<>();
        
        try{
            if(greaterOrLessThan == null)
                throw new UnexpectedValueException(); 
        
            String sign = "<";
            if(greaterOrLessThan.equals(GreaterOrLess.GREATER_THAN))
                sign = ">";
        
            String query = "From Security s where " + feature + sign + value;
            SecurityDAO dao = new SecurityDAOHibernateImpl();
        
            securities = dao.retrieveSecuritiesWithQuery(query);
        }
        catch(UnexpectedValueException e){
            System.err.println(ArrayUtils.toString(e.getStackTrace()));
            System.err.println("The sign ( > or < ) is required to look up the securities.");
        }
        return securities;
    }
    public static float getPercentGreaterThanValue(Long portfolioId, Feature feature, Float value)
    {
        ProspectDAO pDao = new ProspectDAOHibernateImpl();
        List<Prospect> prospects = pDao.getProspectsByPortfolioId(portfolioId);
        
        StringBuilder formattedTickerList = new StringBuilder("");
        for(Prospect p : prospects)
            formattedTickerList.append("'").append(p.getSymbol()).append("',");

        String query = "From Security s where s.ticker in ( " + formattedTickerList.deleteCharAt(formattedTickerList.length() - 1).toString() + " ) and "
                + "s." + feature.toString() + " > " + value;
        SecurityDAO dao = new SecurityDAOHibernateImpl();
        
        return (dao.retrieveSecuritiesWithQuery(query).size() * 1.0f) / ((prospects.size()) * 1.0f);
    }
    public static List<Security> getSecuritiesByPortfolioId(Long portfolioId)
    {
        List<Security> securities = new ArrayList<>();
        
        List<Security> cachedSecurities = portfolioCache.get(portfolioId);
        // Try to get the List from the cache before using the DAOs
        if(cachedSecurities != null)
            securities.addAll(cachedSecurities); // We need to make a copy as caller could mutate the object
        else
        {
            ProspectDAO pDao = new ProspectDAOHibernateImpl();
            List<Prospect> prospects = pDao.getProspectsByPortfolioId(portfolioId);
        
            if(prospects.isEmpty())
                return securities;
            
            StringBuilder formattedTickerList = new StringBuilder("");
            for(Prospect p : prospects)
                formattedTickerList.append("'").append(p.getSymbol()).append("',");

            String query = "From Security s where s.ticker in ( " + formattedTickerList.deleteCharAt(formattedTickerList.length() - 1).toString() + " )";
            SecurityDAO dao = new SecurityDAOHibernateImpl();
            
            securities = dao.retrieveSecuritiesWithQuery(query);
            portfolioCache.put(portfolioId, securities);
        }
        return securities;
    }
    
    public static List<Security> getSecuritiesFromProspectList(List<Prospect> prospectList)
    {
        StringBuilder formattedTickerList = new StringBuilder("");
        
        for(Prospect p : prospectList)
            formattedTickerList.append("'").append(p.getSymbol()).append("',");

        String query = "From Security s where s.ticker in ( " + formattedTickerList.deleteCharAt(formattedTickerList.length() - 1).toString() + " )";
        SecurityDAO dao = new SecurityDAOHibernateImpl();

        return dao.retrieveSecuritiesWithQuery(query);
    }
    
    public static List<Prospect> getProspectsFromSecurities(List<Security> securities, Long portfolioId)
    {
        List<Prospect> prospects = new ArrayList<>();
        StringBuilder formattedTickerList = new StringBuilder("");
        
        if(securities.isEmpty())
            return prospects;
        
        for(Security s : securities)
            formattedTickerList.append("'").append(s.getTicker()).append("',");

        String query = "From Prospect p where p.portfolioId = " + portfolioId.toString() + " AND p.symbol in ( " + formattedTickerList.deleteCharAt(formattedTickerList.length() - 1).toString() + " )";
        
        ProspectDAO dao = new ProspectDAOHibernateImpl();
        prospects = dao.retrieveProspectswithQuery(query);
        
        return prospects;
    }
}
