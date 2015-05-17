/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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
    HashSet <Long> criterionSet;

    public Criteria(){
        criterionSet = new HashSet<>();
    }
    
    public HashSet getCriterionSet() {
        return criterionSet;
    }

    public void setCriterionSet(HashSet criterionSet) {
        this.criterionSet = criterionSet;
    }
    
    @Override
    public boolean equals(Object compareTo){
        if(compareTo != null){
            if(compareTo.getClass().toString().equals("Criteria"))
            {
                Criteria compareToCrit = (Criteria)compareTo;
                HashSet<Long> compareToCriterionSet = compareToCrit.getCriterionSet();
                if(compareToCriterionSet.containsAll(this.getCriterionSet()))
                    if(this.getCriterionSet().containsAll(compareToCriterionSet))
                        return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.criterionSet);
        return hash;
    }
    
    
}
