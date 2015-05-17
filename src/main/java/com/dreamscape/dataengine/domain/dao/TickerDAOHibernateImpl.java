/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.Ticker;
import com.dreamscape.dataengine.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jared
 */
public class TickerDAOHibernateImpl implements TickerDAO{
    @Override
    public Long create(Ticker ticker){
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
        
        Long tickerID = (Long)session.save(ticker);
        
        session.getTransaction().commit();
        
        return tickerID;
    }
    @Override
    public Ticker retrieve(String symbol){
        Ticker ticker = new Ticker();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Query retrieveTicker = session.createQuery("from Ticker t where t.symbol = '" + symbol + "'");
        List<Ticker> tickers = retrieveTicker.list();
        
        if(tickers.size() > 0)
            ticker = tickers.get(0);
        
        return ticker;
    }
    
    @Override
    public void update(String symbol, List <String> columnsToUpdate, List <Object> updatedValues){
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        
//        session.beginTransaction();
//        String columnsAndValues = "";
//        int numColumns = columnsToUpdate.size();
//        for(int i = 0; i < numColumns; i++){
//            columnsAndValues += columnsToUpdate.get(i) + "='" + ((Double)updatedValues.get(i)).toString() + "',";
//        }
//        columnsAndValues = columnsAndValues.substring(0, columnsAndValues.length() - 1);
//        Query updateSecurity = session.createQuery("Update Security s set " + columnsAndValues + " where s.ticker = '" + ticker + "'");
//        
//        updateSecurity.executeUpdate();
//        
//        session.getTransaction().commit();
    }
    public void delete(String symbol){
        
    }
}
