/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import com.dreamscape.dataengine.utils.PerformanceTimeFrame;
import com.dreamscape.tradingdayinformation.implementation.EODPriceInformation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    Float perf360;
    Float perf540;
    Float perf720;
    
    @Transient
    Float performanceForComparison;
    
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
            
            //TODO: FIX ME
            ArrayList<Double> pricesSinceCreation = null;//EODPriceInformation.downloadHistoricalPrices(s.getTicker(), creationDate);
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

    public Float getPerf360() {
        return perf360;
    }

    public void setPerf360(Float perf360) {
        this.perf360 = perf360;
    }

    public Float getPerf540() {
        return perf540;
    }

    public void setPerf540(Float perf540) {
        this.perf540 = perf540;
    }

    public Float getPerf720() {
        return perf720;
    }

    public void setPerf720(Float perf720) {
        this.perf720 = perf720;
    }

    public void setPerf240(Float perf240) {
        this.perf240 = perf240;
    }

    public Map<String, Boolean> getUpdateFlags() {
        return updateFlags;
    }

    public Float getPerformanceForComparison() {
        return performanceForComparison;
    }

    public void setPerformanceForComparison(Float performanceForComparison) {
        this.performanceForComparison = performanceForComparison;
    }

    @Override
    public String toString() {
        return "Prospect{" + "ticker=" + symbol + ", Signal and Features=" + signalAndFeatures + "}";
    }
    public Float getPerformance(PerformanceTimeFrame timeFrame)
    {
        validateTimeFrame(timeFrame);
        Float performance = 0.0f;
        switch(timeFrame)
                {
                    case Perf3:
                    {
                        performance = this.getPerf3();
                        break;
                    }
                    case Perf5:
                    {
                        performance = this.getPerf5();
                        break;
                    }
                    case Perf10:
                    {
                        performance = this.getPerf10();
                        break;
                    }
                    case Perf20:
                    {
                        performance = this.getPerf20();
                        break;
                    }
                    case Perf30:
                    {
                        performance = this.getPerf30();
                        break;
                    }
                    case Perf40:
                    {
                        performance = this.getPerf40();
                        break;
                    }
                    case Perf60:
                    {
                        performance = this.getPerf60();
                        break;
                    }
                    case Perf120:
                    {
                        performance = this.getPerf120();
                        break;
                    }
                    case Perf240:
                    {
                        performance = this.getPerf240();
                        break;
                    }
                    case Perf360:
                    {
                        performance = this.getPerf360();
                        break;
                    }
                    case Perf540:
                    {
                        performance = this.getPerf540();
                        break;
                    }
                    case Perf720:
                    {
                        performance = this.getPerf720();
                        break;
                    }
                }
                return performance;
        
    }
    public static Comparator<Prospect> getComparatorAndInitializeListForSorting(List<Prospect> prospectsToInitialize, PerformanceTimeFrame timeFrame)
    {
        initializeProspectComparisonValues(prospectsToInitialize, timeFrame);
        return new Comparator<Prospect>() {
            @Override
            public int compare(Prospect o1, Prospect o2) {
                //try{
                    if(o1 == null && o2 == null)
                        return 0;
                    if(o1 == null)
                        return -1;
                    if(o2 == null)
                        return 1;
                
                Float value1 = o1.getPerformanceForComparison();
                Float value2 = o2.getPerformanceForComparison();
                
                if(value1 == null && value2 == null)
                    return 0;
                else if(value1 == null)
                    return -1;
                else if(value2 == null)
                    return 1;
                else if(value1 < value2)
                    return -1;
                else if(value1.equals(value2)) 
                    return 0;
                return 1;
                /*}
                catch(NullPointerException e)
                {
                    System.err.println("Caught NPE.");
                    throw e;
                }*/
            };
        };
    }
    public static boolean validateTimeFrame(PerformanceTimeFrame timeFrame)
    {
        return Arrays.asList(PerformanceTimeFrame.values()).contains(timeFrame);
    }
    
    private static void initializeProspectComparisonValues(List<Prospect> prospects, PerformanceTimeFrame timeFrame)
    {
        switch(timeFrame)
        {
            case Perf3:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf3());
                break;
            }
            case Perf5:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf5());
                break;
            }
            case Perf10:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf10());
                break;
            }
            case Perf20:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf20());
                break;
            }
            case Perf30:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf30());
                break;
            }
            case Perf40:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf40());
                break;
            }
            case Perf60:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf60());
                break;
            }
            case Perf120:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf120());
                break;
            }
            case Perf240:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf240());
                break;
            }
            case Perf360:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf360());
                break;
            }
            case Perf540:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf540());
                break;
            }
            case Perf720:
            {
                for(Prospect p : prospects)
                    p.setPerformanceForComparison(p.getPerf720());
                break;
            }
        }
    }
}
