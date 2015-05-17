/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.processes;

import static com.dreamscape.dataengine.processes.CalculateVolumeWeightedVariance.calculateTenDayEMA;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Jared
 */
public class CalculateVolumeWeightedVarianceTest {
    private static final int TEN_DAYS = 10;
    public CalculateVolumeWeightedVarianceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Ignore
    public void calculateVWVWithRandomClosesAndVolTest(){
        final int NUM_OF_DAYS_TO_GENERATE = 31;
        
        Random r = new Random();
        
        Double[] thirtyOneDaysClose = new Double[NUM_OF_DAYS_TO_GENERATE];
        Double[] thirtyOneDaysVol = new Double[NUM_OF_DAYS_TO_GENERATE];
        
        for(int i=0; i<NUM_OF_DAYS_TO_GENERATE; i++){
            thirtyOneDaysClose[i] = Math.abs(r.nextDouble()) + 3.0;
            thirtyOneDaysVol[i] = Math.abs(new Double(r.nextLong() * 1.0));
        }
        System.out.println("Random Closes: {");
        for(int i = 0; i < NUM_OF_DAYS_TO_GENERATE; i++){
            System.out.print(thirtyOneDaysClose[i] + " ");
        }
        System.out.println("}");
        
        System.out.println("Random Volumes: {");
        for(int i = 0; i < NUM_OF_DAYS_TO_GENERATE; i++){
            System.out.print(thirtyOneDaysVol[i] + " ");
        }
        System.out.println("}");
        
        Double[] volumeWeightedVarianceIndicator = CalculateVolumeWeightedVariance.lastTenDaysIndicator(thirtyOneDaysClose, thirtyOneDaysVol);
              
        Double tenDayEMA, tenDayVolEMA, varianceFromEMA, volVarianceFromEMA, percentVariance, volPercentVariance;
        
        BigDecimal roundedPriceVariance, roundedVolVariance;
        final int decimalPlaces = 2;
        
        for(int i = 0; i < TEN_DAYS; i++){
            tenDayEMA = calculateTenDayEMA(Arrays.copyOfRange(thirtyOneDaysClose, 1, 21));
            varianceFromEMA = thirtyOneDaysClose[i] - tenDayEMA;
            percentVariance = (varianceFromEMA / tenDayEMA) * 100;
            
            roundedPriceVariance = new BigDecimal(percentVariance);
            roundedPriceVariance = roundedPriceVariance.setScale(decimalPlaces, RoundingMode.HALF_UP);
                    
            tenDayVolEMA = calculateTenDayEMA(Arrays.copyOfRange(thirtyOneDaysVol, 1, 21));
            volVarianceFromEMA = thirtyOneDaysVol[i] - tenDayVolEMA;
            volPercentVariance = (volVarianceFromEMA / tenDayVolEMA) * 100;
            
            roundedVolVariance = new BigDecimal(volPercentVariance);
            roundedVolVariance = roundedVolVariance.setScale(decimalPlaces, RoundingMode.HALF_UP);
            
            System.out.println("Price Day " + (i+1) + ": " + thirtyOneDaysClose[i] + " Vol: " + thirtyOneDaysVol[i] + "  Variance: " + roundedPriceVariance + "%  Volume Variance: " + roundedVolVariance + "%  VWV: " + volumeWeightedVarianceIndicator[/*TEN_DAYS - i - 1*/ i]);
            //System.out.println("Volume Weighted Variance for Day " + (i+1) + ": " + volumeWeightedVarianceIndicator[i]);
        }
    }
    @Test
    public void calculateVWVWithSomeRealDataTest(){
        Double[] thirtyOneDaysClose = {4.02, 4.07, 3.87, 4.01, 3.88, 3.73, 3.81, 4.11, 4.01, 3.74, 
            3.54, 3.65, 3.95, 4.04, 4.15, 4.28, 4.01, 3.92, 4.00, 4.09, 3.91, 4.04, 3.77, 4.17, 
            4.24, 4.04, 4.06, 4.03, 3.95, 4.22, 3.96/*, 3.86, 3.63*/};
        
        Double[] thirtyOneDaysVol = {10_375_235.0, 15_399_300.0, 7_791_489.0, 7_941_086.0, 5_224_991.0, 12_256_781.0, 
            13_470_966.0, 37_901_832.0, 12_915_022.0, 19_849_408.0, 16_895_510.0, 15_891_732.0, 10_416_090.0, 
            14_721_883.0, 13_401_219.0, 16_755_189.0, 13_561_922.0, 7_782_096.0, 8_743_328.0, 10_611_076.0, 
            9_667_760.0, 12_372_988.0, 11_081_847.0, 6_568_248.0, 11_731_808.0, 10_351_863.0, 12_531_216.0, 
            10_244_056.0, 26_283_876.0, 21_474_302.0, 13_175_625.0};
        
        Double[] volumeWeightedVarianceIndicator = CalculateVolumeWeightedVariance.lastTenDaysIndicator(thirtyOneDaysClose, thirtyOneDaysVol);
        
        Double tenDayEMA, tenDayVolEMA, varianceFromEMA, volVarianceFromEMA, percentVariance, volPercentVariance;
        
        BigDecimal roundedPriceVariance, roundedVolVariance;
        final int decimalPlaces = 2;
        
        for(int i = TEN_DAYS - 1; i >= 0; i--){
            
            tenDayEMA = calculateTenDayEMA(Arrays.copyOfRange(thirtyOneDaysClose, 1, 21));
            varianceFromEMA = thirtyOneDaysClose[i] - tenDayEMA;
            percentVariance = (varianceFromEMA / tenDayEMA) * 100;
            
            roundedPriceVariance = new BigDecimal(percentVariance);
            roundedPriceVariance = roundedPriceVariance.setScale(decimalPlaces, RoundingMode.HALF_UP);
                    
            tenDayVolEMA = calculateTenDayEMA(Arrays.copyOfRange(thirtyOneDaysVol, 1, 21));
            volVarianceFromEMA = thirtyOneDaysVol[i] - tenDayVolEMA;
            volPercentVariance = (volVarianceFromEMA / tenDayVolEMA) * 100;
            
            roundedVolVariance = new BigDecimal(volPercentVariance);
            roundedVolVariance = roundedVolVariance.setScale(decimalPlaces, RoundingMode.HALF_UP);
            
            System.out.println("Price Day " + (i+1) + ": " + thirtyOneDaysClose[i] + " Vol: " + thirtyOneDaysVol[i] + "  Variance: " + roundedPriceVariance + "%  Volume Variance: " + roundedVolVariance + "%  VWV: " + (volumeWeightedVarianceIndicator[/*TEN_DAYS - i - 1*/ i]));
        }
    }
}
