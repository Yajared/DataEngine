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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    static class Suffix{
        // Signals
        public static final String UNUSUAL_VOLUME = "ta_unusualvolume";
        public static final String TOP_GAINERS = "ta_topgainers";
        public static final String TOP_LOSERS = "ta_toplosers";
        public static final String NEW_HIGH = "ta_newhigh";
        public static final String NEW_LOW = "ta_newlow";
        public static final String MOST_VOLATILE = "ta_mostvolative";
        public static final String MOST_ACTIVE = "ta_mostactive";
        public static final String OVERBOUGHT = "ta_overbought";
        public static final String OVERSOLD = "ta_oversold";
        public static final String DOWNGRADES = "n_downgrades";
        public static final String UPGRADES = "n_upgrades";
        public static final String EARNINGS_BEFORE = "n_earningsbefore";
        public static final String EARNINGS_AFTER = "n_earningsafter";
        public static final String INSIDER_BUYING = "it_latestbuys";
        public static final String INSIDER_SELLING = "it_latestsales";
        public static final String MAJOR_NEWS = "n_majornews";
        public static final String HORIZONTAL_SR = "ta_p_horizontal";
        public static final String TL_RESISTANCE = "ta_p_tlresistance";
        public static final String TL_SUPPORT = "ta_p_tlsupport";
        public static final String WEDGE_UP = "ta_p_wedgeup";
        public static final String WEDGE_DOWN = "ta_p_wedgedown";
        public static final String CHANNEL_UP = "ta_p_channelup";
        public static final String CHANNEL_DOWN = "ta_p_channeldown";
        public static final String CHANNEL = "ta_p_channel";
        public static final String DOUBLE_TOP = "ta_p_doubletop";
        public static final String DOUBLE_BOTTOM = "ta_p_doublebottom";
        public static final String MULTIPLE_TOP = "ta_p_multipletop";
        public static final String MULTIPLE_BOTTOM = "ta_p_multiplebottom";
        public static final String HEAD_AND_SHOULDERS = "ta_p_headandshoulders";
        public static final String HEAD_AND_SHOULDERS_INVERSE = "ta_p_headandshouldersinv";
        
        // Features
        public static final String EXCHANGE_NASDAQ = "exch_nasd";
        public static final String EXCHANGE_AMEX = "exch_amex";
        public static final String EXCHANGE_NYSE = "exch_nyse";
        public static final String INDEX_SANDP500 = "idx_sp500";
        public static final String INDEX_DJIA = "idx_dji";
        public static final String SECTOR_FINANCIAL = "sec_financial";
        public static final String SECTOR_TECHNOLOGY = "sec_technology";
        public static final String SECTOR_CONGLOMERATES = "sec_conglomerates";
        public static final String SECTOR_INDUSTRIAL_GOODS = "sec_industrialgoods";
        public static final String SECTOR_UTILITIES = "sec_utilities";
        public static final String SECTOR_BASIC_MATERIALS = "sec_basicmaterials";
        public static final String SECTOR_CONSUMER_GOODS = "sec_consumergoods";
        public static final String SECTOR_HEALTHCARE = "sec_healthcare";
        public static final String INDUSTRY_DRUG_RELATED_PRODUCTS = "ind_drugrelatedproducts";
        public static final String INDUSTRY_DIAGNOSTIC_SUBSTANCES = "ind_diagnosticsubstances";
        public static final String INDUSTRY_LONG_TERM_CARE_FACILITIES = "ind_longtermcarefacilities";
        public static final String INDUSTRY_DRUG_MANUFACTURERS_MAJOR = "ind_drugmanufacturersmajor";
        public static final String INDUSTRY_HOME_HEALTH_CARE = "ind_homehealthcare";
        public static final String INDUSTRY_STOCKS_ONLY_EX_FUNDS = "ind_stocksonly";
        public static final String INDUSTRY_HOSPITALS = "ind_hospitals";
        public static final String INDUSTRY_BIOTECHNOLOGY = "ind_biotechnology";
        public static final String INDUSTRY_DRUGS_GENERIC = "ind_drugsgeneric";
        public static final String INDUSTRY_DRUG_DELIVERY = "ind_drugdelivery";
        public static final String INDUSTRY_MEDICAL_INSTRUMENTS_AND_SUPPLIES = "ind_medicalinstrumentssupplies";
        public static final String INDUSTRY_SPECIALIZED_HEALTH_SERVICES = "ind_specializedhealthservices";
        public static final String INDUSTRY_HEALTH_CARE_PLANS = "ind_healthcareplans";
        public static final String INDUSTRY_MEDICAL_LABORATORIES_AND_RESEARCH = "ind_medicallaboratoriesresearch";
        public static final String INDUSTRY_DRUG_MANUFACTURERS_OTHER = "ind_drugmanufacturersother";
        public static final String INDUSTRY_MEDICAL_PRACTITIONERS = "ind_medicalpractitioners";
        public static final String INDUSTRY_MEDICAL_APPLIANCES_AND_EQUIPMENT = "ind_medicalappliancesequipment";
        public static final String COUNTRY_IRELAND = "geo_ireland";
        public static final String COUNTRY_BERMUDA = "geo_bermuda";
        public static final String COUNTRY_SPAIN = "geo_spain";
        public static final String COUNTRY_COLOMBIA = "geo_colombia";
        public static final String COUNTRY_CHANNEL_ISLANDS = "geo_channelislands";
        public static final String COUNTRY_INDONESIA = "geo_indonesia";
        public static final String COUNTRY_CHINA_AND_HONG_KONG = "geo_chinahongkong";
        public static final String COUNTRY_GERMANY = "geo_germany";
        public static final String COUNTRY_PORTUGAL = "geo_portugal";
        public static final String COUNTRY_BAHAMAS = "geo_bahamas";
        public static final String COUNTRY_NETHERLANDS_ANTILLES = "geo_netherlandsantilles";
        public static final String COUNTRY_CHINA = "geo_china";
        public static final String COUNTRY_SOUTH_KOREA = "geo_southkorea";
        public static final String COUNTRY_CANADA = "geo_canada";
        public static final String COUNTRY_HONG_KONG = "geo_hongkong";
        public static final String COUNTRY_MEXICO = "geo_mexico";
        public static final String COUNTRY_FINLAND = "geo_finland";
        public static final String COUNTRY_BELGIUM = "geo_belgium";
        public static final String COUNTRY_MALAYSIA = "geo_malaysia";
        public static final String COUNTRY_UNITED_KINGDOM = "geo_unitedkingdom";
        public static final String COUNTRY_ICELAND = "geo_iceland";
        public static final String COUNTRY_AUSTRALIA = "geo_australia";
        public static final String COUNTRY_PHILIPPINES = "geo_philippines";
        public static final String COUNTRY_MARSHALL_ISLANDS = "geo_marshallislands";
        public static final String COUNTRY_SOUTH_AFRICA = "geo_southafrica";
        public static final String COUNTRY_EUROPE = "geo_europe";
        public static final String COUNTRY_PERU = "geo_peru";
        public static final String COUNTRY_PAPUA_NEW_GUINEA = "geo_papuanewguinea";
        public static final String COUNTRY_FOREIGN_EX_USA = "geo_notusa";
        public static final String COUNTRY_MONACO = "geo_monaco";
        public static final String COUNTRY_ISRAEL = "geo_israel";
        public static final String COUNTRY_USA = "geo_usa";
        public static final String COUNTRY_SINGAPORE = "geo_singapore";
        public static final String COUNTRY_NORWAY = "geo_norway";
        public static final String COUNTRY_ARGENTINA = "geo_argentina";
        public static final String COUNTRY_CHILE = "geo_chile";
        public static final String COUNTRY_LATIN_AMERICA = "geo_latinamerica";
        public static final String COUNTRY_JAPAN = "geo_japan";
        public static final String COUNTRY_BRITISH_VIRGIN_ISLANDS = "geo_britishvirginislands";
        public static final String COUNTRY_ASIA = "geo_asia";
        public static final String COUNTRY_SWITZERLAND = "geo_switzerland";
        public static final String COUNTRY_FRANCE = "geo_france";
        public static final String COUNTRY_INDIA = "geo_india";
        public static final String COUNTRY_ITALY = "geo_italy";
        public static final String COUNTRY_SWEDEN = "geo_sweden";
        public static final String COUNTRY_HUNGARY = "geo_hungary";
        public static final String COUNTRY_KAZAKHSTAN = "geo_kazakhstan";
        public static final String COUNTRY_TAIWAN = "geo_taiwan";
        public static final String COUNTRY_GREECE = "geo_greece";
        public static final String COUNTRY_UNITED_ARAB_EMIRATES = "geo_unitedarabemirates";
        public static final String COUNTRY_CYPRUS = "geo_cyprus";
        public static final String COUNTRY_NETHERLANDS = "geo_netherlands";
        public static final String COUNTRY_LUXEMBOURG = "geo_luxembourg";
        public static final String COUNTRY_BENELUX = "geo_benelux";
        public static final String COUNTRY_PANAMA = "geo_panama";
        public static final String COUNTRY_BRAZIL = "geo_brazil";
        public static final String COUNTRY_RUSSIA = "geo_russia";
        public static final String COUNTRY_NEW_ZEALAND = "geo_newzealand";
        public static final String COUNTRY_TURKEY = "geo_turkey";
        public static final String COUNTRY_DENMARK = "geo_denmark";
        public static final String COUNTRY_BRIC = "geo_bric";
        public static final String COUNTRY_CAYMAN_ISLANDS = "geo_caymanislands";
        public static final String MARKET_CAP_LESS_SMALL = "cap_smallunder";
        public static final String MARKET_CAP_MORE_MID = "cap_midover";
        public static final String MARKET_CAP_LESS_LARGE = "cap_largeunder";
        public static final String MARKET_CAP_NANO = "cap_nano";
        public static final String MARKET_CAP_LESS_MID = "cap_midunder";
        public static final String MARKET_CAP_MICRO = "cap_micro";
        public static final String MARKET_CAP_MORE_SMALL = "cap_smallover";
        public static final String MARKET_CAP_MEGA = "cap_mega";
        public static final String MARKET_CAP_MORE_MICRO = "cap_microover";
        public static final String MARKET_CAP_MID = "cap_mid";
        public static final String MARKET_CAP_LESS_MICRO = "cap_microunder";
        public static final String MARKET_CAP_SMALL = "cap_small";
        public static final String MARKET_CAP_MORE_LARGE = "cap_largeover";
        public static final String MARKET_CAP_LARGE = "cap_large";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_10 = "fa_pe_u10";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_20 = "fa_pe_u20";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_5 = "fa_pe_o5";
        public static final String PRICE_TO_EARNINGS_RATIO_PROFITABLE = "fa_pe_profitable";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_15 = "fa_pe_o15";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_30 = "fa_pe_o30";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_30 = "fa_pe_u30";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_50 = "fa_pe_o50";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_25 = "fa_pe_o25";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_40 = "fa_pe_u40";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_40 = "fa_pe_o40";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_50 = "fa_pe_u50";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_15 = "fa_pe_u15";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_45 = "fa_pe_u45";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_25 = "fa_pe_u25";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_35 = "fa_pe_u35";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_10 = "fa_pe_o10";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_35 = "fa_pe_o35";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_20 = "fa_pe_o20";
        public static final String PRICE_TO_EARNINGS_RATIO_OVER_45 = "fa_pe_o45";
        public static final String PRICE_TO_EARNINGS_RATIO_UNDER_5 = "fa_pe_u5";
        public static final String PRICE_TO_EARNINGS_RATIO_HIGH = "fa_pe_high";
        public static final String PRICE_TO_EARNINGS_RATIO_LOW = "fa_pe_low";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_10 = "fa_fpe_u10";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_20 = "fa_fpe_u20";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_5 = "fa_fpe_o5";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_PROFITABLE = "fa_fpe_profitable";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_15 = "fa_fpe_o15";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_30 = "fa_fpe_o30";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_30 = "fa_fpe_u30";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_50 = "fa_fpe_o50";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_25 = "fa_fpe_o25";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_40 = "fa_fpe_u40";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_40 = "fa_fpe_o40";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_50 = "fa_fpe_u50";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_15 = "fa_fpe_u15";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_45 = "fa_fpe_u45";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_25 = "fa_fpe_u25";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_35 = "fa_fpe_u35";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_10 = "fa_fpe_o10";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_35 = "fa_fpe_o35";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_20 = "fa_fpe_o20";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_45 = "fa_fpe_o45";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_5 = "fa_fpe_u5";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_HIGH = "fa_fpe_high";
        public static final String FORWARD_PRICE_TO_EARNINGS_RATIO_LOW = "fa_fpe_low";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_OVER_2 = "fa_peg_o2";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_OVER_1 = "fa_peg_o1";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_UNDER_2 = "fa_peg_u2";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_OVER_3 = "fa_peg_o3";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_UNDER_1 = "fa_peg_u1";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_UNDER_3 = "fa_peg_u3";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_HIGH = "fa_peg_high";
        public static final String PRICE_TO_EARNINGS_TO_GROWTH_LOW = "fa_peg_low";
        public static final String PRICE_TO_SALES_RATIO_UNDER_10 = "fa_ps_u10";
        public static final String PRICE_TO_SALES_RATIO_OVER_2 = "fa_ps_o2";
        public static final String PRICE_TO_SALES_RATIO_OVER_1 = "fa_ps_o1";
        public static final String PRICE_TO_SALES_RATIO_OVER_4 = "fa_ps_o4";
        public static final String PRICE_TO_SALES_RATIO_UNDER_2 = "fa_ps_u2";
        public static final String PRICE_TO_SALES_RATIO_OVER_3 = "fa_ps_o3";
        public static final String PRICE_TO_SALES_RATIO_UNDER_1 = "fa_ps_u1";
        public static final String PRICE_TO_SALES_RATIO_OVER_6 = "fa_ps_o6";
        public static final String PRICE_TO_SALES_RATIO_UNDER_4 = "fa_ps_u4";
        public static final String PRICE_TO_SALES_RATIO_OVER_5 = "fa_ps_o5";
        public static final String PRICE_TO_SALES_RATIO_UNDER_3 = "fa_ps_u3";
        public static final String PRICE_TO_SALES_RATIO_OVER_8 = "fa_ps_o8";
        public static final String PRICE_TO_SALES_RATIO_OVER_7 = "fa_ps_o7";
        public static final String PRICE_TO_SALES_RATIO_OVER_9 = "fa_ps_o9";
        public static final String PRICE_TO_SALES_RATIO_OVER_10 = "fa_ps_o10";
        public static final String PRICE_TO_SALES_RATIO_UNDER_7 = "fa_ps_u7";
        public static final String PRICE_TO_SALES_RATIO_UNDER_8 = "fa_ps_u8";
        public static final String PRICE_TO_SALES_RATIO_UNDER_5 = "fa_ps_u5";
        public static final String PRICE_TO_SALES_RATIO_HIGH = "fa_ps_high";
        public static final String PRICE_TO_SALES_RATIO_UNDER_6 = "fa_ps_u6";
        public static final String PRICE_TO_SALES_RATIO_LOW = "fa_ps_low";
        public static final String PRICE_TO_SALES_RATIO_UNDER_9 = "fa_ps_u9";
        public static final String PRICE_TO_BOOK_RATIO_OVER_2 = "fa_pb_o2";
        public static final String PRICE_TO_BOOK_RATIO_OVER_1 = "fa_pb_o1";
        public static final String PRICE_TO_BOOK_RATIO_UNDER_2 = "fa_pb_u2";
        public static final String PRICE_TO_BOOK_RATIO_OVER_3 = "fa_pb_o3";
        public static final String PRICE_TO_BOOK_RATIO_UNDER_1 = "fa_pb_u1";
        public static final String PRICE_TO_BOOK_RATIO_UNDER_3 = "fa_pb_u3";
        public static final String PRICE_TO_BOOK_RATIO_HIGH = "fa_pb_high";
        public static final String PRICE_TO_BOOK_RATIO_LOW = "fa_pb_low";
        public static final String PRICE_TO_CASH_RATIO_OVER_40 = "fa_pc_o40";
        public static final String PRICE_TO_CASH_RATIO_OVER_20 = "fa_pc_o20";
        public static final String PRICE_TO_CASH_RATIO_UNDER_7 = "fa_pc_u7";
        public static final String PRICE_TO_CASH_RATIO_UNDER_8 = "fa_pc_u8";
        public static final String PRICE_TO_CASH_RATIO_UNDER_5 = "fa_pc_u5";
        public static final String PRICE_TO_CASH_RATIO_UNDER_6 = "fa_pc_u6";
        public static final String PRICE_TO_CASH_RATIO_UNDER_9 = "fa_pc_u9";
        public static final String PRICE_TO_CASH_RATIO_UNDER_10 = "fa_pc_u10";
        public static final String PRICE_TO_CASH_RATIO_OVER_2 = "fa_pc_o2";
        public static final String PRICE_TO_CASH_RATIO_OVER_1 = "fa_pc_o1";
        public static final String PRICE_TO_CASH_RATIO_UNDER_2 = "fa_pc_u2";
        public static final String PRICE_TO_CASH_RATIO_OVER_4 = "fa_pc_o4";
        public static final String PRICE_TO_CASH_RATIO_UNDER_1 = "fa_pc_u1";
        public static final String PRICE_TO_CASH_RATIO_OVER_3 = "fa_pc_o3";
        public static final String PRICE_TO_CASH_RATIO_UNDER_4 = "fa_pc_u4";
        public static final String PRICE_TO_CASH_RATIO_OVER_6 = "fa_pc_o6";
        public static final String PRICE_TO_CASH_RATIO_UNDER_3 = "fa_pc_u3";
        public static final String PRICE_TO_CASH_RATIO_OVER_5 = "fa_pc_o5";
        public static final String PRICE_TO_CASH_RATIO_OVER_8 = "fa_pc_o8";
        public static final String PRICE_TO_CASH_RATIO_OVER_7 = "fa_pc_o7";
        public static final String PRICE_TO_CASH_RATIO_OVER_30 = "fa_pc_o30";
        public static final String PRICE_TO_CASH_RATIO_OVER_50 = "fa_pc_o50";
        public static final String PRICE_TO_CASH_RATIO_OVER_9 = "fa_pc_o9";
        public static final String PRICE_TO_CASH_RATIO_OVER_10 = "fa_pc_o10";
        public static final String PRICE_TO_CASH_RATIO_HIGH = "fa_pc_high";
        public static final String PRICE_TO_CASH_RATIO_LOW = "fa_pc_low";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_20 = "fa_pfcf_u20";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_70 = "fa_pfcf_u70";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_80 = "fa_pfcf_o80";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_60 = "fa_pfcf_o60";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_40 = "fa_pfcf_u40";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_25 = "fa_pfcf_o25";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_40 = "fa_pfcf_o40";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_45 = "fa_pfcf_u45";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_90 = "fa_pfcf_u90";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_25 = "fa_pfcf_u25";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_60 = "fa_pfcf_u60";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_20 = "fa_pfcf_o20";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_45 = "fa_pfcf_o45";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_5 = "fa_pfcf_u5";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_10 = "fa_pfcf_u10";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_70 = "fa_pfcf_o70";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_5 = "fa_pfcf_o5";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_15 = "fa_pfcf_o15";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_80 = "fa_pfcf_u80";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_30 = "fa_pfcf_o30";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_30 = "fa_pfcf_u30";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_50 = "fa_pfcf_o50";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_50 = "fa_pfcf_u50";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_15 = "fa_pfcf_u15";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_90 = "fa_pfcf_o90";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_35 = "fa_pfcf_u35";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_100 = "fa_pfcf_o100";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_10 = "fa_pfcf_o10";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_35 = "fa_pfcf_o35";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_HIGH = "fa_pfcf_high";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_LOW = "fa_pfcf_low";
        public static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_100 = "fa_pfcf_u100";
        public static final String EPS_GROWTH_THIS_YEAR_NEGATIVE = "fa_epsyoy_neg";
        public static final String EPS_GROWTH_THIS_YEAR_UNDER_10_PERCENT = "fa_epsyoy_u10";
        public static final String EPS_GROWTH_THIS_YEAR_UNDER_20_PERCENT = "fa_epsyoy_u20";
        public static final String EPS_GROWTH_THIS_YEAR_OVER_5_PERCENT = "fa_epsyoy_o5";
        public static final String EPS_GROWTH_THIS_YEAR_OVER_15_PERCENT = "fa_epsyoy_o15";
        public static final String EPS_GROWTH_THIS_YEAR_OVER_30_PERCENT = "fa_epsyoy_o30";
        public static final String EPS_GROWTH_THIS_YEAR_UNDER_30_PERCENT = "fa_epsyoy_u30";
        public static final String EPS_GROWTH_THIS_YEAR_OVER_25_PERCENT = "fa_epsyoy_o25";
        public static final String EPS_GROWTH_THIS_YEAR_UNDER_15_PERCENT = "fa_epsyoy_u15";
        public static final String EPS_GROWTH_THIS_YEAR_POSITIVE = "fa_epsyoy_pos";
        public static final String EPS_GROWTH_THIS_YEAR__UNDER_25_PERCENT = "fa_epsyoy_u25";
        public static final String EPS_GROWTH_THIS_YEAR_POSITIVE_LOW = "fa_epsyoy_poslow";
        public static final String EPS_GROWTH_THIS_YEAR_OVER_10_PERCENT = "fa_epsyoy_o10";
        public static final String EPS_GROWTH_THIS_YEAR_OVER_20_PERCENT = "fa_epsyoy_o20";
        public static final String EPS_GROWTH_THIS_YEAR_UNDER_5_PERCENT = "fa_epsyoy_u5";
        public static final String EPS_GROWTH_THIS_YEAR_HIGH = "fa_epsyoy_high";
        public static final String EPS_GROWTH_NEXT_YEAR_NEGATIVE = "fa_epsyoy1_neg";
        public static final String EPS_GROWTH_NEXT_YEAR_UNDER_10_PERCENT = "fa_epsyoy1_u10";
        public static final String EPS_GROWTH_NEXT_YEAR_UNDER_20_PERCENT = "fa_epsyoy1_u20";
        public static final String EPS_GROWTH_NEXT_YEAR_OVER_5_PERCENT = "fa_epsyoy1_o5";
        public static final String EPS_GROWTH_NEXT_YEAR_OVER_15_PERCENT = "fa_epsyoy1_o15";
        public static final String EPS_GROWTH_NEXT_YEAR_OVER_30_PERCENT = "fa_epsyoy1_o30";
        public static final String EPS_GROWTH_NEXT_YEAR_UNDER_30_PERCENT = "fa_epsyoy1_u30";
        public static final String EPS_GROWTH_NEXT_YEAR_OVER_25_PERCENT = "fa_epsyoy1_o25";
        public static final String EPS_GROWTH_NEXT_YEAR_UNDER_15_PERCENT = "fa_epsyoy1_u15";
        public static final String EPS_GROWTH_NEXT_YEAR_POSITIVE = "fa_epsyoy1_pos";
        public static final String EPS_GROWTH_NEXT_YEAR__UNDER_25_PERCENT = "fa_epsyoy1_u25";
        public static final String EPS_GROWTH_NEXT_YEAR_POSITIVE_LOW = "fa_epsyoy1_poslow";
        public static final String EPS_GROWTH_NEXT_YEAR_OVER_10_PERCENT = "fa_epsyoy1_o10";
        public static final String EPS_GROWTH_NEXT_YEAR_OVER_20_PERCENT = "fa_epsyoy1_o20";
        public static final String EPS_GROWTH_NEXT_YEAR_UNDER_5_PERCENT = "fa_epsyoy1_u5";
        public static final String EPS_GROWTH_NEXT_YEAR_HIGH = "fa_epsyoy1_high";
        public static final String EPS_GROWTH_PAST_5_YEARS_NEGATIVE = "fa_eps5years_neg";
        public static final String EPS_GROWTH_PAST_5_YEARS_UNDER_10_PERCENT = "fa_eps5years_u10";
        public static final String EPS_GROWTH_PAST_5_YEARS_UNDER_20_PERCENT = "fa_eps5years_u20";
        public static final String EPS_GROWTH_PAST_5_YEARS_OVER_5_PERCENT = "fa_eps5years_o5";
        public static final String EPS_GROWTH_PAST_5_YEARS_OVER_15_PERCENT = "fa_eps5years_o15";
        public static final String EPS_GROWTH_PAST_5_YEARS_OVER_30_PERCENT = "fa_eps5years_o30";
        public static final String EPS_GROWTH_PAST_5_YEARS_UNDER_30_PERCENT = "fa_eps5years_u30";
        public static final String EPS_GROWTH_PAST_5_YEARS_OVER_25_PERCENT = "fa_eps5years_o25";
        public static final String EPS_GROWTH_PAST_5_YEARS_UNDER_15_PERCENT = "fa_eps5years_u15";
        public static final String EPS_GROWTH_PAST_5_YEARS_POSITIVE = "fa_eps5years_pos";
        public static final String EPS_GROWTH_PAST_5_YEARS__UNDER_25_PERCENT = "fa_eps5years_u25";
        public static final String EPS_GROWTH_PAST_5_YEARS_POSITIVE_LOW = "fa_eps5years_poslow";
        public static final String EPS_GROWTH_PAST_5_YEARS_OVER_10_PERCENT = "fa_eps5years_o10";
        public static final String EPS_GROWTH_PAST_5_YEARS_OVER_20_PERCENT = "fa_eps5years_o20";
        public static final String EPS_GROWTH_PAST_5_YEARS_UNDER_5_PERCENT = "fa_eps5years_u5";
        public static final String EPS_GROWTH_PAST_5_YEARS_HIGH = "fa_eps5years_high";
        public static final String EPS_GROWTH_NEXT_5_YEARS_NEGATIVE = "fa_estltgrowth_neg";
        public static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_10_PERCENT = "fa_estltgrowth_u10";
        public static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_20_PERCENT = "fa_estltgrowth_u20";
        public static final String EPS_GROWTH_NEXT_5_YEARS_OVER_5_PERCENT = "fa_estltgrowth_o5";
        public static final String EPS_GROWTH_NEXT_5_YEARS_OVER_15_PERCENT = "fa_estltgrowth_o15";
        public static final String EPS_GROWTH_NEXT_5_YEARS_OVER_30_PERCENT = "fa_estltgrowth_o30";
        public static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_30_PERCENT = "fa_estltgrowth_u30";
        public static final String EPS_GROWTH_NEXT_5_YEARS_OVER_25_PERCENT = "fa_estltgrowth_o25";
        public static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_15_PERCENT = "fa_estltgrowth_u15";
        public static final String EPS_GROWTH_NEXT_5_YEARS_POSITIVE = "fa_estltgrowth_pos";
        public static final String EPS_GROWTH_NEXT_5_YEARS__UNDER_25_PERCENT = "fa_estltgrowth_u25";
        public static final String EPS_GROWTH_NEXT_5_YEARS_POSITIVE_LOW = "fa_estltgrowth_poslow";
        public static final String EPS_GROWTH_NEXT_5_YEARS_OVER_10_PERCENT = "fa_estltgrowth_o10";
        public static final String EPS_GROWTH_NEXT_5_YEARS_OVER_20_PERCENT = "fa_estltgrowth_o20";
        public static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_5_PERCENT = "fa_estltgrowth_u5";
        public static final String EPS_GROWTH_NEXT_5_YEARS_HIGH = "fa_estltgrowth_high";
        public static final String SALES_GROWTH_PAST_5_YEARS_NEGATIVE = "fa_sales5years_neg";
        public static final String SALES_GROWTH_PAST_5_YEARS_UNDER_10_PERCENT = "fa_sales5years_u10";
        public static final String SALES_GROWTH_PAST_5_YEARS_UNDER_20_PERCENT = "fa_sales5years_u20";
        public static final String SALES_GROWTH_PAST_5_YEARS_OVER_5_PERCENT = "fa_sales5years_o5";
        public static final String SALES_GROWTH_PAST_5_YEARS_OVER_15_PERCENT = "fa_sales5years_o15";
        public static final String SALES_GROWTH_PAST_5_YEARS_OVER_30_PERCENT = "fa_sales5years_o30";
        public static final String SALES_GROWTH_PAST_5_YEARS_UNDER_30_PERCENT = "fa_sales5years_u30";
        public static final String SALES_GROWTH_PAST_5_YEARS_OVER_25_PERCENT = "fa_sales5years_o25";
        public static final String SALES_GROWTH_PAST_5_YEARS_UNDER_15_PERCENT = "fa_sales5years_u15";
        public static final String SALES_GROWTH_PAST_5_YEARS_POSITIVE = "fa_sales5years_pos";
        public static final String SALES_GROWTH_PAST_5_YEARS_UNDER_25_PERCENT = "fa_sales5years_u25";
        public static final String SALES_GROWTH_PAST_5_YEARS_POSITIVE_LOW = "fa_sales5years_poslow";
        public static final String SALES_GROWTH_PAST_5_YEARS_OVER_10_PERCENT = "fa_sales5years_o10";
        public static final String SALES_GROWTH_PAST_5_YEARS_OVER_20_PERCENT = "fa_sales5years_o20";
        public static final String SALES_GROWTH_PAST_5_YEARS_UNDER_5_PERCENT = "fa_sales5years_u5";
        public static final String SALES_GROWTH_PAST_5_YEARS_HIGH = "fa_sales5years_high";
        public static final String EPS_GROWTH_QTR_OVER_QTR_NEGATIVE = "fa_epsqoq_neg";
        public static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_10_PERCENT = "fa_epsqoq_u10";
        public static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_20_PERCENT = "fa_epsqoq_u20";
        public static final String EPS_GROWTH_QTR_OVER_QTR_OVER_5_PERCENT = "fa_epsqoq_o5";
        public static final String EPS_GROWTH_QTR_OVER_QTR_OVER_15_PERCENT = "fa_epsqoq_o15";
        public static final String EPS_GROWTH_QTR_OVER_QTR_OVER_30_PERCENT = "fa_epsqoq_o30";
        public static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_30_PERCENT = "fa_epsqoq_u30";
        public static final String EPS_GROWTH_QTR_OVER_QTR_OVER_25_PERCENT = "fa_epsqoq_o25";
        public static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_15_PERCENT = "fa_epsqoq_u15";
        public static final String EPS_GROWTH_QTR_OVER_QTR_POSITIVE = "fa_epsqoq_pos";
        public static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_25_PERCENT = "fa_epsqoq_u25";
        public static final String EPS_GROWTH_QTR_OVER_QTR_POSITIVE_LOW = "fa_epsqoq_poslow";
        public static final String EPS_GROWTH_QTR_OVER_QTR_OVER_10_PERCENT = "fa_epsqoq_o10";
        public static final String EPS_GROWTH_QTR_OVER_QTR_OVER_20_PERCENT = "fa_epsqoq_o20";
        public static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_5_PERCENT = "fa_epsqoq_u5";
        public static final String EPS_GROWTH_QTR_OVER_QTR_HIGH = "fa_epsqoq_high";
        public static final String SALES_GROWTH_QTR_OVER_QTR_NEGATIVE = "fa_salesqoq_neg";
        public static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_10_PERCENT = "fa_salesqoq_u10";
        public static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_20_PERCENT = "fa_salesqoq_u20";
        public static final String SALES_GROWTH_QTR_OVER_QTR_OVER_5_PERCENT = "fa_salesqoq_o5";
        public static final String SALES_GROWTH_QTR_OVER_QTR_OVER_15_PERCENT = "fa_salesqoq_o15";
        public static final String SALES_GROWTH_QTR_OVER_QTR_OVER_30_PERCENT = "fa_salesqoq_o30";
        public static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_30_PERCENT = "fa_salesqoq_u30";
        public static final String SALES_GROWTH_QTR_OVER_QTR_OVER_25_PERCENT = "fa_salesqoq_o25";
        public static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_15_PERCENT = "fa_salesqoq_u15";
        public static final String SALES_GROWTH_QTR_OVER_QTR_POSITIVE = "fa_salesqoq_pos";
        public static final String SALES_GROWTH_QTR_OVER_QTR__UNDER_25_PERCENT = "fa_salesqoq_u25";
        public static final String SALES_GROWTH_QTR_OVER_QTR_POSITIVE_LOW = "fa_salesqoq_poslow";
        public static final String SALES_GROWTH_QTR_OVER_QTR_OVER_10_PERCENT = "fa_salesqoq_o10";
        public static final String SALES_GROWTH_QTR_OVER_QTR_OVER_20_PERCENT = "fa_salesqoq_o20";
        public static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_5_PERCENT = "fa_salesqoq_u5";
        public static final String SALES_GROWTH_QTR_OVER_QTR_HIGH = "fa_salesqoq_high";
        public static final String DIVIDEND_YIELD_OVER_2_PERCENT = "fa_div_o2";
        public static final String DIVIDEND_YIELD_OVER_1_PERCENT = "fa_div_o1";
        public static final String DIVIDEND_YIELD_VERY_HIGH = "fa_div_veryhigh";
        public static final String DIVIDEND_YIELD_OVER_4_PERCENT = "fa_div_o4";
        public static final String DIVIDEND_YIELD_OVER_3_PERCENT = "fa_div_o3";
        public static final String DIVIDEND_YIELD_OVER_6_PERCENT = "fa_div_o6";
        public static final String DIVIDEND_YIELD_OVER_5_PERCENT = "fa_div_o5";
        public static final String DIVIDEND_YIELD_OVER_8_PERCENT = "fa_div_o8";
        public static final String DIVIDEND_YIELD_OVER_7_PERCENT = "fa_div_o7";
        public static final String DIVIDEND_YIELD_OVER_9_PERCENT = "fa_div_o9";
        public static final String DIVIDEND_YIELD_POSITIVE = "fa_div_pos";
        public static final String DIVIDEND_YIELD_OVER_10_PERCENT = "fa_div_o10";
        public static final String DIVIDEND_YIELD_NONE = "fa_div_none";
        public static final String DIVIDEND_YIELD_HIGH = "fa_div_high";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_roa_u-10";
        public static final String RETURN_ON_ASSETS_TTM_NEGATIVE = "fa_roa_neg";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_35_PERCENT = "fa_roa_u-35";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_25_PERCENT = "fa_roa_u-25";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_45_PERCENT = "fa_roa_u-45";
        public static final String RETURN_ON_ASSETS_TTM_VERY_POSITIVE = "fa_roa_verypos";
        public static final String RETURN_ON_ASSETS_TTM_OVER_5_PERCENT = "fa_roa_o5";
        public static final String RETURN_ON_ASSETS_TTM_OVER_15_PERCENT = "fa_roa_o15";
        public static final String RETURN_ON_ASSETS_TTM_OVER_30_PERCENT = "fa_roa_o30";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_40_PERCENT = "fa_roa_u-40";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_15_PERCENT = "fa_roa_u-15";
        public static final String RETURN_ON_ASSETS_TTM_OVER_50_PERCENT = "fa_roa_o50";
        public static final String RETURN_ON_ASSETS_TTM_OVER_25_PERCENT = "fa_roa_o25";
        public static final String RETURN_ON_ASSETS_TTM_OVER_40_PERCENT = "fa_roa_o40";
        public static final String RETURN_ON_ASSETS_TTM_VERY_NEGATIVE = "fa_roa_veryneg";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_roa_u-50";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_roa_u-30";
        public static final String RETURN_ON_ASSETS_TTM_POSITIVE = "fa_roa_pos";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_roa_u-20";
        public static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_5_PERCENT = "fa_roa_u-5";
        public static final String RETURN_ON_ASSETS_TTM_OVER_10_PERCENT = "fa_roa_o10";
        public static final String RETURN_ON_ASSETS_TTM_OVER_35_PERCENT = "fa_roa_o35";
        public static final String RETURN_ON_ASSETS_TTM_OVER_20_PERCENT = "fa_roa_o20";
        public static final String RETURN_ON_ASSETS_TTM_OVER_45 = "fa_roa_o45";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_roe_u-10";
        public static final String RETURN_ON_EQUITY_TTM_NEGATIVE = "fa_roe_neg";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_35_PERCENT = "fa_roe_u-35";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_25_PERCENT = "fa_roe_u-25";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_45_PERCENT = "fa_roe_u-45";
        public static final String RETURN_ON_EQUITY_TTM_VERY_POSITIVE = "fa_roe_verypos";
        public static final String RETURN_ON_EQUITY_TTM_OVER_5_PERCENT = "fa_roe_o5";
        public static final String RETURN_ON_EQUITY_TTM_OVER_15_PERCENT = "fa_roe_o15";
        public static final String RETURN_ON_EQUITY_TTM_OVER_30_PERCENT = "fa_roe_o30";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_40_PERCENT = "fa_roe_u-40";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_15_PERCENT = "fa_roe_u-15";
        public static final String RETURN_ON_EQUITY_TTM_OVER_50_PERCENT = "fa_roe_o50";
        public static final String RETURN_ON_EQUITY_TTM_OVER_25_PERCENT = "fa_roe_o25";
        public static final String RETURN_ON_EQUITY_TTM_OVER_40_PERCENT = "fa_roe_o40";
        public static final String RETURN_ON_EQUITY_TTM_VERY_NEGATIVE = "fa_roe_veryneg";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_roe_u-50";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_roe_u-30";
        public static final String RETURN_ON_EQUITY_TTM_POSITIVE = "fa_roe_pos";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_roe_u-20";
        public static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_5_PERCENT = "fa_roe_u-5";
        public static final String RETURN_ON_EQUITY_TTM_OVER_10_PERCENT = "fa_roe_o10";
        public static final String RETURN_ON_EQUITY_TTM_OVER_35_PERCENT = "fa_roe_o35";
        public static final String RETURN_ON_EQUITY_TTM_OVER_20_PERCENT = "fa_roe_o20";
        public static final String RETURN_ON_EQUITY_TTM_OVER_45 = "fa_roe_o45";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_roi_u-10";
        public static final String RETURN_ON_INVESTMENT_TTM_NEGATIVE = "fa_roi_neg";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_35_PERCENT = "fa_roi_u-35";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_25_PERCENT = "fa_roi_u-25";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_45_PERCENT = "fa_roi_u-45";
        public static final String RETURN_ON_INVESTMENT_TTM_VERY_POSITIVE = "fa_roi_verypos";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_5_PERCENT = "fa_roi_o5";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_15_PERCENT = "fa_roi_o15";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_30_PERCENT = "fa_roi_o30";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_40_PERCENT = "fa_roi_u-40";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_15_PERCENT = "fa_roi_u-15";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_50_PERCENT = "fa_roi_o50";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_25_PERCENT = "fa_roi_o25";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_40_PERCENT = "fa_roi_o40";
        public static final String RETURN_ON_INVESTMENT_TTM_VERY_NEGATIVE = "fa_roi_veryneg";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_roi_u-50";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_roi_u-30";
        public static final String RETURN_ON_INVESTMENT_TTM_POSITIVE = "fa_roi_pos";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_roi_u-20";
        public static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_5_PERCENT = "fa_roi_u-5";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_10_PERCENT = "fa_roi_o10";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_35_PERCENT = "fa_roi_o35";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_20_PERCENT = "fa_roi_o20";
        public static final String RETURN_ON_INVESTMENT_TTM_OVER_45 = "fa_roi_o45";
        public static final String CURRENT_RATIO_MRQ_OVER_0_POINT_5 = "fa_curratio_o0.5";
        public static final String CURRENT_RATIO_MRQ_OVER_2 = "fa_curratio_o2";
        public static final String CURRENT_RATIO_MRQ_OVER_1 = "fa_curratio_o1";
        public static final String CURRENT_RATIO_MRQ_OVER_10 = "fa_curratio_o10";
        public static final String CURRENT_RATIO_MRQ_OVER_4 = "fa_curratio_o4";
        public static final String CURRENT_RATIO_MRQ_OVER_3 = "fa_curratio_o3";
        public static final String CURRENT_RATIO_MRQ_UNDER_1 = "fa_curratio_u1";
        public static final String CURRENT_RATIO_MRQ_OVER_5 = "fa_curratio_o5";
        public static final String CURRENT_RATIO_MRQ_OVER_1_POINT_5 = "fa_curratio_o1.5";
        public static final String CURRENT_RATIO_MRQ_UNDER_0_POINT_5 = "fa_curratio_u0.5";
        public static final String CURRENT_RATIO_MRQ_HIGH = "fa_curratio_high";
        public static final String CURRENT_RATIO_MRQ_LOW = "fa_curratio_low";
        public static final String QUICK_RATIO_MRQ_OVER_0_POINT_5 = "fa_quickratio_o0.5";
        public static final String QUICK_RATIO_MRQ_OVER_2 = "fa_quickratio_o2";
        public static final String QUICK_RATIO_MRQ_OVER_1 = "fa_quickratio_o1";
        public static final String QUICK_RATIO_MRQ_OVER_10 = "fa_quickratio_o10";
        public static final String QUICK_RATIO_MRQ_OVER_4 = "fa_quickratio_o4";
        public static final String QUICK_RATIO_MRQ_OVER_3 = "fa_quickratio_o3";
        public static final String QUICK_RATIO_MRQ_UNDER_1 = "fa_quickratio_u1";
        public static final String QUICK_RATIO_MRQ_OVER_5 = "fa_quickratio_o5";
        public static final String QUICK_RATIO_MRQ_OVER_1_POINT_5 = "fa_quickratio_o1.5";
        public static final String QUICK_RATIO_MRQ_UNDER_0_POINT_5 = "fa_quickratio_u0.5";
        public static final String QUICK_RATIO_MRQ_HIGH = "fa_quickratio_high";
        public static final String QUICK_RATIO_MRQ_LOW = "fa_quickratio_low";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_5 = "fa_ltdebteq_o0.5";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_6 = "fa_ltdebteq_o0.6";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_7 = "fa_ltdebteq_o0.7";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_1 = "fa_ltdebteq_o1";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_8 = "fa_ltdebteq_o0.8";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_9 = "fa_ltdebteq_o0.9";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_1 = "fa_ltdebteq_u1";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_5 = "fa_ltdebteq_u0.5";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_6 = "fa_ltdebteq_u0.6";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_3 = "fa_ltdebteq_u0.3";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_4 = "fa_ltdebteq_u0.4";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_1 = "fa_ltdebteq_o0.1";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_9 = "fa_ltdebteq_u0.9";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_2 = "fa_ltdebteq_o0.2";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_3 = "fa_ltdebteq_o0.3";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_7 = "fa_ltdebteq_u0.7";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_4 = "fa_ltdebteq_o0.4";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_8 = "fa_ltdebteq_u0.8";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_2 = "fa_ltdebteq_u0.2";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_1 = "fa_ltdebteq_u0.1";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_HIGH = "fa_ltdebteq_high";
        public static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_LOW = "fa_ltdebteq_low";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_5 = "fa_debteq_o0.5";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_6 = "fa_debteq_o0.6";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_7 = "fa_debteq_o0.7";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_1 = "fa_debteq_o1";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_8 = "fa_debteq_o0.8";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_9 = "fa_debteq_o0.9";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_1 = "fa_debteq_u1";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_5 = "fa_debteq_u0.5";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_6 = "fa_debteq_u0.6";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_3 = "fa_debteq_u0.3";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_4 = "fa_debteq_u0.4";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_1 = "fa_debteq_o0.1";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_9 = "fa_debteq_u0.9";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_2 = "fa_debteq_o0.2";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_3 = "fa_debteq_o0.3";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_7 = "fa_debteq_u0.7";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_4 = "fa_debteq_o0.4";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_8 = "fa_debteq_u0.8";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_2 = "fa_debteq_u0.2";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_1 = "fa_debteq_u0.1";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_HIGH = "fa_debteq_high";
        public static final String TOTAL_DEBT_TO_EQUITY_MRQ_LOW = "fa_debteq_low";
        public static final String GROSS_MARGIN_TTM_NEGATIVE = "fa_grossmargin_neg";
        public static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_grossmargin_u-10";
        public static final String GROSS_MARGIN_TTM_UNDER_20_PERCENT = "fa_grossmargin_u20";
        public static final String GROSS_MARGIN_TTM_UNDER_70_PERCENT = "fa_grossmargin_u70";
        public static final String GROSS_MARGIN_TTM_OVER_80_PERCENT = "fa_grossmargin_o80";
        public static final String GROSS_MARGIN_TTM_OVER_60_PERCENT = "fa_grossmargin_o60";
        public static final String GROSS_MARGIN_TTM_OVER_25_PERCENT = "fa_grossmargin_o25";
        public static final String GROSS_MARGIN_TTM_UNDER_40_PERCENT = "fa_grossmargin_u40";
        public static final String GROSS_MARGIN_TTM_OVER_40_PERCENT = "fa_grossmargin_o40";
        public static final String GROSS_MARGIN_TTM_UNDER_45_PERCENT = "fa_grossmargin_u45";
        public static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_grossmargin_u-50";
        public static final String GROSS_MARGIN_TTM_UNDER_90_PERCENT = "fa_grossmargin_u90";
        public static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_grossmargin_u-30";
        public static final String GROSS_MARGIN_TTM_POSITIVE = "fa_grossmargin_pos";
        public static final String GROSS_MARGIN_TTM_UNDER_25_PERCENT = "fa_grossmargin_u25";
        public static final String GROSS_MARGIN_TTM_UNDER_60_PERCENT = "fa_grossmargin_u60";
        public static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_70_PERCENT = "fa_grossmargin_u-70";
        public static final String GROSS_MARGIN_TTM_OVER_20_PERCENT = "fa_grossmargin_o20";
        public static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_100_PERCENT = "fa_grossmargin_u-100";
        public static final String GROSS_MARGIN_TTM_OVER_45_PERCENT = "fa_grossmargin_o45";
        public static final String GROSS_MARGIN_TTM_UNDER_5_PERCENT = "fa_grossmargin_u5";
        public static final String GROSS_MARGIN_TTM_OVER_0_PERCENT = "fa_grossmargin_o0";
        public static final String GROSS_MARGIN_TTM_UNDER_10_PERCENT = "fa_grossmargin_u10";
        public static final String GROSS_MARGIN_TTM_UNDER_0_PERCENT = "fa_grossmargin_u0";
        public static final String GROSS_MARGIN_TTM_OVER_70_PERCENT = "fa_grossmargin_o70";
        public static final String GROSS_MARGIN_TTM_OVER_5_PERCENT = "fa_grossmargin_o5";
        public static final String GROSS_MARGIN_TTM_OVER_15_PERCENT = "fa_grossmargin_o15";
        public static final String GROSS_MARGIN_TTM_OVER_30_PERCENT = "fa_grossmargin_o30";
        public static final String GROSS_MARGIN_TTM_UNDER_80_PERCENT = "fa_grossmargin_u80";
        public static final String GROSS_MARGIN_TTM_UNDER_30_PERCENT = "fa_grossmargin_u30";
        public static final String GROSS_MARGIN_TTM_OVER_50_PERCENT = "fa_grossmargin_o50";
        public static final String GROSS_MARGIN_TTM_UNDER_50_PERCENT = "fa_grossmargin_u50";
        public static final String GROSS_MARGIN_TTM_UNDER_15_PERCENT = "fa_grossmargin_u15";
        public static final String GROSS_MARGIN_TTM_OVER_90_PERCENT = "fa_grossmargin_o90";
        public static final String GROSS_MARGIN_TTM_UNDER_35_PERCENT = "fa_grossmargin_u35";
        public static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_grossmargin_u-20";
        public static final String GROSS_MARGIN_TTM_OVER_10_PERCENT = "fa_grossmargin_o10";
        public static final String GROSS_MARGIN_TTM_OVER_35_PERCENT = "fa_grossmargin_o35";
        public static final String GROSS_MARGIN_TTM_HIGH = "fa_grossmargin_high";
        public static final String OPERATING_MARGIN_TTM_NEGATIVE = "fa_opermargin_neg";
        public static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_opermargin_u-10";
        public static final String OPERATING_MARGIN_TTM_UNDER_20_PERCENT = "fa_opermargin_u20";
        public static final String OPERATING_MARGIN_TTM_UNDER_70_PERCENT = "fa_opermargin_u70";
        public static final String OPERATING_MARGIN_TTM_OVER_80_PERCENT = "fa_opermargin_o80";
        public static final String OPERATING_MARGIN_TTM_OVER_60_PERCENT = "fa_opermargin_o60";
        public static final String OPERATING_MARGIN_TTM_OVER_25_PERCENT = "fa_opermargin_o25";
        public static final String OPERATING_MARGIN_TTM_UNDER_40_PERCENT = "fa_opermargin_u40";
        public static final String OPERATING_MARGIN_TTM_OVER_40_PERCENT = "fa_opermargin_o40";
        public static final String OPERATING_MARGIN_TTM_UNDER_45_PERCENT = "fa_opermargin_u45";
        public static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_opermargin_u-50";
        public static final String OPERATING_MARGIN_TTM_UNDER_90_PERCENT = "fa_opermargin_u90";
        public static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_opermargin_u-30";
        public static final String OPERATING_MARGIN_TTM_POSITIVE = "fa_opermargin_pos";
        public static final String OPERATING_MARGIN_TTM_UNDER_25_PERCENT = "fa_opermargin_u25";
        public static final String OPERATING_MARGIN_TTM_UNDER_60_PERCENT = "fa_opermargin_u60";
        public static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_70_PERCENT = "fa_opermargin_u-70";
        public static final String OPERATING_MARGIN_TTM_OVER_20_PERCENT = "fa_opermargin_o20";
        public static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_100_PERCENT = "fa_opermargin_u-100";
        public static final String OPERATING_MARGIN_TTM_OVER_45_PERCENT = "fa_opermargin_o45";
        public static final String OPERATING_MARGIN_TTM_UNDER_5_PERCENT = "fa_opermargin_u5";
        public static final String OPERATING_MARGIN_TTM_OVER_0_PERCENT = "fa_opermargin_o0";
        public static final String OPERATING_MARGIN_TTM_UNDER_10_PERCENT = "fa_opermargin_u10";
        public static final String OPERATING_MARGIN_TTM_UNDER_0_PERCENT = "fa_opermargin_u0";
        public static final String OPERATING_MARGIN_TTM_OVER_70_PERCENT = "fa_opermargin_o70";
        public static final String OPERATING_MARGIN_TTM_OVER_5_PERCENT = "fa_opermargin_o5";
        public static final String OPERATING_MARGIN_TTM_OVER_15_PERCENT = "fa_opermargin_o15";
        public static final String OPERATING_MARGIN_TTM_OVER_30_PERCENT = "fa_opermargin_o30";
        public static final String OPERATING_MARGIN_TTM_UNDER_80_PERCENT = "fa_opermargin_u80";
        public static final String OPERATING_MARGIN_TTM_UNDER_30_PERCENT = "fa_opermargin_u30";
        public static final String OPERATING_MARGIN_TTM_OVER_50_PERCENT = "fa_opermargin_o50";
        public static final String OPERATING_MARGIN_TTM_UNDER_50_PERCENT = "fa_opermargin_u50";
        public static final String OPERATING_MARGIN_TTM_UNDER_15_PERCENT = "fa_opermargin_u15";
        public static final String OPERATING_MARGIN_TTM_OVER_90_PERCENT = "fa_opermargin_o90";
        public static final String OPERATING_MARGIN_TTM_UNDER_35_PERCENT = "fa_opermargin_u35";
        public static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_opermargin_u-20";
        public static final String OPERATING_MARGIN_TTM_OVER_10_PERCENT = "fa_opermargin_o10";
        public static final String OPERATING_MARGIN_TTM_OVER_35_PERCENT = "fa_opermargin_o35";
        public static final String OPERATING_MARGIN_TTM_HIGH = "fa_opermargin_high";
        public static final String NET_PROFIT_MARGIN_NEGATIVE = "fa_netmargin_neg";
        public static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_10_PERCENT = "fa_netmargin_u-10";
        public static final String NET_PROFIT_MARGIN_UNDER_20_PERCENT = "fa_netmargin_u20";
        public static final String NET_PROFIT_MARGIN_UNDER_70_PERCENT = "fa_netmargin_u70";
        public static final String NET_PROFIT_MARGIN_OVER_80_PERCENT = "fa_netmargin_o80";
        public static final String NET_PROFIT_MARGIN_OVER_60_PERCENT = "fa_netmargin_o60";
        public static final String NET_PROFIT_MARGIN_OVER_25_PERCENT = "fa_netmargin_o25";
        public static final String NET_PROFIT_MARGIN_UNDER_40_PERCENT = "fa_netmargin_u40";
        public static final String NET_PROFIT_MARGIN_OVER_40_PERCENT = "fa_netmargin_o40";
        public static final String NET_PROFIT_MARGIN_UNDER_45_PERCENT = "fa_netmargin_u45";
        public static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_50_PERCENT = "fa_netmargin_u-50";
        public static final String NET_PROFIT_MARGIN_UNDER_90_PERCENT = "fa_netmargin_u90";
        public static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_30_PERCENT = "fa_netmargin_u-30";
        public static final String NET_PROFIT_MARGIN_POSITIVE = "fa_netmargin_pos";
        public static final String NET_PROFIT_MARGIN_UNDER_25_PERCENT = "fa_netmargin_u25";
        public static final String NET_PROFIT_MARGIN_UNDER_60_PERCENT = "fa_netmargin_u60";
        public static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_70_PERCENT = "fa_netmargin_u-70";
        public static final String NET_PROFIT_MARGIN_OVER_20_PERCENT = "fa_netmargin_o20";
        public static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_100_PERCENT = "fa_netmargin_u-100";
        public static final String NET_PROFIT_MARGIN_OVER_45_PERCENT = "fa_netmargin_o45";
        public static final String NET_PROFIT_MARGIN_UNDER_5_PERCENT = "fa_netmargin_u5";
        public static final String NET_PROFIT_MARGIN_OVER_0_PERCENT = "fa_netmargin_o0";
        public static final String NET_PROFIT_MARGIN_UNDER_10_PERCENT = "fa_netmargin_u10";
        public static final String NET_PROFIT_MARGIN_UNDER_0_PERCENT = "fa_netmargin_u0";
        public static final String NET_PROFIT_MARGIN_OVER_70_PERCENT = "fa_netmargin_o70";
        public static final String NET_PROFIT_MARGIN_OVER_5_PERCENT = "fa_netmargin_o5";
        public static final String NET_PROFIT_MARGIN_OVER_15_PERCENT = "fa_netmargin_o15";
        public static final String NET_PROFIT_MARGIN_OVER_30_PERCENT = "fa_netmargin_o30";
        public static final String NET_PROFIT_MARGIN_UNDER_80_PERCENT = "fa_netmargin_u80";
        public static final String NET_PROFIT_MARGIN_UNDER_30_PERCENT = "fa_netmargin_u30";
        public static final String NET_PROFIT_MARGIN_OVER_50_PERCENT = "fa_netmargin_o50";
        public static final String NET_PROFIT_MARGIN_UNDER_50_PERCENT = "fa_netmargin_u50";
        public static final String NET_PROFIT_MARGIN_UNDER_15_PERCENT = "fa_netmargin_u15";
        public static final String NET_PROFIT_MARGIN_OVER_90_PERCENT = "fa_netmargin_o90";
        public static final String NET_PROFIT_MARGIN_UNDER_35_PERCENT = "fa_netmargin_u35";
        public static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_20_PERCENT = "fa_netmargin_u-20";
        public static final String NET_PROFIT_MARGIN_OVER_10_PERCENT = "fa_netmargin_o10";
        public static final String NET_PROFIT_MARGIN_OVER_35_PERCENT = "fa_netmargin_o35";
        public static final String NET_PROFIT_MARGIN_HIGH = "fa_netmargin_high";
        public static final String PAYOUT_RATIO_OVER_0_PERCENT = "fa_payoutratio_o0";
        public static final String PAYOUT_RATIO_UNDER_10_PERCENT = "fa_payoutratio_u10";
        public static final String PAYOUT_RATIO_UNDER_20_PERCENT = "fa_payoutratio_u20";
        public static final String PAYOUT_RATIO_OVER_70_PERCENT = "fa_payoutratio_o70";
        public static final String PAYOUT_RATIO_UNDER_70_PERCENT = "fa_payoutratio_u70";
        public static final String PAYOUT_RATIO_UNDER_80_PERCENT = "fa_payoutratio_u80";
        public static final String PAYOUT_RATIO_OVER_80_PERCENT = "fa_payoutratio_o80";
        public static final String PAYOUT_RATIO_OVER_30_PERCENT = "fa_payoutratio_o30";
        public static final String PAYOUT_RATIO_UNDER_30_PERCENT = "fa_payoutratio_u30";
        public static final String PAYOUT_RATIO_OVER_60_PERCENT = "fa_payoutratio_o60";
        public static final String PAYOUT_RATIO_UNDER_40_PERCENT = "fa_payoutratio_u40";
        public static final String PAYOUT_RATIO_OVER_50_PERCENT = "fa_payoutratio_o50";
        public static final String PAYOUT_RATIO_UNDER_50_PERCENT = "fa_payoutratio_u50";
        public static final String PAYOUT_RATIO_OVER_40_PERCENT = "fa_payoutratio_o40";
        public static final String PAYOUT_RATIO_UNDER_90_PERCENT = "fa_payoutratio_u90";
        public static final String PAYOUT_RATIO_OVER_90_PERCENT = "fa_payoutratio_o90";
        public static final String PAYOUT_RATIO_POSITIVE = "fa_payoutratio_pos";
        public static final String PAYOUT_RATIO_UNDER_60_PERCENT = "fa_payoutratio_u60";
        public static final String PAYOUT_RATIO_OVER_10_PERCENT = "fa_payoutratio_o10";
        public static final String PAYOUT_RATIO_OVER_20_PERCENT = "fa_payoutratio_o20";
        public static final String PAYOUT_RATIO_NONE = "fa_payoutratio_none";
        public static final String PAYOUT_RATIO_HIGH = "fa_payoutratio_high";
        public static final String PAYOUT_RATIO_LOW = "fa_payoutratio_low";
        public static final String PAYOUT_RATIO_UNDER_100_PERCENT = "fa_payoutratio_u100";
        public static final String PAYOUT_RATIO_OVER_100_PERCENT = "fa_payoutratio_100";
        public static final String INSIDER_OWNERSHIP_OVER_70_PERCENT = "sh_insiderown_o70";
        public static final String INSIDER_OWNERSHIP_OVER_10_PERCENT = "sh_insiderown_o10";
        public static final String INSIDER_OWNERSHIP_VERY_HIGH = "sh_insiderown_veryhigh";
        public static final String INSIDER_OWNERSHIP_OVER_20_PERCENT = "sh_insiderown_o20";
        public static final String INSIDER_OWNERSHIP_OVER_80_PERCENT = "sh_insiderown_o80";
        public static final String INSIDER_OWNERSHIP_OVER_30_PERCENT = "sh_insiderown_o30";
        public static final String INSIDER_OWNERSHIP_OVER_60_PERCENT = "sh_insiderown_o60";
        public static final String INSIDER_OWNERSHIP_OVER_50_PERCENT = "sh_insiderown_o50";
        public static final String INSIDER_OWNERSHIP_HIGH = "sh_insiderown_high";
        public static final String INSIDER_OWNERSHIP_OVER_40_PERCENT = "sh_insiderown_o40";
        public static final String INSIDER_OWNERSHIP_LOW = "sh_insiderown_low";
        public static final String INSIDER_OWNERSHIP_OVER_90_PERCENT = "sh_insiderown_o90";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_35_PERCENT = "sh_insidertrans_u-35";
        public static final String INSIDER_TRANSCATIONS_NEGATIVE = "sh_insidertrans_neg";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_10_PERCENT = "sh_insidertrans_u-10";
        public static final String INSIDER_TRANSCATIONS_OVER_80_PERCENT = "sh_insidertrans_o80";
        public static final String INSIDER_TRANSCATIONS_OVER_60_PERCENT = "sh_insidertrans_o60";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_90_PERCENT = "sh_insidertrans_u-90";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_15_PERCENT = "sh_insidertrans_u-15";
        public static final String INSIDER_TRANSCATIONS_OVER_25_PERCENT = "sh_insidertrans_o25";
        public static final String INSIDER_TRANSCATIONS_OVER_40_PERCENT = "sh_insidertrans_o40";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_50_PERCENT = "sh_insidertrans_u-50";
        public static final String INSIDER_TRANSCATIONS_VERY_NEGATIVE = "sh_insidertrans_veryneg";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_30_PERCENT = "sh_insidertrans_u-30";
        public static final String INSIDER_TRANSCATIONS_POSITIVE = "sh_insidertrans_pos";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_70_PERCENT = "sh_insidertrans_u-70";
        public static final String INSIDER_TRANSCATIONS_OVER_20_PERCENT = "sh_insidertrans_o20";
        public static final String INSIDER_TRANSCATIONS_OVER_45_PERCENT = "sh_insidertrans_o45";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_25_PERCENT = "sh_insidertrans_u-25";
        public static final String INSIDER_TRANSCATIONS_OVER_70_PERCENT = "sh_insidertrans_o70";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_45_PERCENT = "sh_insidertrans_u-45";
        public static final String INSIDER_TRANSCATIONS_VERY_POSITIVE = "sh_insidertrans_verypos";
        public static final String INSIDER_TRANSCATIONS_OVER_5_PERCENT = "sh_insidertrans_o5";
        public static final String INSIDER_TRANSCATIONS_OVER_15_PERCENT = "sh_insidertrans_o15";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_40_PERCENT = "sh_insidertrans_u-40";
        public static final String INSIDER_TRANSCATIONS_OVER_30_PERCENT = "sh_insidertrans_o30";
        public static final String INSIDER_TRANSCATIONS_OVER_50_PERCENT = "sh_insidertrans_o50";
        public static final String INSIDER_TRANSCATIONS_OVER_90_PERCENT = "sh_insidertrans_o90";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_20_PERCENT = "sh_insidertrans_u-20";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_60_PERCENT = "sh_insidertrans_u-60";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_5_PERCENT = "sh_insidertrans_u-5";
        public static final String INSIDER_TRANSCATIONS_OVER_10_PERCENT = "sh_insidertrans_o10";
        public static final String INSIDER_TRANSCATIONS_OVER_35_PERCENT = "sh_insidertrans_o35";
        public static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_80_PERCENT = "sh_insidertrans_u-80";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_10_PERCENT = "sh_instown_u10";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_70_PERCENT = "sh_instown_o70";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_20_PERCENT = "sh_instown_u20";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_70_PERCENT = "sh_instown_u70";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_80_PERCENT = "sh_instown_o80";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_30_PERCENT = "sh_instown_o30";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_80_PERCENT = "sh_instown_u80";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_60_PERCENT = "sh_instown_o60";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_30_PERCENT = "sh_instown_u30";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_50_PERCENT = "sh_instown_o50";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_40_PERCENT = "sh_instown_u40";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_40_PERCENT = "sh_instown_o40";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_50_PERCENT = "sh_instown_u50";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_90_PERCENT = "sh_instown_o90";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_90_PERCENT = "sh_instown_u90";
        public static final String INSTITUTIONAL_OWNERSHIP_UNDER_60_PERCENT = "sh_instown_u60";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_10_PERCENT = "sh_instown_o10";
        public static final String INSTITUTIONAL_OWNERSHIP_OVER_20_PERCENT = "sh_instown_o20";
        public static final String INSTITUTIONAL_OWNERSHIP_HIGH = "sh_instown_high";
        public static final String INSTITUTIONAL_OWNERSHIP_LOW = "sh_instown_low";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_10_PERCENT = "sh_insttrans_u-10";
        public static final String INSTITUTIONAL_TRANSACTION_NEGATIVE = "sh_insttrans_neg";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_35_PERCENT = "sh_insttrans_u-35";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_25_PERCENT = "sh_insttrans_u-25";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_45_PERCENT = "sh_insttrans_u-45";
        public static final String INSTITUTIONAL_TRANSACTION_VERY_POSITIVE = "sh_insttrans_verypos";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_5_PERCENT = "sh_insttrans_o5";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_15_PERCENT = "sh_insttrans_o15";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_30_PERCENT = "sh_insttrans_o30";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_40_PERCENT = "sh_insttrans_u-40";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_15_PERCENT = "sh_insttrans_u-15";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_50_PERCENT = "sh_insttrans_o50";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_25_PERCENT = "sh_insttrans_o25";
        public static final String INSTITUTIONAL_TRANSACTION_VERY_NEGATIVE = "sh_insttrans_veryneg";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_50_PERCENT = "sh_insttrans_u-50";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_30_PERCENT = "sh_insttrans_u-30";
        public static final String INSTITUTIONAL_TRANSACTION_POSITIVE = "sh_insttrans_pos";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_20_PERCENT = "sh_insttrans_u-20";
        public static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_5_PERCENT = "sh_insttrans_u-5";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_10_PERCENT = "sh_insttrans_o10";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_35_PERCENT = "sh_insttrans_o35";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_20_PERCENT = "sh_insttrans_o20";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_45_PERCENT = "sh_insttrans_o45";
        public static final String INSTITUTIONAL_TRANSACTION_OVER_40_PERCENT = "sh_insttrans_40";
        public static final String SHORT_SELLING_UNDER_10_PERCENT = "sh_short_u10";
        public static final String SHORT_SELLING_UNDER_20_PERCENT = "sh_short_u20";
        public static final String SHORT_SELLING_OVER_5_PERCENT = "sh_short_o5";
        public static final String SHORT_SELLING_OVER_15_PERCENT = "sh_short_o15";
        public static final String SHORT_SELLING_OVER_30_PERCENT = "sh_short_o30";
        public static final String SHORT_SELLING_UNDER_30_PERCENT = "sh_short_u30";
        public static final String SHORT_SELLING_OVER_25_PERCENT = "sh_short_o25";
        public static final String SHORT_SELLING_UNDER_15_PERCENT = "sh_short_u15";
        public static final String SHORT_SELLING_UNDER_25_PERCENT = "sh_short_u25";
        public static final String SHORT_SELLING_OVER_10_PERCENT = "sh_short_o10";
        public static final String SHORT_SELLING_OVER_20_PERCENT = "sh_short_o20";
        public static final String SHORT_SELLING_UNDER_5_PERCENT = "sh_short_u5";
        public static final String SHORT_SELLING_HIGH = "sh_short_high";
        public static final String SHORT_SELLING_LOW = "sh_short_low";
        public static final String ANALYST_RECOMMENDATION_STRONG_SELL = "fsm_an_recom_strongsell";
        public static final String ANALYST_RECOMMENDATION_STRONG_BUY = "fsm_an_recom_strongbuy";
        public static final String ANALYST_RECOMMENDATION_SELL = "fsm_an_recom_sell";
        public static final String ANALYST_RECOMMENDATION_BUY = "fsm_an_recom_buy";
        public static final String ANALYST_RECOMMENDATION_BUY_OR_BETTER = "fsm_an_recom_buybetter";
        public static final String ANALYST_RECOMMENDATION_HOLD = "fsm_an_recom_hold";
        public static final String ANALYST_RECOMMENDATION_SELL_OR_WORSE = "fsm_an_recom_sellworse";
        public static final String OPTION_SHORT_SHORTABLE = "sh_opt_short";
        public static final String OPTION_SHORT_OPTIONABLE_AND_SHORTABLE = "sh_opt_optionshort";
        public static final String OPTION_SHORT_OPTIONABLE = "sh_opt_option";
        public static final String EARNINGS_DATE_PREVIOUS_5_DAYS = "fsm_earningsdate_prevdays5";
        public static final String EARNINGS_DATE_TOMORROW = "fsm_earningsdate_tomorrow";
        public static final String EARNINGS_DATE_THIS_MONTH = "fsm_earningsdate_thismonth";
        public static final String EARNINGS_DATE_NEXT_5_DAYS = "fsm_earningsdate_nextdays5";
        public static final String EARNINGS_DATE_YESTERDAY = "fsm_earningsdate_yesterday";
        public static final String EARNINGS_DATE_TODAY = "fsm_earningsdate_today";
        public static final String EARNINGS_DATE_TODAY_AFTER_MARKET_CLOSE = "fsm_earningsdate_todayafter";
        public static final String EARNINGS_DATE_PREVIOUS_WEEK = "fsm_earningsdate_prevweek";
        public static final String EARNINGS_DATE_YESTERDAY_AFTER_MARKET_CLOSE = "fsm_earningsdate_yesterdayafter";
        public static final String EARNINGS_DATE_TODAY_BEFORE_MARKET_OPEN = "fsm_earningsdate_todaybefore";
        public static final String EARNINGS_DATE_THIS_WEEK = "fsm_earningsdate_thisweek";
        public static final String EARNINGS_DATE_TOMORROW_AFTER_MARKET_CLOSE = "fsm_earningsdate_tomorrowafter";
        public static final String EARNINGS_DATE_TOMORROW_BEFORE_MARKET_OPEN = "fsm_earningsdate_tomorrowbefore";
        public static final String EARNINGS_DATE_NEXT_WEEK = "fsm_earningsdate_nextweek";
        public static final String EARNINGS_DATE_YESTERDAY_BEFORE_MARKET_OPEN = "fsm_earningsdate_yesterdaybefore";
        public static final String PERFORMANCE_MONTH_NEGATIVE_50_PERCENT = "ta_perf_4w50u";
        public static final String PERFORMANCE_HALF_50_PERCENT = "ta_perf_26w50o";
        public static final String PERFORMANCE_WEEK_UP = "ta_perf_1wup";
        public static final String PERFORMANCE_HALF_NEGATIVE_50_PERCENT = "ta_perf_26w50u";
        public static final String PERFORMANCE_MONTH_10_PERCENT = "ta_perf_4w10o";
        public static final String PERFORMANCE_MONTH_50_PERCENT = "ta_perf_4w50o";
        public static final String PERFORMANCE_HALF_30_PERCENT = "ta_perf_26w30o";
        public static final String PERFORMANCE_QUARTER_DOWN = "ta_perf_13wdown";
        public static final String PERFORMANCE_MONTH_NEGATIVE_10_PERCENT = "ta_perf_4w10u";
        public static final String PERFORMANCE_YTD_NEGATIVE_30_PERCENT = "ta_perf_ytd30u";
        public static final String PERFORMANCE_QUARTER_NEGATIVE_10_PERCENT = "ta_perf_13w10u";
        public static final String PERFORMANCE_YTD_20_PERCENT = "ta_perf_ytd20o";
        public static final String PERFORMANCE_YTD_NEGATIVE_20_PERCENT = "ta_perf_ytd20u";
        public static final String PERFORMANCE_YTD_30_PERCENT = "ta_perf_ytd30o";
        public static final String PERFORMANCE_QUARTER_30_PERCENT = "ta_perf_13w30o";
        public static final String PERFORMANCE_YTD_5_PERCENT = "ta_perf_ytd5o";
        public static final String PERFORMANCE_QUARTER_NEGATIVE_20_PERCENT = "ta_perf_13w20u";
        public static final String PERFORMANCE_HALF_NEGATIVE_30_PERCENT = "ta_perf_26w30u";
        public static final String PERFORMANCE_YTD_NEGATIVE_5_PERCENT = "ta_perf_ytd5u";
        public static final String PERFORMANCE_YEAR_200_PERCENT = "ta_perf_52_W200o";
        public static final String PERFORMANCE_TODAY_UP = "ta_perf_dup";
        public static final String PERFORMANCE_YEAR_NEGATIVE_50_PERCENT = "ta_perf_52_W50u";
        public static final String PERFORMANCE_QUARTER_UP = "ta_perf_13wup";
        public static final String PERFORMANCE_TODAY_5_PERCENT = "ta_perf_d5o";
        public static final String PERFORMANCE_QUARTER_50_PERCENT = "ta_perf_13w50o";
        public static final String PERFORMANCE_YEAR_NEGATIVE_75_PERCENT = "ta_perf_52_W75u";
        public static final String PERFORMANCE_YEAR_20_PERCENT = "ta_perf_52_W20o";
        public static final String PERFORMANCE_YTD_50_PERCENT = "ta_perf_ytd50o";
        public static final String PERFORMANCE_QUARTER_NEGATIVE_30_PERCENT = "ta_perf_13w30u";
        public static final String PERFORMANCE_YEAR_50_PERCENT = "ta_perf_52_W50o";
        public static final String PERFORMANCE_HALF_DOWN = "ta_perf_26wdown";
        public static final String PERFORMANCE_QUARTER_NEGATIVE_50_PERCENT = "ta_perf_13w50u";
        public static final String PERFORMANCE_TODAY_NEGATIVE_5_PERCENT = "ta_perf_d5u";
        public static final String PERFORMANCE_YEAR_NEGATIVE_20_PERCENT = "ta_perf_52_W20u";
        public static final String PERFORMANCE_WEEK_10_PERCENT = "ta_perf_1w10o";
        public static final String PERFORMANCE_TODAY_NEGATIVE_10_PERCENT = "ta_perf_d10u";
        public static final String PERFORMANCE_TODAY_DOWN = "ta_perf_ddown";
        public static final String PERFORMANCE_WEEK_NEGATIVE_10_PERCENT = "ta_perf_1w10u";
        public static final String PERFORMANCE_YTD_DOWN = "ta_perf_ytddown";
        public static final String PERFORMANCE_HALF_NEGATIVE_75_PERCENT = "ta_perf_26w75u";
        public static final String PERFORMANCE_TODAY_10_PERCENT = "ta_perf_d10o";
        public static final String PERFORMANCE_HALF_NEGATIVE_10_PERCENT = "ta_perf_26w10u";
        public static final String PERFORMANCE_QUARTER_10_PERCENT = "ta_perf_13w10o";
        public static final String PERFORMANCE_YTD_10_PERCENT = "ta_perf_ytd10o";
        public static final String PERFORMANCE_YEAR_NEGATIVE_10_PERCENT = "ta_perf_52_W10u";
        public static final String PERFORMANCE_HALF_NEGATIVE_20_PERCENT = "ta_perf_26w20u";
        public static final String PERFORMANCE_YEAR_500_PERCENT = "ta_perf_52_W500o";
        public static final String PERFORMANCE_YTD_NEGATIVE_50_PERCENT = "ta_perf_ytd50u";
        public static final String PERFORMANCE_HALF_100_PERCENT = "ta_perf_26w100o";
        public static final String PERFORMANCE_YTD_NEGATIVE_10_PERCENT = "ta_perf_ytd10u";
        public static final String PERFORMANCE_YEAR_10_PERCENT = "ta_perf_52_W10o";
        public static final String PERFORMANCE_QUARTER_20_PERCENT = "ta_perf_13w20o";
        public static final String PERFORMANCE_YEAR_UP = "ta_perf_52_Wup";
        public static final String PERFORMANCE_WEEK_30_PERCENT = "ta_perf_1w30o";
        public static final String PERFORMANCE_MONTH_NEGATIVE_30_PERCENT = "ta_perf_4w30u";
        public static final String PERFORMANCE_YTD_NEGATIVE_75_PERCENT = "ta_perf_ytd75u";
        public static final String PERFORMANCE_WEEK_DOWN = "ta_perf_1wdown";
        public static final String PERFORMANCE_HALF_UP = "ta_perf_26wup";
        public static final String PERFORMANCE_YEAR_300_PERCENT = "ta_perf_52_W300o";
        public static final String PERFORMANCE_MONTH_NEGATIVE_20_PERCENT = "ta_perf_4w20u";
        public static final String PERFORMANCE_WEEK_20_PERCENT = "ta_perf_1w20o";
        public static final String PERFORMANCE_HALF_10_PERCENT = "ta_perf_26w10o";
        public static final String PERFORMANCE_YEAR_100_PERCENT = "ta_perf_52_W100o";
        public static final String PERFORMANCE_MONTH_DOWN = "ta_perf_4wdown";
        public static final String PERFORMANCE_HALF_20_PERCENT = "ta_perf_26w20o";
        public static final String PERFORMANCE_WEEK_NEGATIVE_20_PERCENT = "ta_perf_1w20u";
        public static final String PERFORMANCE_MONTH_20_PERCENT = "ta_perf_4w20o";
        public static final String PERFORMANCE_YTD_UP = "ta_perf_ytdup";
        public static final String PERFORMANCE_TODAY_15_PERCENT = "ta_perf_d15o";
        public static final String PERFORMANCE_YTD_100_PERCENT = "ta_perf_ytd100o";
        public static final String PERFORMANCE_MONTH_UP = "ta_perf_4wup";
        public static final String PERFORMANCE_YEAR_NEGATIVE_30_PERCENT = "ta_perf_52_W30u";
        public static final String PERFORMANCE_YEAR_30_PERCENT = "ta_perf_52_W30o";
        public static final String PERFORMANCE_WEEK_NEGATIVE_30_PERCENT = "ta_perf_1w30u";
        public static final String PERFORMANCE_TODAY_NEGATIVE_15_PERCENT = "ta_perf_d15u";
        public static final String PERFORMANCE_MONTH_30_PERCENT = "ta_perf_4w30o";
        public static final String PERFORMANCE_YEAR_DOWN = "ta_perf_52_Wdown";
        public static final String PERFORMANCE_2_MONTH_NEGATIVE_50_PERCENT = "ta_perf2_4w50u";
        public static final String PERFORMANCE_2_HALF_50_PERCENT = "ta_perf2_26w50o";
        public static final String PERFORMANCE_2_WEEK_UP = "ta_perf2_1wup";
        public static final String PERFORMANCE_2_HALF_NEGATIVE_50_PERCENT = "ta_perf2_26w50u";
        public static final String PERFORMANCE_2_MONTH_10_PERCENT = "ta_perf2_4w10o";
        public static final String PERFORMANCE_2_MONTH_50_PERCENT = "ta_perf2_4w50o";
        public static final String PERFORMANCE_2_HALF_30_PERCENT = "ta_perf2_26w30o";
        public static final String PERFORMANCE_2_QUARTER_DOWN = "ta_perf2_13wdown";
        public static final String PERFORMANCE_2_MONTH_NEGATIVE_10_PERCENT = "ta_perf2_4w10u";
        public static final String PERFORMANCE_2_YTD_NEGATIVE_30_PERCENT = "ta_perf2_ytd30u";
        public static final String PERFORMANCE_2_QUARTER_NEGATIVE_10_PERCENT = "ta_perf2_13w10u";
        public static final String PERFORMANCE_2_YTD_20_PERCENT = "ta_perf2_ytd20o";
        public static final String PERFORMANCE_2_YTD_NEGATIVE_20_PERCENT = "ta_perf2_ytd20u";
        public static final String PERFORMANCE_2_YTD_30_PERCENT = "ta_perf2_ytd30o";
        public static final String PERFORMANCE_2_QUARTER_30_PERCENT = "ta_perf2_13w30o";
        public static final String PERFORMANCE_2_YTD_5_PERCENT = "ta_perf2_ytd5o";
        public static final String PERFORMANCE_2_QUARTER_NEGATIVE_20_PERCENT = "ta_perf2_13w20u";
        public static final String PERFORMANCE_2_HALF_NEGATIVE_30_PERCENT = "ta_perf2_26w30u";
        public static final String PERFORMANCE_2_YTD_NEGATIVE_5_PERCENT = "ta_perf2_ytd5u";
        public static final String PERFORMANCE_2_YEAR_200_PERCENT = "ta_perf2_52_W200o";
        public static final String PERFORMANCE_2_TODAY_UP = "ta_perf2_dup";
        public static final String PERFORMANCE_2_YEAR_NEGATIVE_50_PERCENT = "ta_perf2_52_W50u";
        public static final String PERFORMANCE_2_QUARTER_UP = "ta_perf2_13wup";
        public static final String PERFORMANCE_2_TODAY_5_PERCENT = "ta_perf2_d5o";
        public static final String PERFORMANCE_2_QUARTER_50_PERCENT = "ta_perf2_13w50o";
        public static final String PERFORMANCE_2_YEAR_NEGATIVE_75_PERCENT = "ta_perf2_52_W75u";
        public static final String PERFORMANCE_2_YEAR_20_PERCENT = "ta_perf2_52_W20o";
        public static final String PERFORMANCE_2_YTD_50_PERCENT = "ta_perf2_ytd50o";
        public static final String PERFORMANCE_2_QUARTER_NEGATIVE_30_PERCENT = "ta_perf2_13w30u";
        public static final String PERFORMANCE_2_YEAR_50_PERCENT = "ta_perf2_52_W50o";
        public static final String PERFORMANCE_2_HALF_DOWN = "ta_perf2_26wdown";
        public static final String PERFORMANCE_2_QUARTER_NEGATIVE_50_PERCENT = "ta_perf2_13w50u";
        public static final String PERFORMANCE_2_TODAY_NEGATIVE_5_PERCENT = "ta_perf2_d5u";
        public static final String PERFORMANCE_2_YEAR_NEGATIVE_20_PERCENT = "ta_perf2_52_W20u";
        public static final String PERFORMANCE_2_WEEK_10_PERCENT = "ta_perf2_1w10o";
        public static final String PERFORMANCE_2_TODAY_NEGATIVE_10_PERCENT = "ta_perf2_d10u";
        public static final String PERFORMANCE_2_TODAY_DOWN = "ta_perf2_ddown";
        public static final String PERFORMANCE_2_WEEK_NEGATIVE_10_PERCENT = "ta_perf2_1w10u";
        public static final String PERFORMANCE_2_YTD_DOWN = "ta_perf2_ytddown";
        public static final String PERFORMANCE_2_HALF_NEGATIVE_75_PERCENT = "ta_perf2_26w75u";
        public static final String PERFORMANCE_2_TODAY_10_PERCENT = "ta_perf2_d10o";
        public static final String PERFORMANCE_2_HALF_NEGATIVE_10_PERCENT = "ta_perf2_26w10u";
        public static final String PERFORMANCE_2_QUARTER_10_PERCENT = "ta_perf2_13w10o";
        public static final String PERFORMANCE_2_YTD_10_PERCENT = "ta_perf2_ytd10o";
        public static final String PERFORMANCE_2_YEAR_NEGATIVE_10_PERCENT = "ta_perf2_52_W10u";
        public static final String PERFORMANCE_2_HALF_NEGATIVE_20_PERCENT = "ta_perf2_26w20u";
        public static final String PERFORMANCE_2_YEAR_500_PERCENT = "ta_perf2_52_W500o";
        public static final String PERFORMANCE_2_YTD_NEGATIVE_50_PERCENT = "ta_perf2_ytd50u";
        public static final String PERFORMANCE_2_HALF_100_PERCENT = "ta_perf2_26w100o";
        public static final String PERFORMANCE_2_YTD_NEGATIVE_10_PERCENT = "ta_perf2_ytd10u";
        public static final String PERFORMANCE_2_YEAR_10_PERCENT = "ta_perf2_52_W10o";
        public static final String PERFORMANCE_2_QUARTER_20_PERCENT = "ta_perf2_13w20o";
        public static final String PERFORMANCE_2_YEAR_UP = "ta_perf2_52_Wup";
        public static final String PERFORMANCE_2_WEEK_30_PERCENT = "ta_perf2_1w30o";
        public static final String PERFORMANCE_2_MONTH_NEGATIVE_30_PERCENT = "ta_perf2_4w30u";
        public static final String PERFORMANCE_2_YTD_NEGATIVE_75_PERCENT = "ta_perf2_ytd75u";
        public static final String PERFORMANCE_2_WEEK_DOWN = "ta_perf2_1wdown";
        public static final String PERFORMANCE_2_HALF_UP = "ta_perf2_26wup";
        public static final String PERFORMANCE_2_YEAR_300_PERCENT = "ta_perf2_52_W300o";
        public static final String PERFORMANCE_2_MONTH_NEGATIVE_20_PERCENT = "ta_perf2_4w20u";
        public static final String PERFORMANCE_2_WEEK_20_PERCENT = "ta_perf2_1w20o";
        public static final String PERFORMANCE_2_HALF_10_PERCENT = "ta_perf2_26w10o";
        public static final String PERFORMANCE_2_YEAR_100_PERCENT = "ta_perf2_52_W100o";
        public static final String PERFORMANCE_2_MONTH_DOWN = "ta_perf2_4wdown";
        public static final String PERFORMANCE_2_HALF_20_PERCENT = "ta_perf2_26w20o";
        public static final String PERFORMANCE_2_WEEK_NEGATIVE_20_PERCENT = "ta_perf2_1w20u";
        public static final String PERFORMANCE_2_MONTH_20_PERCENT = "ta_perf2_4w20o";
        public static final String PERFORMANCE_2_YTD_UP = "ta_perf2_ytdup";
        public static final String PERFORMANCE_2_TODAY_15_PERCENT = "ta_perf2_d15o";
        public static final String PERFORMANCE_2_YTD_100_PERCENT = "ta_perf2_ytd100o";
        public static final String PERFORMANCE_2_MONTH_UP = "ta_perf2_4wup";
        public static final String PERFORMANCE_2_YEAR_NEGATIVE_30_PERCENT = "ta_perf2_52_W30u";
        public static final String PERFORMANCE_2_YEAR_30_PERCENT = "ta_perf2_52_W30o";
        public static final String PERFORMANCE_2_WEEK_NEGATIVE_30_PERCENT = "ta_perf2_1w30u";
        public static final String PERFORMANCE_2_TODAY_NEGATIVE_15_PERCENT = "ta_perf2_d15u";
        public static final String PERFORMANCE_2_MONTH_30_PERCENT = "ta_perf2_4w30o";
        public static final String PERFORMANCE_2_YEAR_DOWN = "ta_perf2_52_Wdown";
        public static final String VOLATILITY_WEEK_OVER_9_PERCENT = "ta_volatility_wo9";
        public static final String VOLATILITY_WEEK_OVER_12_PERCENT = "ta_volatility_wo12";
        public static final String VOLATILITY_WEEK_OVER_8_PERCENT = "ta_volatility_wo8";
        public static final String VOLATILITY_WEEK_OVER_15_PERCENT = "ta_volatility_wo15";
        public static final String VOLATILITY_MONTH_OVER_5_PERCENT = "ta_volatility_mo5";
        public static final String VOLATILITY_MONTH_OVER_4_PERCENT = "ta_volatility_mo4";
        public static final String VOLATILITY_MONTH_OVER_3_PERCENT = "ta_volatility_mo3";
        public static final String VOLATILITY_MONTH_OVER_2_PERCENT = "ta_volatility_mo2";
        public static final String VOLATILITY_MONTH_OVER_9_PERCENT = "ta_volatility_mo9";
        public static final String VOLATILITY_WEEK_OVER_10_PERCENT = "ta_volatility_wo10";
        public static final String VOLATILITY_MONTH_OVER_8_PERCENT = "ta_volatility_mo8";
        public static final String VOLATILITY_MONTH_OVER_7_PERCENT = "ta_volatility_mo7";
        public static final String VOLATILITY_MONTH_OVER_6_PERCENT = "ta_volatility_mo6";
        public static final String VOLATILITY_MONTH_OVER_15_PERCENT = "ta_volatility_mo15";
        public static final String VOLATILITY_MONTH_OVER_12_PERCENT = "ta_volatility_mo12";
        public static final String VOLATILITY_MONTH_OVER_10_PERCENT = "ta_volatility_mo10";
        public static final String VOLATILITY_WEEK_OVER_3_PERCENT = "ta_volatility_wo3";
        public static final String VOLATILITY_WEEK_OVER_4_PERCENT = "ta_volatility_wo4";
        public static final String VOLATILITY_WEEK_OVER_5_PERCENT = "ta_volatility_wo5";
        public static final String VOLATILITY_WEEK_OVER_6_PERCENT = "ta_volatility_wo6";
        public static final String VOLATILITY_WEEK_OVER_7_PERCENT = "ta_volatility_wo7";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_30 = "ta_rsi_os30";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_40 = "ta_rsi_os40";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_20 = "ta_rsi_os20";
        public static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERSOLD_GREATER_THAN_50 = "ta_rsi_nos50";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_10 = "ta_rsi_os10";
        public static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERSOLD_GREATER_THAN_40 = "ta_rsi_nos40";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_90 = "ta_rsi_ob90";
        public static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERBOUGHT_LESS_THAN_60 = "ta_rsi_nob60";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_80 = "ta_rsi_ob80";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_70 = "ta_rsi_ob70";
        public static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_60 = "ta_rsi_ob60";
        public static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERBOUGHT_GREATER_THAN_50 = "ta_rsi_nob50";
        public static final String GAP_UP_10_PERCENT = "ta_gap_u10";
        public static final String GAP_DOWN = "ta_gap_d";
        public static final String GAP_UP_0_PERCENT = "ta_gap_u0";
        public static final String GAP_UP_20_PERCENT = "ta_gap_u20";
        public static final String GAP_UP_2_PERCENT = "ta_gap_u2";
        public static final String GAP_UP_1_PERCENT = "ta_gap_u1";
        public static final String GAP_UP_4_PERCENT = "ta_gap_u4";
        public static final String GAP_UP_3_PERCENT = "ta_gap_u3";
        public static final String GAP_DOWN_9_PERCENT = "ta_gap_d9";
        public static final String GAP_DOWN_8_PERCENT = "ta_gap_d8";
        public static final String GAP_DOWN_5_PERCENT = "ta_gap_d5";
        public static final String GAP_UP_15_PERCENT = "ta_gap_u15";
        public static final String GAP_DOWN_4_PERCENT = "ta_gap_d4";
        public static final String GAP_DOWN_7_PERCENT = "ta_gap_d7";
        public static final String GAP_DOWN_6_PERCENT = "ta_gap_d6";
        public static final String GAP_DOWN_0_PERCENT = "ta_gap_d0";
        public static final String GAP_DOWN_1_PERCENT = "ta_gap_d1";
        public static final String GAP_DOWN_2_PERCENT = "ta_gap_d2";
        public static final String GAP_UP = "ta_gap_u";
        public static final String GAP_DOWN_3_PERCENT = "ta_gap_d3";
        public static final String GAP_DOWN_20_PERCENT = "ta_gap_d20";
        public static final String GAP_DOWN_10_PERCENT = "ta_gap_d10";
        public static final String GAP_UP_7_PERCENT = "ta_gap_u7";
        public static final String GAP_UP_8_PERCENT = "ta_gap_u8";
        public static final String GAP_UP_5_PERCENT = "ta_gap_u5";
        public static final String GAP_UP_6_PERCENT = "ta_gap_u6";
        public static final String GAP_DOWN_15_PERCENT = "ta_gap_d15";
        public static final String GAP_UP_9_PERCENT = "ta_gap_u9";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_BELOW_SMA_20 = "ta_SMA_20_pb20";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_ABOVE_SMA_20 = "ta_SMA_20_pa30";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_BELOW_SMA_20 = "ta_SMA_20_pb10";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_ABOVE_SMA_20 = "ta_SMA_20_pa40";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_ABOVE_SMA_20 = "ta_SMA_20_pa50";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_CROSSED_SMA_200 = "ta_SMA_20_cross200";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_ABOVE_SMA_50 = "ta_SMA_20_sa50";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_CROSSED_SMA_50 = "ta_SMA_20_cross50";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_BELOW_SMA_50 = "ta_SMA_20_sb50";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_BELOW_SMA_200 = "ta_SMA_20_sb200";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_CROSSED_SMA_200_ABOVE = "ta_SMA_20_cross200a";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_ABOVE_SMA_20 = "ta_SMA_20_pa10";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_CROSSED_SMA_50_BELOW = "ta_SMA_20_cross50b";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_ABOVE_SMA_20 = "ta_SMA_20_pa20";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_CROSSED_SMA_200_BELOW = "ta_SMA_20_cross200b";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_CROSSED_SMA_50_ABOVE = "ta_SMA_20_cross50a";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_ABOVE_SMA_20 = "ta_SMA_20_pa";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_SMA_20_ABOVE_SMA_200 = "ta_SMA_20_sa200";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_BELOW_SMA_20 = "ta_SMA_20_pb";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_20 = "ta_SMA_20_pc";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_20_BELOW = "ta_SMA_20_pcb";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_BELOW_SMA_20 = "ta_SMA_20_pb30";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_20_ABOVE = "ta_SMA_20_pca";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_BELOW_SMA_20 = "ta_SMA_20_pb50";
        public static final String I_20_DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_BELOW_SMA_20 = "ta_SMA_20_pb40";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_BELOW_SMA_50 = "ta_SMA_50_pb20";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_ABOVE_SMA_50 = "ta_SMA_50_pa30";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_BELOW_SMA_50 = "ta_SMA_50_pb10";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_ABOVE_SMA_50 = "ta_SMA_50_pa40";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_ABOVE_SMA_50 = "ta_SMA_50_pa50";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_ABOVE_SMA_20 = "ta_SMA_50_sa20";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_CROSSED_SMA_200 = "ta_SMA_50_cross200";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_CROSSED_SMA_20 = "ta_SMA_50_cross20";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_BELOW_SMA_200 = "ta_SMA_50_sb200";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_CROSSED_SMA_200_ABOVE = "ta_SMA_50_cross200a";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_ABOVE_SMA_50 = "ta_SMA_50_pa10";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_ABOVE_SMA_50 = "ta_SMA_50_pa20";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_BELOW_SMA_20 = "ta_SMA_50_sb20";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_CROSSED_SMA_200_BELOW = "ta_SMA_50_cross200b";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_ABOVE_SMA_50 = "ta_SMA_50_pa";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_ABOVE_SMA_200 = "ta_SMA_50_sa200";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_BELOW_SMA_50 = "ta_SMA_50_pb";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_50 = "ta_SMA_50_pc";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_CROSSED_SMA_20_ABOVE = "ta_SMA_50_cross20a";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_50_BELOW = "ta_SMA_50_pcb";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_BELOW_SMA_50 = "ta_SMA_50_pb30";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_50_ABOVE = "ta_SMA_50_pca";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_BELOW_SMA_50 = "ta_SMA_50_pb50";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_BELOW_SMA_50 = "ta_SMA_50_pb40";
        public static final String I_50_DAY_SIMPLE_MOVING_AVERAGE_SMA_50_CROSSED_SMA_20_BELOW = "ta_SMA_50_cross20b";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb20";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa30";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa50";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_ABOVE_SMA_20 = "ta_SMA_200_sa20";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_CROSSED_SMA_20 = "ta_SMA_200_cross20";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_90_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa90";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_70_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa70";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_CROSSED_SMA_50_BELOW = "ta_SMA_200_cross50b";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa20";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_BELOW_SMA_20 = "ta_SMA_200_sb20";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_CROSSED_SMA_50_ABOVE = "ta_SMA_200_cross50a";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_CROSSED_SMA_20_ABOVE = "ta_SMA_200_cross20a";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_60_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb60";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_80_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb80";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb40";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_CROSSED_SMA_20_BELOW = "ta_SMA_200_cross20b";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_60_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa60";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb10";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa40";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_ABOVE_SMA_50 = "ta_SMA_200_sa50";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_CROSSED_SMA_50 = "ta_SMA_200_cross50";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_SMA_200_BELOW_SMA_50 = "ta_SMA_200_sb50";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa10";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_80_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa80";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_ABOVE_SMA_200 = "ta_SMA_200_pa";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_100_PERCENT_ABOVE_SMA_200 = "ta_SMA_200_pa100";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_BELOW_SMA_200 = "ta_SMA_200_pb";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_90_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb90";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_200 = "ta_SMA_200_pc";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_70_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb70";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb30";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_200_BELOW = "ta_SMA_200_pcb";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA_200_ABOVE = "ta_SMA_200_pca";
        public static final String I_200_DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_BELOW_SMA_200 = "ta_SMA_200_pb50";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_10_PERCENT = "ta_change_u10";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN = "ta_change_d";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_20_PERCENT = "ta_change_u20";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_2_PERCENT = "ta_change_u2";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_1_PERCENT = "ta_change_u1";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_4_PERCENT = "ta_change_u4";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_3_PERCENT = "ta_change_u3";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_9_PERCENT = "ta_change_d9";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_8_PERCENT = "ta_change_d8";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_5_PERCENT = "ta_change_d5";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_15_PERCENT = "ta_change_u15";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_4_PERCENT = "ta_change_d4";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_7_PERCENT = "ta_change_d7";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_6_PERCENT = "ta_change_d6";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_1_PERCENT = "ta_change_d1";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_2_PERCENT = "ta_change_d2";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP = "ta_change_u";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_3_PERCENT = "ta_change_d3";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_20_PERCENT = "ta_change_d20";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_10_PERCENT = "ta_change_d10";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_7_PERCENT = "ta_change_u7";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_8_PERCENT = "ta_change_u8";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_5_PERCENT = "ta_change_u5";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_6_PERCENT = "ta_change_u6";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_15_PERCENT = "ta_change_d15";
        public static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_9_PERCENT = "ta_change_u9";
        public static final String CHANGE_FROM_OPEN_UP_10_PERCENT = "ta_changeopen_u10";
        public static final String CHANGE_FROM_OPEN_DOWN = "ta_changeopen_d";
        public static final String CHANGE_FROM_OPEN_UP_20_PERCENT = "ta_changeopen_u20";
        public static final String CHANGE_FROM_OPEN_UP_2_PERCENT = "ta_changeopen_u2";
        public static final String CHANGE_FROM_OPEN_UP_1_PERCENT = "ta_changeopen_u1";
        public static final String CHANGE_FROM_OPEN_UP_4_PERCENT = "ta_changeopen_u4";
        public static final String CHANGE_FROM_OPEN_UP_3_PERCENT = "ta_changeopen_u3";
        public static final String CHANGE_FROM_OPEN_DOWN_9_PERCENT = "ta_changeopen_d9";
        public static final String CHANGE_FROM_OPEN_DOWN_8_PERCENT = "ta_changeopen_d8";
        public static final String CHANGE_FROM_OPEN_DOWN_5_PERCENT = "ta_changeopen_d5";
        public static final String CHANGE_FROM_OPEN_UP_15_PERCENT = "ta_changeopen_u15";
        public static final String CHANGE_FROM_OPEN_DOWN_4_PERCENT = "ta_changeopen_d4";
        public static final String CHANGE_FROM_OPEN_DOWN_7_PERCENT = "ta_changeopen_d7";
        public static final String CHANGE_FROM_OPEN_DOWN_6_PERCENT = "ta_changeopen_d6";
        public static final String CHANGE_FROM_OPEN_DOWN_1_PERCENT = "ta_changeopen_d1";
        public static final String CHANGE_FROM_OPEN_DOWN_2_PERCENT = "ta_changeopen_d2";
        public static final String CHANGE_FROM_OPEN_UP = "ta_changeopen_u";
        public static final String CHANGE_FROM_OPEN_DOWN_3_PERCENT = "ta_changeopen_d3";
        public static final String CHANGE_FROM_OPEN_DOWN_20_PERCENT = "ta_changeopen_d20";
        public static final String CHANGE_FROM_OPEN_DOWN_10_PERCENT = "ta_changeopen_d10";
        public static final String CHANGE_FROM_OPEN_UP_7_PERCENT = "ta_changeopen_u7";
        public static final String CHANGE_FROM_OPEN_UP_8_PERCENT = "ta_changeopen_u8";
        public static final String CHANGE_FROM_OPEN_UP_5_PERCENT = "ta_changeopen_u5";
        public static final String CHANGE_FROM_OPEN_UP_6_PERCENT = "ta_changeopen_u6";
        public static final String CHANGE_FROM_OPEN_DOWN_15_PERCENT = "ta_changeopen_d15";
        public static final String CHANGE_FROM_OPEN_UP_9_PERCENT = "ta_changeopen_u9";
        public static final String I_20_DAY_HIGH_LOW_I_10_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a10h";
        public static final String I_20_DAY_HIGH_LOW_I_20_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a20h";
        public static final String I_20_DAY_HIGH_LOW_I_40_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b40h";
        public static final String I_20_DAY_HIGH_LOW_I_0NEGATIVE_10_PERCENT_BELOW_HIGH = "ta_HIGH_LOW20d_b0to10h";
        public static final String I_20_DAY_HIGH_LOW_I_15_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a15h";
        public static final String I_20_DAY_HIGH_LOW_I_10_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b10h";
        public static final String I_20_DAY_HIGH_LOW_I_5_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b5h";
        public static final String I_20_DAY_HIGH_LOW_I_5_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a5h";
        public static final String I_20_DAY_HIGH_LOW_I_0_TO_10_PERCENT_ABOVE_LOW = "ta_HIGH_LOW20d_a0to10h";
        public static final String I_20_DAY_HIGH_LOW_I_30_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a30h";
        public static final String I_20_DAY_HIGH_LOW_I_20_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b20h";
        public static final String I_20_DAY_HIGH_LOW_I_50_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a50h";
        public static final String I_20_DAY_HIGH_LOW_I_30_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b30h";
        public static final String I_20_DAY_HIGH_LOW_I_0_TO_3_PERCENT_ABOVE_LOW = "ta_HIGH_LOW20d_a0to3h";
        public static final String I_20_DAY_HIGH_LOW_I_0_TO_5_PERCENT_ABOVE_LOW = "ta_HIGH_LOW20d_a0to5h";
        public static final String I_20_DAY_HIGH_LOW_I_0_TO_3_PERCENT_BELOW_HIGH = "ta_HIGH_LOW20d_b0to3h";
        public static final String I_20_DAY_HIGH_LOW_I_15_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b15h";
        public static final String I_20_DAY_HIGH_LOW_I_0_TO_5_PERCENT_BELOW_HIGH = "ta_HIGH_LOW20d_b0to5h";
        public static final String I_20_DAY_HIGH_LOW_I_40_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW20d_a40h";
        public static final String I_20_DAY_HIGH_LOW_NEW_HIGH = "ta_HIGH_LOW20d_nh";
        public static final String I_20_DAY_HIGH_LOW_NEW_LOW = "ta_HIGH_LOW20d_nl";
        public static final String I_20_DAY_HIGH_LOW_I_50_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW20d_b50h";
        public static final String I_50_DAY_HIGH_LOW_I_10_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a10h";
        public static final String I_50_DAY_HIGH_LOW_I_20_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a20h";
        public static final String I_50_DAY_HIGH_LOW_I_40_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b40h";
        public static final String I_50_DAY_HIGH_LOW_I_0_TO_10_PERCENT_BELOW_HIGH = "ta_HIGH_LOW50d_b0to10h";
        public static final String I_50_DAY_HIGH_LOW_I_15_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a15h";
        public static final String I_50_DAY_HIGH_LOW_I_10_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b10h";
        public static final String I_50_DAY_HIGH_LOW_I_5_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b5h";
        public static final String I_50_DAY_HIGH_LOW_I_5_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a5h";
        public static final String I_50_DAY_HIGH_LOW_I_0_TO_10_PERCENT_ABOVE_LOW = "ta_HIGH_LOW50d_a0to10h";
        public static final String I_50_DAY_HIGH_LOW_I_30_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a30h";
        public static final String I_50_DAY_HIGH_LOW_I_20_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b20h";
        public static final String I_50_DAY_HIGH_LOW_I_50_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a50h";
        public static final String I_50_DAY_HIGH_LOW_I_30_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b30h";
        public static final String I_50_DAY_HIGH_LOW_I_0_TO_3_PERCENT_ABOVE_LOW = "ta_HIGH_LOW50d_a0to3h";
        public static final String I_50_DAY_HIGH_LOW_I_0_TO_5_PERCENT_ABOVE_LOW = "ta_HIGH_LOW50d_a0to5h";
        public static final String I_50_DAY_HIGH_LOW_I_0_TO_3_PERCENT_BELOW_HIGH = "ta_HIGH_LOW50d_b0to3h";
        public static final String I_50_DAY_HIGH_LOW_I_15_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b15h";
        public static final String I_50_DAY_HIGH_LOW_I_0_TO_5_PERCENT_BELOW_HIGH = "ta_HIGH_LOW50d_b0to5h";
        public static final String I_50_DAY_HIGH_LOW_I_40_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW50d_a40h";
        public static final String I_50_DAY_HIGH_LOW_NEW_HIGH = "ta_HIGH_LOW50d_nh";
        public static final String I_50_DAY_HIGH_LOW_NEW_LOW = "ta_HIGH_LOW50d_nl";
        public static final String I_50_DAY_HIGH_LOW_I_50_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW50d_b50h";
        public static final String I_52_WEEK_HIGH_LOW_I_10_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a10h";
        public static final String I_52_WEEK_HIGH_LOW_I_20_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a20h";
        public static final String I_52_WEEK_HIGH_LOW_I_40_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b40h";
        public static final String I_52_WEEK_HIGH_LOW_I_0_TO_10_PERCENT_BELOW_HIGH = "ta_HIGH_LOW52_W_b0to10h";
        public static final String I_52_WEEK_HIGH_LOW_I_15_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a15h";
        public static final String I_52_WEEK_HIGH_LOW_I_10_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b10h";
        public static final String I_52_WEEK_HIGH_LOW_I_5_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b5h";
        public static final String I_52_WEEK_HIGH_LOW_I_5_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a5h";
        public static final String I_52_WEEK_HIGH_LOW_I_0_TO_10_PERCENT_ABOVE_LOW = "ta_HIGH_LOW52_W_a0to10h";
        public static final String I_52_WEEK_HIGH_LOW_I_30_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a30h";
        public static final String I_52_WEEK_HIGH_LOW_I_20_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b20h";
        public static final String I_52_WEEK_HIGH_LOW_I_50_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a50h";
        public static final String I_52_WEEK_HIGH_LOW_I_30_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b30h";
        public static final String I_52_WEEK_HIGH_LOW_I_0_TO_3_PERCENT_ABOVE_LOW = "ta_HIGH_LOW52_W_a0to3h";
        public static final String I_52_WEEK_HIGH_LOW_I_0_TO_5_PERCENT_ABOVE_LOW = "ta_HIGH_LOW52_W_a0to5h";
        public static final String I_52_WEEK_HIGH_LOW_I_0_TO_3_PERCENT_BELOW_HIGH = "ta_HIGH_LOW52_W_b0to3h";
        public static final String I_52_WEEK_HIGH_LOW_I_15_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b15h";
        public static final String I_52_WEEK_HIGH_LOW_I_0_TO_5_PERCENT_BELOW_HIGH = "ta_HIGH_LOW52_W_b0to5h";
        public static final String I_52_WEEK_HIGH_LOW_I_40_PERCENT_OR_MORE_ABOVE_LOW = "ta_HIGH_LOW52_W_a40h";
        public static final String I_52_WEEK_HIGH_LOW_NEW_HIGH = "ta_HIGH_LOW52_W_nh";
        public static final String I_52_WEEK_HIGH_LOW_NEW_LOW = "ta_HIGH_LOW52_W_nl";
        public static final String I_52_WEEK_HIGH_LOW_I_50_PERCENT_OR_MORE_BELOW_HIGH = "ta_HIGH_LOW52_W_b50h";
        public static final String PATTERN_CHANNEL_UP_STRONG = "ta_pattern_channelup2";
        public static final String PATTERN_TRIANGLE_ASCENDING_STRONG = "ta_pattern_wedgeresistance2";
        public static final String PATTERN_DOUBLE_TOP = "ta_pattern_doubletop";
        public static final String PATTERN_WEDGE_STRONG = "ta_pattern_wedge2";
        public static final String PATTERN_WEDGE_DOWN_STRONG = "ta_pattern_wedgedown2";
        public static final String PATTERN_CHANNEL_DOWN = "ta_pattern_channeldown";
        public static final String PATTERN_MULTIPLE_BOTTOM = "ta_pattern_multiplebottom";
        public static final String PATTERN_DOUBLE_BOTTOM = "ta_pattern_doublebottom";
        public static final String PATTERN_TL_SUPPORT = "ta_pattern_tlsupport";
        public static final String PATTERN_WEDGE_UP_STRONG = "ta_pattern_wedgeup2";
        public static final String PATTERN_TRIANGLE_DESCENDING_STRONG = "ta_pattern_wedgesupport2";
        public static final String PATTERN_HEAD_AND_SHOULDERS_INVERSE = "ta_pattern_headandshouldersinv";
        public static final String PATTERN_CHANNEL_DOWN_STRONG = "ta_pattern_channeldown2";
        public static final String PATTERN_WEDGE = "ta_pattern_wedge";
        public static final String PATTERN_TL_RESISTANCE_STRONG = "ta_pattern_tlresistance2";
        public static final String PATTERN_HORIZONTAL_S_R_STRONG = "ta_pattern_horizontal2";
        public static final String PATTERN_CHANNEL_UP = "ta_pattern_channelup";
        public static final String PATTERN_MULTIPLE_TOP = "ta_pattern_multipletop";
        public static final String PATTERN_WEDGE_DOWN = "ta_pattern_wedgedown";
        public static final String PATTERN_TRIANGLE_DESCENDING = "ta_pattern_wedgesupport";
        public static final String PATTERN_CHANNEL_STRONG = "ta_pattern_channel2";
        public static final String PATTERN_HEAD_AND_SHOULDERS = "ta_pattern_headandshoulders";
        public static final String PATTERN_TRIANGLE_ASCENDING = "ta_pattern_wedgeresistance";
        public static final String PATTERN_HORIZONTAL_S_R = "ta_pattern_horizontal";
        public static final String PATTERN_WEDGE_UP = "ta_pattern_wedgeup";
        public static final String PATTERN_TL_RESISTANCE = "ta_pattern_tlresistance";
        public static final String PATTERN_TL_SUPPORT_STRONG = "ta_pattern_tlsupport2";
        public static final String PATTERN_CHANNEL = "ta_pattern_channel";
        public static final String CANDLESTICK_DRAGONFLY_DOJI = "ta_candlestick_dd";
        public static final String CANDLESTICK_DOJI = "ta_candlestick_d";
        public static final String CANDLESTICK_LONG_LOWER_SHADOW = "ta_candlestick_lls";
        public static final String CANDLESTICK_SPINNING_TOP_WHITE = "ta_candlestick_stw";
        public static final String CANDLESTICK_INVERTED_HAMMER = "ta_candlestick_ih";
        public static final String CANDLESTICK_SPINNING_TOP_BLACK = "ta_candlestick_stb";
        public static final String CANDLESTICK_MARUBOZU_BLACK = "ta_candlestick_mb";
        public static final String CANDLESTICK_GRAVESTONE_DOJI = "ta_candlestick_gd";
        public static final String CANDLESTICK_LONG_UPPER_SHADOW = "ta_candlestick_lus";
        public static final String CANDLESTICK_MARUBOZU_WHITE = "ta_candlestick_mw";
        public static final String CANDLESTICK_HAMMER = "ta_candlestick_h";
        public static final String BETA_I_0_TO_1 = "ta_beta_0to1";
        public static final String BETA_OVER_0 = "ta_beta_o0";
        public static final String BETA_OVER_0_POINT_5 = "ta_beta_o0.5";
        public static final String BETA_UNDER_0 = "ta_beta_u0";
        public static final String BETA_OVER_2 = "ta_beta_o2";
        public static final String BETA_OVER_1 = "ta_beta_o1";
        public static final String BETA_OVER_4 = "ta_beta_o4";
        public static final String BETA_UNDER_2 = "ta_beta_u2";
        public static final String BETA_UNDER_1 = "ta_beta_u1";
        public static final String BETA_UNDER_1_POINT_5 = "ta_beta_u1.5";
        public static final String BETA_OVER_3 = "ta_beta_o3";
        public static final String BETA_I_1_TO_2 = "ta_beta_1to2";
        public static final String BETA_OVER_1_POINT_5 = "ta_beta_o1.5";
        public static final String BETA_UNDER_0_POINT_5 = "ta_beta_u0.5";
        public static final String BETA_OVER_2_POINT_5 = "ta_beta_o2.5";
        public static final String BETA_I_0_POINT_5_TO_1_POINT_5 = "ta_beta_0.5to1.5";
        public static final String BETA_I_1_TO_1_POINT_5 = "ta_beta_1to1.5";
        public static final String BETA_I_0_POINT_5_TO_1 = "ta_beta_0.5to1";
        public static final String BETA_I_0_TO_0_POINT_5 = "ta_beta_0to0.5";
        public static final String AVERAGE_TRUE_RANGE_OVER_0_POINT_5 = "ta_averagetruerange_o0.5";
        public static final String AVERAGE_TRUE_RANGE_OVER_2 = "ta_averagetruerange_o2";
        public static final String AVERAGE_TRUE_RANGE_OVER_1 = "ta_averagetruerange_o1";
        public static final String AVERAGE_TRUE_RANGE_UNDER_2_POINT_5 = "ta_averagetruerange_u2.5";
        public static final String AVERAGE_TRUE_RANGE_UNDER_2 = "ta_averagetruerange_u2";
        public static final String AVERAGE_TRUE_RANGE_OVER_4 = "ta_averagetruerange_o4";
        public static final String AVERAGE_TRUE_RANGE_UNDER_1_POINT_5 = "ta_averagetruerange_u1.5";
        public static final String AVERAGE_TRUE_RANGE_UNDER_1 = "ta_averagetruerange_u1";
        public static final String AVERAGE_TRUE_RANGE_OVER_3 = "ta_averagetruerange_o3";
        public static final String AVERAGE_TRUE_RANGE_UNDER_4 = "ta_averagetruerange_u4";
        public static final String AVERAGE_TRUE_RANGE_UNDER_3 = "ta_averagetruerange_u3";
        public static final String AVERAGE_TRUE_RANGE_OVER_1_POINT_5 = "ta_averagetruerange_o1.5";
        public static final String AVERAGE_TRUE_RANGE_OVER_5 = "ta_averagetruerange_o5";
        public static final String AVERAGE_TRUE_RANGE_UNDER_0_POINT_5 = "ta_averagetruerange_u0.5";
        public static final String AVERAGE_TRUE_RANGE_OVER_2_POINT_5 = "ta_averagetruerange_o2.5";
        public static final String AVERAGE_TRUE_RANGE_UNDER_3_POINT_5 = "ta_averagetruerange_u3.5";
        public static final String AVERAGE_TRUE_RANGE_UNDER_0_POINT_75 = "ta_averagetruerange_u0.75";
        public static final String AVERAGE_TRUE_RANGE_UNDER_0_POINT_25 = "ta_averagetruerange_u0.25";
        public static final String AVERAGE_TRUE_RANGE_OVER_4_POINT_5 = "ta_averagetruerange_o4.5";
        public static final String AVERAGE_TRUE_RANGE_OVER_0_POINT_75 = "ta_averagetruerange_o0.75";
        public static final String AVERAGE_TRUE_RANGE_UNDER_5 = "ta_averagetruerange_u5";
        public static final String AVERAGE_TRUE_RANGE_OVER_0_POINT_25 = "ta_averagetruerange_o0.25";
        public static final String AVERAGE_TRUE_RANGE_OVER_3_POINT_5 = "ta_averagetruerange_o3.5";
        public static final String AVERAGE_TRUE_RANGE_UNDER_4_POINT_5 = "ta_averagetruerange_u4.5";
        public static final String AVERAGE_VOLUME_UNDER_750_K = "sh_avgvol_u750";
        public static final String AVERAGE_VOLUME_OVER_500_K = "sh_avgvol_o500";
        public static final String AVERAGE_VOLUME_I_100_K_TO_1M = "sh_avgvol_100to1000";
        public static final String AVERAGE_VOLUME_OVER_200_K = "sh_avgvol_o200";
        public static final String AVERAGE_VOLUME_OVER_50_K = "sh_avgvol_o50";
        public static final String AVERAGE_VOLUME_UNDER_50_K = "sh_avgvol_u50";
        public static final String AVERAGE_VOLUME_I_500_K_TO_1M = "sh_avgvol_500to1000";
        public static final String AVERAGE_VOLUME_UNDER_1M = "sh_avgvol_u1000";
        public static final String AVERAGE_VOLUME_UNDER_500_K = "sh_avgvol_u500";
        public static final String AVERAGE_VOLUME_OVER_750_K = "sh_avgvol_o750";
        public static final String AVERAGE_VOLUME_I_500_K_TO_10M = "sh_avgvol_500to10000";
        public static final String AVERAGE_VOLUME_OVER_100_K = "sh_avgvol_o100";
        public static final String AVERAGE_VOLUME_OVER_2M = "sh_avgvol_o2000";
        public static final String AVERAGE_VOLUME_I_100_K_TO_500_K = "sh_avgvol_100to500";
        public static final String AVERAGE_VOLUME_OVER_400_K = "sh_avgvol_o400";
        public static final String AVERAGE_VOLUME_OVER_1M = "sh_avgvol_o1000";
        public static final String AVERAGE_VOLUME_UNDER_100_K = "sh_avgvol_u100";
        public static final String AVERAGE_VOLUME_OVER_300_K = "sh_avgvol_o300";
        public static final String RELATIVE_VOLUME_OVER_0_POINT_5 = "sh_relvol_o0.5";
        public static final String RELATIVE_VOLUME_OVER_2 = "sh_relvol_o2";
        public static final String RELATIVE_VOLUME_OVER_1 = "sh_relvol_o1";
        public static final String RELATIVE_VOLUME_UNDER_2 = "sh_relvol_u2";
        public static final String RELATIVE_VOLUME_OVER_3 = "sh_relvol_o3";
        public static final String RELATIVE_VOLUME_UNDER_1_POINT_5 = "sh_relvol_u1.5";
        public static final String RELATIVE_VOLUME_UNDER_1 = "sh_relvol_u1";
        public static final String RELATIVE_VOLUME_OVER_5 = "sh_relvol_o5";
        public static final String RELATIVE_VOLUME_OVER_1_POINT_5 = "sh_relvol_o1.5";
        public static final String RELATIVE_VOLUME_UNDER_0_POINT_5 = "sh_relvol_u0.5";
        public static final String RELATIVE_VOLUME_UNDER_0_POINT_75 = "sh_relvol_u0.75";
        public static final String RELATIVE_VOLUME_OVER_10 = "sh_relvol_o10";
        public static final String RELATIVE_VOLUME_UNDER_0_POINT_25 = "sh_relvol_u0.25";
        public static final String RELATIVE_VOLUME_UNDER_0_POINT_1 = "sh_relvol_u0.1";
        public static final String RELATIVE_VOLUME_OVER_0_POINT_75 = "sh_relvol_o0.75";
        public static final String RELATIVE_VOLUME_OVER_0_POINT_25 = "sh_relvol_o0.25";
        public static final String PRICE_UNDER_20_DOLLARS = "sh_price_u20";
        public static final String PRICE_1_TO_10_DOLLARS = "sh_price_1to10";
        public static final String PRICE_OVER_80_DOLLARS = "sh_price_o80";
        public static final String PRICE_OVER_60_DOLLARS = "sh_price_o60";
        public static final String PRICE_UNDER_40_DOLLARS = "sh_price_u40";
        public static final String PRICE_5_TO_20_DOLLARS = "sh_price_5to20";
        public static final String PRICE_OVER_40_DOLLARS = "sh_price_o40";
        public static final String PRICE_OVER_20_DOLLARS = "sh_price_o20";
        public static final String PRICE_20_TO_50_DOLLARS = "sh_price_20to50";
        public static final String PRICE_10_TO_20_DOLLARS = "sh_price_10to20";
        public static final String PRICE_UNDER_7_DOLLARS = "sh_price_u7";
        public static final String PRICE_UNDER_5_DOLLARS = "sh_price_u5";
        public static final String PRICE_UNDER_10_DOLLARS = "sh_price_u10";
        public static final String PRICE_OVER_2_DOLLARS = "sh_price_o2";
        public static final String PRICE_OVER_1_DOLLARS = "sh_price_o1";
        public static final String PRICE_1_TO_20_DOLLARS = "sh_price_1to20";
        public static final String PRICE_OVER_70_DOLLARS = "sh_price_o70";
        public static final String PRICE_UNDER_2_DOLLARS = "sh_price_u2";
        public static final String PRICE_OVER_4_DOLLARS = "sh_price_o4";
        public static final String PRICE_UNDER_1_DOLLARS = "sh_price_u1";
        public static final String PRICE_OVER_3_DOLLARS = "sh_price_o3";
        public static final String PRICE_UNDER_4_DOLLARS = "sh_price_u4";
        public static final String PRICE_UNDER_3_DOLLARS = "sh_price_u3";
        public static final String PRICE_OVER_5_DOLLARS = "sh_price_o5";
        public static final String PRICE_OVER_15_DOLLARS = "sh_price_o15";
        public static final String PRICE_1_TO_5_DOLLARS = "sh_price_1to5";
        public static final String PRICE_OVER_7_DOLLARS = "sh_price_o7";
        public static final String PRICE_OVER_30_DOLLARS = "sh_price_o30";
        public static final String PRICE_UNDER_30_DOLLARS = "sh_price_u30";
        public static final String PRICE_5_TO_50_DOLLARS = "sh_price_5to50";
        public static final String PRICE_OVER_50_DOLLARS = "sh_price_o50";
        public static final String PRICE_UNDER_50_DOLLARS = "sh_price_u50";
        public static final String PRICE_UNDER_15_DOLLARS = "sh_price_u15";
        public static final String PRICE_OVER_90_DOLLARS = "sh_price_o90";
        public static final String PRICE_5_TO_10_DOLLARS = "sh_price_5to10";
        public static final String PRICE_50_TO_100_DOLLARS = "sh_price_50to100";
        public static final String PRICE_10_TO_50_DOLLARS = "sh_price_10to50";
        public static final String PRICE_OVER_100_DOLLARS = "sh_price_o100";
        public static final String PRICE_OVER_10_DOLLARS = "sh_price_o10";
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
        public static enum FeatureCategory { Exchange,Index,Sector,Industry,Country,MarketCap,PricetoEarningsRatio,ForwardPricetoEarningsRatio,PricetoEarningstoGrowth,
            PricetoSalesRatio,PricetoBookRatio,PricetoCashRatio,PricetoFreeCashFlowttm,Epsgrowththisyear,Epsgrowthnextyear,
            Epsgrowthpast5years,Epsgrowthnext5years,SalesGrowthpast5years,Epsgrowthqtroverqtr,Salesgrowthqtroverqtr,DividendYield,
            ReturnonAssetsttm,ReturnonEquityttm,ReturnonInvestmentttm,CurrentRatiomrq,QuickRatiomrq,LongTermDebttoEquitymrq,
            TotalDebttoEquitymrq,GrossMarginttm,OperatingMarginttm,NetProfitMargin,PayoutRatio,InsiderOwnership,InsiderTranscations,
            InstitutionalOwnership,InstitutionalTransaction,ShortSelling,AnalystRecommendation,OptionShort,EarningsDate,Performance,
            Performance2,Volatility,RelativeStrengthIndex14,Gap,I_20_DAYSimpleMovingAverage,I_50_DAYSimpleMovingAverage,
            I_200_DAYSimpleMovingAverage,ChangefrompreviousClose,ChangefromOpen,I_20_DAY_HIGH_LOW,I_50_DAY_HIGH_LOW,I_52_Week_HIGH_LOW,Pattern,
            Candlestick,Beta,AverageTrueRange,AverageVolume,RelativeVolume,Price }
        public static enum Exchange { NASDAQ, AMEX,NYSE }
        public static enum Index { SANDP500, DJIA }
        public static enum Sector { FINANCIAL, TECHNOLOGY, CONGLOMERATES, INDUSTRIAL_GOODS,
                                    UTILITIES, BASIC_MATERIALS, CONSUMER_GOODS, HEALTHCARE, SERVICES }
        public static enum Industry { DRUG_RELATED_PRODUCTS, DIAGNOSTIC_SUBSTANCES,LONG_TERM_CARE_FACILITIES,DRUG_MANUFACTURERS_MAJOR,HOME_HEALTH_CARE,
                                        STOCKS_ONLY_EX_FUNDS,HOSPITALS,BIOTECHNOLOGY,DRUGS_GENERIC,DRUG_DELIVERY,MEDICAL_INSTRUMENTS_AND_SUPPLIES,
                                        SPECIALIZED_HEALTH_SERVICES,HEALTH_CARE_PLANS,MEDICAL_LABORATORIES_AND_RESEARCH,DRUG_MANUFACTURERS_OTHER,
                                        MEDICAL_PRACTITIONERS,MEDICAL_APPLIANCES_AND_EQUIPMENT }
        public static enum Country { IRELAND,BERMUDA,SPAIN,COLOMBIA,CHANNEL_ISLANDS,INDONESIA,CHINA_AND_HONG_KONG,GERMANY,PORTUGAL,
                                        BAHAMAS,NETHERLANDS_ANTILLES,CHINA,SOUTH_KOREA,CANADA,HONG_KONG,MEXICO,FINLAND,BELGIUM,MALAYSIA,UNITED_KINGDOM,
                                        ICELAND,AUSTRALIA,PHILIPPINES,MARSHALL_ISLANDS,SOUTH_AFRICA,EUROPE,PERU,PAPUA_NEW_GUINEA,FOREIGN_EX_USA,MONACO,
                                        ISRAEL,USA,SINGAPORE,NORWAY,ARGENTINA,CHILE,LATIN_AMERICA,JAPAN,BRITISH_VIRGIN_ISLANDS,ASIA,
                                        SWITZERLAND,FRANCE,INDIA,ITALY,SWEDEN,HUNGARY,KAZAKHSTAN,TAIWAN,GREECE,UNITED_ARAB_EMIRATES,CYPRUS,
                                        NETHERLANDS,LUXEMBOURG,BENELUX,PANAMA,BRAZIL,RUSSIA,NEW_ZEALAND,TURKEY,DENMARK,BRIC,CAYMAN_ISLANDS }
        public static enum MarketCap { LESS_SMALL,MORE_MID,LESS_LARGE,NANO,LESS_MID,MICRO,MORE_SMALL,MEGA,MORE_MICRO,MID,
                                        LESS_MICRO,SMALL,MORE_LARGE,LARGE }
        public static enum PriceToEarningsRatio { UNDER_10,UNDER_20,OVER_5,PROFITABLE,OVER_15,OVER_30,UNDER_30,OVER_50,OVER_25,
                                                    UNDER_40,OVER_40,UNDER_50,UNDER_15,UNDER_45,UNDER_25,UNDER_35,OVER_10,OVER_35,
                                                    OVER_20,OVER_45,UNDER_5,HIGH,LOW }
        public static enum ForwardPriceToEarningsRatio { UNDER_10,UNDER_20,OVER_5,PROFITABLE,OVER_15,OVER_30,UNDER_30,OVER_50,OVER_25,
                                                    UNDER_40,OVER_40,UNDER_50,UNDER_15,UNDER_45,UNDER_25,UNDER_35,OVER_10,OVER_35,
                                                    OVER_20,OVER_45,UNDER_5,HIGH,LOW }
        public static enum PriceToEarningsToGrowth { OVER_2,OVER_1,UNDER_2,OVER_3,UNDER_1,UNDER_3,HIGH,LOW }
        public static enum PriceToSalesRatio { UNDER_10,OVER_2,OVER_1,OVER_4,UNDER_2,OVER_3,UNDER_1,OVER_6,UNDER_4,OVER_5,UNDER_3,
                                                OVER_8,OVER_7,OVER_9,OVER_10,UNDER_7,UNDER_8,UNDER_5,HIGH,UNDER_6,LOW,UNDER_9}
        public static enum PriceToBookRatio { UNDER_10,OVER_2,OVER_1,OVER_4,UNDER_2,OVER_3,UNDER_1,OVER_6,UNDER_4,OVER_5,UNDER_3,
                                                OVER_8,OVER_7,OVER_9,OVER_10,UNDER_7,UNDER_8,UNDER_5,HIGH,UNDER_6,LOW,UNDER_9 }
        public static enum PriceToCashRatio { UNDER_10,OVER_2,OVER_1,OVER_4,UNDER_2,OVER_3,UNDER_1,OVER_6,UNDER_4,OVER_5,UNDER_3,
                                                OVER_8,OVER_7,OVER_9,OVER_10,UNDER_7,UNDER_8,UNDER_5,HIGH,UNDER_6,LOW,UNDER_9,
                                                OVER_40,OVER_20,OVER_30,OVER_50}
        public static enum PriceToFreeCashFlowTtm { HIGH,LOW,UNDER_20,UNDER_70,OVER_80,OVER_60,UNDER_40,OVER_25,OVER_40,UNDER_45,
                                                    UNDER_90,UNDER_25,UNDER_60,OVER_20,OVER_45,UNDER_5,UNDER_10,OVER_70,OVER_5,
                                                    OVER_15,UNDER_80,OVER_30,UNDER_30,OVER_50,UNDER_50,UNDER_15,OVER_90,UNDER_35,OVER_100,
                                                    OVER_10,OVER_35,UNDER_100 }
        public static enum EpsGrowthThisYear { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum EpsGrowthNextYear { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum EpsGrowthPast5Years { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum EpsGrowthNext5Years { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum SalesGrowthPast5Years { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum EpsGrowthQtrOverQtr { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum SalesGrowthQtrOverQtr { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum DividendYield { HIGH,OVER_2_PERCENT,OVER_1_PERCENT,VERY_HIGH,OVER_4_PERCENT,OVER_3_PERCENT,OVER_6_PERCENT,
                                            OVER_5_PERCENT,OVER_8_PERCENT,OVER_7_PERCENT,OVER_9_PERCENT,POSITIVE,OVER_10_PERCENT,NONE }
        public static enum ReturnOnAssetsTtm { UNDER_NEGATIVE_10_PERCENT,NEGATIVE,UNDER_NEGATIVE_35_PERCENT,UNDER_NEGATIVE_25_PERCENT,UNDER_NEGATIVE_45_PERCENT,
                                                VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,UNDER_NEGATIVE_40_PERCENT,
                                                UNDER_NEGATIVE_15_PERCENT,OVER_50_PERCENT,OVER_25_PERCENT,OVER_40_PERCENT,VERY_NEGATIVE,
                                                UNDER_NEGATIVE_50_PERCENT,UNDER_NEGATIVE_30_PERCENT,POSITIVE,UNDER_NEGATIVE_20_PERCENT,UNDER_NEGATIVE_5_PERCENT,
                                                OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT }
        public static enum ReturnOnEquityTtm { UNDER_NEGATIVE_10_PERCENT,NEGATIVE,UNDER_NEGATIVE_35_PERCENT,UNDER_NEGATIVE_25_PERCENT,UNDER_NEGATIVE_45_PERCENT,
                                                VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,UNDER_NEGATIVE_40_PERCENT,
                                                UNDER_NEGATIVE_15_PERCENT,OVER_50_PERCENT,OVER_25_PERCENT,OVER_40_PERCENT,VERY_NEGATIVE,
                                                UNDER_NEGATIVE_50_PERCENT,UNDER_NEGATIVE_30_PERCENT,POSITIVE,UNDER_NEGATIVE_20_PERCENT,UNDER_NEGATIVE_5_PERCENT,
                                                OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT }
        public static enum ReturnOnInvestmentTtm { UNDER_NEGATIVE_10_PERCENT,NEGATIVE,UNDER_NEGATIVE_35_PERCENT,UNDER_NEGATIVE_25_PERCENT,UNDER_NEGATIVE_45_PERCENT,
                                                VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,UNDER_NEGATIVE_40_PERCENT,
                                                UNDER_NEGATIVE_15_PERCENT,OVER_50_PERCENT,OVER_25_PERCENT,OVER_40_PERCENT,VERY_NEGATIVE,
                                                UNDER_NEGATIVE_50_PERCENT,UNDER_NEGATIVE_30_PERCENT,POSITIVE,UNDER_NEGATIVE_20_PERCENT,UNDER_NEGATIVE_5_PERCENT,
                                                OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT }
        public static enum CurrentRatioMrq { OVER_0_POINT_5,OVER_2,OVER_1,OVER_10,OVER_4,OVER_3,UNDER_1,OVER_5,OVER_1_POINT_5,
                                                UNDER_0_POINT_5,HIGH,LOW }
        public static enum QuickRatioMrq { OVER_0_POINT_5,OVER_2,OVER_1,OVER_10,OVER_4,OVER_3,UNDER_1,OVER_5,OVER_1_POINT_5,
                                            UNDER_0_POINT_5,HIGH,LOW }
        public static enum LongTermDebtToEquityMrq { OVER_0_POINT_5,OVER_0_POINT_6,OVER_0_POINT_7,OVER_1,OVER_0_POINT_8,
                                                        OVER_0_POINT_9,UNDER_1,UNDER_0_POINT_5,UNDER_0_POINT_6,UNDER_0_POINT_3,
                                                        UNDER_0_POINT_4,OVER_0_POINT_1,UNDER_0_POINT_9,OVER_0_POINT_2,OVER_0_POINT_3,
                                                        UNDER_0_POINT_7,OVER_0_POINT_4,UNDER_0_POINT_8,UNDER_0_POINT_2,UNDER_0_POINT_1,
                                                        HIGH,LOW }
        public static enum TotalDebtToEquityMrq { OVER_0_POINT_5,OVER_0_POINT_6,OVER_0_POINT_7,OVER_1,OVER_0_POINT_8,OVER_0_POINT_9,
                                                    UNDER_1,UNDER_0_POINT_5,UNDER_0_POINT_6,UNDER_0_POINT_3,UNDER_0_POINT_4,OVER_0_POINT_1,
                                                    UNDER_0_POINT_9,OVER_0_POINT_2,OVER_0_POINT_3,UNDER_0_POINT_7,OVER_0_POINT_4,
                                                    UNDER_0_POINT_8,UNDER_0_POINT_2,UNDER_0_POINT_1,HIGH,LOW }
        public static enum GrossMarginTtm { NEGATIVE,UNDER_NEGATIVE_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,OVER_60_PERCENT,
                                            OVER_25_PERCENT,UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_45_PERCENT,UNDER_NEGATIVE_50_PERCENT,UNDER_90_PERCENT,
                                            UNDER_NEGATIVE_30_PERCENT,POSITIVE,UNDER_25_PERCENT,UNDER_60_PERCENT,UNDER_NEGATIVE_70_PERCENT,
                                            OVER_20_PERCENT,UNDER_NEGATIVE_100_PERCENT,OVER_45_PERCENT,UNDER_5_PERCENT,OVER_0_PERCENT,
                                            UNDER_10_PERCENT,UNDER_0_PERCENT,OVER_70_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_80_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,UNDER_50_PERCENT,UNDER_15_PERCENT,
                                            OVER_90_PERCENT,UNDER_35_PERCENT,UNDER_NEGATIVE_20_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,HIGH }
        public static enum OperatingMarginTtm { NEGATIVE,UNDER_NEGATIVE_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,OVER_60_PERCENT,
                                            OVER_25_PERCENT,UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_45_PERCENT,UNDER_NEGATIVE_50_PERCENT,UNDER_90_PERCENT,
                                            UNDER_NEGATIVE_30_PERCENT,POSITIVE,UNDER_25_PERCENT,UNDER_60_PERCENT,UNDER_NEGATIVE_70_PERCENT,
                                            OVER_20_PERCENT,UNDER_NEGATIVE_100_PERCENT,OVER_45_PERCENT,UNDER_5_PERCENT,OVER_0_PERCENT,
                                            UNDER_10_PERCENT,UNDER_0_PERCENT,OVER_70_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_80_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,UNDER_50_PERCENT,UNDER_15_PERCENT,
                                            OVER_90_PERCENT,UNDER_35_PERCENT,UNDER_NEGATIVE_20_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,HIGH }
        public static enum NetProfitMargin { NEGATIVE,UNDER_NEGATIVE_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,OVER_60_PERCENT,
                                            OVER_25_PERCENT,UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_45_PERCENT,UNDER_NEGATIVE_50_PERCENT,UNDER_90_PERCENT,
                                            UNDER_NEGATIVE_30_PERCENT,POSITIVE,UNDER_25_PERCENT,UNDER_60_PERCENT,UNDER_NEGATIVE_70_PERCENT,
                                            OVER_20_PERCENT,UNDER_NEGATIVE_100_PERCENT,OVER_45_PERCENT,UNDER_5_PERCENT,OVER_0_PERCENT,
                                            UNDER_10_PERCENT,UNDER_0_PERCENT,OVER_70_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_80_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,UNDER_50_PERCENT,UNDER_15_PERCENT,
                                            OVER_90_PERCENT,UNDER_35_PERCENT,UNDER_NEGATIVE_20_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,HIGH }
        public static enum PayoutRatio { OVER_0_PERCENT,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_70_PERCENT,UNDER_70_PERCENT,UNDER_80_PERCENT,
                                            OVER_80_PERCENT,OVER_30_PERCENT,UNDER_30_PERCENT,OVER_60_PERCENT,UNDER_40_PERCENT,
                                            OVER_50_PERCENT,UNDER_50_PERCENT,OVER_40_PERCENT,UNDER_90_PERCENT,OVER_90_PERCENT,
                                            POSITIVE,UNDER_60_PERCENT,OVER_10_PERCENT,OVER_20_PERCENT,NONE,HIGH,LOW,UNDER_100_PERCENT,
                                            OVER_100_PERCENT }
        public static enum InsiderOwnership { OVER_70_PERCENT,OVER_10_PERCENT,VERY_HIGH,OVER_20_PERCENT,OVER_80_PERCENT,OVER_30_PERCENT,
                                                OVER_60_PERCENT,OVER_50_PERCENT,HIGH,OVER_40_PERCENT,LOW,OVER_90_PERCENT }
        public static enum InsiderTranscations { UNDER_NEGATIVE_35_PERCENT,NEGATIVE,UNDER_NEGATIVE_10_PERCENT,OVER_80_PERCENT,
                                                    OVER_60_PERCENT,UNDER_NEGATIVE_90_PERCENT,UNDER_NEGATIVE_15_PERCENT,OVER_25_PERCENT,
                                                    OVER_40_PERCENT,UNDER_NEGATIVE_50_PERCENT,VERY_NEGATIVE,UNDER_NEGATIVE_30_PERCENT,
                                                    POSITIVE,UNDER_NEGATIVE_70_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT,UNDER_NEGATIVE_25_PERCENT,
                                                    OVER_70_PERCENT,UNDER_NEGATIVE_45_PERCENT,VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,
                                                    UNDER_NEGATIVE_40_PERCENT,OVER_30_PERCENT,OVER_50_PERCENT,OVER_90_PERCENT,UNDER_NEGATIVE_20_PERCENT,
                                                    UNDER_NEGATIVE_60_PERCENT,UNDER_NEGATIVE_5_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,UNDER_NEGATIVE_80_PERCENT,
                                                    UNDER_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT }
        public static enum InstitutionalOwnership { UNDER_10_PERCENT,OVER_70_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,
                                                    OVER_30_PERCENT,UNDER_80_PERCENT,OVER_60_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,
                                                    UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_50_PERCENT,OVER_90_PERCENT,UNDER_90_PERCENT,
                                                    UNDER_60_PERCENT,OVER_10_PERCENT,OVER_20_PERCENT,HIGH,LOW }
        public static enum InstitutionalTransaction { UNDER_NEGATIVE_35_PERCENT,NEGATIVE,UNDER_NEGATIVE_10_PERCENT,OVER_80_PERCENT,
                                                    OVER_60_PERCENT,UNDER_NEGATIVE_90_PERCENT,UNDER_NEGATIVE_15_PERCENT,OVER_25_PERCENT,
                                                    OVER_40_PERCENT,UNDER_NEGATIVE_50_PERCENT,VERY_NEGATIVE,UNDER_NEGATIVE_30_PERCENT,
                                                    POSITIVE,UNDER_NEGATIVE_70_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT,UNDER_NEGATIVE_25_PERCENT,
                                                    OVER_70_PERCENT,UNDER_NEGATIVE_45_PERCENT,VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,
                                                    UNDER_NEGATIVE_40_PERCENT,OVER_30_PERCENT,OVER_50_PERCENT,OVER_90_PERCENT,UNDER_NEGATIVE_20_PERCENT,
                                                    UNDER_NEGATIVE_60_PERCENT,UNDER_NEGATIVE_5_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,UNDER_NEGATIVE_80_PERCENT,
                                                    UNDER_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT }
        public static enum ShortSelling { OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT,OVER_40_PERCENT,
                                            UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,UNDER_25_PERCENT,UNDER_5_PERCENT,
                                            HIGH,LOW }
        public static enum AnalystRecommendation { STRONG_SELL,STRONG_BUY,SELL,BUY,BUY_OR_BETTER,HOLD,SELL_OR_WORSE }
        public static enum OptionShort { SHORTABLE,OPTIONABLE_AND_SHORTABLE,OPTIONABLE }
        public static enum EarningsDate { PREVIOUS_5_DAYS,TOMORROW,THIS_MONTH,NEXT_5_DAYS,YESTERDAY,TODAY,TODAY_AFTER_MARKET_CLOSE,
                                            PREVIOUS_WEEK,YESTERDAY_AFTER_MARKET_CLOSE,TODAY_BEFORE_MARKET_OPEN,THIS_WEEK,
                                            TOMORROW_AFTER_MARKET_CLOSE,TOMORROW_BEFORE_MARKET_OPEN,NEXT_WEEK,YESTERDAY_BEFORE_MARKET_OPEN }
        public static enum Performance { MONTH_NEGATIVE_50_PERCENT,HALF_50_PERCENT,WEEK_UP,HALF_NEGATIVE_50_PERCENT,MONTH_10_PERCENT,
                                            MONTH_50_PERCENT,HALF_30_PERCENT,QUARTER_DOWN,MONTH_NEGATIVE_10_PERCENT,YTD_NEGATIVE_30_PERCENT,
                                            QUARTER_NEGATIVE_10_PERCENT,YTD_20_PERCENT,YTD_NEGATIVE_20_PERCENT,YTD_30_PERCENT,QUARTER_30_PERCENT,
                                            YTD_5_PERCENT,QUARTER_NEGATIVE_20_PERCENT,HALF_NEGATIVE_30_PERCENT,YTD_NEGATIVE_5_PERCENT,
                                            YEAR_200_PERCENT,TODAY_UP,YEAR_NEGATIVE_50_PERCENT,QUARTER_UP,TODAY_5_PERCENT,
                                            QUARTER_50_PERCENT,YEAR_NEGATIVE_75_PERCENT,YEAR_20_PERCENT,YTD_50_PERCENT,QUARTER_NEGATIVE_30_PERCENT,
                                            YEAR_50_PERCENT,HALF_DOWN,QUARTER_NEGATIVE_50_PERCENT,TODAY_NEGATIVE_5_PERCENT,YEAR_NEGATIVE_20_PERCENT,
                                            WEEK_10_PERCENT,TODAY_NEGATIVE_10_PERCENT,TODAY_DOWN,WEEK_NEGATIVE_10_PERCENT,YTD_DOWN,
                                            HALF_NEGATIVE_75_PERCENT,TODAY_10_PERCENT,HALF_NEGATIVE_10_PERCENT,QUARTER_10_PERCENT,
                                            YTD_10_PERCENT,YEAR_NEGATIVE_10_PERCENT,HALF_NEGATIVE_20_PERCENT,YEAR_500_PERCENT,YTD_NEGATIVE_50_PERCENT,
                                            HALF_100_PERCENT,YTD_NEGATIVE_10_PERCENT,YEAR_10_PERCENT,QUARTER_20_PERCENT,YEAR_UP,
                                            WEEK_30_PERCENT,MONTH_NEGATIVE_30_PERCENT,YTD_NEGATIVE_75_PERCENT,WEEK_DOWN,HALF_UP,YEAR_300_PERCENT,
                                            MONTH_NEGATIVE_20_PERCENT,WEEK_20_PERCENT,HALF_10_PERCENT,YEAR_100_PERCENT,MONTH_DOWN,
                                            HALF_20_PERCENT,WEEK_NEGATIVE_20_PERCENT,MONTH_20_PERCENT,YTD_UP,TODAY_15_PERCENT,
                                            YTD_100_PERCENT,MONTH_UP,YEAR_NEGATIVE_30_PERCENT,YEAR_30_PERCENT,WEEK_NEGATIVE_30_PERCENT,
                                            TODAY_NEGATIVE_15_PERCENT,MONTH_30_PERCENT,YEAR_DOWN }
        public static enum Performance2 { MONTH_NEGATIVE_50_PERCENT,HALF_50_PERCENT,WEEK_UP,HALF_NEGATIVE_50_PERCENT,MONTH_10_PERCENT,
                                            MONTH_50_PERCENT,HALF_30_PERCENT,QUARTER_DOWN,MONTH_NEGATIVE_10_PERCENT,YTD_NEGATIVE_30_PERCENT,
                                            QUARTER_NEGATIVE_10_PERCENT,YTD_20_PERCENT,YTD_NEGATIVE_20_PERCENT,YTD_30_PERCENT,QUARTER_30_PERCENT,
                                            YTD_5_PERCENT,QUARTER_NEGATIVE_20_PERCENT,HALF_NEGATIVE_30_PERCENT,YTD_NEGATIVE_5_PERCENT,
                                            YEAR_200_PERCENT,TODAY_UP,YEAR_NEGATIVE_50_PERCENT,QUARTER_UP,TODAY_5_PERCENT,
                                            QUARTER_50_PERCENT,YEAR_NEGATIVE_75_PERCENT,YEAR_20_PERCENT,YTD_50_PERCENT,QUARTER_NEGATIVE_30_PERCENT,
                                            YEAR_50_PERCENT,HALF_DOWN,QUARTER_NEGATIVE_50_PERCENT,TODAY_NEGATIVE_5_PERCENT,YEAR_NEGATIVE_20_PERCENT,
                                            WEEK_10_PERCENT,TODAY_NEGATIVE_10_PERCENT,TODAY_DOWN,WEEK_NEGATIVE_10_PERCENT,YTD_DOWN,
                                            HALF_NEGATIVE_75_PERCENT,TODAY_10_PERCENT,HALF_NEGATIVE_10_PERCENT,QUARTER_10_PERCENT,
                                            YTD_10_PERCENT,YEAR_NEGATIVE_10_PERCENT,HALF_NEGATIVE_20_PERCENT,YEAR_500_PERCENT,YTD_NEGATIVE_50_PERCENT,
                                            HALF_100_PERCENT,YTD_NEGATIVE_10_PERCENT,YEAR_10_PERCENT,QUARTER_20_PERCENT,YEAR_UP,
                                            WEEK_30_PERCENT,MONTH_NEGATIVE_30_PERCENT,YTD_NEGATIVE_75_PERCENT,WEEK_DOWN,HALF_UP,YEAR_300_PERCENT,
                                            MONTH_NEGATIVE_20_PERCENT,WEEK_20_PERCENT,HALF_10_PERCENT,YEAR_100_PERCENT,MONTH_DOWN,
                                            HALF_20_PERCENT,WEEK_NEGATIVE_20_PERCENT,MONTH_20_PERCENT,YTD_UP,TODAY_15_PERCENT,
                                            YTD_100_PERCENT,MONTH_UP,YEAR_NEGATIVE_30_PERCENT,YEAR_30_PERCENT,WEEK_NEGATIVE_30_PERCENT,
                                            TODAY_NEGATIVE_15_PERCENT,MONTH_30_PERCENT,YEAR_DOWN }
        public static enum Volatility { WEEK_OVER_9_PERCENT,WEEK_OVER_12_PERCENT,WEEK_OVER_8_PERCENT,WEEK_OVER_15_PERCENT,
                                        MONTH_OVER_5_PERCENT,MONTH_OVER_4_PERCENT,MONTH_OVER_3_PERCENT,MONTH_OVER_2_PERCENT,
                                        MONTH_OVER_9_PERCENT,WEEK_OVER_10_PERCENT,MONTH_OVER_8_PERCENT,MONTH_OVER_7_PERCENT,
                                        MONTH_OVER_6_PERCENT,MONTH_OVER_15_PERCENT,MONTH_OVER_12_PERCENT,MONTH_OVER_10_PERCENT,
                                        WEEK_OVER_3_PERCENT,WEEK_OVER_4_PERCENT,WEEK_OVER_5_PERCENT,WEEK_OVER_6_PERCENT,WEEK_OVER_7_PERCENT }
        public static enum RelativeStrengthIndex14 { OVERSOLD_30,OVERSOLD_40,OVERSOLD_20,NOT_OVERSOLD_GREATER_THAN_50,OVERSOLD_10,
                                                        NOT_OVERSOLD_GREATER_THAN_40,OVERBOUGHT_90,NOT_OVERBOUGHT_LESS_THAN_60,
                                                        OVERBOUGHT_80,OVERBOUGHT_70,OVERBOUGHT_60,NOT_OVERBOUGHT_LESS_THAN_50 }
        public static enum Gap { UP_10_PERCENT,DOWN,UP_0_PERCENT,UP_20_PERCENT,UP_2_PERCENT,UP_1_PERCENT,UP_4_PERCENT,UP_3_PERCENT,
                                    DOWN_9_PERCENT,DOWN_8_PERCENT,DOWN_5_PERCENT,UP_15_PERCENT,DOWN_4_PERCENT,DOWN_7_PERCENT,
                                    DOWN_6_PERCENT,DOWN_0_PERCENT,DOWN_1_PERCENT,DOWN_2_PERCENT,UP,DOWN_3_PERCENT,DOWN_20_PERCENT,
                                    DOWN_10_PERCENT,UP_7_PERCENT,UP_8_PERCENT,UP_5_PERCENT,UP_6_PERCENT,DOWN_15_PERCENT,UP_9_PERCENT }
        public static enum I_20_DAYSimpleMovingAverage { PRICE_20_PERCENT_BELOW_SMA_20,PRICE_30_PERCENT_ABOVE_SMA_20,PRICE_10_PERCENT_BELOW_SMA_20,
                                                        PRICE_40_PERCENT_ABOVE_SMA_20,PRICE_50_PERCENT_ABOVE_SMA_20,SMA_20_CROSSED_SMA_200,
                                                        SMA_20_ABOVE_SMA_50,SMA_20_CROSSED_SMA_50,SMA_20_BELOW_SMA_50,SMA_20_BELOW_SMA_200,
                                                        SMA_20_CROSSED_SMA_200_ABOVE,PRICE_10_PERCENT_ABOVE_SMA_20,SMA_20_CROSSED_SMA_50_BELOW,
                                                        PRICE_20_PERCENT_ABOVE_SMA_20,SMA_20_CROSSED_SMA_200_BELOW,SMA_20_CROSSED_SMA_50_ABOVE,
                                                        PRICE_ABOVE_SMA_20,SMA_20_ABOVE_SMA_200,PRICE_BELOW_SMA_20,PRICE_CROSSED_SMA_20,
                                                        PRICE_CROSSED_SMA_20_BELOW,PRICE_30_PERCENT_BELOW_SMA_20,PRICE_CROSSED_SMA_20_ABOVE,
                                                        PRICE_50_PERCENT_BELOW_SMA_20,PRICE_40_PERCENT_BELOW_SMA_20 }
        public static enum I_50_DAYSimpleMovingAverage { PRICE_20_PERCENT_BELOW_SMA_50,PRICE_30_PERCENT_ABOVE_SMA_50,PRICE_10_PERCENT_BELOW_SMA_50,
                                                        PRICE_40_PERCENT_ABOVE_SMA_50,PRICE_50_PERCENT_ABOVE_SMA_50,SMA_50_ABOVE_SMA_20,
                                                        SMA_50_CROSSED_SMA_200,SMA_50_CROSSED_SMA_20,SMA_50_BELOW_SMA_200,SMA_50_CROSSED_SMA_200_ABOVE,
                                                        PRICE_10_PERCENT_ABOVE_SMA_50,PRICE_20_PERCENT_ABOVE_SMA_50,SMA_50_BELOW_SMA_20,
                                                        SMA_50_CROSSED_SMA_200_BELOW,PRICE_ABOVE_SMA_50,SMA_50_ABOVE_SMA_200,
                                                        PRICE_BELOW_SMA_50,PRICE_CROSSED_SMA_50,SMA_50_CROSSED_SMA_20_ABOVE,
                                                        PRICE_CROSSED_SMA_50_BELOW,PRICE_30_PERCENT_BELOW_SMA_50,PRICE_CROSSED_SMA_50_ABOVE,
                                                        PRICE_50_PERCENT_BELOW_SMA_50,PRICE_40_PERCENT_BELOW_SMA_50,SMA_50_CROSSED_SMA_20_BELOW }
        public static enum I_200_DAYSimpleMovingAverage { PRICE_20_PERCENT_BELOW_SMA_200,PRICE_30_PERCENT_ABOVE_SMA_200,PRICE_50_PERCENT_ABOVE_SMA_200,
                                                        SMA_200_ABOVE_SMA_20,SMA_200_CROSSED_SMA_20,PRICE_90_PERCENT_ABOVE_SMA_200,
                                                        PRICE_70_PERCENT_ABOVE_SMA_200,SMA_200_CROSSED_SMA_50_BELOW,PRICE_20_PERCENT_ABOVE_SMA_200,
                                                        SMA_200_BELOW_SMA_20,SMA_200_CROSSED_SMA_50_ABOVE,SMA_200_CROSSED_SMA_20_ABOVE,PRICE_60_PERCENT_BELOW_SMA_200,
                                                        PRICE_80_PERCENT_BELOW_SMA_200,PRICE_40_PERCENT_BELOW_SMA_200,SMA_200_CROSSED_SMA_20_BELOW,
                                                        PRICE_60_PERCENT_ABOVE_SMA_200,PRICE_10_PERCENT_BELOW_SMA_200,PRICE_40_PERCENT_ABOVE_SMA_200,
                                                        SMA_200_ABOVE_SMA_50,SMA_200_CROSSED_SMA_50,SMA_200_BELOW_SMA_50,PRICE_10_PERCENT_ABOVE_SMA_200,
                                                        PRICE_80_PERCENT_ABOVE_SMA_200,PRICE_ABOVE_SMA_200,PRICE_100_PERCENT_ABOVE_SMA_200,
                                                        PRICE_BELOW_SMA_200,PRICE_90_PERCENT_BELOW_SMA_200,PRICE_CROSSED_SMA_200,
                                                        PRICE_70_PERCENT_BELOW_SMA_200,PRICE_30_PERCENT_BELOW_SMA_200,PRICE_CROSSED_SMA_200_BELOW,
                                                        PRICE_CROSSED_SMA_200_ABOVE,PRICE_50_PERCENT_BELOW_SMA_200 }
        public static enum ChangeFromPreviousClose { UP_10_PERCENT,DOWN,UP_20_PERCENT,UP_2_PERCENT,UP_1_PERCENT,UP_4_PERCENT,
                                                        UP_3_PERCENT,DOWN_9_PERCENT,DOWN_8_PERCENT,DOWN_5_PERCENT,UP_15_PERCENT,
                                                        DOWN_4_PERCENT,DOWN_7_PERCENT,DOWN_6_PERCENT,DOWN_1_PERCENT,DOWN_2_PERCENT,
                                                        UP,DOWN_3_PERCENT,DOWN_20_PERCENT,DOWN_10_PERCENT,UP_7_PERCENT,UP_8_PERCENT,
                                                        UP_5_PERCENT,UP_6_PERCENT,DOWN_15_PERCENT,UP_9_PERCENT }
        public static enum ChangeFromOpen { UP_10_PERCENT,DOWN,UP_20_PERCENT,UP_2_PERCENT,UP_1_PERCENT,UP_4_PERCENT,
                                            UP_3_PERCENT,DOWN_9_PERCENT,DOWN_8_PERCENT,DOWN_5_PERCENT,UP_15_PERCENT,DOWN_4_PERCENT,
                                            DOWN_7_PERCENT,DOWN_6_PERCENT,DOWN_1_PERCENT,DOWN_2_PERCENT,UP,DOWN_3_PERCENT,
                                            DOWN_20_PERCENT,DOWN_10_PERCENT,UP_7_PERCENT,UP_8_PERCENT,UP_5_PERCENT,
                                            UP_6_PERCENT,DOWN_15_PERCENT,UP_9_PERCENT }
        public static enum I_20_DAY_HIGH_LOW { I_10_PERCENT_OR_MORE_ABOVE_LOW,I_20_PERCENT_OR_MORE_ABOVE_LOW,I_40_PERCENT_OR_MORE_BELOW_HIGH,
                                            I_0_TO_10_PERCENT_BELOW_HIGH,I_15_PERCENT_OR_MORE_ABOVE_LOW,I_10_PERCENT_OR_MORE_BELOW_HIGH,
                                            I_5_PERCENT_OR_MORE_BELOW_HIGH,I_5_PERCENT_OR_MORE_ABOVE_LOW,I_0_TO_10_PERCENT_ABOVE_LOW,
                                            I_30_PERCENT_OR_MORE_ABOVE_LOW,I_20_PERCENT_OR_MORE_BELOW_HIGH,I_50_PERCENT_OR_MORE_ABOVE_LOW,
                                            I_30_PERCENT_OR_MORE_BELOW_HIGH,I_0_TO_3_PERCENT_ABOVE_LOW,I_0_TO_5_PERCENT_ABOVE_LOW,
                                            I_0_TO_3_PERCENT_BELOW_HIGH,I_15_PERCENT_OR_MORE_BELOW_HIGH,I_0_TO_5_PERCENT_BELOW_HIGH,
                                            I_40_PERCENT_OR_MORE_ABOVE_LOW,NEW_HIGH,NEW_LOW,I_50_PERCENT_OR_MORE_BELOW_HIGH }
        public static enum I_50_DAY_HIGH_LOW { I_10_PERCENT_OR_MORE_ABOVE_LOW,I_20_PERCENT_OR_MORE_ABOVE_LOW,I_40_PERCENT_OR_MORE_BELOW_HIGH,
                                            I_0_TO_10_PERCENT_BELOW_HIGH,I_15_PERCENT_OR_MORE_ABOVE_LOW,I_10_PERCENT_OR_MORE_BELOW_HIGH,
                                            I_5_PERCENT_OR_MORE_BELOW_HIGH,I_5_PERCENT_OR_MORE_ABOVE_LOW,I_0_TO_10_PERCENT_ABOVE_LOW,
                                            I_30_PERCENT_OR_MORE_ABOVE_LOW,I_20_PERCENT_OR_MORE_BELOW_HIGH,I_50_PERCENT_OR_MORE_ABOVE_LOW,
                                            I_30_PERCENT_OR_MORE_BELOW_HIGH,I_0_TO_3_PERCENT_ABOVE_LOW,I_0_TO_5_PERCENT_ABOVE_LOW,
                                            I_0_TO_3_PERCENT_BELOW_HIGH,I_15_PERCENT_OR_MORE_BELOW_HIGH,I_0_TO_5_PERCENT_BELOW_HIGH,
                                            I_40_PERCENT_OR_MORE_ABOVE_LOW,NEW_HIGH,NEW_LOW,I_50_PERCENT_OR_MORE_BELOW_HIGH }
        public static enum I_52_Week_HIGH_LOW { I_10_PERCENT_OR_MORE_ABOVE_LOW,I_20_PERCENT_OR_MORE_ABOVE_LOW,I_40_PERCENT_OR_MORE_BELOW_HIGH,
                                            I_0_TO_10_PERCENT_BELOW_HIGH,I_15_PERCENT_OR_MORE_ABOVE_LOW,I_10_PERCENT_OR_MORE_BELOW_HIGH,
                                            I_5_PERCENT_OR_MORE_BELOW_HIGH,I_5_PERCENT_OR_MORE_ABOVE_LOW,I_0_TO_10_PERCENT_ABOVE_LOW,
                                            I_30_PERCENT_OR_MORE_ABOVE_LOW,I_20_PERCENT_OR_MORE_BELOW_HIGH,I_50_PERCENT_OR_MORE_ABOVE_LOW,
                                            I_30_PERCENT_OR_MORE_BELOW_HIGH,I_0_TO_3_PERCENT_ABOVE_LOW,I_0_TO_5_PERCENT_ABOVE_LOW,
                                            I_0_TO_3_PERCENT_BELOW_HIGH,I_15_PERCENT_OR_MORE_BELOW_HIGH,I_0_TO_5_PERCENT_BELOW_HIGH,
                                            I_40_PERCENT_OR_MORE_ABOVE_LOW,NEW_HIGH,NEW_LOW,I_50_PERCENT_OR_MORE_BELOW_HIGH }
        public static enum Pattern { CHANNEL_UP_STRONG,TRIANGLE_ASCENDING_STRONG,DOUBLE_TOP,WEDGE_STRONG,WEDGE_DOWN_STRONG,
                                        CHANNEL_DOWN,MULTIPLE_BOTTOM,DOUBLE_BOTTOM,TL_SUPPORT,WEDGE_UP_STRONG,TRIANGLE_DESCENDING_STRONG,
                                        HEAD_AND_SHOULDERS_INVERSE,CHANNEL_DOWN_STRONG,WEDGE,TL_RESISTANCE_STRONG,HORIZONTAL_S_R_STRONG,
                                        CHANNEL_UP,MULTIPLE_TOP,WEDGE_DOWN,TRIANGLE_DESCENDING,CHANNEL_STRONG,HEAD_AND_SHOULDERS,
                                        TRIANGLE_ASCENDING,HORIZONTAL_S_R,WEDGE_UP,TL_RESISTANCE,TL_SUPPORT_STRONG,CHANNEL }
        public static enum Candlestick { DRAGONFLY_DOJI,DOJI,LONG_LOWER_SHADOW,SPINNING_TOP_WHITE,INVERTED_HAMMER,SPINNING_TOP_BLACK,
                                        MARUBOZU_BLACK,GRAVESTONE_DOJI,LONG_UPPER_SHADOW,MARUBOZU_WHITE,HAMMER }
        public static enum Beta { I_0_TO_1,OVER_0,OVER_0_POINT_5,UNDER_0,OVER_2,OVER_1,OVER_4,UNDER_2,UNDER_1,UNDER_1_POINT_5,
                                    OVER_3,I_1_TO_2,OVER_1_POINT_5,UNDER_0_POINT_5,OVER_2_POINT_5,I_0_POINT_5_TO_1_POINT_5,I_1_TO_1_POINT_5,
                                    I_0_POINT_5_TO_1,I_0_TO_0_POINT_5 }
        public static enum AverageTrueRange { OVER_0_POINT_5,OVER_2,OVER_1,UNDER_2_POINT_5,UNDER_2,OVER_4,UNDER_1_POINT_5,
                                                UNDER_1,OVER_3,UNDER_4,UNDER_3,OVER_1_POINT_5,OVER_5,UNDER_0_POINT_5,OVER_2_POINT_5,
                                                UNDER_3_POINT_5,UNDER_0_POINT_75,UNDER_0_POINT_25,OVER_4_POINT_5,OVER_0_POINT_75,
                                                UNDER_5,OVER_0_POINT_25,OVER_3_POINT_5,UNDER_4_POINT_5 }
        public static enum AverageVolume { UNDER_750_K,OVER_500_K,I_100_K_TO_1M,OVER_200_K,OVER_50_K,UNDER_50_K,I_500_K_TO_1M,UNDER_1M,
                                            UNDER_500_K,OVER_750_K,I_500_K_TO_10M,OVER_100_K,OVER_2M,I_100_K_TO_500_K,OVER_400_K,
                                            OVER_1M,UNDER_100_K,OVER_300_K }
        public static enum RelativeVolume { OVER_0_POINT_5,OVER_2,OVER_1,UNDER_2,OVER_3,UNDER_1_POINT_5,UNDER_1,OVER_5,
                                            OVER_1_POINT_5,UNDER_0_POINT_5,UNDER_0_POINT_75,OVER_10,UNDER_0_POINT_25,UNDER_0_POINT_1,
                                            OVER_0_POINT_75,OVER_0_POINT_25 }
        public static enum Price { UNDER_M_20_DOLLARS,M_1_DOLLARS_TO_M_10_DOLLARS,OVER_M_80_DOLLARS,OVER_M_60_DOLLARS,
                                    UNDER_M_40_DOLLARS,M_5_DOLLARS_TO_M_20_DOLLARS,OVER_M_40_DOLLARS,OVER_M_20_DOLLARS,
                                    M_20_DOLLARS_TO_M_50_DOLLARS,M_10_DOLLARS_TO_M_20_DOLLARS,UNDER_M_7_DOLLARS,UNDER_M_5_DOLLARS,
                                    UNDER_M_10_DOLLARS,OVER_M_2_DOLLARS,OVER_M_1_DOLLARS,M_1_DOLLARS_TO_M_20_DOLLARS,OVER_M_70_DOLLARS,
                                    UNDER_M_2_DOLLARS,OVER_M_4_DOLLARS,UNDER_M_1_DOLLARS,OVER_M_3_DOLLARS,UNDER_M_4_DOLLARS,
                                    UNDER_M_3_DOLLARS,OVER_M_5_DOLLARS,OVER_M_15_DOLLARS,M_1_DOLLARS_TO_M_5_DOLLARS,OVER_M_7_DOLLARS,
                                    OVER_M_30_DOLLARS,UNDER_M_30_DOLLARS,M_5_DOLLARS_TO_M_50_DOLLARS,OVER_M_50_DOLLARS,UNDER_M_50_DOLLARS,
                                    UNDER_M_15_DOLLARS,OVER_M_90_DOLLARS,M_5_DOLLARS_TO_M_10_DOLLARS,M_50_DOLLARS_TO_M_100_DOLLARS,
                                    M_10_DOLLARS_TO_M_50_DOLLARS,OVER_M_100_DOLLARS,OVER_M_10_DOLLARS }
        
    }
    
    static final Signal.SignalSelection signalSelection = Signal.SignalSelection.values()[0]; //pointer to static class instance
    
//    static{
//        primeMap = new HashMap<String, Long>();
//        File file = new File(PRIME_NUMBER_SET_FILE_PATH);
//        File file2 = new File(CRITERIA_NAME_SET_FILE_PATH);
//        try{
//            ArrayList<String> primesAsStrings = CSVParser.convertToListOfString(file, false);
//            ArrayList<String> criteriaNames = CSVParser.convertToListOfString(file2, false);
//            
//            for(int i = 0; i < criteriaNames.size(); i++)
//            {
//                primeMap.put(criteriaNames.get(i), Long.parseLong(primesAsStrings.get(i)));
//            }
//        }
//        catch(IOException e)
//        {
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//            System.exit(0);
//        }
//    }
    static final Map<String, String> suffixMap = new HashMap<>();
    
    private static void initializeSuffixMap(){
        Class suffix = Suffix.class;

        for(Field field : suffix.getFields()){
            try{
                suffixMap.put(field.getName(), (String)field.get(field.getName()));
            }
            catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }    
    }
    
    public FinvizDomainManager(){
        
    }
    public List<Prospect> generateProspects(FinvizDomainManager.Signal.SignalSelection signal, Enum... features){
        
        
        List<Prospect> prospects = FinvizConnectionManager.getProspectListFromCriteria(signal,features);
        
        //if(prospectList.size() > 0)
        //{
            //Prospect[] prospects = convertToProspectArray(prospectList, signal, features);
        
            return prospects;
        //}
        //return new Prospect[0];
    }
    public List<Prospect> generateRandomProspects(){
        
        initializeSuffixMap();
        Random rand = new Random();
        
        Signal.SignalSelection[] signals = signalSelection.values();
        Signal.SignalSelection selectedSignal;
        int randomSignalIndex = rand.nextInt(signals.length + 1);
        if(randomSignalIndex >= signals.length)
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
                    // <editor-fold>
                    switch(currentFeatureCategory){
                        case Exchange:
                            int randExchange = rand.nextInt(Exchange.values().length);
                            featureList.add(Exchange.values()[randExchange]);
                            break;
                        case Index:
                                int randIndex = rand.nextInt(Index.values().length);
                                featureList.add(Index.values()[randIndex]);
                                break;
                        case Sector:
                                int randSector = rand.nextInt(Sector.values().length);
                                featureList.add(Sector.values()[randSector]);
                                break;
                        case Industry:
                                int randIndustry = rand.nextInt(Industry.values().length);
                                featureList.add(Industry.values()[randIndustry]);
                                break;
                        case Country:
                                int randCountry = rand.nextInt(Country.values().length);
                                featureList.add(Country.values()[randCountry]);
                                break;
                        case MarketCap:
                                int randMarketCap = rand.nextInt(MarketCap.values().length);
                                featureList.add(MarketCap.values()[randMarketCap]);
                                break;
                        case PricetoEarningsRatio:
                                int randPricetoEarningsRatio = rand.nextInt(PriceToEarningsRatio.values().length);
                                featureList.add(PriceToEarningsRatio.values()[randPricetoEarningsRatio]);
                                break;
                        case ForwardPricetoEarningsRatio:
                                int randForwardPricetoEarningsRatio = rand.nextInt(ForwardPriceToEarningsRatio.values().length);
                                featureList.add(ForwardPriceToEarningsRatio.values()[randForwardPricetoEarningsRatio]);
                                break;
                        case PricetoEarningstoGrowth:
                                int randPricetoEarningstoGrowth = rand.nextInt(PriceToEarningsToGrowth.values().length);
                                featureList.add(PriceToEarningsToGrowth.values()[randPricetoEarningstoGrowth]);
                                break;
                        case PricetoSalesRatio:
                                int randPricetoSalesRatio = rand.nextInt(PriceToSalesRatio.values().length);
                                featureList.add(PriceToSalesRatio.values()[randPricetoSalesRatio]);
                                break;
                        case PricetoBookRatio:
                                int randPricetoBookRatio = rand.nextInt(PriceToBookRatio.values().length);
                                featureList.add(PriceToBookRatio.values()[randPricetoBookRatio]);
                                break;
                        case PricetoCashRatio:
                                int randPricetoCashRatio = rand.nextInt(PriceToCashRatio.values().length);
                                featureList.add(PriceToCashRatio.values()[randPricetoCashRatio]);
                                break;
                        case PricetoFreeCashFlowttm:
                                int randPricetoFreeCashFlowttm = rand.nextInt(PriceToFreeCashFlowTtm.values().length);
                                featureList.add(PriceToFreeCashFlowTtm.values()[randPricetoFreeCashFlowttm]);
                                break;
                        case Epsgrowththisyear:
                                int randEpsgrowththisyear = rand.nextInt(EpsGrowthThisYear.values().length);
                                featureList.add(EpsGrowthThisYear.values()[randEpsgrowththisyear]);
                                break;
                        case Epsgrowthnextyear:
                                int randEpsgrowthnextyear = rand.nextInt(EpsGrowthNextYear.values().length);
                                featureList.add(EpsGrowthNextYear.values()[randEpsgrowthnextyear]);
                                break;
                        case Epsgrowthpast5years:
                                int randEpsgrowthpast5years = rand.nextInt(EpsGrowthPast5Years.values().length);
                                featureList.add(EpsGrowthPast5Years.values()[randEpsgrowthpast5years]);
                                break;
                        case Epsgrowthnext5years:
                                int randEpsgrowthnext5years = rand.nextInt(EpsGrowthNext5Years.values().length);
                                featureList.add(EpsGrowthNext5Years.values()[randEpsgrowthnext5years]);
                                break;
                        case SalesGrowthpast5years:
                                int randSalesGrowthpast5years = rand.nextInt(SalesGrowthPast5Years.values().length);
                                featureList.add(SalesGrowthPast5Years.values()[randSalesGrowthpast5years]);
                                break;
                        case Epsgrowthqtroverqtr:
                                int randEpsgrowthqtroverqtr = rand.nextInt(EpsGrowthQtrOverQtr.values().length);
                                featureList.add(EpsGrowthQtrOverQtr.values()[randEpsgrowthqtroverqtr]);
                                break;
                        case Salesgrowthqtroverqtr:
                                int randSalesgrowthqtroverqtr = rand.nextInt(SalesGrowthQtrOverQtr.values().length);
                                featureList.add(SalesGrowthQtrOverQtr.values()[randSalesgrowthqtroverqtr]);
                                break;
                        case DividendYield:
                                int randDividendYield = rand.nextInt(DividendYield.values().length);
                                featureList.add(DividendYield.values()[randDividendYield]);
                                break;
                        case ReturnonAssetsttm:
                                int randReturnonAssetsttm = rand.nextInt(ReturnOnAssetsTtm.values().length);
                                featureList.add(ReturnOnAssetsTtm.values()[randReturnonAssetsttm]);
                                break;
                        case ReturnonEquityttm:
                                int randReturnonEquityttm = rand.nextInt(ReturnOnEquityTtm.values().length);
                                featureList.add(ReturnOnEquityTtm.values()[randReturnonEquityttm]);
                                break;
                        case ReturnonInvestmentttm:
                                int randReturnonInvestmentttm = rand.nextInt(ReturnOnInvestmentTtm.values().length);
                                featureList.add(ReturnOnInvestmentTtm.values()[randReturnonInvestmentttm]);
                                break;
                        case CurrentRatiomrq:
                                int randCurrentRatiomrq = rand.nextInt(CurrentRatioMrq.values().length);
                                featureList.add(CurrentRatioMrq.values()[randCurrentRatiomrq]);
                                break;
                        case QuickRatiomrq:
                                int randQuickRatiomrq = rand.nextInt(QuickRatioMrq.values().length);
                                featureList.add(QuickRatioMrq.values()[randQuickRatiomrq]);
                                break;
                        case LongTermDebttoEquitymrq:
                                int randLongTermDebttoEquitymrq = rand.nextInt(LongTermDebtToEquityMrq.values().length);
                                featureList.add(LongTermDebtToEquityMrq.values()[randLongTermDebttoEquitymrq]);
                                break;
                        case TotalDebttoEquitymrq:
                                int randTotalDebttoEquitymrq = rand.nextInt(TotalDebtToEquityMrq.values().length);
                                featureList.add(TotalDebtToEquityMrq.values()[randTotalDebttoEquitymrq]);
                                break;
                        case GrossMarginttm:
                                int randGrossMarginttm = rand.nextInt(GrossMarginTtm.values().length);
                                featureList.add(GrossMarginTtm.values()[randGrossMarginttm]);
                                break;
                        case OperatingMarginttm:
                                int randOperatingMarginttm = rand.nextInt(OperatingMarginTtm.values().length);
                                featureList.add(OperatingMarginTtm.values()[randOperatingMarginttm]);
                                break;
                        case NetProfitMargin:
                                int randNetProfitMargin = rand.nextInt(NetProfitMargin.values().length);
                                featureList.add(NetProfitMargin.values()[randNetProfitMargin]);
                                break;
                        case PayoutRatio:
                                int randPayoutRatio = rand.nextInt(PayoutRatio.values().length);
                                featureList.add(PayoutRatio.values()[randPayoutRatio]);
                                break;
                        case InsiderOwnership:
                                int randInsiderOwnership = rand.nextInt(InsiderOwnership.values().length);
                                featureList.add(InsiderOwnership.values()[randInsiderOwnership]);
                                break;
                        case InsiderTranscations:
                                int randInsiderTranscations = rand.nextInt(InsiderTranscations.values().length);
                                featureList.add(InsiderTranscations.values()[randInsiderTranscations]);
                                break;
                        case InstitutionalOwnership:
                                int randInstitutionalOwnership = rand.nextInt(InstitutionalOwnership.values().length);
                                featureList.add(InstitutionalOwnership.values()[randInstitutionalOwnership]);
                                break;
                        case InstitutionalTransaction:
                                int randInstitutionalTransaction = rand.nextInt(InstitutionalTransaction.values().length);
                                featureList.add(InstitutionalTransaction.values()[randInstitutionalTransaction]);
                                break;
                        case ShortSelling:
                                int randShortSelling = rand.nextInt(ShortSelling.values().length);
                                featureList.add(ShortSelling.values()[randShortSelling]);
                                break;
                        case AnalystRecommendation:
                                int randAnalystRecommendation = rand.nextInt(AnalystRecommendation.values().length);
                                featureList.add(AnalystRecommendation.values()[randAnalystRecommendation]);
                                break;
                        case OptionShort:
                                int randOptionShort = rand.nextInt(OptionShort.values().length);
                                featureList.add(OptionShort.values()[randOptionShort]);
                                break;
                        case EarningsDate:
                                int randEarningsDate = rand.nextInt(EarningsDate.values().length);
                                featureList.add(EarningsDate.values()[randEarningsDate]);
                                break;
                        case Performance:
                                int randPerformance = rand.nextInt(Performance.values().length);
                                featureList.add(Performance.values()[randPerformance]);
                                break;
                        case Performance2:
                                int randPerformance2 = rand.nextInt(Performance2.values().length);
                                featureList.add(Performance2.values()[randPerformance2]);
                                break;
                        case Volatility:
                                int randVolatility = rand.nextInt(Volatility.values().length);
                                featureList.add(Volatility.values()[randVolatility]);
                                break;
                        case RelativeStrengthIndex14:
                                int randRelativeStrengthIndex14 = rand.nextInt(RelativeStrengthIndex14.values().length);
                                featureList.add(RelativeStrengthIndex14.values()[randRelativeStrengthIndex14]);
                                break;
                        case Gap:
                                int randGap = rand.nextInt(Gap.values().length);
                                featureList.add(Gap.values()[randGap]);
                                break;
                        case I_20_DAYSimpleMovingAverage:
                                int randI_20_DAYSimpleMovingAverage = rand.nextInt(I_20_DAYSimpleMovingAverage.values().length);
                                featureList.add(I_20_DAYSimpleMovingAverage.values()[randI_20_DAYSimpleMovingAverage]);
                                break;
                        case I_50_DAYSimpleMovingAverage:
                                int randI_50_DAYSimpleMovingAverage = rand.nextInt(I_50_DAYSimpleMovingAverage.values().length);
                                featureList.add(I_50_DAYSimpleMovingAverage.values()[randI_50_DAYSimpleMovingAverage]);
                                break;
                        case I_200_DAYSimpleMovingAverage:
                                int randI_200_DAYSimpleMovingAverage = rand.nextInt(I_200_DAYSimpleMovingAverage.values().length);
                                featureList.add(I_200_DAYSimpleMovingAverage.values()[randI_200_DAYSimpleMovingAverage]);
                                break;
                        case ChangefrompreviousClose:
                                int randChangefrompreviousClose = rand.nextInt(ChangeFromPreviousClose.values().length);
                                featureList.add(ChangeFromPreviousClose.values()[randChangefrompreviousClose]);
                                break;
                        case ChangefromOpen:
                                int randChangefromOpen = rand.nextInt(ChangeFromOpen.values().length);
                                featureList.add(ChangeFromOpen.values()[randChangefromOpen]);
                                break;
                        case I_20_DAY_HIGH_LOW:
                                int randI_20_DAY_HIGH_LOW = rand.nextInt(I_20_DAY_HIGH_LOW.values().length);
                                featureList.add(I_20_DAY_HIGH_LOW.values()[randI_20_DAY_HIGH_LOW]);
                                break;
                        case I_50_DAY_HIGH_LOW:
                                int randI_50_DAY_HIGH_LOW = rand.nextInt(I_50_DAY_HIGH_LOW.values().length);
                                featureList.add(I_50_DAY_HIGH_LOW.values()[randI_50_DAY_HIGH_LOW]);
                                break;
                        case I_52_Week_HIGH_LOW:
                                int randI_52_Week_HIGH_LOW = rand.nextInt(I_52_Week_HIGH_LOW.values().length);
                                featureList.add(I_52_Week_HIGH_LOW.values()[randI_52_Week_HIGH_LOW]);
                                break;
                        case Pattern:
                                int randPattern = rand.nextInt(Pattern.values().length);
                                featureList.add(Pattern.values()[randPattern]);
                                break;
                        case Candlestick:
                                int randCandlestick = rand.nextInt(Candlestick.values().length);
                                featureList.add(Candlestick.values()[randCandlestick]);
                                break;
                        case Beta:
                                int randBeta = rand.nextInt(Beta.values().length);
                                featureList.add(Beta.values()[randBeta]);
                                break;
                        case AverageTrueRange:
                                int randAverageTrueRange = rand.nextInt(AverageTrueRange.values().length);
                                featureList.add(AverageTrueRange.values()[randAverageTrueRange]);
                                break;
                        case AverageVolume:
                                int randAverageVolume = rand.nextInt(AverageVolume.values().length);
                                featureList.add(AverageVolume.values()[randAverageVolume]);
                                break;
                        case RelativeVolume:
                                int randRelativeVolume = rand.nextInt(RelativeVolume.values().length);
                                featureList.add(RelativeVolume.values()[randRelativeVolume]);
                                break;
                        case Price:
                                int randPrice = rand.nextInt(Price.values().length);
                                featureList.add(Price.values()[randPrice]);
                                break;
                    }
                    break;
                    // </editor-fold>
                }
            }
        }
        
        Enum[] features = featureList.toArray(new Enum[featureList.size()]);
        List<Prospect> prospects = generateProspects(selectedSignal, features);
        
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
                    Class category = features[i].getClass();
                    String topKey = convertNameToKeyFormat(category.toString());
                    String subKey = features[i].toString().toUpperCase();
                    String key = topKey + "_" + subKey.toUpperCase();
                    System.out.println("Key: " + key);
                    
                    String suffixValue = suffixMap.get(key);
                    System.out.println("Retrieved Value: " + suffixValue);
                    
                    ret += suffixValue + ",";
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
    
    private static String convertNameToKeyFormat(String name){
        char prev = '\0';
        String subType = name.substring(name.lastIndexOf(CLASS_SEPARATOR_TOKEN) + 1);
        StringBuilder key = new StringBuilder(subType);
        int modifications = 0;
        for(int i = 0; i < subType.length(); i++){
            char curr = subType.charAt(i);
            
            if(Character.isLowerCase(prev) && Character.isUpperCase(curr))
                key.insert(i + modifications++, "_");
            else if((Character.isAlphabetic(curr) && Character.isDigit(prev)) || (Character.isDigit(curr) && Character.isAlphabetic(prev)))
                key.insert(i + modifications++, "_");
                
            prev = curr;
        }
        //String type = fullName.substring(fullName.indexOf(CLASS_SEPARATOR_TOKEN) + 1, fullName.lastIndexOf(CLASS_SEPARATOR_TOKEN));
                    
        return (key.toString()).toUpperCase();
    }
}