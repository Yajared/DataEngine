/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.processes;

import java.util.Arrays;

/**
 *
 * @author Jared
 */
public class CalculateVolumeWeightedVariance {
    private static final Integer DAYS_IN_PERIOD = 10;
    private static final Double MULTIPLIER = 2 / (new Double(DAYS_IN_PERIOD * 1.0) + 1.0);
    public static Double[] lastTenDaysIndicator(Double[] lastThirtyOneDaysClosing, Double[] lastThirtyOneDaysVolume){
        Double[] lastTenDays = new Double[DAYS_IN_PERIOD];
        Double currentDayIndicator;
        Double movingAvg = 0.0;
        
        lastTenDays[0] = calculateGivenDayIndicator(Arrays.copyOfRange(lastThirtyOneDaysClosing, 0, (DAYS_IN_PERIOD * 2) + 1), Arrays.copyOfRange(lastThirtyOneDaysVolume, 0, (DAYS_IN_PERIOD * 2) + 1));
        for(int i = 1; i < DAYS_IN_PERIOD; i++){
            currentDayIndicator = calculateGivenDayIndicator(Arrays.copyOfRange(lastThirtyOneDaysClosing, i, i+(DAYS_IN_PERIOD * 2) + 1), Arrays.copyOfRange(lastThirtyOneDaysVolume, i, i+(DAYS_IN_PERIOD * 2) + 1));
            System.out.println("Current Day Indicator: " + currentDayIndicator);
            movingAvg = calculateSimpleMovingAvg(Arrays.copyOfRange(lastTenDays, 0, i));
            
            lastTenDays[i] = lastTenDays[i - 1] + currentDayIndicator - movingAvg; /* currentDayIndicator * MULTIPLIER + lastTenDays[i-1] * (1- MULTIPLIER) */ 
            
            lastTenDays[i] = scaleIndicator(lastTenDays[i]);
            
            System.out.println("Current Day Indicator: " + currentDayIndicator + " + " + "lastTenDays[i-1]: " + lastTenDays[i-1] + "=" + lastTenDays[i] );
            System.out.println("Indicator at Day " + (i+1) + ": " + lastTenDays[i]);
            System.out.println();
        }
        
        return lastTenDays;
    }
    
    private static Double scaleIndicator(Double value){
        boolean neg = false;
        final Double mu = 7.2;
        
        if(value < 0)
            neg = true;
        
        value = Math.pow(value, 2);
        value = Math.sqrt(Math.sqrt(value));
        value *= mu;
        
        if(neg)
            return value * -1.0;
        
        return value;
    }
    
