/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.managers;

import com.dreamscape.dataengine.connections.FinvizConnectionManager;
import com.dreamscape.dataengine.domain.Criteria;
import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.managers.FinvizDomainManager.Feature.*;
import com.dreamscape.utillibrary.parsers.CSVParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Jared
 */
public class FinvizDomainManager {
    
    private static final int MAX_NUMBER_OF_SELECTABLE_FEATURES = 8;
    private static final char CLASS_SEPARATOR_TOKEN = '$';
    private static final String PRIME_NUMBER_SET_FILE_PATH = "";
    private static final String CRITERIA_NAME_SET_FILE_PATH = "";
    
    private static HashMap<String, Long> primeMap;
    
    private static class Suffix{
        static final String UNUSUAL_VOLUME = "ta_unusualvolume";
        static final String TOP_GAINERS = "ta_topgainers";
        static final String TOP_LOSERS = "ta_toplosers";
        static final String NEW_HIGH = "ta_newhigh";
        static final String NEW_LOW = "ta_newlow";
        static final String MOST_VOLATILE = "ta_mostvolative";
        static final String MOST_ACTIVE = "ta_mostactive";
        static final String OVERBOUGHT = "ta_overbought";
        static final String OVERSOLD = "ta_oversold";
        static final String DOWNGRADES = "n_downgrades";
        static final String UPGRADES = "n_upgrades";
        static final String EARNINGS_BEFORE = "n_earningsbefore";
        static final String EARNINGS_AFTER = "n_earningsafter";
        static final String INSIDER_BUYING = "it_latestbuys";
        static final String INSIDER_SELLING = "it_latestsales";
        static final String MAJOR_NEWS = "n_majornews";
        static final String HORIZONTAL_SR = "ta_p_horizontal";
        static final String TL_RESISTANCE = "ta_p_tlresistance";
        static final String TL_SUPPORT = "ta_p_tlsupport";
        static final String WEDGE_UP = "ta_p_wedgeup";
        static final String WEDGE_DOWN = "ta_p_wedgedown";
        static final String CHANNEL_UP = "ta_p_channelup";
        static final String CHANNEL_DOWN = "ta_p_channeldown";
        static final String CHANNEL = "ta_p_channel";
        static final String DOUBLE_TOP = "ta_p_doubletop";
        static final String DOUBLE_BOTTOM = "ta_p_doublebottom";
        static final String MULTIPLE_TOP = "ta_p_multipletop";
        static final String MULTIPLE_BOTTOM = "ta_p_multiplebottom";
        static final String HEAD_AND_SHOULDERS = "ta_p_headandshoulders";
        static final String HEAD_AND_SHOULDERS_INVERSE = "ta_p_headandshouldersinv";
        
        static final String EXCHANGE_AMEX = "exch_amex";
        static final String EXCHANGE_NASD = "exch_nasd";
        static final String EXCHANGE_NYSE = "exch_nyse";
        static final String CAP_MEGA = "cap_mega";
        static final String CAP_LARGE = "cap_large";
        static final String CAP_MID = "cap_mid";
        static final String CAP_SMALL = "cap_small";
        static final String CAP_MICRO = "cap_micro";
        static final String CAP_NANO = "cap_nano";
        static final String CAP_LARGE_OVER = "cap_largeover";
        static final String CAP_MID_OVER = "cap_midover";
        static final String CAP_SMALL_OVER = "cap_smallover";
        static final String CAP_MICRO_OVER = "cap_microover";
        static final String CAP_LARGE_UNDER = "cap_largeunder";
        static final String CAP_MID_UNDER = "cap_midunder";
        static final String CAP_SMALL_UNDER = "cap_smallunder";
        static final String CAP_MICRO_UNDER = "cap_microunder";
        static final String OPTSHORT_OPTIONABLE = "sh_opt_option";
        static final String OPTSHORT_SHORTABLE = "sh_opt_short";
        static final String OPTSHORT_OPTIONABLE_AND_SHORTABLE = "sh_opt_optionshort";
        static final String RELATIVE_VOLUME_OVER_10 = "sh_relvol_o10";
        static final String RELATIVE_VOLUME_OVER_5 = "sh_relvol_o5";
        static final String RELATIVE_VOLUME_OVER_3 = "sh_relvol_o3";
        static final String RELATIVE_VOLUME_OVER_2 = "sh_relvol_o2";
        static final String RELATIVE_VOLUME_OVER_1_POINT_5 = "sh_relvol_o1.5";
        static final String RELATIVE_VOLUME_OVER_1 = "sh_relvol_o1";
        static final String RELATIVE_VOLUME_OVER_POINT_75 = "sh_relvol_o0.75";
        static final String RELATIVE_VOLUME_OVER_POINT_5 = "sh_relvol_o0.5";
        static final String RELATIVE_VOLUME_OVER_POINT_25 = "sh_relvol_o0.25";
        static final String RELATIVE_VOLUME_UNDER_2 = "sh_relvol_u2";
        static final String RELATIVE_VOLUME_UNDER_1_POINT_5 = "sh_relvol_u1.5";
        static final String RELATIVE_VOLUME_UNDER_1 = "sh_relvol_u1";
        static final String RELATIVE_VOLUME_UNDER_POINT_75 = "sh_relvol_u0.75";
        static final String RELATIVE_VOLUME_UNDER_POINT_5 = "sh_relvol_u0.5";
        static final String RELATIVE_VOLUME_UNDER_POINT_25 = "sh_relvol_u0.25";
        static final String RELATIVE_VOLUME_UNDER_POINT_1 = "sh_relvol_u0.1";
    }
    
