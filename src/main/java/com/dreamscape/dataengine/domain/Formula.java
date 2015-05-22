/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import com.dreamscape.dataengine.domain.managers.FinvizDomainManager;
import com.mysql.jdbc.StringUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jared
 */
@Entity
@Table
public class Formula implements Serializable{
    @Id
    Long id;
    
    @Column(name="prime_key")
    Long primeKey;
            
    List<String> criteriaNames;

    public Formula(){
    }
    
    public Formula(Prospect[] prospects)
    {
        this.primeKey = 1L;
        if(prospects.length > 0)
            this.criteriaNames = parseCriteria(prospects[0].getSignalAndFeatures());
        for(String criteriaName : criteriaNames)
        {
            this.primeKey *= FinvizDomainManager.lookupPrimeID(criteriaName);
        }
    }
    
    private static List<String> parseCriteria(String criteriaBlob)
    {
        return StringUtils.split(criteriaBlob, ",", true);
    }
    
    @Override
    public boolean equals(Object compareTo){
        if(compareTo != null){
            if(compareTo.getClass().toString().equals("Formula"))
            {
                Formula compareToForm = (Formula)compareTo;
                Long compareToPrimeKey = compareToForm.getPrimeKey();
                List<String> compareToNames = compareToForm.getCriteriaNames();
                if(compareToPrimeKey.compareTo(this.getPrimeKey()) == 0)
                    if(compareToNames.containsAll(this.getCriteriaNames()))
                        return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.primeKey);
        return hash;
    }

    public Long getPrimeKey() {
        return primeKey;
    }

    public void setPrimeID(Long primeKey) {
        this.primeKey = primeKey;
    }

    public List<String> getCriteriaNames() {
        return criteriaNames;
    }

    public void setName(List<String> criteriaNames) {
        this.criteriaNames = criteriaNames;
    }

}
