/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.Ticker;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.dreamscape.dataengine.persistence.HibernateUtil;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 */
public class SecurityDAOHibernateImpl implements SecurityDAO {
    @Override
    public Long create(Security security){
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
 
        //session.saveOrUpdate(security);
        
        Query remDuplicates = session.createQuery("Delete Security s where s.ticker = '" + security.getTicker() + "'");
        remDuplicates.executeUpdate();
        
        session.save(security);
        
        session.getTransaction().commit();
        
        return new Long(5L);
    }
    @Override
    public Security retrieve(String ticker){
        Security security = new Security();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query retrieveSecurity = session.createQuery("from Security s where s.ticker = '" + ticker + "'");
        List<Security> securities = retrieveSecurity.list();
        
        if(securities.size() > 0)
            security = securities.get(0);
        
        session.close();
        return security;
    }
    
    @Override
    public List<Security> retrieveSecuritiesWithQuery(String query)
    {
        List<Security> securities = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
        
            Query retrieveSecurity = session.createQuery(query);
            securities = retrieveSecurity.list();
        }
        catch(HibernateException e)
        {
            throw e;
        }
        finally{
            session.close();
        }
        return securities;
    }
    
    @Override
    public void update(String ticker, List <String> columnsToUpdate, List <Double> updatedValues){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            session.beginTransaction();
            String columnsAndValues = "";
            int numColumns = columnsToUpdate.size();
            for(int i = 0; i < numColumns; i++){
                if(updatedValues.get(i) != null)
                    columnsAndValues += columnsToUpdate.get(i) + "='" + (updatedValues.get(i)) + "',";
            }
            
            if(columnsAndValues.length() > 0)
            {
                if(columnsAndValues.charAt(columnsAndValues.length() - 1) == ',')
                    columnsAndValues = columnsAndValues.substring(0, columnsAndValues.length() - 1); //remove trailing comma
                
                Query updateSecurity = session.createQuery("Update Security s set " + columnsAndValues + " where s.ticker = '" + ticker + "'");

                updateSecurity.executeUpdate();

                session.getTransaction().commit();
            }
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
    }
    public void delete(Long ID){
        
    }
    //public List<Security> retrieveByTickersBatched(List<String> tickers)
    //{
    //    
    //}
}