    private static class FeatureName {
        final static String RELATIVE_VOLUME_OVER_10 = "Feature - Relative Volume Over 10";
    }
    
    private static class SignalName {
        final static String UNUSUAL_VOLUME = "Signal - Unusual Volume";
    }
    public static class Signal extends Criteria {
        public static enum SignalSelection {
            UNUSUAL_VOLUME, TOP_GAINERS, TOP_LOSERS, NEW_HIGH, NEW_LOW, MOST_VOLATILE, MOST_ACTIVE,
            OVERBOUGHT, OVERSOLD, DOWNGRADES, UPGRADES, EARNINGS_BEFORE, EARNINGS_AFTER,
            INSIDER_BUYING, INSIDER_SELLING, MAJOR_NEWS, HORIZONTAL_SR, TL_RESISTANCE,
            TL_SUPPORT, WEDGE_UP, WEDGE_DOWN, CHANNEL_UP, CHANNEL_DOWN, CHANNEL, DOUBLE_TOP,
            DOUBLE_BOTTOM, MULTIPLE_TOP, MULTIPLE_BOTTOM, HEAD_AND_SHOULDERS, 
            HEAD_AND_SHOULDERS_INVERSE  
        }
        
    }
    
    public static class Feature extends Criteria {
        public static enum FeatureCategory{
            EXCHANGE, MARKET_CAP, OPTION_SHORT, RELATIVE_VOLUME
        }
        public static enum Exchange{
            AMEX, NASDAQ, NYSE
        }
        public static enum MarketCap{
            MEGA, LARGE, MID, SMALL, MICRO, NANO,
            LARGE_OVER, MID_OVER, SMALL_OVER, MICRO_OVER,
            LARGE_UNDER, MID_UNDER, SMALL_UNDER, MICRO_UNDER
        }
        public static enum OptionShort{
            OPTIONABLE, SHORTABLE, OPTIONABLE_AND_SHORTABLE
        }
        public static enum RelativeVolume{
            OVER_10, OVER_5, OVER_3, OVER_2, OVER_1_POINT_5, OVER_1, 
            OVER_POINT_75, OVER_POINT_5, OVER_POINT_25, UNDER_2, UNDER_1_POINT_5,
            UNDER_1, UNDER_POINT_75, UNDER_POINT_5, UNDER_POINT_25, UNDER_POINT_1
        }
        
    }
    
    static final Signal.SignalSelection signalSelection = Signal.SignalSelection.values()[0]; //pointer to static class instance
    
