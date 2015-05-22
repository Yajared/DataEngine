/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import java.io.Serializable;
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
public class Criteria implements Serializable{
    @Id
    Long id;
    
    @Column(name="prime_id")
    Long primeID;
            
    String name;

    public Criteria(){
    }
    
    @Override
    public boolean equals(Object compareTo){
        if(compareTo != null){
            if(compareTo.getClass().toString().equals("Criteria"))
            {
                Criteria compareToCrit = (Criteria)compareTo;
                Long compareToPrimeID = compareToCrit.getPrimeID();
                String compareToName = compareToCrit.getName();
                if(compareToPrimeID.compareTo(this.getPrimeID()) == 0)
                    if(compareToName.equals(this.getName()))
                        return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.primeID);
        return hash;
    }

    public Long getPrimeID() {
        return primeID;
    }

    public void setPrimeID(Long primeID) {
        this.primeID = primeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
