/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.Ticker;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.dreamscape.dataengine.persistence.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.transaction.JDBCTransaction;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 */
public class ProspectDAOHibernateImpl implements ProspectDAO{
    @Override
    public Long create(Prospect prospect){
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
        
        System.out.println("Prospect symbol: " + prospect.getSymbol());
        Query q = session.createQuery("From Ticker t where t.symbol ='" + prospect.getSymbol() + "'" );//+ 
        //        " AND signals_and_features = " + prospect.getSignalAndFeatures());
        List<Prospect> resultList = q.list();

        prospect.setCreationDate(DateTime.now());
        
        if (resultList.size() < 1){
            session.close();
            TickerDAO tDAO = new TickerDAOHibernateImpl();
            tDAO.create(new Ticker(prospect.getSymbol()));
            
            session = HibernateUtil.getSessionFactory().openSession();
  
            session.beginTransaction();
        }
        //else
           // tickerID = resultList.get(0).getId();
        
        //prospect.setTickerID(tickerID);
        
        Long prospectID = (Long)session.save(prospect);
      
        session.getTransaction().commit();
 
        q = session.createQuery("From Prospect ");
                 
        resultList = (List<Prospect>)q.list();
        System.out.println("num of prospects:" + resultList.size());
        for (Prospect next : resultList) {
            System.out.println("Next Prospect: " + next);
        }
        return prospectID;
    }
    
    @Override
    public List<Prospect> getAllProspects(){
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
        
        Query q = session.createQuery("From Prospect ");
        
        List<Prospect> allProspects = (List<Prospect>)q.list();
        
        session.disconnect();
        
        return allProspects;
    }
    
    @Override
    public boolean update (Prospect prospect)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        JDBCTransaction transaction = (JDBCTransaction)session.beginTransaction();
        
        try{
            Query remDuplicates = session.createQuery("Delete Prospect p where p.symbol = '" + prospect.getSymbol() + "'");
            remDuplicates.executeUpdate();
        
            prospect.setUpdatedDate(DateTime.now());
            session.save(prospect);
            transaction.commit();
        }
        catch(HibernateException e)
        {
            System.err.println(e.getMessage());
            transaction.rollback();
        }
        finally
        {
            session.close();
        }
        return true;
    }
}
