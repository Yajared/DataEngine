/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Portfolio;
import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.Ticker;
import com.dreamscape.dataengine.persistence.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 */
public class PortfolioDAOHibernateImpl implements PortfolioDAO{
    
    @Override
    public Long create(Portfolio portfolio)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Long primeId = portfolio.getPrimeId();
        if(primeId != -999L)
        {
            Query q = session.createQuery("From Portfolio p where p.primeId='" + primeId + "'");
            if(q.list().isEmpty())
            {
                session.save(portfolio);
                session.getTransaction().commit();
                session.close();
                
                ProspectDAO dao = new ProspectDAOHibernateImpl();
                dao.createProspects(portfolio.getProspects().toArray(new Prospect[1]));
            }
        }
        
        
        return primeId;
    }
    @Override
    public Portfolio retrieve(String query)
    {
        Portfolio ret = null;
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query q = session.createQuery(query);
        if(! q.list().isEmpty())
        {
            if(q.list().size() > 1)
            {
                System.err.println("More than one portfolio found with query:\n" + query + "\nReturning first portfolio!");
            }
            ret = (Portfolio)q.list().get(0);
        }
        
        session.close();
        return ret;
    }
    
    @Override
    public List<Portfolio> getAllPortfolios()
    {
        return new ArrayList<>();
    }
    @Override
    public boolean update(Portfolio portfolio)
    {
        return true;
    }
    
}
