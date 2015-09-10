/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import com.dreamscape.tradingdayinformation.implementation.EODPriceInformation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 At this point Prospects are only really generated from Finviz
 */
@Entity
@Table(name = "prospect")
public class Prospect implements Serializable{
    @Id
    @GeneratedValue
    Long id;
    
    @Column(name="ticker_symbol")
    String symbol;
    
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Column(name="creation_date")
    DateTime creationDate;
    
    @Column(name="portfolio_id")
    Long portfolioId;
    
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @Column(name="updated_date")
    DateTime updatedDate;
    
    @Column(name="price_at_creation")
    Float priceAtCreation;
    
    Float perf3;
    Float perf5;
    Float perf10;
    Float perf20;
    Float perf30;
    Float perf40;
    Float perf60;
    Float perf120;
    Float perf240;
    
    @Transient
    String signalAndFeatures;
    
    @Transient
    Map<String, Boolean> updateFlags = new HashMap<>();
    
    public Prospect(){ 
        updateFlags = new HashMap<>();
    }
    
    public static ArrayList<Prospect> convertSecuritiesToProspects(List<Security> securities, DateTime creationDate)
    {
        ArrayList<Prospect> prospects = new ArrayList<>();
        
        for(Security s : securities)
        {
            Prospect p = new Prospect();
            p.setSymbol(s.getTicker());
            p.setCreationDate(creationDate);
            
            ArrayList<Double> pricesSinceCreation = EODPriceInformation.downloadHistoricalPrices(s.getTicker(), creationDate);
            if(pricesSinceCreation == null)
                p.setPriceAtCreation(null);
            else
                p.setPriceAtCreation(pricesSinceCreation.get(pricesSinceCreation.size() - 1).floatValue());
            
            prospects.add(p);
        }
        
        return prospects;
    }
    
    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String ticker) {
        this.symbol = ticker;
    }

    public String getSignalAndFeatures() {
        return signalAndFeatures;
    }

    public void setSignalAndFeatures(String signalAndFeatures) {
        this.signalAndFeatures = signalAndFeatures;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public DateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(DateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    public Float getPriceAtCreation() {
        return priceAtCreation;
    }

    public void setPriceAtCreation(Float priceAtCreation) {
        this.priceAtCreation = priceAtCreation;
    }

    public Float getPerf3() {
        return perf3;
    }

    public void setPerf3(Float perf3) {
        this.perf3 = perf3;
    }

    public Float getPerf5() {
        return perf5;
    }

    public void setPerf5(Float perf5) {
        this.perf5 = perf5;
    }

    public Float getPerf10() {
        return perf10;
    }

    public void setPerf10(Float perf10) {
        this.perf10 = perf10;
    }

    public Float getPerf20() {
        return perf20;
    }

    public void setPerf20(Float perf20) {
        this.perf20 = perf20;
    }

    public Float getPerf30() {
        return perf30;
    }

    public void setPerf30(Float perf30) {
        this.perf30 = perf30;
    }

    public Float getPerf40() {
        return perf40;
    }

    public void setPerf40(Float perf40) {
        this.perf40 = perf40;
    }

    public Float getPerf60() {
        return perf60;
    }

    public void setPerf60(Float perf60) {
        this.perf60 = perf60;
    }

    public Float getPerf120() {
        return perf120;
    }

    public void setPerf120(Float perf120) {
        this.perf120 = perf120;
    }

    public Float getPerf240() {
        return perf240;
    }

    public void setPerf240(Float perf240) {
        this.perf240 = perf240;
    }

    public Map<String, Boolean> getUpdateFlags() {
        return updateFlags;
    }

    @Override
    public String toString() {
        return "Prospect{" + "ticker=" + symbol + ", Signal and Features=" + signalAndFeatures + "}";
    }
    
}
