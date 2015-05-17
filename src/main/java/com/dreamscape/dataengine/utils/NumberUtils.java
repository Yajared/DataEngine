/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.utils;

/**
 *
 * @author Jared
 */
public class NumberUtils {
    public static Double parsePercentage(String percentAsString){
        int signMultiplier = 1;
        
        if(percentAsString.indexOf("-") != -1){
            percentAsString = percentAsString.substring(1);
            signMultiplier = -1;
        }
        Double percentage = new Double(Double.parseDouble(percentAsString.substring(0, percentAsString.length() - 1)) / 100 * signMultiplier);
        
        return percentage;
    }
}
