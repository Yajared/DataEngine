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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author Jared
 */
public class ProspectDAOHibernateImpl implements ProspectDAO{
    @Override
    public Long create(Prospect prospect){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long prospectID = null;
        try{
            session.beginTransaction();
            System.out.println("Prospect symbol: " + prospect.getSymbol());
            Query q = session.createQuery("From Ticker t where t.symbol ='" + prospect.getSymbol() + "'" );//+ 
        //        " AND signals_and_features = " + prospect.getSignalAndFeatures());
            List<Prospect> resultList = q.list();
            
            if(prospect.getCreationDate() == null)
                prospect.setCreationDate(DateTime.now());
        
            if (resultList.size() < 1){
                session.close();
                TickerDAO tDAO = new TickerDAOHibernateImpl();
                tDAO.create(new Ticker(prospect.getSymbol()));
            
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
            }
            //else
            //    tickerID = resultList.get(0).getId();
        
        //prospect.setTickerID(tickerID);
        
            prospectID = (Long)session.save(prospect);
      
            session.getTransaction().commit();
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
        //q = session.createQuery("From Prospect ");
                 
        //resultList = (List<Prospect>)q.list();
        //System.out.println("num of prospects:" + resultList.size());
        //for (Prospect next : resultList) {
        //    System.out.println("Next Prospect: " + next);
        //}
        return prospectID;
    }
    
    @Override
    public List<Prospect> getAllProspects(){
        List<Prospect> allProspects = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
        try{
            Query q = session.createQuery("From Prospect ");
        
            allProspects = (List<Prospect>)q.list();
        }
        catch(HibernateException e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally{
            session.flush();
            session.disconnect();
        }
        return allProspects;
    }
    
    @Override
    public List<Prospect>getProspectsByPortfolioId(Long portfolioId)
    {
        List<Prospect> allProspects = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
  
        session.beginTransaction();
        try{
            Query q = session.createQuery("From Prospect p where p.portfolioId='" + portfolioId + "'");
            allProspects = (List<Prospect>)q.list();
        }
        catch(HibernateException e)
        {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally{
            session.flush();
            session.disconnect();
        }
        return allProspects;
    }
    
    @Override
    public boolean update (Prospect prospect)
    {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            String dateTime = DateTime.now().toString();
            // Format for input
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
            // Parsing the date
            DateTime jodatime = dtf.parseDateTime(dateTime);
            // Format for output
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            
            StringBuilder updateQuery = new StringBuilder("update Prospect p set p.updatedDate = '" + dtfOut.print(jodatime) + "',");
            if(prospect.getPerf3() != null && prospect.getUpdateFlags().get("Perf3") != null)
                updateQuery.append(" p.perf3 = '" + prospect.getPerf3() + "',");
            if(prospect.getPerf5() != null && prospect.getUpdateFlags().get("Perf5") != null)
                updateQuery.append(" p.perf5 = '"+ prospect.getPerf5() + "',");
            if(prospect.getPerf10() != null && prospect.getUpdateFlags().get("Perf10") != null)
                updateQuery.append(" p.perf10 = '"+ prospect.getPerf10() + "',");
            if(prospect.getPerf20() != null && prospect.getUpdateFlags().get("Perf20") != null)
                updateQuery.append(" p.perf20 = '"+ prospect.getPerf20() + "',");
            if(prospect.getPerf30() != null && prospect.getUpdateFlags().get("Perf30") != null)
                updateQuery.append(" p.perf30 = '"+ prospect.getPerf30() + "',");
            if(prospect.getPerf40() != null && prospect.getUpdateFlags().get("Perf40") != null)
                updateQuery.append(" p.perf40 = '"+ prospect.getPerf40() + "',");
            if(prospect.getPerf60() != null && prospect.getUpdateFlags().get("Perf60") != null)
                updateQuery.append(" p.perf60 = '"+ prospect.getPerf60() + "',");
            if(prospect.getPerf120() != null && prospect.getUpdateFlags().get("Perf120") != null)
                updateQuery.append(" p.perf120 = '"+ prospect.getPerf120() + "',");
            if(prospect.getPerf240() != null && prospect.getUpdateFlags().get("Perf240") != null)
                updateQuery.append(" p.perf240 = '"+ prospect.getPerf240() + "'");

            if(updateQuery.charAt(updateQuery.length() - 1) == ',')
                updateQuery.deleteCharAt(updateQuery.length() - 1);
            
            updateQuery.append("where p.symbol = '" + prospect.getSymbol() + "' AND p.portfolioId = '" + prospect.getPortfolioId() + "'");
            
            session.beginTransaction();
            
            Query update = session.createQuery(updateQuery.toString());
            
            update.executeUpdate();
            session.getTransaction().commit();
        }
        catch(HibernateException e)
        {
            session.getTransaction().rollback();
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        finally
        {
            session.disconnect();
        }
        return true;
    }
    
    @Override
    public boolean createProspects(Prospect[] prospects)
    {
        try{
            if(prospects.length > 0)
            {
                //Formula formula = new Formula(prospects);
                // Create formula
                // Call Formula DAO to find if unique
                // if unique Insert
                // Unique or not, get Formula ID and then assign this to each Prospect and call create for each prospect
            }
            for(Prospect prospect : prospects)
            {
                this.create(prospect);
            }
        }
        catch(RuntimeException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