    static{
        primeMap = new HashMap<String, Long>();
        File file = new File(PRIME_NUMBER_SET_FILE_PATH);
        File file2 = new File(CRITERIA_NAME_SET_FILE_PATH);
        try{
            ArrayList<String> primesAsStrings = CSVParser.convertToListOfString(file, false);
            ArrayList<String> criteriaNames = CSVParser.convertToListOfString(file2, false);
            
            for(int i = 0; i < criteriaNames.size(); i++)
            {
                primeMap.put(criteriaNames.get(i), Long.parseLong(primesAsStrings.get(i)));
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public FinvizDomainManager(){
        
    }
    public Prospect[] generateProspects(FinvizDomainManager.Signal.SignalSelection signal, Enum... features){
        
        
        ArrayList<String> prospectList = FinvizConnectionManager.getProspectList(signal,features);
        
        Prospect[] prospects = convertToProspectArray(prospectList, signal, features);
        
        return prospects;
    }
    public Prospect[] generateRandomProspects(){
        
        Random rand = new Random();
        
        Signal.SignalSelection[] signals = signalSelection.values();
        Signal.SignalSelection selectedSignal;
        int randomSignalIndex = rand.nextInt(signals.length + 1);
        if(randomSignalIndex > signals.length)
            selectedSignal = null;
        else
            selectedSignal = signals[randomSignalIndex];
        
        int randomNumFeatures = rand.nextInt(MAX_NUMBER_OF_SELECTABLE_FEATURES);
        int randomFeatureIndex;
        
          ArrayList<FeatureCategory> featureCategoryList = new ArrayList<>();
          FeatureCategory[] allFeatureCategories = FeatureCategory.values(); // TODO: Need to think of how to rewrite this
          FeatureCategory currentFeatureCategory;
          ArrayList<Enum> featureList = new ArrayList<>();
          ArrayList<Integer> blackOutList = new ArrayList<>();
        
          int randNumGenAttempts;
        
          for(int i = 0; i < randomNumFeatures; i++){
            randNumGenAttempts = 0;
            
            while(randNumGenAttempts++ < 2)
            {
                randomFeatureIndex = rand.nextInt(allFeatureCategories.length);
                if(! blackOutList.contains(new Integer(randomFeatureIndex)))
                {
                    randNumGenAttempts = 0;
                    blackOutList.add(new Integer(randomFeatureIndex));
                    currentFeatureCategory = allFeatureCategories[randomFeatureIndex];
                    System.out.println("Feature Category Selected: " + currentFeatureCategory.toString());
                    featureCategoryList.add(currentFeatureCategory);
                    switch(currentFeatureCategory){
                        case EXCHANGE:
                            System.out.println("Hit Exchange");
                            int randEx = rand.nextInt(Exchange.values().length);
                            System.out.println(Exchange.values()[randEx].toString());
                            featureList.add(Exchange.values()[/*rand.nextInt(Exchange.values().length)*/ randEx]);
                            break;
                        case MARKET_CAP:
                            System.out.println("Hit Market Cap");
                            int randMarketCap = rand.nextInt(MarketCap.values().length);
                            System.out.println(MarketCap.values()[randMarketCap].toString());
                            featureList.add(MarketCap.values()[/*rand.nextInt(MarketCap.values().length)*/randMarketCap]);
                            break;
                        case OPTION_SHORT:
                            System.out.println("Hit Option Short");
                            int randOptionShort = rand.nextInt(OptionShort.values().length);
                            System.out.println(OptionShort.values()[randOptionShort].toString());
                            featureList.add(OptionShort.values()[/*rand.nextInt(OptionShort.values().length)*/randOptionShort]);
                            break;
                        case RELATIVE_VOLUME:
                            System.out.println("Hit Relative Volume");
                            int randRelativeVolume = rand.nextInt(RelativeVolume.values().length);
                            System.out.println(RelativeVolume.values()[randRelativeVolume].toString());
                            featureList.add(RelativeVolume.values()[/*rand.nextInt(RelativeVolume.values().length)*/randRelativeVolume]); 
                            break;                           
                    }
                    break;
                }
            }
        }
        
        Enum[] features = featureList.toArray(new Enum[featureList.size()]);
        Prospect[] prospects = generateProspects(selectedSignal, features);
        
        return prospects;
    }
    
    public static Prospect[] convertToProspectArray(ArrayList<String> prospectList, Signal.SignalSelection signal, Enum... features){
        ArrayList<Prospect> prospects = new ArrayList<>();
        for(String symbol : prospectList){
            //System.out.println("Generating prospect " + dataArray[1] + "...");
            Prospect p = new Prospect();
            
            p.setSignalAndFeatures(signalAndFeaturesToBlobString(false, signal, features));
            
            //System.out.println(p.getSignalAndFeatures());
            
            p.setSymbol(symbol);
            
            prospects.add(p);
        }
        return prospects.toArray(new Prospect[prospects.size()]);
    }
    public static String signalAndFeaturesToBlobString(boolean urlFormatting, Signal.SignalSelection signal, Enum... features){
        String ret = "";
// <editor-fold>
        if(signal != null)
        {
            if(urlFormatting)
                ret += "s=";
            switch(signal){
                case UNUSUAL_VOLUME:
                    ret += Suffix.UNUSUAL_VOLUME;
                    break;
                case TOP_GAINERS:
                    ret += Suffix.TOP_GAINERS;
                    break;
                case TOP_LOSERS:
                    ret += Suffix.TOP_LOSERS;
                    break;
                case NEW_HIGH:
                    ret += Suffix.NEW_HIGH;
                    break;
                case NEW_LOW:
                    ret += Suffix.NEW_LOW;
                    break;
                case MOST_VOLATILE:
                    ret += Suffix.MOST_VOLATILE;
                    break;
                case MOST_ACTIVE:
                    ret += Suffix.MOST_ACTIVE;
                    break;
                case OVERBOUGHT:
                    ret += Suffix.OVERBOUGHT;
                    break;
                case OVERSOLD:
                    ret += Suffix.OVERSOLD;
                    break;
                case DOWNGRADES:
                    ret += Suffix.DOWNGRADES;
                    break;
                case UPGRADES:
                    ret += Suffix.UPGRADES;
                    break;
                case EARNINGS_BEFORE:
                    ret += Suffix.EARNINGS_BEFORE;
                    break;
                case EARNINGS_AFTER:
                    ret += Suffix.EARNINGS_AFTER;
                    break;
                case INSIDER_BUYING:
                    ret += Suffix.INSIDER_BUYING;
                    break;
                case INSIDER_SELLING:
                    ret += Suffix.INSIDER_SELLING;
                    break;
                case MAJOR_NEWS:
                    ret += Suffix.MAJOR_NEWS;
                    break;
                case HORIZONTAL_SR:
                    ret += Suffix.HORIZONTAL_SR;
                    break;
                case TL_RESISTANCE:
                    ret += Suffix.TL_RESISTANCE;
                    break;
                case TL_SUPPORT:
                    ret += Suffix.TL_SUPPORT;
                    break;
                case WEDGE_UP:
                    ret += Suffix.WEDGE_UP;
                    break;
                case WEDGE_DOWN:
                    ret += Suffix.WEDGE_DOWN;
                    break;
                case CHANNEL_UP: 
                    ret += Suffix.CHANNEL_UP;
                    break;
                case CHANNEL_DOWN: 
                    ret += Suffix.CHANNEL_DOWN;
                    break;
                case CHANNEL:
                    ret += Suffix.CHANNEL;
                    break;
                case DOUBLE_TOP:
                    ret += Suffix.DOUBLE_TOP;
                    break;
                case DOUBLE_BOTTOM:
                    ret += Suffix.DOUBLE_BOTTOM;
                    break;
                case MULTIPLE_TOP: 
                    ret += Suffix.MULTIPLE_TOP;
                    break;
                case MULTIPLE_BOTTOM:
                    ret += Suffix.MULTIPLE_BOTTOM;
                    break;
                case HEAD_AND_SHOULDERS:
                    ret += Suffix.HEAD_AND_SHOULDERS;
                    break;
                case HEAD_AND_SHOULDERS_INVERSE: 
                    ret += Suffix.HEAD_AND_SHOULDERS_INVERSE;
                    break;
            }
            if(urlFormatting && features.length > 0)
                ret += "&";
        }
// </editor-fold>
    
            if(features.length>0){
                if(urlFormatting)
                    ret += "f=";
                else if(signal != null)
                    ret += "/";
                
                int featureLength = features.length;
                int i = 0;
                
                for(i=0;i<featureLength;i++){
                    //Object currentFeature;
                    Class category = features[i].getClass();
                    String fullClassName = category.toString();
                    String subType = fullClassName.substring(fullClassName.lastIndexOf(CLASS_SEPARATOR_TOKEN) + 1);
                    System.out.println(subType);
// <editor-fold>
                    switch(subType){
                        case "Exchange":
                            
                            Exchange exchangeFeature = (Exchange)features[i];
                            switch(exchangeFeature){
                                case AMEX:
                                    ret += Suffix.EXCHANGE_AMEX;
                                    break;
                                case NASDAQ:
                                    ret += Suffix.EXCHANGE_NASD;
                                    break;
                                case NYSE:
                                    ret += Suffix.EXCHANGE_NYSE;
                                    break;
                            }
                            break;
                            
                        case "MarketCap":
                            MarketCap marketCapFeature = (MarketCap)features[i];
                            switch(marketCapFeature){
                                case MEGA:
                                    ret += Suffix.CAP_MEGA;
                                    break;
                                case LARGE:
                                    ret += Suffix.CAP_LARGE;
                                    break;
                                case MID:
                                    ret += Suffix.CAP_MID;
                                    break;
                                case SMALL:
                                    ret += Suffix.CAP_SMALL;
                                    break;
                                case MICRO:
                                    ret += Suffix.CAP_MICRO;
                                    break;
                                case NANO:
                                    ret += Suffix.CAP_NANO;
                                    break;
                                case LARGE_OVER:
                                    ret += Suffix.CAP_LARGE_OVER;
                                    break;
                                case MID_OVER:
                                    ret += Suffix.CAP_MID_OVER;
                                    break;
                                case SMALL_OVER:
                                    ret += Suffix.CAP_SMALL_OVER;
                                    break;
                                case MICRO_OVER:
                                    ret += Suffix.CAP_MICRO_OVER;
                                    break;
                                case LARGE_UNDER:
                                    ret += Suffix.CAP_LARGE_UNDER;
                                    break;
                                case MID_UNDER:
                                    ret += Suffix.CAP_MID_UNDER;
                                    break;
                                case SMALL_UNDER:
                                    ret += Suffix.CAP_SMALL_UNDER;
                                    break;
                                case MICRO_UNDER:
                                    ret += Suffix.CAP_MICRO_UNDER;
                                    break;
                            }
                            break;
                            
                        case "OptionShort":
                            OptionShort optionShortFeature = (OptionShort)features[i];
                            switch(optionShortFeature){
                                case OPTIONABLE:
                                    ret += Suffix.OPTSHORT_OPTIONABLE;
                                    break;
                                case SHORTABLE:
                                    ret += Suffix.OPTSHORT_SHORTABLE;
                                    break;
                                case OPTIONABLE_AND_SHORTABLE:
                                    ret += Suffix.OPTSHORT_OPTIONABLE_AND_SHORTABLE;
                                    break;
                            }
                            break;
                       
                        case "RelativeVolume":
                            RelativeVolume relVolFeature = (RelativeVolume)features[i];
                            switch(relVolFeature){
                                case OVER_10:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_10;
                                    break;
                                case OVER_5:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_5;
                                    break;
                                case OVER_3:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_3;
                                    break;
                                case OVER_2:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_2;
                                    break;
                                case OVER_1_POINT_5:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_1_POINT_5;
                                    break;
                                case OVER_1:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_1;
                                    break;
                                case OVER_POINT_75:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_POINT_75;
                                    break;
                                case OVER_POINT_5:
                                    ret += Suffix.RELATIVE_VOLUME_OVER_POINT_5;
                                    break;
                                case OVER_POINT_25: 
                                    ret += Suffix.RELATIVE_VOLUME_OVER_POINT_25;
                                    break;
                                case UNDER_2:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_2;
                                    break;
                                case UNDER_1_POINT_5:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_1_POINT_5;
                                    break;
                                case UNDER_1:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_1;
                                    break;
                                case UNDER_POINT_75:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_POINT_75;
                                    break;
                                case UNDER_POINT_5:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_POINT_5;
                                    break;
                                case UNDER_POINT_25:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_POINT_25;
                                    break;
                                case UNDER_POINT_1:
                                    ret += Suffix.RELATIVE_VOLUME_UNDER_POINT_1;
                                    break;
                            }
                            break;
                    }
// </editor-fold>
                    ret += ",";
                }
                if (i>0)
                    ret = ret.substring(0, ret.length() - 1);
            }
        return ret;
    }
    public static Long lookupPrimeID(String criteriaName)
    {
        return primeMap.get(criteriaName);
    }
}