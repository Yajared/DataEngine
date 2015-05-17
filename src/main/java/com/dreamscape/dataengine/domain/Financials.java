/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

/**
 *
 * @author Jared
 */
public class Financials {
    private String ticker;
    private Double operatingIncome;
    private Double goodwill;
    private Double intangibleAssets;
    private Double totalAssets;
    private Double cashAndSTInvestments;
    private Double longTermInvestments;
    private Double otherLongTermAssets;
    private Double totalCurrentLiabilities;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getOperatingIncome() {
        return operatingIncome;
    }

    public void setOperatingIncome(Double operatingIncome) {
        this.operatingIncome = operatingIncome;
    }

    public Double getGoodwill() {
        return goodwill;
    }

    public void setGoodwill(Double goodwill) {
        this.goodwill = goodwill;
    }

    public Double getIntangibleAssets() {
        return intangibleAssets;
    }

    public void setIntangibleAssets(Double intangibleAssets) {
        this.intangibleAssets = intangibleAssets;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Double getCashAndSTInvestments() {
        return cashAndSTInvestments;
    }

    public void setCashAndSTInvestments(Double cashAndSTInvestments) {
        this.cashAndSTInvestments = cashAndSTInvestments;
    }

    public Double getLongTermInvestments() {
        return longTermInvestments;
    }

    public void setLongTermInvestments(Double longTermInvestments) {
        this.longTermInvestments = longTermInvestments;
    }

    public Double getOtherLongTermAssets() {
        return otherLongTermAssets;
    }

    public void setOtherLongTermAssets(Double otherLongTermAssets) {
        this.otherLongTermAssets = otherLongTermAssets;
    }

    public Double getTotalCurrentLiabilities() {
        return totalCurrentLiabilities;
    }

    public void setTotalCurrentLiabilities(Double totalCurrentLiabilities) {
        this.totalCurrentLiabilities = totalCurrentLiabilities;
    }
    
    
}
