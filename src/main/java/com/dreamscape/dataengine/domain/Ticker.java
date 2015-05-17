/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Jared
 */
@Entity
@Table(name="ticker")
public class Ticker implements Serializable {
    @Id
    @GeneratedValue
    Long id;
    String symbol;

    public Ticker(){}
    
    public Ticker(String symbol){
        this.symbol = symbol;
    }
    
    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    
}