    private static Double calculateGivenDayIndicator(Double [] lastTwentyOneDaysClosing, Double[] lastTwentyOneDaysVolume){
        
        final Double gamma = 2.75; // A constant that indicates the ratio between typical flux in volume vs. flux in price (volume : price)
        
        Double currentClose = lastTwentyOneDaysClosing[0];
        Double [] priorTwentyDaysClosing = Arrays.copyOfRange(lastTwentyOneDaysClosing, 1, lastTwentyOneDaysClosing.length);
        //System.out.println("Size of priorTwentyDaysClosing: " + lastTwentyOneDaysClosing.length);
        Double tenDayPriceEMA = calculateTenDayEMA(priorTwentyDaysClosing);
        
        //System.out.println("Ten Day Price EMA: " + tenDayPriceEMA);
        System.out.println();
        Double stdDev = calculateStandardDeviation(tenDayPriceEMA, Arrays.copyOfRange(priorTwentyDaysClosing, 11, priorTwentyDaysClosing.length));
        System.out.println("Standard Deviation: " + stdDev);
        
        
        Double currentVol = lastTwentyOneDaysVolume[/*priorTwentyDaysVolume.length - 1*/ 0];
        
        System.out.println("Current Day Close: " + currentClose);
        System.out.println("Ten Day Price EMA: " + tenDayPriceEMA);
        
        Double [] priorTwentyDaysVol = Arrays.copyOfRange(lastTwentyOneDaysVolume, 1, lastTwentyOneDaysVolume.length);
        Double tenDayVolEMA = calculateTenDayEMA(priorTwentyDaysVol);
        System.out.println("Current Day Volume: " + currentVol);
        System.out.println("Ten Day Vol EMA: " + tenDayVolEMA);
        
        Double volStdDev = calculateStandardDeviation(tenDayPriceEMA, Arrays.copyOfRange(priorTwentyDaysClosing, 11, priorTwentyDaysClosing.length));
        Double scaledVolStdDev = volStdDev / gamma;
        
        System.out.println("Volume Standard Deviation " + scaledVolStdDev);
        
        Double volVariance = Math.abs(volStdDev - Math.abs(currentVol - tenDayVolEMA));
        System.out.println("Volume Variance: " + volVariance);
//        Double priceDelta = Math.abs(currentClose - tenDayPriceEMA);
//        System.out.println("Price Delta: " + priceDelta);
//        
//        Double volumeDelta = Math.abs(currentVol - tenDayVolEMA);
//        System.out.println("Volume Delta: " + volumeDelta);
//        
//        Double priceDeviation = (priceDelta / tenDayPriceEMA);
//        System.out.println("Price Deviation: " + priceDeviation);
        
//        Double expectedVolume = tenDayVolEMA * priceDeviation * gamma;
//        System.out.println("Expected Volume: " + expectedVolume);
//        //Double volumeDeviation = (volumeDelta / tenDayVolEMA);
//        //System.out.println("Volume Deviation: " + volumeDeviation);
//        Double variance = (currentVol - expectedVolume)/expectedVolume;
//        System.out.println("Variance: " + variance);
//        
//        // The values of 3.88 / 5_224_991 are giving problems because the expected volume is so much lower than actual
////        System.out.println("Ten Day Volume EMA: " + tenDayVolEMA);
////        System.out.println();
////        System.out.println("Prior Close: " + currentClose);
////        System.out.println("Ten Day Price EMA: " + tenDayPriceEMA);
////        Double epsilon = currentClose - tenDayPriceEMA;
////        System.out.println("Epsilon: " + epsilon);
////        Double epsilonPerc = (epsilon / tenDayPriceEMA);
////        System.out.println("Epsilon Percentage: " + epsilonPerc);
////        Double currentDayRelativeVol = ((currentVol - tenDayVolEMA)/tenDayVolEMA) * 100;
////        System.out.println("Given Day Volume: " + currentVol);
////        System.out.println("Ten Day Vol EMA: " + tenDayVolEMA);
////        System.out.println("Given Day Relative Volume: " + currentDayRelativeVol);
////        Double currentDayValue = epsilonPerc * currentDayRelativeVol;
//        
//        //Scale by Ten
//        //currentDayValue *=  10;
//        
//        System.out.println("Given Day Value: " + variance);
//        return variance;
        Double priceVariance = stdDev - Math.abs(currentClose - tenDayPriceEMA);
        
        final Double kappa = 5.0; // scaling to get typical values to sit in a better range
        
        return (priceVariance - scaledVolStdDev) * kappa * 100.0;
    }
    
    protected static Double calculateTenDayEMA(Double[] priorTwentyDaysValues)
    {        
        Double tenDaySMA = 0.0;
        
        Double [] priorTenDaysValues = Arrays.copyOfRange(priorTwentyDaysValues, 0, 10);

        
        //System.out.println("Multiplier: " + MULTIPLIER);
        
        for(int i = 0; i<DAYS_IN_PERIOD; i++){
            tenDaySMA += priorTenDaysValues[i];
            // TODO: Fix this
            //MULTIPLIERs[i] = 2 / (new Double(priorTenDaysValues.length * 1.0) + 1.0 - new Double(i * 1.0));
            //System.out.println("Multiplier at Index " + i + " : " + MULTIPLIERs[i]);
        }
        tenDaySMA /= DAYS_IN_PERIOD;
        //System.out.println("Ten Day SMA: " + tenDaySMA);
        
        Double priorDayEMA = tenDaySMA;
        //Essentially we want to apply this: EMA: {Close - EMA(previous day)} x MULTIPLIER + EMA(previous day)
        // Starting with the prior tenDaySMA (from the currentDay - 20 ~ currentDay - 11 period) as the first "EMA",
        // We then apply the above formula for each current day, with the MULTIPLIER being applied in reverse order (so the most significant value is the last)
        
        for(int i = DAYS_IN_PERIOD - 1; i > 0; i--){
            priorDayEMA = (priorTenDaysValues[i] * MULTIPLIER + priorDayEMA * (1.0-MULTIPLIER));
            //System.out.println("Current Prior Day EMA: " + priorDayEMA);
        }
        Double tenDayEMA = priorDayEMA;
        
        return tenDayEMA;
    }
    private static Double calculateStandardDeviation(Double mean, Double[] values){
        Double total = 0.0;
        
        for(int i = 0; i < values.length;i++)
            total += Math.pow((mean - values[i]), 2);
        
        Double stdDev = Math.sqrt(total / values.length);
        
        return stdDev;
    }
    
    private static Double calculateSimpleMovingAvg(Double[] values){
        Double avg = 0.0;
        Double total = 0.0;
        
        for(int i = 0; i < values.length; i++)
            total += values[i];
        
        avg = total / values.length;
        
        return avg;
    }
}
