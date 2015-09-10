/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.domain;

import com.dreamscape.dataengine.domain.dao.PortfolioDAO;
import com.dreamscape.dataengine.domain.dao.PortfolioDAOHibernateImpl;
import com.dreamscape.dataengine.domain.dao.ProspectDAO;
import com.dreamscape.dataengine.domain.dao.ProspectDAOHibernateImpl;
import com.dreamscape.dataengine.domain.dao.SecurityDAO;
import com.dreamscape.dataengine.domain.dao.SecurityDAOHibernateImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 */
@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable{
    
    @Transient
    private List<Prospect> prospects;
    
    @Id
    @Column(name="primeProduct")
    private Long primeId;
    
    private String criteria;
    
    public Portfolio(){
        prospects = new ArrayList<>();
    }
    
    public static Portfolio createPortfolioFromQuery(Long portfolioPrimeId, String query, DateTime priceDate)
    {
        Portfolio p = new Portfolio();
        p.setPrimeId(portfolioPrimeId);
        
        SecurityDAO dao = new SecurityDAOHibernateImpl();
        ArrayList<Security> securities = ((ArrayList<Security>)dao.retrieveSecuritiesWithQuery(query));
        
        ArrayList<Prospect> prospects = Prospect.convertSecuritiesToProspects(securities, priceDate);
        p.setProspects(prospects);
        
        p.setCriteria(query);
        
        return p;
    }
    public void savePortfolioOfProspects()
    {
        if(this.getProspects() != null)
        {
            for(Prospect p : this.getProspects())
            {
                p.setPortfolioId(this.getPrimeId());
            }
            if(this.getProspects().isEmpty() == false)
            {
                PortfolioDAO dao = new PortfolioDAOHibernateImpl();
                if(dao.create(this) != null)
                {
                    ProspectDAO prospectDao = new ProspectDAOHibernateImpl();
                    for(Prospect p : this.getProspects())
                    {
                        prospectDao.create(p);
                    }
                }
            }
        }
    }
    //private void fillWithProspects()
    //{
    //    ProspectDAO dao = new ProspectDAOHibernateImpl();
    //    this.setProspects(dao.getProspectsByPortfolioId(this.getPrimeId()));
    //}
    
    public List<Prospect> getProspects() {
        return prospects;
    }

    public void setProspects(List<Prospect> prospects) {
        this.prospects = prospects;
    }

    public Long getPrimeId() {
        return primeId;
    }

    public void setPrimeId(Long primeId) {
        this.primeId = primeId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    
}
