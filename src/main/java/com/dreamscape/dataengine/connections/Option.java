/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.connections;

import java.util.Date;

/**
 *
 * @author Jared
 */
public class Option {
    private Date expirationDate;
    private Double bid;
    private Double ask;
    private Integer openInterest;
    private Integer volume;
    private Double impliedVol;
    
    public Option(){
        expirationDate = new Date();
        bid = new Double(0.0);
        ask = new Double(0.0);
        openInterest = 0;
        volume = 0;
        impliedVol = new Double(0.0);
    }
    
    public Option(Date expirationDate, Double bid, Double ask, Integer openInterest, Integer volume, Double impliedVol){
        this.expirationDate = expirationDate;
        this.bid = bid;
        this.ask = ask;
        this.openInterest = openInterest;
        this.volume = volume;
        this.impliedVol = impliedVol;
    }
}
