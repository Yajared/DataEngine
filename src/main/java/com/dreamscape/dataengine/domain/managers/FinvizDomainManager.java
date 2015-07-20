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
        
        /* Previous
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
        */
        
        static final String EXCHANGE_NASDAQ = "exch_nasd";
        static final String EXCHANGE_AMEX = "exch_amex";
        static final String EXCHANGE_NYSE = "exch_nyse";
        static final String INDEX_SANDP500 = "idx_sp500";
        static final String INDEX_DJIA = "idx_dji";
        static final String SECTOR_FINANCIAL = "sec_financial";
        static final String SECTOR_TECHNOLOGY = "sec_technology";
        static final String SECTOR_CONGLOMERATES = "sec_conglomerates";
        static final String SECTOR_INDUSTRIAL_GOODS = "sec_industrialgoods";
        static final String SECTOR_UTILITIES = "sec_utilities";
        static final String SECTOR_BASIC_MATERIALS = "sec_basicmaterials";
        static final String SECTOR_CONSUMER_GOODS = "sec_consumergoods";
        static final String SECTOR_HEALTHCARE = "sec_healthcare";
        static final String INDUSTRY_DRUG_RELATED_PRODUCTS = "ind_drugrelatedproducts";
        static final String INDUSTRY_DIAGNOSTIC_SUBSTANCES = "ind_diagnosticsubstances";
        static final String INDUSTRY_LONG_TERM_CARE_FACILITIES = "ind_longtermcarefacilities";
        static final String INDUSTRY_DRUG_MANUFACTURERS_MAJOR = "ind_drugmanufacturersmajor";
        static final String INDUSTRY_HOME_HEALTH_CARE = "ind_homehealthcare";
        static final String INDUSTRY_STOCKS_ONLY_EX_FUNDS = "ind_stocksonly";
        static final String INDUSTRY_HOSPITALS = "ind_hospitals";
        static final String INDUSTRY_BIOTECHNOLOGY = "ind_biotechnology";
        static final String INDUSTRY_DRUGS_GENERIC = "ind_drugsgeneric";
        static final String INDUSTRY_DRUG_DELIVERY = "ind_drugdelivery";
        static final String INDUSTRY_MEDICAL_INSTRUMENTS_AND_SUPPLIES = "ind_medicalinstrumentssupplies";
        static final String INDUSTRY_SPECIALIZED_HEALTH_SERVICES = "ind_specializedhealthservices";
        static final String INDUSTRY_HEALTH_CARE_PLANS = "ind_healthcareplans";
        static final String INDUSTRY_MEDICAL_LABORATORIES_AND_RESEARCH = "ind_medicallaboratoriesresearch";
        static final String INDUSTRY_DRUG_MANUFACTURERS_OTHER = "ind_drugmanufacturersother";
        static final String INDUSTRY_MEDICAL_PRACTITIONERS = "ind_medicalpractitioners";
        static final String INDUSTRY_MEDICAL_APPLIANCES_AND_EQUIPMENT = "ind_medicalappliancesequipment";
        static final String COUNTRY_IRELAND = "geo_ireland";
        static final String COUNTRY_BERMUDA = "geo_bermuda";
        static final String COUNTRY_SPAIN = "geo_spain";
        static final String COUNTRY_COLOMBIA = "geo_colombia";
        static final String COUNTRY_CHANNEL_ISLANDS = "geo_channelislands";
        static final String COUNTRY_INDONESIA = "geo_indonesia";
        static final String COUNTRY_CHINA_AND_HONG_KONG = "geo_chinahongkong";
        static final String COUNTRY_GERMANY = "geo_germany";
        static final String COUNTRY_PORTUGAL = "geo_portugal";
        static final String COUNTRY_BAHAMAS = "geo_bahamas";
        static final String COUNTRY_NETHERLANDS_ANTILLES = "geo_netherlandsantilles";
        static final String COUNTRY_CHINA = "geo_china";
        static final String COUNTRY_SOUTH_KOREA = "geo_southkorea";
        static final String COUNTRY_CANADA = "geo_canada";
        static final String COUNTRY_HONG_KONG = "geo_hongkong";
        static final String COUNTRY_MEXICO = "geo_mexico";
        static final String COUNTRY_FINLAND = "geo_finland";
        static final String COUNTRY_BELGIUM = "geo_belgium";
        static final String COUNTRY_MALAYSIA = "geo_malaysia";
        static final String COUNTRY_UNITED_KINGDOM = "geo_unitedkingdom";
        static final String COUNTRY_ICELAND = "geo_iceland";
        static final String COUNTRY_AUSTRALIA = "geo_australia";
        static final String COUNTRY_PHILIPPINES = "geo_philippines";
        static final String COUNTRY_MARSHALL_ISLANDS = "geo_marshallislands";
        static final String COUNTRY_SOUTH_AFRICA = "geo_southafrica";
        static final String COUNTRY_EUROPE = "geo_europe";
        static final String COUNTRY_PERU = "geo_peru";
        static final String COUNTRY_PAPUA_NEW_GUINEA = "geo_papuanewguinea";
        static final String COUNTRY_FOREIGN_EX_USA = "geo_notusa";
        static final String COUNTRY_MONACO = "geo_monaco";
        static final String COUNTRY_ISRAEL = "geo_israel";
        static final String COUNTRY_USA = "geo_usa";
        static final String COUNTRY_SINGAPORE = "geo_singapore";
        static final String COUNTRY_NORWAY = "geo_norway";
        static final String COUNTRY_ARGENTINA = "geo_argentina";
        static final String COUNTRY_CHILE = "geo_chile";
        static final String COUNTRY_LATIN_AMERICA = "geo_latinamerica";
        static final String COUNTRY_JAPAN = "geo_japan";
        static final String COUNTRY_BRITISH_VIRGIN_ISLANDS = "geo_britishvirginislands";
        static final String COUNTRY_ASIA = "geo_asia";
        static final String COUNTRY_SWITZERLAND = "geo_switzerland";
        static final String COUNTRY_FRANCE = "geo_france";
        static final String COUNTRY_INDIA = "geo_india";
        static final String COUNTRY_ITALY = "geo_italy";
        static final String COUNTRY_SWEDEN = "geo_sweden";
        static final String COUNTRY_HUNGARY = "geo_hungary";
        static final String COUNTRY_KAZAKHSTAN = "geo_kazakhstan";
        static final String COUNTRY_TAIWAN = "geo_taiwan";
        static final String COUNTRY_GREECE = "geo_greece";
        static final String COUNTRY_UNITED_ARAB_EMIRATES = "geo_unitedarabemirates";
        static final String COUNTRY_CYPRUS = "geo_cyprus";
        static final String COUNTRY_NETHERLANDS = "geo_netherlands";
        static final String COUNTRY_LUXEMBOURG = "geo_luxembourg";
        static final String COUNTRY_BENELUX = "geo_benelux";
        static final String COUNTRY_PANAMA = "geo_panama";
        static final String COUNTRY_BRAZIL = "geo_brazil";
        static final String COUNTRY_RUSSIA = "geo_russia";
        static final String COUNTRY_NEW_ZEALAND = "geo_newzealand";
        static final String COUNTRY_TURKEY = "geo_turkey";
        static final String COUNTRY_DENMARK = "geo_denmark";
        static final String COUNTRY_BRIC = "geo_bric";
        static final String COUNTRY_CAYMAN_ISLANDS = "geo_caymanislands";
        static final String MARKET_CAP__SMALL = "cap_smallunder";
        static final String MARKET_CAP_PLUS_MID = "cap_midover";
        static final String MARKET_CAP__LARGE = "cap_largeunder";
        static final String MARKET_CAP_NANO = "cap_nano";
        static final String MARKET_CAP__MID = "cap_midunder";
        static final String MARKET_CAP_MICRO = "cap_micro";
        static final String MARKET_CAP_PLUS_SMALL = "cap_smallover";
        static final String MARKET_CAP_MEGA = "cap_mega";
        static final String MARKET_CAP_PLUS_MICRO = "cap_microover";
        static final String MARKET_CAP_MID = "cap_mid";
        static final String MARKET_CAP__MICRO = "cap_microunder";
        static final String MARKET_CAP_SMALL = "cap_small";
        static final String MARKET_CAP_PLUS_LARGE = "cap_largeover";
        static final String MARKET_CAP_LARGE = "cap_large";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_10 = "fa_pe_u10";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_20 = "fa_pe_u20";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_5 = "fa_pe_o5";
        static final String PRICE_TO_EARNINGS_RATIO_PROFITABLE = "fa_pe_profitable";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_15 = "fa_pe_o15";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_30 = "fa_pe_o30";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_30 = "fa_pe_u30";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_50 = "fa_pe_o50";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_25 = "fa_pe_o25";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_40 = "fa_pe_u40";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_40 = "fa_pe_o40";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_50 = "fa_pe_u50";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_15 = "fa_pe_u15";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_45 = "fa_pe_u45";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_25 = "fa_pe_u25";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_35 = "fa_pe_u35";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_10 = "fa_pe_o10";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_35 = "fa_pe_o35";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_20 = "fa_pe_o20";
        static final String PRICE_TO_EARNINGS_RATIO_OVER_45 = "fa_pe_o45";
        static final String PRICE_TO_EARNINGS_RATIO_UNDER_5 = "fa_pe_u5";
        static final String PRICE_TO_EARNINGS_RATIO_HIGH = "fa_pe_high";
        static final String PRICE_TO_EARNINGS_RATIO_LOW = "fa_pe_low";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_10 = "fa_fpe_u10";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_20 = "fa_fpe_u20";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_5 = "fa_fpe_o5";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_PROFITABLE = "fa_fpe_profitable";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_15 = "fa_fpe_o15";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_30 = "fa_fpe_o30";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_30 = "fa_fpe_u30";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_50 = "fa_fpe_o50";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_25 = "fa_fpe_o25";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_40 = "fa_fpe_u40";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_40 = "fa_fpe_o40";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_50 = "fa_fpe_u50";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_15 = "fa_fpe_u15";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_45 = "fa_fpe_u45";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_25 = "fa_fpe_u25";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_35 = "fa_fpe_u35";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_10 = "fa_fpe_o10";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_35 = "fa_fpe_o35";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_20 = "fa_fpe_o20";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_OVER_45 = "fa_fpe_o45";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_UNDER_5 = "fa_fpe_u5";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_HIGH = "fa_fpe_high";
        static final String FORWARD_PRICE_TO_EARNINGS_RATIO_LOW = "fa_fpe_low";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_OVER_2 = "fa_peg_o2";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_OVER_1 = "fa_peg_o1";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_UNDER_2 = "fa_peg_u2";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_OVER_3 = "fa_peg_o3";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_UNDER_1 = "fa_peg_u1";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_UNDER_3 = "fa_peg_u3";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_HIGH = "fa_peg_high";
        static final String PRICE_TO_EARNINGS_TO_GROWTH_LOW = "fa_peg_low";
        static final String PRICE_TO_SALES_RATIO_UNDER_10 = "fa_ps_u10";
        static final String PRICE_TO_SALES_RATIO_OVER_2 = "fa_ps_o2";
        static final String PRICE_TO_SALES_RATIO_OVER_1 = "fa_ps_o1";
        static final String PRICE_TO_SALES_RATIO_OVER_4 = "fa_ps_o4";
        static final String PRICE_TO_SALES_RATIO_UNDER_2 = "fa_ps_u2";
        static final String PRICE_TO_SALES_RATIO_OVER_3 = "fa_ps_o3";
        static final String PRICE_TO_SALES_RATIO_UNDER_1 = "fa_ps_u1";
        static final String PRICE_TO_SALES_RATIO_OVER_6 = "fa_ps_o6";
        static final String PRICE_TO_SALES_RATIO_UNDER_4 = "fa_ps_u4";
        static final String PRICE_TO_SALES_RATIO_OVER_5 = "fa_ps_o5";
        static final String PRICE_TO_SALES_RATIO_UNDER_3 = "fa_ps_u3";
        static final String PRICE_TO_SALES_RATIO_OVER_8 = "fa_ps_o8";
        static final String PRICE_TO_SALES_RATIO_OVER_7 = "fa_ps_o7";
        static final String PRICE_TO_SALES_RATIO_OVER_9 = "fa_ps_o9";
        static final String PRICE_TO_SALES_RATIO_OVER_10 = "fa_ps_o10";
        static final String PRICE_TO_SALES_RATIO_UNDER_7 = "fa_ps_u7";
        static final String PRICE_TO_SALES_RATIO_UNDER_8 = "fa_ps_u8";
        static final String PRICE_TO_SALES_RATIO_UNDER_5 = "fa_ps_u5";
        static final String PRICE_TO_SALES_RATIO_HIGH = "fa_ps_high";
        static final String PRICE_TO_SALES_RATIO_UNDER_6 = "fa_ps_u6";
        static final String PRICE_TO_SALES_RATIO_LOW = "fa_ps_low";
        static final String PRICE_TO_SALES_RATIO_UNDER_9 = "fa_ps_u9";
        static final String PRICE_TO_BOOK_RATIO_OVER_2 = "fa_pb_o2";
        static final String PRICE_TO_BOOK_RATIO_OVER_1 = "fa_pb_o1";
        static final String PRICE_TO_BOOK_RATIO_UNDER_2 = "fa_pb_u2";
        static final String PRICE_TO_BOOK_RATIO_OVER_3 = "fa_pb_o3";
        static final String PRICE_TO_BOOK_RATIO_UNDER_1 = "fa_pb_u1";
        static final String PRICE_TO_BOOK_RATIO_UNDER_3 = "fa_pb_u3";
        static final String PRICE_TO_BOOK_RATIO_HIGH = "fa_pb_high";
        static final String PRICE_TO_BOOK_RATIO_LOW = "fa_pb_low";
        static final String PRICE_TO_CASH_RATIO_OVER_40 = "fa_pc_o40";
        static final String PRICE_TO_CASH_RATIO_OVER_20 = "fa_pc_o20";
        static final String PRICE_TO_CASH_RATIO_UNDER_7 = "fa_pc_u7";
        static final String PRICE_TO_CASH_RATIO_UNDER_8 = "fa_pc_u8";
        static final String PRICE_TO_CASH_RATIO_UNDER_5 = "fa_pc_u5";
        static final String PRICE_TO_CASH_RATIO_UNDER_6 = "fa_pc_u6";
        static final String PRICE_TO_CASH_RATIO_UNDER_9 = "fa_pc_u9";
        static final String PRICE_TO_CASH_RATIO_UNDER_10 = "fa_pc_u10";
        static final String PRICE_TO_CASH_RATIO_OVER_2 = "fa_pc_o2";
        static final String PRICE_TO_CASH_RATIO_OVER_1 = "fa_pc_o1";
        static final String PRICE_TO_CASH_RATIO_UNDER_2 = "fa_pc_u2";
        static final String PRICE_TO_CASH_RATIO_OVER_4 = "fa_pc_o4";
        static final String PRICE_TO_CASH_RATIO_UNDER_1 = "fa_pc_u1";
        static final String PRICE_TO_CASH_RATIO_OVER_3 = "fa_pc_o3";
        static final String PRICE_TO_CASH_RATIO_UNDER_4 = "fa_pc_u4";
        static final String PRICE_TO_CASH_RATIO_OVER_6 = "fa_pc_o6";
        static final String PRICE_TO_CASH_RATIO_UNDER_3 = "fa_pc_u3";
        static final String PRICE_TO_CASH_RATIO_OVER_5 = "fa_pc_o5";
        static final String PRICE_TO_CASH_RATIO_OVER_8 = "fa_pc_o8";
        static final String PRICE_TO_CASH_RATIO_OVER_7 = "fa_pc_o7";
        static final String PRICE_TO_CASH_RATIO_OVER_30 = "fa_pc_o30";
        static final String PRICE_TO_CASH_RATIO_OVER_50 = "fa_pc_o50";
        static final String PRICE_TO_CASH_RATIO_OVER_9 = "fa_pc_o9";
        static final String PRICE_TO_CASH_RATIO_OVER_10 = "fa_pc_o10";
        static final String PRICE_TO_CASH_RATIO_HIGH = "fa_pc_high";
        static final String PRICE_TO_CASH_RATIO_LOW = "fa_pc_low";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_20 = "fa_pfcf_u20";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_70 = "fa_pfcf_u70";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_80 = "fa_pfcf_o80";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_60 = "fa_pfcf_o60";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_40 = "fa_pfcf_u40";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_25 = "fa_pfcf_o25";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_40 = "fa_pfcf_o40";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_45 = "fa_pfcf_u45";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_90 = "fa_pfcf_u90";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_25 = "fa_pfcf_u25";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_60 = "fa_pfcf_u60";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_20 = "fa_pfcf_o20";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_45 = "fa_pfcf_o45";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_5 = "fa_pfcf_u5";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_10 = "fa_pfcf_u10";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_70 = "fa_pfcf_o70";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_5 = "fa_pfcf_o5";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_15 = "fa_pfcf_o15";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_80 = "fa_pfcf_u80";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_30 = "fa_pfcf_o30";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_30 = "fa_pfcf_u30";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_50 = "fa_pfcf_o50";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_50 = "fa_pfcf_u50";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_15 = "fa_pfcf_u15";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_90 = "fa_pfcf_o90";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_35 = "fa_pfcf_u35";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_100 = "fa_pfcf_o100";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_10 = "fa_pfcf_o10";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_OVER_35 = "fa_pfcf_o35";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_HIGH = "fa_pfcf_high";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_LOW = "fa_pfcf_low";
        static final String PRICE_TO_FREE_CASH_FLOW_TTM_UNDER_100 = "fa_pfcf_u100";
        static final String EPS_GROWTH_THIS_YEAR_NEGATIVE = "fa_epsyoy_neg";
        static final String EPS_GROWTH_THIS_YEAR_UNDER_10_PERCENT = "fa_epsyoy_u10";
        static final String EPS_GROWTH_THIS_YEAR_UNDER_20_PERCENT = "fa_epsyoy_u20";
        static final String EPS_GROWTH_THIS_YEAR_OVER_5_PERCENT = "fa_epsyoy_o5";
        static final String EPS_GROWTH_THIS_YEAR_OVER_15_PERCENT = "fa_epsyoy_o15";
        static final String EPS_GROWTH_THIS_YEAR_OVER_30_PERCENT = "fa_epsyoy_o30";
        static final String EPS_GROWTH_THIS_YEAR_UNDER_30_PERCENT = "fa_epsyoy_u30";
        static final String EPS_GROWTH_THIS_YEAR_OVER_25_PERCENT = "fa_epsyoy_o25";
        static final String EPS_GROWTH_THIS_YEAR_UNDER_15_PERCENT = "fa_epsyoy_u15";
        static final String EPS_GROWTH_THIS_YEAR_POSITIVE = "fa_epsyoy_pos";
        static final String EPS_GROWTH_THIS_YEAR__UNDER_25_PERCENT = "fa_epsyoy_u25";
        static final String EPS_GROWTH_THIS_YEAR_POSITIVE_LOW = "fa_epsyoy_poslow";
        static final String EPS_GROWTH_THIS_YEAR_OVER_10_PERCENT = "fa_epsyoy_o10";
        static final String EPS_GROWTH_THIS_YEAR_OVER_20_PERCENT = "fa_epsyoy_o20";
        static final String EPS_GROWTH_THIS_YEAR_UNDER_5_PERCENT = "fa_epsyoy_u5";
        static final String EPS_GROWTH_THIS_YEAR_HIGH = "fa_epsyoy_high";
        static final String EPS_GROWTH_NEXT_YEAR_NEGATIVE = "fa_epsyoy1_neg";
        static final String EPS_GROWTH_NEXT_YEAR_UNDER_10_PERCENT = "fa_epsyoy1_u10";
        static final String EPS_GROWTH_NEXT_YEAR_UNDER_20_PERCENT = "fa_epsyoy1_u20";
        static final String EPS_GROWTH_NEXT_YEAR_OVER_5_PERCENT = "fa_epsyoy1_o5";
        static final String EPS_GROWTH_NEXT_YEAR_OVER_15_PERCENT = "fa_epsyoy1_o15";
        static final String EPS_GROWTH_NEXT_YEAR_OVER_30_PERCENT = "fa_epsyoy1_o30";
        static final String EPS_GROWTH_NEXT_YEAR_UNDER_30_PERCENT = "fa_epsyoy1_u30";
        static final String EPS_GROWTH_NEXT_YEAR_OVER_25_PERCENT = "fa_epsyoy1_o25";
        static final String EPS_GROWTH_NEXT_YEAR_UNDER_15_PERCENT = "fa_epsyoy1_u15";
        static final String EPS_GROWTH_NEXT_YEAR_POSITIVE = "fa_epsyoy1_pos";
        static final String EPS_GROWTH_NEXT_YEAR__UNDER_25_PERCENT = "fa_epsyoy1_u25";
        static final String EPS_GROWTH_NEXT_YEAR_POSITIVE_LOW = "fa_epsyoy1_poslow";
        static final String EPS_GROWTH_NEXT_YEAR_OVER_10_PERCENT = "fa_epsyoy1_o10";
        static final String EPS_GROWTH_NEXT_YEAR_OVER_20_PERCENT = "fa_epsyoy1_o20";
        static final String EPS_GROWTH_NEXT_YEAR_UNDER_5_PERCENT = "fa_epsyoy1_u5";
        static final String EPS_GROWTH_NEXT_YEAR_HIGH = "fa_epsyoy1_high";
        static final String EPS_GROWTH_PAST_5_YEARS_NEGATIVE = "fa_eps5years_neg";
        static final String EPS_GROWTH_PAST_5_YEARS_UNDER_10_PERCENT = "fa_eps5years_u10";
        static final String EPS_GROWTH_PAST_5_YEARS_UNDER_20_PERCENT = "fa_eps5years_u20";
        static final String EPS_GROWTH_PAST_5_YEARS_OVER_5_PERCENT = "fa_eps5years_o5";
        static final String EPS_GROWTH_PAST_5_YEARS_OVER_15_PERCENT = "fa_eps5years_o15";
        static final String EPS_GROWTH_PAST_5_YEARS_OVER_30_PERCENT = "fa_eps5years_o30";
        static final String EPS_GROWTH_PAST_5_YEARS_UNDER_30_PERCENT = "fa_eps5years_u30";
        static final String EPS_GROWTH_PAST_5_YEARS_OVER_25_PERCENT = "fa_eps5years_o25";
        static final String EPS_GROWTH_PAST_5_YEARS_UNDER_15_PERCENT = "fa_eps5years_u15";
        static final String EPS_GROWTH_PAST_5_YEARS_POSITIVE = "fa_eps5years_pos";
        static final String EPS_GROWTH_PAST_5_YEARS__UNDER_25_PERCENT = "fa_eps5years_u25";
        static final String EPS_GROWTH_PAST_5_YEARS_POSITIVE_LOW = "fa_eps5years_poslow";
        static final String EPS_GROWTH_PAST_5_YEARS_OVER_10_PERCENT = "fa_eps5years_o10";
        static final String EPS_GROWTH_PAST_5_YEARS_OVER_20_PERCENT = "fa_eps5years_o20";
        static final String EPS_GROWTH_PAST_5_YEARS_UNDER_5_PERCENT = "fa_eps5years_u5";
        static final String EPS_GROWTH_PAST_5_YEARS_HIGH = "fa_eps5years_high";
        static final String EPS_GROWTH_NEXT_5_YEARS_NEGATIVE = "fa_estltgrowth_neg";
        static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_10_PERCENT = "fa_estltgrowth_u10";
        static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_20_PERCENT = "fa_estltgrowth_u20";
        static final String EPS_GROWTH_NEXT_5_YEARS_OVER_5_PERCENT = "fa_estltgrowth_o5";
        static final String EPS_GROWTH_NEXT_5_YEARS_OVER_15_PERCENT = "fa_estltgrowth_o15";
        static final String EPS_GROWTH_NEXT_5_YEARS_OVER_30_PERCENT = "fa_estltgrowth_o30";
        static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_30_PERCENT = "fa_estltgrowth_u30";
        static final String EPS_GROWTH_NEXT_5_YEARS_OVER_25_PERCENT = "fa_estltgrowth_o25";
        static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_15_PERCENT = "fa_estltgrowth_u15";
        static final String EPS_GROWTH_NEXT_5_YEARS_POSITIVE = "fa_estltgrowth_pos";
        static final String EPS_GROWTH_NEXT_5_YEARS__UNDER_25_PERCENT = "fa_estltgrowth_u25";
        static final String EPS_GROWTH_NEXT_5_YEARS_POSITIVE_LOW = "fa_estltgrowth_poslow";
        static final String EPS_GROWTH_NEXT_5_YEARS_OVER_10_PERCENT = "fa_estltgrowth_o10";
        static final String EPS_GROWTH_NEXT_5_YEARS_OVER_20_PERCENT = "fa_estltgrowth_o20";
        static final String EPS_GROWTH_NEXT_5_YEARS_UNDER_5_PERCENT = "fa_estltgrowth_u5";
        static final String EPS_GROWTH_NEXT_5_YEARS_HIGH = "fa_estltgrowth_high";
        static final String SALES_GROWTH_PAST_5_YEARS_NEGATIVE = "fa_sales5years_neg";
        static final String SALES_GROWTH_PAST_5_YEARS_UNDER_10_PERCENT = "fa_sales5years_u10";
        static final String SALES_GROWTH_PAST_5_YEARS_UNDER_20_PERCENT = "fa_sales5years_u20";
        static final String SALES_GROWTH_PAST_5_YEARS_OVER_5_PERCENT = "fa_sales5years_o5";
        static final String SALES_GROWTH_PAST_5_YEARS_OVER_15_PERCENT = "fa_sales5years_o15";
        static final String SALES_GROWTH_PAST_5_YEARS_OVER_30_PERCENT = "fa_sales5years_o30";
        static final String SALES_GROWTH_PAST_5_YEARS_UNDER_30_PERCENT = "fa_sales5years_u30";
        static final String SALES_GROWTH_PAST_5_YEARS_OVER_25_PERCENT = "fa_sales5years_o25";
        static final String SALES_GROWTH_PAST_5_YEARS_UNDER_15_PERCENT = "fa_sales5years_u15";
        static final String SALES_GROWTH_PAST_5_YEARS_POSITIVE = "fa_sales5years_pos";
        static final String SALES_GROWTH_PAST_5_YEARS_UNDER_25_PERCENT = "fa_sales5years_u25";
        static final String SALES_GROWTH_PAST_5_YEARS_POSITIVE_LOW = "fa_sales5years_poslow";
        static final String SALES_GROWTH_PAST_5_YEARS_OVER_10_PERCENT = "fa_sales5years_o10";
        static final String SALES_GROWTH_PAST_5_YEARS_OVER_20_PERCENT = "fa_sales5years_o20";
        static final String SALES_GROWTH_PAST_5_YEARS_UNDER_5_PERCENT = "fa_sales5years_u5";
        static final String SALES_GROWTH_PAST_5_YEARS_HIGH = "fa_sales5years_high";
        static final String EPS_GROWTH_QTR_OVER_QTR_NEGATIVE = "fa_epsqoq_neg";
        static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_10_PERCENT = "fa_epsqoq_u10";
        static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_20_PERCENT = "fa_epsqoq_u20";
        static final String EPS_GROWTH_QTR_OVER_QTR_OVER_5_PERCENT = "fa_epsqoq_o5";
        static final String EPS_GROWTH_QTR_OVER_QTR_OVER_15_PERCENT = "fa_epsqoq_o15";
        static final String EPS_GROWTH_QTR_OVER_QTR_OVER_30_PERCENT = "fa_epsqoq_o30";
        static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_30_PERCENT = "fa_epsqoq_u30";
        static final String EPS_GROWTH_QTR_OVER_QTR_OVER_25_PERCENT = "fa_epsqoq_o25";
        static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_15_PERCENT = "fa_epsqoq_u15";
        static final String EPS_GROWTH_QTR_OVER_QTR_POSITIVE = "fa_epsqoq_pos";
        static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_25_PERCENT = "fa_epsqoq_u25";
        static final String EPS_GROWTH_QTR_OVER_QTR_POSITIVE_LOW = "fa_epsqoq_poslow";
        static final String EPS_GROWTH_QTR_OVER_QTR_OVER_10_PERCENT = "fa_epsqoq_o10";
        static final String EPS_GROWTH_QTR_OVER_QTR_OVER_20_PERCENT = "fa_epsqoq_o20";
        static final String EPS_GROWTH_QTR_OVER_QTR_UNDER_5_PERCENT = "fa_epsqoq_u5";
        static final String EPS_GROWTH_QTR_OVER_QTR_HIGH = "fa_epsqoq_high";
        static final String SALES_GROWTH_QTR_OVER_QTR_NEGATIVE = "fa_salesqoq_neg";
        static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_10_PERCENT = "fa_salesqoq_u10";
        static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_20_PERCENT = "fa_salesqoq_u20";
        static final String SALES_GROWTH_QTR_OVER_QTR_OVER_5_PERCENT = "fa_salesqoq_o5";
        static final String SALES_GROWTH_QTR_OVER_QTR_OVER_15_PERCENT = "fa_salesqoq_o15";
        static final String SALES_GROWTH_QTR_OVER_QTR_OVER_30_PERCENT = "fa_salesqoq_o30";
        static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_30_PERCENT = "fa_salesqoq_u30";
        static final String SALES_GROWTH_QTR_OVER_QTR_OVER_25_PERCENT = "fa_salesqoq_o25";
        static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_15_PERCENT = "fa_salesqoq_u15";
        static final String SALES_GROWTH_QTR_OVER_QTR_POSITIVE = "fa_salesqoq_pos";
        static final String SALES_GROWTH_QTR_OVER_QTR__UNDER_25_PERCENT = "fa_salesqoq_u25";
        static final String SALES_GROWTH_QTR_OVER_QTR_POSITIVE_LOW = "fa_salesqoq_poslow";
        static final String SALES_GROWTH_QTR_OVER_QTR_OVER_10_PERCENT = "fa_salesqoq_o10";
        static final String SALES_GROWTH_QTR_OVER_QTR_OVER_20_PERCENT = "fa_salesqoq_o20";
        static final String SALES_GROWTH_QTR_OVER_QTR_UNDER_5_PERCENT = "fa_salesqoq_u5";
        static final String SALES_GROWTH_QTR_OVER_QTR_HIGH = "fa_salesqoq_high";
        static final String DIVIDEND_YIELD_OVER_2_PERCENT = "fa_div_o2";
        static final String DIVIDEND_YIELD_OVER_1_PERCENT = "fa_div_o1";
        static final String DIVIDEND_YIELD_VERY_HIGH = "fa_div_veryhigh";
        static final String DIVIDEND_YIELD_OVER_4_PERCENT = "fa_div_o4";
        static final String DIVIDEND_YIELD_OVER_3_PERCENT = "fa_div_o3";
        static final String DIVIDEND_YIELD_OVER_6_PERCENT = "fa_div_o6";
        static final String DIVIDEND_YIELD_OVER_5_PERCENT = "fa_div_o5";
        static final String DIVIDEND_YIELD_OVER_8_PERCENT = "fa_div_o8";
        static final String DIVIDEND_YIELD_OVER_7_PERCENT = "fa_div_o7";
        static final String DIVIDEND_YIELD_OVER_9_PERCENT = "fa_div_o9";
        static final String DIVIDEND_YIELD_POSITIVE = "fa_div_pos";
        static final String DIVIDEND_YIELD_OVER_10_PERCENT = "fa_div_o10";
        static final String DIVIDEND_YIELD_NONE = "fa_div_none";
        static final String DIVIDEND_YIELD_HIGH = "fa_div_high";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_roa_u-10";
        static final String RETURN_ON_ASSETS_TTM_NEGATIVE = "fa_roa_neg";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_35_PERCENT = "fa_roa_u-35";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_25_PERCENT = "fa_roa_u-25";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_45_PERCENT = "fa_roa_u-45";
        static final String RETURN_ON_ASSETS_TTM_VERY_POSITIVE = "fa_roa_verypos";
        static final String RETURN_ON_ASSETS_TTM_OVER_5_PERCENT = "fa_roa_o5";
        static final String RETURN_ON_ASSETS_TTM_OVER_15_PERCENT = "fa_roa_o15";
        static final String RETURN_ON_ASSETS_TTM_OVER_30_PERCENT = "fa_roa_o30";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_40_PERCENT = "fa_roa_u-40";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_15_PERCENT = "fa_roa_u-15";
        static final String RETURN_ON_ASSETS_TTM_OVER_50_PERCENT = "fa_roa_o50";
        static final String RETURN_ON_ASSETS_TTM_OVER_25_PERCENT = "fa_roa_o25";
        static final String RETURN_ON_ASSETS_TTM_OVER_40_PERCENT = "fa_roa_o40";
        static final String RETURN_ON_ASSETS_TTM_VERY_NEGATIVE = "fa_roa_veryneg";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_roa_u-50";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_roa_u-30";
        static final String RETURN_ON_ASSETS_TTM_POSITIVE = "fa_roa_pos";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_roa_u-20";
        static final String RETURN_ON_ASSETS_TTM_UNDER_NEGATIVE_5_PERCENT = "fa_roa_u-5";
        static final String RETURN_ON_ASSETS_TTM_OVER_10_PERCENT = "fa_roa_o10";
        static final String RETURN_ON_ASSETS_TTM_OVER_35_PERCENT = "fa_roa_o35";
        static final String RETURN_ON_ASSETS_TTM_OVER_20_PERCENT = "fa_roa_o20";
        static final String RETURN_ON_ASSETS_TTM_OVER_45 = "fa_roa_o45";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_roe_u-10";
        static final String RETURN_ON_EQUITY_TTM_NEGATIVE = "fa_roe_neg";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_35_PERCENT = "fa_roe_u-35";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_25_PERCENT = "fa_roe_u-25";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_45_PERCENT = "fa_roe_u-45";
        static final String RETURN_ON_EQUITY_TTM_VERY_POSITIVE = "fa_roe_verypos";
        static final String RETURN_ON_EQUITY_TTM_OVER_5_PERCENT = "fa_roe_o5";
        static final String RETURN_ON_EQUITY_TTM_OVER_15_PERCENT = "fa_roe_o15";
        static final String RETURN_ON_EQUITY_TTM_OVER_30_PERCENT = "fa_roe_o30";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_40_PERCENT = "fa_roe_u-40";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_15_PERCENT = "fa_roe_u-15";
        static final String RETURN_ON_EQUITY_TTM_OVER_50_PERCENT = "fa_roe_o50";
        static final String RETURN_ON_EQUITY_TTM_OVER_25_PERCENT = "fa_roe_o25";
        static final String RETURN_ON_EQUITY_TTM_OVER_40_PERCENT = "fa_roe_o40";
        static final String RETURN_ON_EQUITY_TTM_VERY_NEGATIVE = "fa_roe_veryneg";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_roe_u-50";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_roe_u-30";
        static final String RETURN_ON_EQUITY_TTM_POSITIVE = "fa_roe_pos";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_roe_u-20";
        static final String RETURN_ON_EQUITY_TTM_UNDER_NEGATIVE_5_PERCENT = "fa_roe_u-5";
        static final String RETURN_ON_EQUITY_TTM_OVER_10_PERCENT = "fa_roe_o10";
        static final String RETURN_ON_EQUITY_TTM_OVER_35_PERCENT = "fa_roe_o35";
        static final String RETURN_ON_EQUITY_TTM_OVER_20_PERCENT = "fa_roe_o20";
        static final String RETURN_ON_EQUITY_TTM_OVER_45 = "fa_roe_o45";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_roi_u-10";
        static final String RETURN_ON_INVESTMENT_TTM_NEGATIVE = "fa_roi_neg";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_35_PERCENT = "fa_roi_u-35";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_25_PERCENT = "fa_roi_u-25";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_45_PERCENT = "fa_roi_u-45";
        static final String RETURN_ON_INVESTMENT_TTM_VERY_POSITIVE = "fa_roi_verypos";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_5_PERCENT = "fa_roi_o5";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_15_PERCENT = "fa_roi_o15";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_30_PERCENT = "fa_roi_o30";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_40_PERCENT = "fa_roi_u-40";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_15_PERCENT = "fa_roi_u-15";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_50_PERCENT = "fa_roi_o50";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_25_PERCENT = "fa_roi_o25";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_40_PERCENT = "fa_roi_o40";
        static final String RETURN_ON_INVESTMENT_TTM_VERY_NEGATIVE = "fa_roi_veryneg";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_roi_u-50";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_roi_u-30";
        static final String RETURN_ON_INVESTMENT_TTM_POSITIVE = "fa_roi_pos";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_roi_u-20";
        static final String RETURN_ON_INVESTMENT_TTM_UNDER_NEGATIVE_5_PERCENT = "fa_roi_u-5";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_10_PERCENT = "fa_roi_o10";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_35_PERCENT = "fa_roi_o35";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_20_PERCENT = "fa_roi_o20";
        static final String RETURN_ON_INVESTMENT_TTM_OVER_45 = "fa_roi_o45";
        static final String CURRENT_RATIO_MRQ_OVER_0_POINT_5 = "fa_curratio_o0.5";
        static final String CURRENT_RATIO_MRQ_OVER_2 = "fa_curratio_o2";
        static final String CURRENT_RATIO_MRQ_OVER_1 = "fa_curratio_o1";
        static final String CURRENT_RATIO_MRQ_OVER_10 = "fa_curratio_o10";
        static final String CURRENT_RATIO_MRQ_OVER_4 = "fa_curratio_o4";
        static final String CURRENT_RATIO_MRQ_OVER_3 = "fa_curratio_o3";
        static final String CURRENT_RATIO_MRQ_UNDER_1 = "fa_curratio_u1";
        static final String CURRENT_RATIO_MRQ_OVER_5 = "fa_curratio_o5";
        static final String CURRENT_RATIO_MRQ_OVER_1_POINT_5 = "fa_curratio_o1.5";
        static final String CURRENT_RATIO_MRQ_UNDER_0_POINT_5 = "fa_curratio_u0.5";
        static final String CURRENT_RATIO_MRQ_HIGH = "fa_curratio_high";
        static final String CURRENT_RATIO_MRQ_LOW = "fa_curratio_low";
        static final String QUICK_RATIO_MRQ_OVER_0_POINT_5 = "fa_quickratio_o0.5";
        static final String QUICK_RATIO_MRQ_OVER_2 = "fa_quickratio_o2";
        static final String QUICK_RATIO_MRQ_OVER_1 = "fa_quickratio_o1";
        static final String QUICK_RATIO_MRQ_OVER_10 = "fa_quickratio_o10";
        static final String QUICK_RATIO_MRQ_OVER_4 = "fa_quickratio_o4";
        static final String QUICK_RATIO_MRQ_OVER_3 = "fa_quickratio_o3";
        static final String QUICK_RATIO_MRQ_UNDER_1 = "fa_quickratio_u1";
        static final String QUICK_RATIO_MRQ_OVER_5 = "fa_quickratio_o5";
        static final String QUICK_RATIO_MRQ_OVER_1_POINT_5 = "fa_quickratio_o1.5";
        static final String QUICK_RATIO_MRQ_UNDER_0_POINT_5 = "fa_quickratio_u0.5";
        static final String QUICK_RATIO_MRQ_HIGH = "fa_quickratio_high";
        static final String QUICK_RATIO_MRQ_LOW = "fa_quickratio_low";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_5 = "fa_ltdebteq_o0.5";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_6 = "fa_ltdebteq_o0.6";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_7 = "fa_ltdebteq_o0.7";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_1 = "fa_ltdebteq_o1";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_8 = "fa_ltdebteq_o0.8";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_9 = "fa_ltdebteq_o0.9";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_1 = "fa_ltdebteq_u1";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_5 = "fa_ltdebteq_u0.5";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_6 = "fa_ltdebteq_u0.6";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_3 = "fa_ltdebteq_u0.3";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_4 = "fa_ltdebteq_u0.4";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_1 = "fa_ltdebteq_o0.1";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_9 = "fa_ltdebteq_u0.9";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_2 = "fa_ltdebteq_o0.2";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_3 = "fa_ltdebteq_o0.3";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_7 = "fa_ltdebteq_u0.7";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_4 = "fa_ltdebteq_o0.4";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_8 = "fa_ltdebteq_u0.8";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_2 = "fa_ltdebteq_u0.2";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_1 = "fa_ltdebteq_u0.1";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_HIGH = "fa_ltdebteq_high";
        static final String LONG_TERM_DEBT_TO_EQUITY_MRQ_LOW = "fa_ltdebteq_low";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_5 = "fa_debteq_o0.5";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_6 = "fa_debteq_o0.6";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_7 = "fa_debteq_o0.7";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_1 = "fa_debteq_o1";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_8 = "fa_debteq_o0.8";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_9 = "fa_debteq_o0.9";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_1 = "fa_debteq_u1";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_5 = "fa_debteq_u0.5";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_6 = "fa_debteq_u0.6";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_3 = "fa_debteq_u0.3";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_4 = "fa_debteq_u0.4";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_1 = "fa_debteq_o0.1";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_9 = "fa_debteq_u0.9";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_2 = "fa_debteq_o0.2";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_3 = "fa_debteq_o0.3";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_7 = "fa_debteq_u0.7";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_OVER_0_POINT_4 = "fa_debteq_o0.4";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_8 = "fa_debteq_u0.8";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_2 = "fa_debteq_u0.2";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_UNDER_0_POINT_1 = "fa_debteq_u0.1";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_HIGH = "fa_debteq_high";
        static final String TOTAL_DEBT_TO_EQUITY_MRQ_LOW = "fa_debteq_low";
        static final String GROSS_MARGIN_TTM_NEGATIVE = "fa_grossmargin_neg";
        static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_grossmargin_u-10";
        static final String GROSS_MARGIN_TTM_UNDER_20_PERCENT = "fa_grossmargin_u20";
        static final String GROSS_MARGIN_TTM_UNDER_70_PERCENT = "fa_grossmargin_u70";
        static final String GROSS_MARGIN_TTM_OVER_80_PERCENT = "fa_grossmargin_o80";
        static final String GROSS_MARGIN_TTM_OVER_60_PERCENT = "fa_grossmargin_o60";
        static final String GROSS_MARGIN_TTM_OVER_25_PERCENT = "fa_grossmargin_o25";
        static final String GROSS_MARGIN_TTM_UNDER_40_PERCENT = "fa_grossmargin_u40";
        static final String GROSS_MARGIN_TTM_OVER_40_PERCENT = "fa_grossmargin_o40";
        static final String GROSS_MARGIN_TTM_UNDER_45_PERCENT = "fa_grossmargin_u45";
        static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_grossmargin_u-50";
        static final String GROSS_MARGIN_TTM_UNDER_90_PERCENT = "fa_grossmargin_u90";
        static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_grossmargin_u-30";
        static final String GROSS_MARGIN_TTM_POSITIVE = "fa_grossmargin_pos";
        static final String GROSS_MARGIN_TTM_UNDER_25_PERCENT = "fa_grossmargin_u25";
        static final String GROSS_MARGIN_TTM_UNDER_60_PERCENT = "fa_grossmargin_u60";
        static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_70_PERCENT = "fa_grossmargin_u-70";
        static final String GROSS_MARGIN_TTM_OVER_20_PERCENT = "fa_grossmargin_o20";
        static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_100_PERCENT = "fa_grossmargin_u-100";
        static final String GROSS_MARGIN_TTM_OVER_45_PERCENT = "fa_grossmargin_o45";
        static final String GROSS_MARGIN_TTM_UNDER_5_PERCENT = "fa_grossmargin_u5";
        static final String GROSS_MARGIN_TTM_OVER_0_PERCENT = "fa_grossmargin_o0";
        static final String GROSS_MARGIN_TTM_UNDER_10_PERCENT = "fa_grossmargin_u10";
        static final String GROSS_MARGIN_TTM_UNDER_0_PERCENT = "fa_grossmargin_u0";
        static final String GROSS_MARGIN_TTM_OVER_70_PERCENT = "fa_grossmargin_o70";
        static final String GROSS_MARGIN_TTM_OVER_5_PERCENT = "fa_grossmargin_o5";
        static final String GROSS_MARGIN_TTM_OVER_15_PERCENT = "fa_grossmargin_o15";
        static final String GROSS_MARGIN_TTM_OVER_30_PERCENT = "fa_grossmargin_o30";
        static final String GROSS_MARGIN_TTM_UNDER_80_PERCENT = "fa_grossmargin_u80";
        static final String GROSS_MARGIN_TTM_UNDER_30_PERCENT = "fa_grossmargin_u30";
        static final String GROSS_MARGIN_TTM_OVER_50_PERCENT = "fa_grossmargin_o50";
        static final String GROSS_MARGIN_TTM_UNDER_50_PERCENT = "fa_grossmargin_u50";
        static final String GROSS_MARGIN_TTM_UNDER_15_PERCENT = "fa_grossmargin_u15";
        static final String GROSS_MARGIN_TTM_OVER_90_PERCENT = "fa_grossmargin_o90";
        static final String GROSS_MARGIN_TTM_UNDER_35_PERCENT = "fa_grossmargin_u35";
        static final String GROSS_MARGIN_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_grossmargin_u-20";
        static final String GROSS_MARGIN_TTM_OVER_10_PERCENT = "fa_grossmargin_o10";
        static final String GROSS_MARGIN_TTM_OVER_35_PERCENT = "fa_grossmargin_o35";
        static final String GROSS_MARGIN_TTM_HIGH = "fa_grossmargin_high";
        static final String OPERATING_MARGIN_TTM_NEGATIVE = "fa_opermargin_neg";
        static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_10_PERCENT = "fa_opermargin_u-10";
        static final String OPERATING_MARGIN_TTM_UNDER_20_PERCENT = "fa_opermargin_u20";
        static final String OPERATING_MARGIN_TTM_UNDER_70_PERCENT = "fa_opermargin_u70";
        static final String OPERATING_MARGIN_TTM_OVER_80_PERCENT = "fa_opermargin_o80";
        static final String OPERATING_MARGIN_TTM_OVER_60_PERCENT = "fa_opermargin_o60";
        static final String OPERATING_MARGIN_TTM_OVER_25_PERCENT = "fa_opermargin_o25";
        static final String OPERATING_MARGIN_TTM_UNDER_40_PERCENT = "fa_opermargin_u40";
        static final String OPERATING_MARGIN_TTM_OVER_40_PERCENT = "fa_opermargin_o40";
        static final String OPERATING_MARGIN_TTM_UNDER_45_PERCENT = "fa_opermargin_u45";
        static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_50_PERCENT = "fa_opermargin_u-50";
        static final String OPERATING_MARGIN_TTM_UNDER_90_PERCENT = "fa_opermargin_u90";
        static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_30_PERCENT = "fa_opermargin_u-30";
        static final String OPERATING_MARGIN_TTM_POSITIVE = "fa_opermargin_pos";
        static final String OPERATING_MARGIN_TTM_UNDER_25_PERCENT = "fa_opermargin_u25";
        static final String OPERATING_MARGIN_TTM_UNDER_60_PERCENT = "fa_opermargin_u60";
        static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_70_PERCENT = "fa_opermargin_u-70";
        static final String OPERATING_MARGIN_TTM_OVER_20_PERCENT = "fa_opermargin_o20";
        static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_100_PERCENT = "fa_opermargin_u-100";
        static final String OPERATING_MARGIN_TTM_OVER_45_PERCENT = "fa_opermargin_o45";
        static final String OPERATING_MARGIN_TTM_UNDER_5_PERCENT = "fa_opermargin_u5";
        static final String OPERATING_MARGIN_TTM_OVER_0_PERCENT = "fa_opermargin_o0";
        static final String OPERATING_MARGIN_TTM_UNDER_10_PERCENT = "fa_opermargin_u10";
        static final String OPERATING_MARGIN_TTM_UNDER_0_PERCENT = "fa_opermargin_u0";
        static final String OPERATING_MARGIN_TTM_OVER_70_PERCENT = "fa_opermargin_o70";
        static final String OPERATING_MARGIN_TTM_OVER_5_PERCENT = "fa_opermargin_o5";
        static final String OPERATING_MARGIN_TTM_OVER_15_PERCENT = "fa_opermargin_o15";
        static final String OPERATING_MARGIN_TTM_OVER_30_PERCENT = "fa_opermargin_o30";
        static final String OPERATING_MARGIN_TTM_UNDER_80_PERCENT = "fa_opermargin_u80";
        static final String OPERATING_MARGIN_TTM_UNDER_30_PERCENT = "fa_opermargin_u30";
        static final String OPERATING_MARGIN_TTM_OVER_50_PERCENT = "fa_opermargin_o50";
        static final String OPERATING_MARGIN_TTM_UNDER_50_PERCENT = "fa_opermargin_u50";
        static final String OPERATING_MARGIN_TTM_UNDER_15_PERCENT = "fa_opermargin_u15";
        static final String OPERATING_MARGIN_TTM_OVER_90_PERCENT = "fa_opermargin_o90";
        static final String OPERATING_MARGIN_TTM_UNDER_35_PERCENT = "fa_opermargin_u35";
        static final String OPERATING_MARGIN_TTM_UNDER_NEGATIVE_20_PERCENT = "fa_opermargin_u-20";
        static final String OPERATING_MARGIN_TTM_OVER_10_PERCENT = "fa_opermargin_o10";
        static final String OPERATING_MARGIN_TTM_OVER_35_PERCENT = "fa_opermargin_o35";
        static final String OPERATING_MARGIN_TTM_HIGH = "fa_opermargin_high";
        static final String NET_PROFIT_MARGIN_NEGATIVE = "fa_netmargin_neg";
        static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_10_PERCENT = "fa_netmargin_u-10";
        static final String NET_PROFIT_MARGIN_UNDER_20_PERCENT = "fa_netmargin_u20";
        static final String NET_PROFIT_MARGIN_UNDER_70_PERCENT = "fa_netmargin_u70";
        static final String NET_PROFIT_MARGIN_OVER_80_PERCENT = "fa_netmargin_o80";
        static final String NET_PROFIT_MARGIN_OVER_60_PERCENT = "fa_netmargin_o60";
        static final String NET_PROFIT_MARGIN_OVER_25_PERCENT = "fa_netmargin_o25";
        static final String NET_PROFIT_MARGIN_UNDER_40_PERCENT = "fa_netmargin_u40";
        static final String NET_PROFIT_MARGIN_OVER_40_PERCENT = "fa_netmargin_o40";
        static final String NET_PROFIT_MARGIN_UNDER_45_PERCENT = "fa_netmargin_u45";
        static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_50_PERCENT = "fa_netmargin_u-50";
        static final String NET_PROFIT_MARGIN_UNDER_90_PERCENT = "fa_netmargin_u90";
        static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_30_PERCENT = "fa_netmargin_u-30";
        static final String NET_PROFIT_MARGIN_POSITIVE = "fa_netmargin_pos";
        static final String NET_PROFIT_MARGIN_UNDER_25_PERCENT = "fa_netmargin_u25";
        static final String NET_PROFIT_MARGIN_UNDER_60_PERCENT = "fa_netmargin_u60";
        static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_70_PERCENT = "fa_netmargin_u-70";
        static final String NET_PROFIT_MARGIN_OVER_20_PERCENT = "fa_netmargin_o20";
        static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_100_PERCENT = "fa_netmargin_u-100";
        static final String NET_PROFIT_MARGIN_OVER_45_PERCENT = "fa_netmargin_o45";
        static final String NET_PROFIT_MARGIN_UNDER_5_PERCENT = "fa_netmargin_u5";
        static final String NET_PROFIT_MARGIN_OVER_0_PERCENT = "fa_netmargin_o0";
        static final String NET_PROFIT_MARGIN_UNDER_10_PERCENT = "fa_netmargin_u10";
        static final String NET_PROFIT_MARGIN_UNDER_0_PERCENT = "fa_netmargin_u0";
        static final String NET_PROFIT_MARGIN_OVER_70_PERCENT = "fa_netmargin_o70";
        static final String NET_PROFIT_MARGIN_OVER_5_PERCENT = "fa_netmargin_o5";
        static final String NET_PROFIT_MARGIN_OVER_15_PERCENT = "fa_netmargin_o15";
        static final String NET_PROFIT_MARGIN_OVER_30_PERCENT = "fa_netmargin_o30";
        static final String NET_PROFIT_MARGIN_UNDER_80_PERCENT = "fa_netmargin_u80";
        static final String NET_PROFIT_MARGIN_UNDER_30_PERCENT = "fa_netmargin_u30";
        static final String NET_PROFIT_MARGIN_OVER_50_PERCENT = "fa_netmargin_o50";
        static final String NET_PROFIT_MARGIN_UNDER_50_PERCENT = "fa_netmargin_u50";
        static final String NET_PROFIT_MARGIN_UNDER_15_PERCENT = "fa_netmargin_u15";
        static final String NET_PROFIT_MARGIN_OVER_90_PERCENT = "fa_netmargin_o90";
        static final String NET_PROFIT_MARGIN_UNDER_35_PERCENT = "fa_netmargin_u35";
        static final String NET_PROFIT_MARGIN_UNDER_NEGATIVE_20_PERCENT = "fa_netmargin_u-20";
        static final String NET_PROFIT_MARGIN_OVER_10_PERCENT = "fa_netmargin_o10";
        static final String NET_PROFIT_MARGIN_OVER_35_PERCENT = "fa_netmargin_o35";
        static final String NET_PROFIT_MARGIN_HIGH = "fa_netmargin_high";
        static final String PAYOUT_RATIO_OVER_0_PERCENT = "fa_payoutratio_o0";
        static final String PAYOUT_RATIO_UNDER_10_PERCENT = "fa_payoutratio_u10";
        static final String PAYOUT_RATIO_UNDER_20_PERCENT = "fa_payoutratio_u20";
        static final String PAYOUT_RATIO_OVER_70_PERCENT = "fa_payoutratio_o70";
        static final String PAYOUT_RATIO_UNDER_70_PERCENT = "fa_payoutratio_u70";
        static final String PAYOUT_RATIO_UNDER_80_PERCENT = "fa_payoutratio_u80";
        static final String PAYOUT_RATIO_OVER_80_PERCENT = "fa_payoutratio_o80";
        static final String PAYOUT_RATIO_OVER_30_PERCENT = "fa_payoutratio_o30";
        static final String PAYOUT_RATIO_UNDER_30_PERCENT = "fa_payoutratio_u30";
        static final String PAYOUT_RATIO_OVER_60_PERCENT = "fa_payoutratio_o60";
        static final String PAYOUT_RATIO_UNDER_40_PERCENT = "fa_payoutratio_u40";
        static final String PAYOUT_RATIO_OVER_50_PERCENT = "fa_payoutratio_o50";
        static final String PAYOUT_RATIO_UNDER_50_PERCENT = "fa_payoutratio_u50";
        static final String PAYOUT_RATIO_OVER_40_PERCENT = "fa_payoutratio_o40";
        static final String PAYOUT_RATIO_UNDER_90_PERCENT = "fa_payoutratio_u90";
        static final String PAYOUT_RATIO_OVER_90_PERCENT = "fa_payoutratio_o90";
        static final String PAYOUT_RATIO_POSITIVE = "fa_payoutratio_pos";
        static final String PAYOUT_RATIO_UNDER_60_PERCENT = "fa_payoutratio_u60";
        static final String PAYOUT_RATIO_OVER_10_PERCENT = "fa_payoutratio_o10";
        static final String PAYOUT_RATIO_OVER_20_PERCENT = "fa_payoutratio_o20";
        static final String PAYOUT_RATIO_NONE = "fa_payoutratio_none";
        static final String PAYOUT_RATIO_HIGH = "fa_payoutratio_high";
        static final String PAYOUT_RATIO_LOW = "fa_payoutratio_low";
        static final String PAYOUT_RATIO_UNDER_100_PERCENT = "fa_payoutratio_u100";
        static final String PAYOUT_RATIO_OVER_100_PERCENT = "fa_payoutratio_100";
        static final String INSIDER_OWNERSHIP_OVER_70_PERCENT = "sh_insiderown_o70";
        static final String INSIDER_OWNERSHIP_OVER_10_PERCENT = "sh_insiderown_o10";
        static final String INSIDER_OWNERSHIP_VERY_HIGH = "sh_insiderown_veryhigh";
        static final String INSIDER_OWNERSHIP_OVER_20_PERCENT = "sh_insiderown_o20";
        static final String INSIDER_OWNERSHIP_OVER_80_PERCENT = "sh_insiderown_o80";
        static final String INSIDER_OWNERSHIP_OVER_30_PERCENT = "sh_insiderown_o30";
        static final String INSIDER_OWNERSHIP_OVER_60_PERCENT = "sh_insiderown_o60";
        static final String INSIDER_OWNERSHIP_OVER_50_PERCENT = "sh_insiderown_o50";
        static final String INSIDER_OWNERSHIP_HIGH = "sh_insiderown_high";
        static final String INSIDER_OWNERSHIP_OVER_40_PERCENT = "sh_insiderown_o40";
        static final String INSIDER_OWNERSHIP_LOW = "sh_insiderown_low";
        static final String INSIDER_OWNERSHIP_OVER_90_PERCENT = "sh_insiderown_o90";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_35_PERCENT = "sh_insidertrans_u-35";
        static final String INSIDER_TRANSCATIONS_NEGATIVE = "sh_insidertrans_neg";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_10_PERCENT = "sh_insidertrans_u-10";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_80_PERCENT = "sh_insidertrans_o80";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_60_PERCENT = "sh_insidertrans_o60";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_90_PERCENT = "sh_insidertrans_u-90";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_15_PERCENT = "sh_insidertrans_u-15";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_25_PERCENT = "sh_insidertrans_o25";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_40_PERCENT = "sh_insidertrans_o40";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_50_PERCENT = "sh_insidertrans_u-50";
        static final String INSIDER_TRANSCATIONS_VERY_NEGATIVE = "sh_insidertrans_veryneg";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_30_PERCENT = "sh_insidertrans_u-30";
        static final String INSIDER_TRANSCATIONS_POSITIVE = "sh_insidertrans_pos";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_70_PERCENT = "sh_insidertrans_u-70";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_20_PERCENT = "sh_insidertrans_o20";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_45_PERCENT = "sh_insidertrans_o45";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_25_PERCENT = "sh_insidertrans_u-25";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_70_PERCENT = "sh_insidertrans_o70";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_45_PERCENT = "sh_insidertrans_u-45";
        static final String INSIDER_TRANSCATIONS_VERY_POSITIVE = "sh_insidertrans_verypos";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_5_PERCENT = "sh_insidertrans_o5";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_15_PERCENT = "sh_insidertrans_o15";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_40_PERCENT = "sh_insidertrans_u-40";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_30_PERCENT = "sh_insidertrans_o30";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_50_PERCENT = "sh_insidertrans_o50";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_90_PERCENT = "sh_insidertrans_o90";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_20_PERCENT = "sh_insidertrans_u-20";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_60_PERCENT = "sh_insidertrans_u-60";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_5_PERCENT = "sh_insidertrans_u-5";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_10_PERCENT = "sh_insidertrans_o10";
        static final String INSIDER_TRANSCATIONS_OVER_PLUS_35_PERCENT = "sh_insidertrans_o35";
        static final String INSIDER_TRANSCATIONS_UNDER_NEGATIVE_80_PERCENT = "sh_insidertrans_u-80";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_10_PERCENT = "sh_instown_u10";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_70_PERCENT = "sh_instown_o70";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_20_PERCENT = "sh_instown_u20";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_70_PERCENT = "sh_instown_u70";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_80_PERCENT = "sh_instown_o80";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_30_PERCENT = "sh_instown_o30";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_80_PERCENT = "sh_instown_u80";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_60_PERCENT = "sh_instown_o60";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_30_PERCENT = "sh_instown_u30";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_50_PERCENT = "sh_instown_o50";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_40_PERCENT = "sh_instown_u40";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_40_PERCENT = "sh_instown_o40";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_50_PERCENT = "sh_instown_u50";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_90_PERCENT = "sh_instown_o90";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_90_PERCENT = "sh_instown_u90";
        static final String INSTITUTIONAL_OWNERSHIP_UNDER_60_PERCENT = "sh_instown_u60";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_10_PERCENT = "sh_instown_o10";
        static final String INSTITUTIONAL_OWNERSHIP_OVER_20_PERCENT = "sh_instown_o20";
        static final String INSTITUTIONAL_OWNERSHIP_HIGH = "sh_instown_high";
        static final String INSTITUTIONAL_OWNERSHIP_LOW = "sh_instown_low";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_10_PERCENT = "sh_insttrans_u-10";
        static final String INSTITUTIONAL_TRANSACTION_NEGATIVE = "sh_insttrans_neg";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_35_PERCENT = "sh_insttrans_u-35";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_25_PERCENT = "sh_insttrans_u-25";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_45_PERCENT = "sh_insttrans_u-45";
        static final String INSTITUTIONAL_TRANSACTION_VERY_POSITIVE = "sh_insttrans_verypos";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_5_PERCENT = "sh_insttrans_o5";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_15_PERCENT = "sh_insttrans_o15";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_30_PERCENT = "sh_insttrans_o30";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_40_PERCENT = "sh_insttrans_u-40";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_15_PERCENT = "sh_insttrans_u-15";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_50_PERCENT = "sh_insttrans_o50";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_25_PERCENT = "sh_insttrans_o25";
        static final String INSTITUTIONAL_TRANSACTION_VERY_NEGATIVE = "sh_insttrans_veryneg";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_50_PERCENT = "sh_insttrans_u-50";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_30_PERCENT = "sh_insttrans_u-30";
        static final String INSTITUTIONAL_TRANSACTION_POSITIVE = "sh_insttrans_pos";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_20_PERCENT = "sh_insttrans_u-20";
        static final String INSTITUTIONAL_TRANSACTION_UNDER_NEGATIVE_5_PERCENT = "sh_insttrans_u-5";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_10_PERCENT = "sh_insttrans_o10";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_35_PERCENT = "sh_insttrans_o35";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_20_PERCENT = "sh_insttrans_o20";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_45_PERCENT = "sh_insttrans_o45";
        static final String INSTITUTIONAL_TRANSACTION_OVER_PLUS_40_PERCENT = "sh_insttrans_40";
        static final String SHORT_SELLING_UNDER_10_PERCENT = "sh_short_u10";
        static final String SHORT_SELLING_UNDER_20_PERCENT = "sh_short_u20";
        static final String SHORT_SELLING_OVER_5_PERCENT = "sh_short_o5";
        static final String SHORT_SELLING_OVER_15_PERCENT = "sh_short_o15";
        static final String SHORT_SELLING_OVER_30_PERCENT = "sh_short_o30";
        static final String SHORT_SELLING_UNDER_30_PERCENT = "sh_short_u30";
        static final String SHORT_SELLING_OVER_25_PERCENT = "sh_short_o25";
        static final String SHORT_SELLING_UNDER_15_PERCENT = "sh_short_u15";
        static final String SHORT_SELLING_UNDER_25_PERCENT = "sh_short_u25";
        static final String SHORT_SELLING_OVER_10_PERCENT = "sh_short_o10";
        static final String SHORT_SELLING_OVER_20_PERCENT = "sh_short_o20";
        static final String SHORT_SELLING_UNDER_5_PERCENT = "sh_short_u5";
        static final String SHORT_SELLING_HIGH = "sh_short_high";
        static final String SHORT_SELLING_LOW = "sh_short_low";
        static final String ANALYST_RECOMMENDATION_STRONG_SELL = "fsm_an_recom_strongsell";
        static final String ANALYST_RECOMMENDATION_STRONG_BUY = "fsm_an_recom_strongbuy";
        static final String ANALYST_RECOMMENDATION_SELL = "fsm_an_recom_sell";
        static final String ANALYST_RECOMMENDATION_BUY = "fsm_an_recom_buy";
        static final String ANALYST_RECOMMENDATION_BUY_OR_BETTER = "fsm_an_recom_buybetter";
        static final String ANALYST_RECOMMENDATION_HOLD = "fsm_an_recom_hold";
        static final String ANALYST_RECOMMENDATION_SELL_OR_WORSE = "fsm_an_recom_sellworse";
        static final String OPTION_SHORT_SHORTABLE = "sh_opt_short";
        static final String OPTION_SHORT_OPTIONABLE_AND_SHORTABLE = "sh_opt_optionshort";
        static final String OPTION_SHORT_OPTIONABLE = "sh_opt_option";
        static final String EARNINGS_DATE_PREVIOUS_5_DAYS = "fsm_earningsdate_prevdays5";
        static final String EARNINGS_DATE_TOMORROW = "fsm_earningsdate_tomorrow";
        static final String EARNINGS_DATE_THIS_MONTH = "fsm_earningsdate_thismonth";
        static final String EARNINGS_DATE_NEXT_5_DAYS = "fsm_earningsdate_nextdays5";
        static final String EARNINGS_DATE_YESTERDAY = "fsm_earningsdate_yesterday";
        static final String EARNINGS_DATE_TODAY = "fsm_earningsdate_today";
        static final String EARNINGS_DATE_TODAY_AFTER_MARKET_CLOSE = "fsm_earningsdate_todayafter";
        static final String EARNINGS_DATE_PREVIOUS_WEEK = "fsm_earningsdate_prevweek";
        static final String EARNINGS_DATE_YESTERDAY_AFTER_MARKET_CLOSE = "fsm_earningsdate_yesterdayafter";
        static final String EARNINGS_DATE_TODAY_BEFORE_MARKET_OPEN = "fsm_earningsdate_todaybefore";
        static final String EARNINGS_DATE_THIS_WEEK = "fsm_earningsdate_thisweek";
        static final String EARNINGS_DATE_TOMORROW_AFTER_MARKET_CLOSE = "fsm_earningsdate_tomorrowafter";
        static final String EARNINGS_DATE_TOMORROW_BEFORE_MARKET_OPEN = "fsm_earningsdate_tomorrowbefore";
        static final String EARNINGS_DATE_NEXT_WEEK = "fsm_earningsdate_nextweek";
        static final String EARNINGS_DATE_YESTERDAY_BEFORE_MARKET_OPEN = "fsm_earningsdate_yesterdaybefore";
        static final String PERFORMANCE_MONTH_NEGATIVE_50_PERCENT = "ta_perf_4w50u";
        static final String PERFORMANCE_HALF_PLUS_50_PERCENT = "ta_perf_26w50o";
        static final String PERFORMANCE_WEEK_UP = "ta_perf_1wup";
        static final String PERFORMANCE_HALF_NEGATIVE_50_PERCENT = "ta_perf_26w50u";
        static final String PERFORMANCE_MONTH_PLUS_10_PERCENT = "ta_perf_4w10o";
        static final String PERFORMANCE_MONTH_PLUS_50_PERCENT = "ta_perf_4w50o";
        static final String PERFORMANCE_HALF_PLUS_30_PERCENT = "ta_perf_26w30o";
        static final String PERFORMANCE_QUARTER_DOWN = "ta_perf_13wdown";
        static final String PERFORMANCE_MONTH_NEGATIVE_10_PERCENT = "ta_perf_4w10u";
        static final String PERFORMANCE_YTD_NEGATIVE_30_PERCENT = "ta_perf_ytd30u";
        static final String PERFORMANCE_QUARTER_NEGATIVE_10_PERCENT = "ta_perf_13w10u";
        static final String PERFORMANCE_YTD_PLUS_20_PERCENT = "ta_perf_ytd20o";
        static final String PERFORMANCE_YTD_NEGATIVE_20_PERCENT = "ta_perf_ytd20u";
        static final String PERFORMANCE_YTD_PLUS_30_PERCENT = "ta_perf_ytd30o";
        static final String PERFORMANCE_QUARTER_PLUS_30_PERCENT = "ta_perf_13w30o";
        static final String PERFORMANCE_YTD_PLUS_5_PERCENT = "ta_perf_ytd5o";
        static final String PERFORMANCE_QUARTER_NEGATIVE_20_PERCENT = "ta_perf_13w20u";
        static final String PERFORMANCE_HALF_NEGATIVE_30_PERCENT = "ta_perf_26w30u";
        static final String PERFORMANCE_YTD_NEGATIVE_5_PERCENT = "ta_perf_ytd5u";
        static final String PERFORMANCE_YEAR_PLUS_200_PERCENT = "ta_perf_52w200o";
        static final String PERFORMANCE_TODAY_UP = "ta_perf_dup";
        static final String PERFORMANCE_YEAR_NEGATIVE_50_PERCENT = "ta_perf_52w50u";
        static final String PERFORMANCE_QUARTER_UP = "ta_perf_13wup";
        static final String PERFORMANCE_TODAY_PLUS_5_PERCENT = "ta_perf_d5o";
        static final String PERFORMANCE_QUARTER_PLUS_50_PERCENT = "ta_perf_13w50o";
        static final String PERFORMANCE_YEAR_NEGATIVE_75_PERCENT = "ta_perf_52w75u";
        static final String PERFORMANCE_YEAR_PLUS_20_PERCENT = "ta_perf_52w20o";
        static final String PERFORMANCE_YTD_PLUS_50_PERCENT = "ta_perf_ytd50o";
        static final String PERFORMANCE_QUARTER_NEGATIVE_30_PERCENT = "ta_perf_13w30u";
        static final String PERFORMANCE_YEAR_PLUS_50_PERCENT = "ta_perf_52w50o";
        static final String PERFORMANCE_HALF_DOWN = "ta_perf_26wdown";
        static final String PERFORMANCE_QUARTER_NEGATIVE_50_PERCENT = "ta_perf_13w50u";
        static final String PERFORMANCE_TODAY_NEGATIVE_5_PERCENT = "ta_perf_d5u";
        static final String PERFORMANCE_YEAR_NEGATIVE_20_PERCENT = "ta_perf_52w20u";
        static final String PERFORMANCE_WEEK_PLUS_10_PERCENT = "ta_perf_1w10o";
        static final String PERFORMANCE_TODAY_NEGATIVE_10_PERCENT = "ta_perf_d10u";
        static final String PERFORMANCE_TODAY_DOWN = "ta_perf_ddown";
        static final String PERFORMANCE_WEEK_NEGATIVE_10_PERCENT = "ta_perf_1w10u";
        static final String PERFORMANCE_YTD_DOWN = "ta_perf_ytddown";
        static final String PERFORMANCE_HALF_NEGATIVE_75_PERCENT = "ta_perf_26w75u";
        static final String PERFORMANCE_TODAY_PLUS_10_PERCENT = "ta_perf_d10o";
        static final String PERFORMANCE_HALF_NEGATIVE_10_PERCENT = "ta_perf_26w10u";
        static final String PERFORMANCE_QUARTER_PLUS_10_PERCENT = "ta_perf_13w10o";
        static final String PERFORMANCE_YTD_PLUS_10_PERCENT = "ta_perf_ytd10o";
        static final String PERFORMANCE_YEAR_NEGATIVE_10_PERCENT = "ta_perf_52w10u";
        static final String PERFORMANCE_HALF_NEGATIVE_20_PERCENT = "ta_perf_26w20u";
        static final String PERFORMANCE_YEAR_PLUS_500_PERCENT = "ta_perf_52w500o";
        static final String PERFORMANCE_YTD_NEGATIVE_50_PERCENT = "ta_perf_ytd50u";
        static final String PERFORMANCE_HALF_PLUS_100_PERCENT = "ta_perf_26w100o";
        static final String PERFORMANCE_YTD_NEGATIVE_10_PERCENT = "ta_perf_ytd10u";
        static final String PERFORMANCE_YEAR_PLUS_10_PERCENT = "ta_perf_52w10o";
        static final String PERFORMANCE_QUARTER_PLUS_20_PERCENT = "ta_perf_13w20o";
        static final String PERFORMANCE_YEAR_UP = "ta_perf_52wup";
        static final String PERFORMANCE_WEEK_PLUS_30_PERCENT = "ta_perf_1w30o";
        static final String PERFORMANCE_MONTH_NEGATIVE_30_PERCENT = "ta_perf_4w30u";
        static final String PERFORMANCE_YTD_NEGATIVE_75_PERCENT = "ta_perf_ytd75u";
        static final String PERFORMANCE_WEEK_DOWN = "ta_perf_1wdown";
        static final String PERFORMANCE_HALF_UP = "ta_perf_26wup";
        static final String PERFORMANCE_YEAR_PLUS_300_PERCENT = "ta_perf_52w300o";
        static final String PERFORMANCE_MONTH_NEGATIVE_20_PERCENT = "ta_perf_4w20u";
        static final String PERFORMANCE_WEEK_PLUS_20_PERCENT = "ta_perf_1w20o";
        static final String PERFORMANCE_HALF_PLUS_10_PERCENT = "ta_perf_26w10o";
        static final String PERFORMANCE_YEAR_PLUS_100_PERCENT = "ta_perf_52w100o";
        static final String PERFORMANCE_MONTH_DOWN = "ta_perf_4wdown";
        static final String PERFORMANCE_HALF_PLUS_20_PERCENT = "ta_perf_26w20o";
        static final String PERFORMANCE_WEEK_NEGATIVE_20_PERCENT = "ta_perf_1w20u";
        static final String PERFORMANCE_MONTH_PLUS_20_PERCENT = "ta_perf_4w20o";
        static final String PERFORMANCE_YTD_UP = "ta_perf_ytdup";
        static final String PERFORMANCE_TODAY_PLUS_15_PERCENT = "ta_perf_d15o";
        static final String PERFORMANCE_YTD_PLUS_100_PERCENT = "ta_perf_ytd100o";
        static final String PERFORMANCE_MONTH_UP = "ta_perf_4wup";
        static final String PERFORMANCE_YEAR_NEGATIVE_30_PERCENT = "ta_perf_52w30u";
        static final String PERFORMANCE_YEAR_PLUS_30_PERCENT = "ta_perf_52w30o";
        static final String PERFORMANCE_WEEK_NEGATIVE_30_PERCENT = "ta_perf_1w30u";
        static final String PERFORMANCE_TODAY_NEGATIVE_15_PERCENT = "ta_perf_d15u";
        static final String PERFORMANCE_MONTH_PLUS_30_PERCENT = "ta_perf_4w30o";
        static final String PERFORMANCE_YEAR_DOWN = "ta_perf_52wdown";
        static final String PERFORMANCE_2_MONTH_NEGATIVE_50_PERCENT = "ta_perf2_4w50u";
        static final String PERFORMANCE_2_HALF_PLUS_50_PERCENT = "ta_perf2_26w50o";
        static final String PERFORMANCE_2_WEEK_UP = "ta_perf2_1wup";
        static final String PERFORMANCE_2_HALF_NEGATIVE_50_PERCENT = "ta_perf2_26w50u";
        static final String PERFORMANCE_2_MONTH_PLUS_10_PERCENT = "ta_perf2_4w10o";
        static final String PERFORMANCE_2_MONTH_PLUS_50_PERCENT = "ta_perf2_4w50o";
        static final String PERFORMANCE_2_HALF_PLUS_30_PERCENT = "ta_perf2_26w30o";
        static final String PERFORMANCE_2_QUARTER_DOWN = "ta_perf2_13wdown";
        static final String PERFORMANCE_2_MONTH_NEGATIVE_10_PERCENT = "ta_perf2_4w10u";
        static final String PERFORMANCE_2_YTD_NEGATIVE_30_PERCENT = "ta_perf2_ytd30u";
        static final String PERFORMANCE_2_QUARTER_NEGATIVE_10_PERCENT = "ta_perf2_13w10u";
        static final String PERFORMANCE_2_YTD_PLUS_20_PERCENT = "ta_perf2_ytd20o";
        static final String PERFORMANCE_2_YTD_NEGATIVE_20_PERCENT = "ta_perf2_ytd20u";
        static final String PERFORMANCE_2_YTD_PLUS_30_PERCENT = "ta_perf2_ytd30o";
        static final String PERFORMANCE_2_QUARTER_PLUS_30_PERCENT = "ta_perf2_13w30o";
        static final String PERFORMANCE_2_YTD_PLUS_5_PERCENT = "ta_perf2_ytd5o";
        static final String PERFORMANCE_2_QUARTER_NEGATIVE_20_PERCENT = "ta_perf2_13w20u";
        static final String PERFORMANCE_2_HALF_NEGATIVE_30_PERCENT = "ta_perf2_26w30u";
        static final String PERFORMANCE_2_YTD_NEGATIVE_5_PERCENT = "ta_perf2_ytd5u";
        static final String PERFORMANCE_2_YEAR_PLUS_200_PERCENT = "ta_perf2_52w200o";
        static final String PERFORMANCE_2_TODAY_UP = "ta_perf2_dup";
        static final String PERFORMANCE_2_YEAR_NEGATIVE_50_PERCENT = "ta_perf2_52w50u";
        static final String PERFORMANCE_2_QUARTER_UP = "ta_perf2_13wup";
        static final String PERFORMANCE_2_TODAY_PLUS_5_PERCENT = "ta_perf2_d5o";
        static final String PERFORMANCE_2_QUARTER_PLUS_50_PERCENT = "ta_perf2_13w50o";
        static final String PERFORMANCE_2_YEAR_NEGATIVE_75_PERCENT = "ta_perf2_52w75u";
        static final String PERFORMANCE_2_YEAR_PLUS_20_PERCENT = "ta_perf2_52w20o";
        static final String PERFORMANCE_2_YTD_PLUS_50_PERCENT = "ta_perf2_ytd50o";
        static final String PERFORMANCE_2_QUARTER_NEGATIVE_30_PERCENT = "ta_perf2_13w30u";
        static final String PERFORMANCE_2_YEAR_PLUS_50_PERCENT = "ta_perf2_52w50o";
        static final String PERFORMANCE_2_HALF_DOWN = "ta_perf2_26wdown";
        static final String PERFORMANCE_2_QUARTER_NEGATIVE_50_PERCENT = "ta_perf2_13w50u";
        static final String PERFORMANCE_2_TODAY_NEGATIVE_5_PERCENT = "ta_perf2_d5u";
        static final String PERFORMANCE_2_YEAR_NEGATIVE_20_PERCENT = "ta_perf2_52w20u";
        static final String PERFORMANCE_2_WEEK_PLUS_10_PERCENT = "ta_perf2_1w10o";
        static final String PERFORMANCE_2_TODAY_NEGATIVE_10_PERCENT = "ta_perf2_d10u";
        static final String PERFORMANCE_2_TODAY_DOWN = "ta_perf2_ddown";
        static final String PERFORMANCE_2_WEEK_NEGATIVE_10_PERCENT = "ta_perf2_1w10u";
        static final String PERFORMANCE_2_YTD_DOWN = "ta_perf2_ytddown";
        static final String PERFORMANCE_2_HALF_NEGATIVE_75_PERCENT = "ta_perf2_26w75u";
        static final String PERFORMANCE_2_TODAY_PLUS_10_PERCENT = "ta_perf2_d10o";
        static final String PERFORMANCE_2_HALF_NEGATIVE_10_PERCENT = "ta_perf2_26w10u";
        static final String PERFORMANCE_2_QUARTER_PLUS_10_PERCENT = "ta_perf2_13w10o";
        static final String PERFORMANCE_2_YTD_PLUS_10_PERCENT = "ta_perf2_ytd10o";
        static final String PERFORMANCE_2_YEAR_NEGATIVE_10_PERCENT = "ta_perf2_52w10u";
        static final String PERFORMANCE_2_HALF_NEGATIVE_20_PERCENT = "ta_perf2_26w20u";
        static final String PERFORMANCE_2_YEAR_PLUS_500_PERCENT = "ta_perf2_52w500o";
        static final String PERFORMANCE_2_YTD_NEGATIVE_50_PERCENT = "ta_perf2_ytd50u";
        static final String PERFORMANCE_2_HALF_PLUS_100_PERCENT = "ta_perf2_26w100o";
        static final String PERFORMANCE_2_YTD_NEGATIVE_10_PERCENT = "ta_perf2_ytd10u";
        static final String PERFORMANCE_2_YEAR_PLUS_10_PERCENT = "ta_perf2_52w10o";
        static final String PERFORMANCE_2_QUARTER_PLUS_20_PERCENT = "ta_perf2_13w20o";
        static final String PERFORMANCE_2_YEAR_UP = "ta_perf2_52wup";
        static final String PERFORMANCE_2_WEEK_PLUS_30_PERCENT = "ta_perf2_1w30o";
        static final String PERFORMANCE_2_MONTH_NEGATIVE_30_PERCENT = "ta_perf2_4w30u";
        static final String PERFORMANCE_2_YTD_NEGATIVE_75_PERCENT = "ta_perf2_ytd75u";
        static final String PERFORMANCE_2_WEEK_DOWN = "ta_perf2_1wdown";
        static final String PERFORMANCE_2_HALF_UP = "ta_perf2_26wup";
        static final String PERFORMANCE_2_YEAR_PLUS_300_PERCENT = "ta_perf2_52w300o";
        static final String PERFORMANCE_2_MONTH_NEGATIVE_20_PERCENT = "ta_perf2_4w20u";
        static final String PERFORMANCE_2_WEEK_PLUS_20_PERCENT = "ta_perf2_1w20o";
        static final String PERFORMANCE_2_HALF_PLUS_10_PERCENT = "ta_perf2_26w10o";
        static final String PERFORMANCE_2_YEAR_PLUS_100_PERCENT = "ta_perf2_52w100o";
        static final String PERFORMANCE_2_MONTH_DOWN = "ta_perf2_4wdown";
        static final String PERFORMANCE_2_HALF_PLUS_20_PERCENT = "ta_perf2_26w20o";
        static final String PERFORMANCE_2_WEEK_NEGATIVE_20_PERCENT = "ta_perf2_1w20u";
        static final String PERFORMANCE_2_MONTH_PLUS_20_PERCENT = "ta_perf2_4w20o";
        static final String PERFORMANCE_2_YTD_UP = "ta_perf2_ytdup";
        static final String PERFORMANCE_2_TODAY_PLUS_15_PERCENT = "ta_perf2_d15o";
        static final String PERFORMANCE_2_YTD_PLUS_100_PERCENT = "ta_perf2_ytd100o";
        static final String PERFORMANCE_2_MONTH_UP = "ta_perf2_4wup";
        static final String PERFORMANCE_2_YEAR_NEGATIVE_30_PERCENT = "ta_perf2_52w30u";
        static final String PERFORMANCE_2_YEAR_PLUS_30_PERCENT = "ta_perf2_52w30o";
        static final String PERFORMANCE_2_WEEK_NEGATIVE_30_PERCENT = "ta_perf2_1w30u";
        static final String PERFORMANCE_2_TODAY_NEGATIVE_15_PERCENT = "ta_perf2_d15u";
        static final String PERFORMANCE_2_MONTH_PLUS_30_PERCENT = "ta_perf2_4w30o";
        static final String PERFORMANCE_2_YEAR_DOWN = "ta_perf2_52wdown";
        static final String VOLATILITY_WEEK_OVER_9_PERCENT = "ta_volatility_wo9";
        static final String VOLATILITY_WEEK_OVER_12_PERCENT = "ta_volatility_wo12";
        static final String VOLATILITY_WEEK_OVER_8_PERCENT = "ta_volatility_wo8";
        static final String VOLATILITY_WEEK_OVER_15_PERCENT = "ta_volatility_wo15";
        static final String VOLATILITY_MONTH_OVER_5_PERCENT = "ta_volatility_mo5";
        static final String VOLATILITY_MONTH_OVER_4_PERCENT = "ta_volatility_mo4";
        static final String VOLATILITY_MONTH_OVER_3_PERCENT = "ta_volatility_mo3";
        static final String VOLATILITY_MONTH_OVER_2_PERCENT = "ta_volatility_mo2";
        static final String VOLATILITY_MONTH_OVER_9_PERCENT = "ta_volatility_mo9";
        static final String VOLATILITY_WEEK_OVER_10_PERCENT = "ta_volatility_wo10";
        static final String VOLATILITY_MONTH_OVER_8_PERCENT = "ta_volatility_mo8";
        static final String VOLATILITY_MONTH_OVER_7_PERCENT = "ta_volatility_mo7";
        static final String VOLATILITY_MONTH_OVER_6_PERCENT = "ta_volatility_mo6";
        static final String VOLATILITY_MONTH_OVER_15_PERCENT = "ta_volatility_mo15";
        static final String VOLATILITY_MONTH_OVER_12_PERCENT = "ta_volatility_mo12";
        static final String VOLATILITY_MONTH_OVER_10_PERCENT = "ta_volatility_mo10";
        static final String VOLATILITY_WEEK_OVER_3_PERCENT = "ta_volatility_wo3";
        static final String VOLATILITY_WEEK_OVER_4_PERCENT = "ta_volatility_wo4";
        static final String VOLATILITY_WEEK_OVER_5_PERCENT = "ta_volatility_wo5";
        static final String VOLATILITY_WEEK_OVER_6_PERCENT = "ta_volatility_wo6";
        static final String VOLATILITY_WEEK_OVER_7_PERCENT = "ta_volatility_wo7";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_30 = "ta_rsi_os30";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_40 = "ta_rsi_os40";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_20 = "ta_rsi_os20";
        static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERSOLD_GREATER_THAN_50 = "ta_rsi_nos50";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERSOLD_10 = "ta_rsi_os10";
        static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERSOLD_GREATER_THAN_40 = "ta_rsi_nos40";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_90 = "ta_rsi_ob90";
        static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERBOUGHT_LESS_THAN_60 = "ta_rsi_nob60";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_80 = "ta_rsi_ob80";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_70 = "ta_rsi_ob70";
        static final String RELATIVE_STRENGTH_INDEX_14_OVERBOUGHT_60 = "ta_rsi_ob60";
        static final String RELATIVE_STRENGTH_INDEX_14_NOT_OVERBOUGHT_GREATER_THAN_50 = "ta_rsi_nob50";
        static final String GAP_UP_10_PERCENT = "ta_gap_u10";
        static final String GAP_DOWN = "ta_gap_d";
        static final String GAP_UP_0_PERCENT = "ta_gap_u0";
        static final String GAP_UP_20_PERCENT = "ta_gap_u20";
        static final String GAP_UP_2_PERCENT = "ta_gap_u2";
        static final String GAP_UP_1_PERCENT = "ta_gap_u1";
        static final String GAP_UP_4_PERCENT = "ta_gap_u4";
        static final String GAP_UP_3_PERCENT = "ta_gap_u3";
        static final String GAP_DOWN_9_PERCENT = "ta_gap_d9";
        static final String GAP_DOWN_8_PERCENT = "ta_gap_d8";
        static final String GAP_DOWN_5_PERCENT = "ta_gap_d5";
        static final String GAP_UP_15_PERCENT = "ta_gap_u15";
        static final String GAP_DOWN_4_PERCENT = "ta_gap_d4";
        static final String GAP_DOWN_7_PERCENT = "ta_gap_d7";
        static final String GAP_DOWN_6_PERCENT = "ta_gap_d6";
        static final String GAP_DOWN_0_PERCENT = "ta_gap_d0";
        static final String GAP_DOWN_1_PERCENT = "ta_gap_d1";
        static final String GAP_DOWN_2_PERCENT = "ta_gap_d2";
        static final String GAP_UP = "ta_gap_u";
        static final String GAP_DOWN_3_PERCENT = "ta_gap_d3";
        static final String GAP_DOWN_20_PERCENT = "ta_gap_d20";
        static final String GAP_DOWN_10_PERCENT = "ta_gap_d10";
        static final String GAP_UP_7_PERCENT = "ta_gap_u7";
        static final String GAP_UP_8_PERCENT = "ta_gap_u8";
        static final String GAP_UP_5_PERCENT = "ta_gap_u5";
        static final String GAP_UP_6_PERCENT = "ta_gap_u6";
        static final String GAP_DOWN_15_PERCENT = "ta_gap_d15";
        static final String GAP_UP_9_PERCENT = "ta_gap_u9";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_BELOW_SMA20 = "ta_sma20_pb20";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_ABOVE_SMA20 = "ta_sma20_pa30";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_BELOW_SMA20 = "ta_sma20_pb10";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_ABOVE_SMA20 = "ta_sma20_pa40";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_ABOVE_SMA20 = "ta_sma20_pa50";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_CROSSED_SMA200 = "ta_sma20_cross200";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_ABOVE_SMA50 = "ta_sma20_sa50";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_CROSSED_SMA50 = "ta_sma20_cross50";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_BELOW_SMA50 = "ta_sma20_sb50";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_BELOW_SMA200 = "ta_sma20_sb200";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_CROSSED_SMA200_ABOVE = "ta_sma20_cross200a";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_ABOVE_SMA20 = "ta_sma20_pa10";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_CROSSED_SMA50_BELOW = "ta_sma20_cross50b";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_ABOVE_SMA20 = "ta_sma20_pa20";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_CROSSED_SMA200_BELOW = "ta_sma20_cross200b";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_CROSSED_SMA50_ABOVE = "ta_sma20_cross50a";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_ABOVE_SMA20 = "ta_sma20_pa";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_SMA20_ABOVE_SMA200 = "ta_sma20_sa200";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_BELOW_SMA20 = "ta_sma20_pb";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA20 = "ta_sma20_pc";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA20_BELOW = "ta_sma20_pcb";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_BELOW_SMA20 = "ta_sma20_pb30";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA20_ABOVE = "ta_sma20_pca";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_BELOW_SMA20 = "ta_sma20_pb50";
        static final String I20DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_BELOW_SMA20 = "ta_sma20_pb40";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_BELOW_SMA50 = "ta_sma50_pb20";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_ABOVE_SMA50 = "ta_sma50_pa30";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_BELOW_SMA50 = "ta_sma50_pb10";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_ABOVE_SMA50 = "ta_sma50_pa40";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_ABOVE_SMA50 = "ta_sma50_pa50";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_ABOVE_SMA20 = "ta_sma50_sa20";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_CROSSED_SMA200 = "ta_sma50_cross200";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_CROSSED_SMA20 = "ta_sma50_cross20";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_BELOW_SMA200 = "ta_sma50_sb200";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_CROSSED_SMA200_ABOVE = "ta_sma50_cross200a";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_ABOVE_SMA50 = "ta_sma50_pa10";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_ABOVE_SMA50 = "ta_sma50_pa20";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_BELOW_SMA20 = "ta_sma50_sb20";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_CROSSED_SMA200_BELOW = "ta_sma50_cross200b";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_ABOVE_SMA50 = "ta_sma50_pa";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_ABOVE_SMA200 = "ta_sma50_sa200";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_BELOW_SMA50 = "ta_sma50_pb";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA50 = "ta_sma50_pc";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_CROSSED_SMA20_ABOVE = "ta_sma50_cross20a";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA50_BELOW = "ta_sma50_pcb";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_BELOW_SMA50 = "ta_sma50_pb30";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA50_ABOVE = "ta_sma50_pca";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_BELOW_SMA50 = "ta_sma50_pb50";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_BELOW_SMA50 = "ta_sma50_pb40";
        static final String I50DAY_SIMPLE_MOVING_AVERAGE_SMA50_CROSSED_SMA20_BELOW = "ta_sma50_cross20b";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_BELOW_SMA200 = "ta_sma200_pb20";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_ABOVE_SMA200 = "ta_sma200_pa30";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_ABOVE_SMA200 = "ta_sma200_pa50";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_ABOVE_SMA20 = "ta_sma200_sa20";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_CROSSED_SMA20 = "ta_sma200_cross20";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_90_PERCENT_ABOVE_SMA200 = "ta_sma200_pa90";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_70_PERCENT_ABOVE_SMA200 = "ta_sma200_pa70";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_CROSSED_SMA50_BELOW = "ta_sma200_cross50b";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_20_PERCENT_ABOVE_SMA200 = "ta_sma200_pa20";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_BELOW_SMA20 = "ta_sma200_sb20";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_CROSSED_SMA50_ABOVE = "ta_sma200_cross50a";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_CROSSED_SMA20_ABOVE = "ta_sma200_cross20a";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_60_PERCENT_BELOW_SMA200 = "ta_sma200_pb60";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_80_PERCENT_BELOW_SMA200 = "ta_sma200_pb80";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_BELOW_SMA200 = "ta_sma200_pb40";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_CROSSED_SMA20_BELOW = "ta_sma200_cross20b";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_60_PERCENT_ABOVE_SMA200 = "ta_sma200_pa60";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_BELOW_SMA200 = "ta_sma200_pb10";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_40_PERCENT_ABOVE_SMA200 = "ta_sma200_pa40";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_ABOVE_SMA50 = "ta_sma200_sa50";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_CROSSED_SMA50 = "ta_sma200_cross50";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_SMA200_BELOW_SMA50 = "ta_sma200_sb50";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_10_PERCENT_ABOVE_SMA200 = "ta_sma200_pa10";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_80_PERCENT_ABOVE_SMA200 = "ta_sma200_pa80";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_ABOVE_SMA200 = "ta_sma200_pa";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_100_PERCENT_ABOVE_SMA200 = "ta_sma200_pa100";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_BELOW_SMA200 = "ta_sma200_pb";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_90_PERCENT_BELOW_SMA200 = "ta_sma200_pb90";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA200 = "ta_sma200_pc";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_70_PERCENT_BELOW_SMA200 = "ta_sma200_pb70";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_30_PERCENT_BELOW_SMA200 = "ta_sma200_pb30";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA200_BELOW = "ta_sma200_pcb";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_CROSSED_SMA200_ABOVE = "ta_sma200_pca";
        static final String I200DAY_SIMPLE_MOVING_AVERAGE_PRICE_50_PERCENT_BELOW_SMA200 = "ta_sma200_pb50";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_10_PERCENT = "ta_change_u10";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN = "ta_change_d";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_20_PERCENT = "ta_change_u20";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_2_PERCENT = "ta_change_u2";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_1_PERCENT = "ta_change_u1";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_4_PERCENT = "ta_change_u4";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_3_PERCENT = "ta_change_u3";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_9_PERCENT = "ta_change_d9";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_8_PERCENT = "ta_change_d8";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_5_PERCENT = "ta_change_d5";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_15_PERCENT = "ta_change_u15";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_4_PERCENT = "ta_change_d4";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_7_PERCENT = "ta_change_d7";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_6_PERCENT = "ta_change_d6";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_1_PERCENT = "ta_change_d1";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_2_PERCENT = "ta_change_d2";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP = "ta_change_u";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_3_PERCENT = "ta_change_d3";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_20_PERCENT = "ta_change_d20";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_10_PERCENT = "ta_change_d10";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_7_PERCENT = "ta_change_u7";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_8_PERCENT = "ta_change_u8";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_5_PERCENT = "ta_change_u5";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_6_PERCENT = "ta_change_u6";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_DOWN_15_PERCENT = "ta_change_d15";
        static final String CHANGE_FROM_PREVIOUS_CLOSE_UP_9_PERCENT = "ta_change_u9";
        static final String CHANGE_FROM_OPEN_UP_10_PERCENT = "ta_changeopen_u10";
        static final String CHANGE_FROM_OPEN_DOWN = "ta_changeopen_d";
        static final String CHANGE_FROM_OPEN_UP_20_PERCENT = "ta_changeopen_u20";
        static final String CHANGE_FROM_OPEN_UP_2_PERCENT = "ta_changeopen_u2";
        static final String CHANGE_FROM_OPEN_UP_1_PERCENT = "ta_changeopen_u1";
        static final String CHANGE_FROM_OPEN_UP_4_PERCENT = "ta_changeopen_u4";
        static final String CHANGE_FROM_OPEN_UP_3_PERCENT = "ta_changeopen_u3";
        static final String CHANGE_FROM_OPEN_DOWN_9_PERCENT = "ta_changeopen_d9";
        static final String CHANGE_FROM_OPEN_DOWN_8_PERCENT = "ta_changeopen_d8";
        static final String CHANGE_FROM_OPEN_DOWN_5_PERCENT = "ta_changeopen_d5";
        static final String CHANGE_FROM_OPEN_UP_15_PERCENT = "ta_changeopen_u15";
        static final String CHANGE_FROM_OPEN_DOWN_4_PERCENT = "ta_changeopen_d4";
        static final String CHANGE_FROM_OPEN_DOWN_7_PERCENT = "ta_changeopen_d7";
        static final String CHANGE_FROM_OPEN_DOWN_6_PERCENT = "ta_changeopen_d6";
        static final String CHANGE_FROM_OPEN_DOWN_1_PERCENT = "ta_changeopen_d1";
        static final String CHANGE_FROM_OPEN_DOWN_2_PERCENT = "ta_changeopen_d2";
        static final String CHANGE_FROM_OPEN_UP = "ta_changeopen_u";
        static final String CHANGE_FROM_OPEN_DOWN_3_PERCENT = "ta_changeopen_d3";
        static final String CHANGE_FROM_OPEN_DOWN_20_PERCENT = "ta_changeopen_d20";
        static final String CHANGE_FROM_OPEN_DOWN_10_PERCENT = "ta_changeopen_d10";
        static final String CHANGE_FROM_OPEN_UP_7_PERCENT = "ta_changeopen_u7";
        static final String CHANGE_FROM_OPEN_UP_8_PERCENT = "ta_changeopen_u8";
        static final String CHANGE_FROM_OPEN_UP_5_PERCENT = "ta_changeopen_u5";
        static final String CHANGE_FROM_OPEN_UP_6_PERCENT = "ta_changeopen_u6";
        static final String CHANGE_FROM_OPEN_DOWN_15_PERCENT = "ta_changeopen_d15";
        static final String CHANGE_FROM_OPEN_UP_9_PERCENT = "ta_changeopen_u9";
        static final String I20DAY_HIGH_LOW_I10_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a10h";
        static final String I20DAY_HIGH_LOW_I20_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a20h";
        static final String I20DAY_HIGH_LOW_I40_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b40h";
        static final String I20DAY_HIGH_LOW_I0NEGATIVE_10_PERCENT_BELOW_HIGH = "ta_highlow20d_b0to10h";
        static final String I20DAY_HIGH_LOW_I15_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a15h";
        static final String I20DAY_HIGH_LOW_I10_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b10h";
        static final String I20DAY_HIGH_LOW_I5_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b5h";
        static final String I20DAY_HIGH_LOW_I5_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a5h";
        static final String I20DAY_HIGH_LOW_I0_TO_10_PERCENT_ABOVE_LOW = "ta_highlow20d_a0to10h";
        static final String I20DAY_HIGH_LOW_I30_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a30h";
        static final String I20DAY_HIGH_LOW_I20_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b20h";
        static final String I20DAY_HIGH_LOW_I50_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a50h";
        static final String I20DAY_HIGH_LOW_I30_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b30h";
        static final String I20DAY_HIGH_LOW_I0_TO_3_PERCENT_ABOVE_LOW = "ta_highlow20d_a0to3h";
        static final String I20DAY_HIGH_LOW_I0_TO_5_PERCENT_ABOVE_LOW = "ta_highlow20d_a0to5h";
        static final String I20DAY_HIGH_LOW_I0_TO_3_PERCENT_BELOW_HIGH = "ta_highlow20d_b0to3h";
        static final String I20DAY_HIGH_LOW_I15_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b15h";
        static final String I20DAY_HIGH_LOW_I0_TO_5_PERCENT_BELOW_HIGH = "ta_highlow20d_b0to5h";
        static final String I20DAY_HIGH_LOW_I40_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow20d_a40h";
        static final String I20DAY_HIGH_LOW_NEW_HIGH = "ta_highlow20d_nh";
        static final String I20DAY_HIGH_LOW_NEW_LOW = "ta_highlow20d_nl";
        static final String I20DAY_HIGH_LOW_I50_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow20d_b50h";
        static final String I50DAY_HIGH_LOW_I10_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a10h";
        static final String I50DAY_HIGH_LOW_I20_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a20h";
        static final String I50DAY_HIGH_LOW_I40_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b40h";
        static final String I50DAY_HIGH_LOW_I0_TO_10_PERCENT_BELOW_HIGH = "ta_highlow50d_b0to10h";
        static final String I50DAY_HIGH_LOW_I15_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a15h";
        static final String I50DAY_HIGH_LOW_I10_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b10h";
        static final String I50DAY_HIGH_LOW_I5_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b5h";
        static final String I50DAY_HIGH_LOW_I5_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a5h";
        static final String I50DAY_HIGH_LOW_I0_TO_10_PERCENT_ABOVE_LOW = "ta_highlow50d_a0to10h";
        static final String I50DAY_HIGH_LOW_I30_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a30h";
        static final String I50DAY_HIGH_LOW_I20_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b20h";
        static final String I50DAY_HIGH_LOW_I50_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a50h";
        static final String I50DAY_HIGH_LOW_I30_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b30h";
        static final String I50DAY_HIGH_LOW_I0_TO_3_PERCENT_ABOVE_LOW = "ta_highlow50d_a0to3h";
        static final String I50DAY_HIGH_LOW_I0_TO_5_PERCENT_ABOVE_LOW = "ta_highlow50d_a0to5h";
        static final String I50DAY_HIGH_LOW_I0_TO_3_PERCENT_BELOW_HIGH = "ta_highlow50d_b0to3h";
        static final String I50DAY_HIGH_LOW_I15_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b15h";
        static final String I50DAY_HIGH_LOW_I0_TO_5_PERCENT_BELOW_HIGH = "ta_highlow50d_b0to5h";
        static final String I50DAY_HIGH_LOW_I40_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow50d_a40h";
        static final String I50DAY_HIGH_LOW_NEW_HIGH = "ta_highlow50d_nh";
        static final String I50DAY_HIGH_LOW_NEW_LOW = "ta_highlow50d_nl";
        static final String I50DAY_HIGH_LOW_I50_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow50d_b50h";
        static final String I52WEEK_HIGH_LOW_I10_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a10h";
        static final String I52WEEK_HIGH_LOW_I20_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a20h";
        static final String I52WEEK_HIGH_LOW_I40_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b40h";
        static final String I52WEEK_HIGH_LOW_I0_TO_10_PERCENT_BELOW_HIGH = "ta_highlow52w_b0to10h";
        static final String I52WEEK_HIGH_LOW_I15_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a15h";
        static final String I52WEEK_HIGH_LOW_I10_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b10h";
        static final String I52WEEK_HIGH_LOW_I5_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b5h";
        static final String I52WEEK_HIGH_LOW_I5_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a5h";
        static final String I52WEEK_HIGH_LOW_I0_TO_10_PERCENT_ABOVE_LOW = "ta_highlow52w_a0to10h";
        static final String I52WEEK_HIGH_LOW_I30_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a30h";
        static final String I52WEEK_HIGH_LOW_I20_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b20h";
        static final String I52WEEK_HIGH_LOW_I50_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a50h";
        static final String I52WEEK_HIGH_LOW_I30_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b30h";
        static final String I52WEEK_HIGH_LOW_I0_TO_3_PERCENT_ABOVE_LOW = "ta_highlow52w_a0to3h";
        static final String I52WEEK_HIGH_LOW_I0_TO_5_PERCENT_ABOVE_LOW = "ta_highlow52w_a0to5h";
        static final String I52WEEK_HIGH_LOW_I0_TO_3_PERCENT_BELOW_HIGH = "ta_highlow52w_b0to3h";
        static final String I52WEEK_HIGH_LOW_I15_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b15h";
        static final String I52WEEK_HIGH_LOW_I0_TO_5_PERCENT_BELOW_HIGH = "ta_highlow52w_b0to5h";
        static final String I52WEEK_HIGH_LOW_I40_PERCENT_OR_MORE_ABOVE_LOW = "ta_highlow52w_a40h";
        static final String I52WEEK_HIGH_LOW_NEW_HIGH = "ta_highlow52w_nh";
        static final String I52WEEK_HIGH_LOW_NEW_LOW = "ta_highlow52w_nl";
        static final String I52WEEK_HIGH_LOW_I50_PERCENT_OR_MORE_BELOW_HIGH = "ta_highlow52w_b50h";
        static final String PATTERN_CHANNEL_UP_STRONG = "ta_pattern_channelup2";
        static final String PATTERN_TRIANGLE_ASCENDING_STRONG = "ta_pattern_wedgeresistance2";
        static final String PATTERN_DOUBLE_TOP = "ta_pattern_doubletop";
        static final String PATTERN_WEDGE_STRONG = "ta_pattern_wedge2";
        static final String PATTERN_WEDGE_DOWN_STRONG = "ta_pattern_wedgedown2";
        static final String PATTERN_CHANNEL_DOWN = "ta_pattern_channeldown";
        static final String PATTERN_MULTIPLE_BOTTOM = "ta_pattern_multiplebottom";
        static final String PATTERN_DOUBLE_BOTTOM = "ta_pattern_doublebottom";
        static final String PATTERN_TL_SUPPORT = "ta_pattern_tlsupport";
        static final String PATTERN_WEDGE_UP_STRONG = "ta_pattern_wedgeup2";
        static final String PATTERN_TRIANGLE_DESCENDING_STRONG = "ta_pattern_wedgesupport2";
        static final String PATTERN_HEAD_AND_SHOULDERS_INVERSE = "ta_pattern_headandshouldersinv";
        static final String PATTERN_CHANNEL_DOWN_STRONG = "ta_pattern_channeldown2";
        static final String PATTERN_WEDGE = "ta_pattern_wedge";
        static final String PATTERN_TL_RESISTANCE_STRONG = "ta_pattern_tlresistance2";
        static final String PATTERN_HORIZONTAL_S_R_STRONG = "ta_pattern_horizontal2";
        static final String PATTERN_CHANNEL_UP = "ta_pattern_channelup";
        static final String PATTERN_MULTIPLE_TOP = "ta_pattern_multipletop";
        static final String PATTERN_WEDGE_DOWN = "ta_pattern_wedgedown";
        static final String PATTERN_TRIANGLE_DESCENDING = "ta_pattern_wedgesupport";
        static final String PATTERN_CHANNEL_STRONG = "ta_pattern_channel2";
        static final String PATTERN_HEAD_AND_SHOULDERS = "ta_pattern_headandshoulders";
        static final String PATTERN_TRIANGLE_ASCENDING = "ta_pattern_wedgeresistance";
        static final String PATTERN_HORIZONTAL_S_R = "ta_pattern_horizontal";
        static final String PATTERN_WEDGE_UP = "ta_pattern_wedgeup";
        static final String PATTERN_TL_RESISTANCE = "ta_pattern_tlresistance";
        static final String PATTERN_TL_SUPPORT_STRONG = "ta_pattern_tlsupport2";
        static final String PATTERN_CHANNEL = "ta_pattern_channel";
        static final String CANDLESTICK_DRAGONFLY_DOJI = "ta_candlestick_dd";
        static final String CANDLESTICK_DOJI = "ta_candlestick_d";
        static final String CANDLESTICK_LONG_LOWER_SHADOW = "ta_candlestick_lls";
        static final String CANDLESTICK_SPINNING_TOP_WHITE = "ta_candlestick_stw";
        static final String CANDLESTICK_INVERTED_HAMMER = "ta_candlestick_ih";
        static final String CANDLESTICK_SPINNING_TOP_BLACK = "ta_candlestick_stb";
        static final String CANDLESTICK_MARUBOZU_BLACK = "ta_candlestick_mb";
        static final String CANDLESTICK_GRAVESTONE_DOJI = "ta_candlestick_gd";
        static final String CANDLESTICK_LONG_UPPER_SHADOW = "ta_candlestick_lus";
        static final String CANDLESTICK_MARUBOZU_WHITE = "ta_candlestick_mw";
        static final String CANDLESTICK_HAMMER = "ta_candlestick_h";
        static final String BETA_I0_TO_1 = "ta_beta_0to1";
        static final String BETA_OVER_0 = "ta_beta_o0";
        static final String BETA_OVER_0_POINT_5 = "ta_beta_o0.5";
        static final String BETA_UNDER_0 = "ta_beta_u0";
        static final String BETA_OVER_2 = "ta_beta_o2";
        static final String BETA_OVER_1 = "ta_beta_o1";
        static final String BETA_OVER_4 = "ta_beta_o4";
        static final String BETA_UNDER_2 = "ta_beta_u2";
        static final String BETA_UNDER_1 = "ta_beta_u1";
        static final String BETA_UNDER_1_POINT_5 = "ta_beta_u1.5";
        static final String BETA_OVER_3 = "ta_beta_o3";
        static final String BETA_I1_TO_2 = "ta_beta_1to2";
        static final String BETA_OVER_1_POINT_5 = "ta_beta_o1.5";
        static final String BETA_UNDER_0_POINT_5 = "ta_beta_u0.5";
        static final String BETA_OVER_2_POINT_5 = "ta_beta_o2.5";
        static final String BETA_I0_POINT_5_TO_1_POINT_5 = "ta_beta_0.5to1.5";
        static final String BETA_I1_TO_1_POINT_5 = "ta_beta_1to1.5";
        static final String BETA_I0_POINT_5_TO_1 = "ta_beta_0.5to1";
        static final String BETA_I0_TO_0_POINT_5 = "ta_beta_0to0.5";
        static final String AVERAGE_TRUE_RANGE_OVER_0_POINT_5 = "ta_averagetruerange_o0.5";
        static final String AVERAGE_TRUE_RANGE_OVER_2 = "ta_averagetruerange_o2";
        static final String AVERAGE_TRUE_RANGE_OVER_1 = "ta_averagetruerange_o1";
        static final String AVERAGE_TRUE_RANGE_UNDER_2_POINT_5 = "ta_averagetruerange_u2.5";
        static final String AVERAGE_TRUE_RANGE_UNDER_2 = "ta_averagetruerange_u2";
        static final String AVERAGE_TRUE_RANGE_OVER_4 = "ta_averagetruerange_o4";
        static final String AVERAGE_TRUE_RANGE_UNDER_1_POINT_5 = "ta_averagetruerange_u1.5";
        static final String AVERAGE_TRUE_RANGE_UNDER_1 = "ta_averagetruerange_u1";
        static final String AVERAGE_TRUE_RANGE_OVER_3 = "ta_averagetruerange_o3";
        static final String AVERAGE_TRUE_RANGE_UNDER_4 = "ta_averagetruerange_u4";
        static final String AVERAGE_TRUE_RANGE_UNDER_3 = "ta_averagetruerange_u3";
        static final String AVERAGE_TRUE_RANGE_OVER_1_POINT_5 = "ta_averagetruerange_o1.5";
        static final String AVERAGE_TRUE_RANGE_OVER_5 = "ta_averagetruerange_o5";
        static final String AVERAGE_TRUE_RANGE_UNDER_0_POINT_5 = "ta_averagetruerange_u0.5";
        static final String AVERAGE_TRUE_RANGE_OVER_2_POINT_5 = "ta_averagetruerange_o2.5";
        static final String AVERAGE_TRUE_RANGE_UNDER_3_POINT_5 = "ta_averagetruerange_u3.5";
        static final String AVERAGE_TRUE_RANGE_UNDER_0_POINT_75 = "ta_averagetruerange_u0.75";
        static final String AVERAGE_TRUE_RANGE_UNDER_0_POINT_25 = "ta_averagetruerange_u0.25";
        static final String AVERAGE_TRUE_RANGE_OVER_4_POINT_5 = "ta_averagetruerange_o4.5";
        static final String AVERAGE_TRUE_RANGE_OVER_0_POINT_75 = "ta_averagetruerange_o0.75";
        static final String AVERAGE_TRUE_RANGE_UNDER_5 = "ta_averagetruerange_u5";
        static final String AVERAGE_TRUE_RANGE_OVER_0_POINT_25 = "ta_averagetruerange_o0.25";
        static final String AVERAGE_TRUE_RANGE_OVER_3_POINT_5 = "ta_averagetruerange_o3.5";
        static final String AVERAGE_TRUE_RANGE_UNDER_4_POINT_5 = "ta_averagetruerange_u4.5";
        static final String AVERAGE_VOLUME_UNDER_750K = "sh_avgvol_u750";
        static final String AVERAGE_VOLUME_OVER_500K = "sh_avgvol_o500";
        static final String AVERAGE_VOLUME_I100K_TO_1M = "sh_avgvol_100to1000";
        static final String AVERAGE_VOLUME_OVER_200K = "sh_avgvol_o200";
        static final String AVERAGE_VOLUME_OVER_50K = "sh_avgvol_o50";
        static final String AVERAGE_VOLUME_UNDER_50K = "sh_avgvol_u50";
        static final String AVERAGE_VOLUME_I500K_TO_1M = "sh_avgvol_500to1000";
        static final String AVERAGE_VOLUME_UNDER_1M = "sh_avgvol_u1000";
        static final String AVERAGE_VOLUME_UNDER_500K = "sh_avgvol_u500";
        static final String AVERAGE_VOLUME_OVER_750K = "sh_avgvol_o750";
        static final String AVERAGE_VOLUME_I500K_TO_10M = "sh_avgvol_500to10000";
        static final String AVERAGE_VOLUME_OVER_100K = "sh_avgvol_o100";
        static final String AVERAGE_VOLUME_OVER_2M = "sh_avgvol_o2000";
        static final String AVERAGE_VOLUME_I100K_TO_500K = "sh_avgvol_100to500";
        static final String AVERAGE_VOLUME_OVER_400K = "sh_avgvol_o400";
        static final String AVERAGE_VOLUME_OVER_1M = "sh_avgvol_o1000";
        static final String AVERAGE_VOLUME_UNDER_100K = "sh_avgvol_u100";
        static final String AVERAGE_VOLUME_OVER_300K = "sh_avgvol_o300";
        static final String RELATIVE_VOLUME_OVER_0_POINT_5 = "sh_relvol_o0.5";
        static final String RELATIVE_VOLUME_OVER_2 = "sh_relvol_o2";
        static final String RELATIVE_VOLUME_OVER_1 = "sh_relvol_o1";
        static final String RELATIVE_VOLUME_UNDER_2 = "sh_relvol_u2";
        static final String RELATIVE_VOLUME_OVER_3 = "sh_relvol_o3";
        static final String RELATIVE_VOLUME_UNDER_1_POINT_5 = "sh_relvol_u1.5";
        static final String RELATIVE_VOLUME_UNDER_1 = "sh_relvol_u1";
        static final String RELATIVE_VOLUME_OVER_5 = "sh_relvol_o5";
        static final String RELATIVE_VOLUME_OVER_1_POINT_5 = "sh_relvol_o1.5";
        static final String RELATIVE_VOLUME_UNDER_0_POINT_5 = "sh_relvol_u0.5";
        static final String RELATIVE_VOLUME_UNDER_0_POINT_75 = "sh_relvol_u0.75";
        static final String RELATIVE_VOLUME_OVER_10 = "sh_relvol_o10";
        static final String RELATIVE_VOLUME_UNDER_0_POINT_25 = "sh_relvol_u0.25";
        static final String RELATIVE_VOLUME_UNDER_0_POINT_1 = "sh_relvol_u0.1";
        static final String RELATIVE_VOLUME_OVER_0_POINT_75 = "sh_relvol_o0.75";
        static final String RELATIVE_VOLUME_OVER_0_POINT_25 = "sh_relvol_o0.25";
        static final String PRICE_UNDER_20_DOLLARS = "sh_price_u20";
        static final String PRICE_1_TO_10_DOLLARS = "sh_price_1to10";
        static final String PRICE_OVER_80_DOLLARS = "sh_price_o80";
        static final String PRICE_OVER_60_DOLLARS = "sh_price_o60";
        static final String PRICE_UNDER_40_DOLLARS = "sh_price_u40";
        static final String PRICE_5_TO_20_DOLLARS = "sh_price_5to20";
        static final String PRICE_OVER_40_DOLLARS = "sh_price_o40";
        static final String PRICE_OVER_20_DOLLARS = "sh_price_o20";
        static final String PRICE_20_TO_50_DOLLARS = "sh_price_20to50";
        static final String PRICE_10_TO_20_DOLLARS = "sh_price_10to20";
        static final String PRICE_UNDER_7_DOLLARS = "sh_price_u7";
        static final String PRICE_UNDER_5_DOLLARS = "sh_price_u5";
        static final String PRICE_UNDER_10_DOLLARS = "sh_price_u10";
        static final String PRICE_OVER_2_DOLLARS = "sh_price_o2";
        static final String PRICE_OVER_1_DOLLARS = "sh_price_o1";
        static final String PRICE_1_TO_20_DOLLARS = "sh_price_1to20";
        static final String PRICE_OVER_70_DOLLARS = "sh_price_o70";
        static final String PRICE_UNDER_2_DOLLARS = "sh_price_u2";
        static final String PRICE_OVER_4_DOLLARS = "sh_price_o4";
        static final String PRICE_UNDER_1_DOLLARS = "sh_price_u1";
        static final String PRICE_OVER_3_DOLLARS = "sh_price_o3";
        static final String PRICE_UNDER_4_DOLLARS = "sh_price_u4";
        static final String PRICE_UNDER_3_DOLLARS = "sh_price_u3";
        static final String PRICE_OVER_5_DOLLARS = "sh_price_o5";
        static final String PRICE_OVER_15_DOLLARS = "sh_price_o15";
        static final String PRICE_1_TO_5_DOLLARS = "sh_price_1to5";
        static final String PRICE_OVER_7_DOLLARS = "sh_price_o7";
        static final String PRICE_OVER_30_DOLLARS = "sh_price_o30";
        static final String PRICE_UNDER_30_DOLLARS = "sh_price_u30";
        static final String PRICE_5_TO_50_DOLLARS = "sh_price_5to50";
        static final String PRICE_OVER_50_DOLLARS = "sh_price_o50";
        static final String PRICE_UNDER_50_DOLLARS = "sh_price_u50";
        static final String PRICE_UNDER_15_DOLLARS = "sh_price_u15";
        static final String PRICE_OVER_90_DOLLARS = "sh_price_o90";
        static final String PRICE_5_TO_10_DOLLARS = "sh_price_5to10";
        static final String PRICE_50_TO_100_DOLLARS = "sh_price_50to100";
        static final String PRICE_10_TO_50_DOLLARS = "sh_price_10to50";
        static final String PRICE_OVER_100_DOLLARS = "sh_price_o100";
        static final String PRICE_OVER_10_DOLLARS = "sh_price_o10";
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
        public static enum FeatureCategory { Exchange,Index,Sector,Industry,Country,MarketCap,PricetoEarningsRatio,ForwardPricetoEarningsRatio,PricetoEarningstoGrowth,
PricetoSalesRatio,PricetoBookRatio,PricetoCashRatio,PricetoFreeCashFlowttm,Epsgrowththisyear,Epsgrowthnextyear,
Epsgrowthpast5years,Epsgrowthnext5years,SalesGrowthpast5years,Epsgrowthqtroverqtr,Salesgrowthqtroverqtr,DividendYield,
ReturnonAssetsttm,ReturnonEquityttm,ReturnonInvestmentttm,CurrentRatiomrq,QuickRatiomrq,LongTermDebttoEquitymrq,
TotalDebttoEquitymrq,GrossMarginttm,OperatingMarginttm,NetProfitMargin,PayoutRatio,InsiderOwnership,InsiderTranscations,
InstitutionalOwnership,InstitutionalTransaction,ShortSelling,AnalystRecommendation,OptionShort,EarningsDate,Performance,
Performance2,Volatility,RelativeStrengthIndex14,Gap,I20DaySimpleMovingAverage,I50DaySimpleMovingAverage,
I200DaySimpleMovingAverage,ChangefrompreviousClose,ChangefromOpen,I20DayHighLow,I50DayHighLow,I52WeekHighLow,Pattern,
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
        public static enum PricetoEarningsRatio { UNDER_10,UNDER_20,OVER_5,PROFITABLE,OVER_15,OVER_30,UNDER_30,OVER_50,OVER_25,
                                                    UNDER_40,OVER_40,UNDER_50,UNDER_15,UNDER_45,UNDER_25,UNDER_35,OVER_10,OVER_35,
                                                    OVER_20,OVER_45,UNDER_5,HIGH,LOW }
        public static enum ForwardPricetoEarningsRatio { UNDER_10,UNDER_20,OVER_5,PROFITABLE,OVER_15,OVER_30,UNDER_30,OVER_50,OVER_25,
                                                    UNDER_40,OVER_40,UNDER_50,UNDER_15,UNDER_45,UNDER_25,UNDER_35,OVER_10,OVER_35,
                                                    OVER_20,OVER_45,UNDER_5,HIGH,LOW }
        public static enum PricetoEarningstoGrowth { OVER_2,OVER_1,UNDER_2,OVER_3,UNDER_1,UNDER_3,HIGH,LOW }
        public static enum PricetoSalesRatio { UNDER_10,OVER_2,OVER_1,OVER_4,UNDER_2,OVER_3,UNDER_1,OVER_6,UNDER_4,OVER_5,UNDER_3,
                                                OVER_8,OVER_7,OVER_9,OVER_10,UNDER_7,UNDER_8,UNDER_5,HIGH,UNDER_6,LOW,UNDER_9}
        public static enum PricetoBookRatio { UNDER_10,OVER_2,OVER_1,OVER_4,UNDER_2,OVER_3,UNDER_1,OVER_6,UNDER_4,OVER_5,UNDER_3,
                                                OVER_8,OVER_7,OVER_9,OVER_10,UNDER_7,UNDER_8,UNDER_5,HIGH,UNDER_6,LOW,UNDER_9 }
        public static enum PricetoCashRatio { UNDER_10,OVER_2,OVER_1,OVER_4,UNDER_2,OVER_3,UNDER_1,OVER_6,UNDER_4,OVER_5,UNDER_3,
                                                OVER_8,OVER_7,OVER_9,OVER_10,UNDER_7,UNDER_8,UNDER_5,HIGH,UNDER_6,LOW,UNDER_9,
                                                OVER_40,OVER_20,OVER_30,OVER_50}
        public static enum PricetoFreeCashFlowttm { HIGH,LOW,UNDER_20,UNDER_70,OVER_80,OVER_60,UNDER_40,OVER_25,OVER_40,UNDER_45,
                                                    UNDER_90,UNDER_25,UNDER_60,OVER_20,OVER_45,UNDER_5,UNDER_10,OVER_70,OVER_5,
                                                    OVER_15,UNDER_80,OVER_30,UNDER_30,OVER_50,UNDER_50,UNDER_15,OVER_90,UNDER_35,OVER_100,
                                                    OVER_10,OVER_35,UNDER_100 }
        public static enum Epsgrowththisyear { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum Epsgrowthnextyear { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum Epsgrowthpast5years { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum Epsgrowthnext5years { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum SalesGrowthpast5years { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum Epsgrowthqtroverqtr { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum Salesgrowthqtroverqtr { NEGATIVE,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                                UNDER_30_PERCENT,OVER_25_PERCENT,UNDER_15_PERCENT,POSITIVE,UNDER_25_PERCENT,POSITIVE_LOW,
                                                OVER_10_PERCENT,OVER_20_PERCENT,UNDER_5_PERCENT,HIGH }
        public static enum DividendYield { HIGH,OVER_2_PERCENT,OVER_1_PERCENT,VERY_HIGH,OVER_4_PERCENT,OVER_3_PERCENT,OVER_6_PERCENT,
                                            OVER_5_PERCENT,OVER_8_PERCENT,OVER_7_PERCENT,OVER_9_PERCENT,POSITIVE,OVER_10_PERCENT,NONE }
        public static enum ReturnonAssetsttm { UNDER_NEG_10_PERCENT,NEGATIVE,UNDER_NEG_35_PERCENT,UNDER_NEG_25_PERCENT,UNDER_NEG_45_PERCENT,
                                                VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,UNDER_NEG_40_PERCENT,
                                                UNDER_NEG_15_PERCENT,OVER_50_PERCENT,OVER_25_PERCENT,OVER_40_PERCENT,VERY_NEGATIVE,
                                                UNDER_NEG_50_PERCENT,UNDER_NEG_30_PERCENT,POSITIVE,UNDER_NEG_20_PERCENT,UNDER_NEG_5_PERCENT,
                                                OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT }
        public static enum ReturnonEquityttm { UNDER_NEG_10_PERCENT,NEGATIVE,UNDER_NEG_35_PERCENT,UNDER_NEG_25_PERCENT,UNDER_NEG_45_PERCENT,
                                                VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,UNDER_NEG_40_PERCENT,
                                                UNDER_NEG_15_PERCENT,OVER_50_PERCENT,OVER_25_PERCENT,OVER_40_PERCENT,VERY_NEGATIVE,
                                                UNDER_NEG_50_PERCENT,UNDER_NEG_30_PERCENT,POSITIVE,UNDER_NEG_20_PERCENT,UNDER_NEG_5_PERCENT,
                                                OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT }
        public static enum ReturnonInvestmentttm { UNDER_NEG_10_PERCENT,NEGATIVE,UNDER_NEG_35_PERCENT,UNDER_NEG_25_PERCENT,UNDER_NEG_45_PERCENT,
                                                VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,UNDER_NEG_40_PERCENT,
                                                UNDER_NEG_15_PERCENT,OVER_50_PERCENT,OVER_25_PERCENT,OVER_40_PERCENT,VERY_NEGATIVE,
                                                UNDER_NEG_50_PERCENT,UNDER_NEG_30_PERCENT,POSITIVE,UNDER_NEG_20_PERCENT,UNDER_NEG_5_PERCENT,
                                                OVER_10_PERCENT,OVER_35_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT }
        public static enum CurrentRatiomrq { OVER_0_POINT_5,OVER_2,OVER_1,OVER_10,OVER_4,OVER_3,UNDER_1,OVER_5,OVER_1_POINT_5,
                                                UNDER_0_POINT_5,HIGH,LOW }
        public static enum QuickRatiomrq { OVER_0_POINT_5,OVER_2,OVER_1,OVER_10,OVER_4,OVER_3,UNDER_1,OVER_5,OVER_1_POINT_5,
                                            UNDER_0_POINT_5,HIGH,LOW }
        public static enum LongTermDebttoEquitymrq { OVER_0_POINT_5,OVER_0_POINT_6,OVER_0_POINT_7,OVER_1,OVER_0_POINT_8,
                                                        OVER_0_POINT_9,UNDER_1,UNDER_0_POINT_5,UNDER_0_POINT_6,UNDER_0_POINT_3,
                                                        UNDER_0_POINT_4,OVER_0_POINT_1,UNDER_0_POINT_9,OVER_0_POINT_2,OVER_0_POINT_3,
                                                        UNDER_0_POINT_7,OVER_0_POINT_4,UNDER_0_POINT_8,UNDER_0_POINT_2,UNDER_0_POINT_1,
                                                        HIGH,LOW }
        public static enum TotalDebttoEquitymrq { OVER_0_POINT_5,OVER_0_POINT_6,OVER_0_POINT_7,OVER_1,OVER_0_POINT_8,OVER_0_POINT_9,
                                                    UNDER_1,UNDER_0_POINT_5,UNDER_0_POINT_6,UNDER_0_POINT_3,UNDER_0_POINT_4,OVER_0_POINT_1,
                                                    UNDER_0_POINT_9,OVER_0_POINT_2,OVER_0_POINT_3,UNDER_0_POINT_7,OVER_0_POINT_4,
                                                    UNDER_0_POINT_8,UNDER_0_POINT_2,UNDER_0_POINT_1,HIGH,LOW }
        public static enum GrossMarginttm { NEGATIVE,UNDER_NEG_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,OVER_60_PERCENT,
                                            OVER_25_PERCENT,UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_45_PERCENT,UNDER_NEG_50_PERCENT,UNDER_90_PERCENT,
                                            UNDER_NEG_30_PERCENT,POSITIVE,UNDER_25_PERCENT,UNDER_60_PERCENT,UNDER_NEG_70_PERCENT,
                                            OVER_20_PERCENT,UNDER_NEG_100_PERCENT,OVER_45_PERCENT,UNDER_5_PERCENT,OVER_0_PERCENT,
                                            UNDER_10_PERCENT,UNDER_0_PERCENT,OVER_70_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_80_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,UNDER_50_PERCENT,UNDER_15_PERCENT,
                                            OVER_90_PERCENT,UNDER_35_PERCENT,UNDER_NEG_20_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,HIGH }
        public static enum OperatingMarginttm { NEGATIVE,UNDER_NEG_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,OVER_60_PERCENT,
                                            OVER_25_PERCENT,UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_45_PERCENT,UNDER_NEG_50_PERCENT,UNDER_90_PERCENT,
                                            UNDER_NEG_30_PERCENT,POSITIVE,UNDER_25_PERCENT,UNDER_60_PERCENT,UNDER_NEG_70_PERCENT,
                                            OVER_20_PERCENT,UNDER_NEG_100_PERCENT,OVER_45_PERCENT,UNDER_5_PERCENT,OVER_0_PERCENT,
                                            UNDER_10_PERCENT,UNDER_0_PERCENT,OVER_70_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_80_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,UNDER_50_PERCENT,UNDER_15_PERCENT,
                                            OVER_90_PERCENT,UNDER_35_PERCENT,UNDER_NEG_20_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,HIGH }
        public static enum NetProfitMargin { NEGATIVE,UNDER_NEG_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,OVER_60_PERCENT,
                                            OVER_25_PERCENT,UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_45_PERCENT,UNDER_NEG_50_PERCENT,UNDER_90_PERCENT,
                                            UNDER_NEG_30_PERCENT,POSITIVE,UNDER_25_PERCENT,UNDER_60_PERCENT,UNDER_NEG_70_PERCENT,
                                            OVER_20_PERCENT,UNDER_NEG_100_PERCENT,OVER_45_PERCENT,UNDER_5_PERCENT,OVER_0_PERCENT,
                                            UNDER_10_PERCENT,UNDER_0_PERCENT,OVER_70_PERCENT,OVER_5_PERCENT,OVER_15_PERCENT,OVER_30_PERCENT,
                                            UNDER_80_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,UNDER_50_PERCENT,UNDER_15_PERCENT,
                                            OVER_90_PERCENT,UNDER_35_PERCENT,UNDER_NEG_20_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,HIGH }
        public static enum PayoutRatio { OVER_0_PERCENT,UNDER_10_PERCENT,UNDER_20_PERCENT,OVER_70_PERCENT,UNDER_70_PERCENT,UNDER_80_PERCENT,
                                            OVER_80_PERCENT,OVER_30_PERCENT,UNDER_30_PERCENT,OVER_60_PERCENT,UNDER_40_PERCENT,
                                            OVER_50_PERCENT,UNDER_50_PERCENT,OVER_40_PERCENT,UNDER_90_PERCENT,OVER_90_PERCENT,
                                            POSITIVE,UNDER_60_PERCENT,OVER_10_PERCENT,OVER_20_PERCENT,NONE,HIGH,LOW,UNDER_100_PERCENT,
                                            OVER_100_PERCENT }
        public static enum InsiderOwnership { OVER_70_PERCENT,OVER_10_PERCENT,VERY_HIGH,OVER_20_PERCENT,OVER_80_PERCENT,OVER_30_PERCENT,
                                                OVER_60_PERCENT,OVER_50_PERCENT,HIGH,OVER_40_PERCENT,LOW,OVER_90_PERCENT }
        public static enum InsiderTranscations { UNDER_NEG_35_PERCENT,NEGATIVE,UNDER_NEG_10_PERCENT,OVER_80_PERCENT,
                                                    OVER_60_PERCENT,UNDER_NEG_90_PERCENT,UNDER_NEG_15_PERCENT,OVER_25_PERCENT,
                                                    OVER_40_PERCENT,UNDER_NEG_50_PERCENT,VERY_NEGATIVE,UNDER_NEG_30_PERCENT,
                                                    POSITIVE,UNDER_NEG_70_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT,UNDER_NEG_25_PERCENT,
                                                    OVER_70_PERCENT,UNDER_NEG_45_PERCENT,VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,
                                                    UNDER_NEG_40_PERCENT,OVER_30_PERCENT,OVER_50_PERCENT,OVER_90_PERCENT,UNDER_NEG_20_PERCENT,
                                                    UNDER_NEG_60_PERCENT,UNDER_NEG_5_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,UNDER_NEG_80_PERCENT,
                                                    UNDER_10_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT }
        public static enum InstitutionalOwnership { UNDER_10_PERCENT,OVER_70_PERCENT,UNDER_20_PERCENT,UNDER_70_PERCENT,OVER_80_PERCENT,
                                                    OVER_30_PERCENT,UNDER_80_PERCENT,OVER_60_PERCENT,UNDER_30_PERCENT,OVER_50_PERCENT,
                                                    UNDER_40_PERCENT,OVER_40_PERCENT,UNDER_50_PERCENT,OVER_90_PERCENT,UNDER_90_PERCENT,
                                                    UNDER_60_PERCENT,OVER_10_PERCENT,OVER_20_PERCENT,HIGH,LOW }
        public static enum InstitutionalTransaction { UNDER_NEG_35_PERCENT,NEGATIVE,UNDER_NEG_10_PERCENT,OVER_80_PERCENT,
                                                    OVER_60_PERCENT,UNDER_NEG_90_PERCENT,UNDER_NEG_15_PERCENT,OVER_25_PERCENT,
                                                    OVER_40_PERCENT,UNDER_NEG_50_PERCENT,VERY_NEGATIVE,UNDER_NEG_30_PERCENT,
                                                    POSITIVE,UNDER_NEG_70_PERCENT,OVER_20_PERCENT,OVER_45_PERCENT,UNDER_NEG_25_PERCENT,
                                                    OVER_70_PERCENT,UNDER_NEG_45_PERCENT,VERY_POSITIVE,OVER_5_PERCENT,OVER_15_PERCENT,
                                                    UNDER_NEG_40_PERCENT,OVER_30_PERCENT,OVER_50_PERCENT,OVER_90_PERCENT,UNDER_NEG_20_PERCENT,
                                                    UNDER_NEG_60_PERCENT,UNDER_NEG_5_PERCENT,OVER_10_PERCENT,OVER_35_PERCENT,UNDER_NEG_80_PERCENT,
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
        public static enum Performance { MONTH_NEG_50_PERCENT,HALF_50_PERCENT,WEEK_UP,HALF_NEG_50_PERCENT,MONTH_10_PERCENT,
                                            MONTH_50_PERCENT,HALF_30_PERCENT,QUARTER_DOWN,MONTH_NEG_10_PERCENT,YTD_NEG_30_PERCENT,
                                            QUARTER_NEG_10_PERCENT,YTD_20_PERCENT,YTD_NEG_20_PERCENT,YTD_30_PERCENT,QUARTER_30_PERCENT,
                                            YTD_5_PERCENT,QUARTER_NEG_20_PERCENT,HALF_NEG_30_PERCENT,YTD_NEG_5_PERCENT,
                                            YEAR_200_PERCENT,TODAY_UP,YEAR_NEG_50_PERCENT,QUARTER_UP,TODAY_5_PERCENT,
                                            QUARTER_50_PERCENT,YEAR_NEG_75_PERCENT,YEAR_20_PERCENT,YTD_50_PERCENT,QUARTER_NEG_30_PERCENT,
                                            YEAR_50_PERCENT,HALF_DOWN,QUARTER_NEG_50_PERCENT,TODAY_NEG_5_PERCENT,YEAR_NEG_20_PERCENT,
                                            WEEK_10_PERCENT,TODAY_NEG_10_PERCENT,TODAY_DOWN,WEEK_NEG_10_PERCENT,YTD_DOWN,
                                            HALF_NEG_75_PERCENT,TODAY_10_PERCENT,HALF_NEG_10_PERCENT,QUARTER_10_PERCENT,
                                            YTD_10_PERCENT,YEAR_NEG_10_PERCENT,HALF_NEG_20_PERCENT,YEAR_500_PERCENT,YTD_NEG_50_PERCENT,
                                            HALF_100_PERCENT,YTD_NEG_10_PERCENT,YEAR_10_PERCENT,QUARTER_20_PERCENT,YEAR_UP,
                                            WEEK_30_PERCENT,MONTH_NEG_30_PERCENT,YTD_NEG_75_PERCENT,WEEK_DOWN,HALF_UP,YEAR_300_PERCENT,
                                            MONTH_NEG_20_PERCENT,WEEK_20_PERCENT,HALF_10_PERCENT,YEAR_100_PERCENT,MONTH_DOWN,
                                            HALF_20_PERCENT,WEEK_NEG_20_PERCENT,MONTH_20_PERCENT,YTD_UP,TODAY_15_PERCENT,
                                            YTD_100_PERCENT,MONTH_UP,YEAR_NEG_30_PERCENT,YEAR_30_PERCENT,WEEK_NEG_30_PERCENT,
                                            TODAY_NEG_15_PERCENT,MONTH_30_PERCENT,YEAR_DOWN }
        public static enum Performance2 { MONTH_NEG_50_PERCENT,HALF_50_PERCENT,WEEK_UP,HALF_NEG_50_PERCENT,MONTH_10_PERCENT,
                                            MONTH_50_PERCENT,HALF_30_PERCENT,QUARTER_DOWN,MONTH_NEG_10_PERCENT,YTD_NEG_30_PERCENT,
                                            QUARTER_NEG_10_PERCENT,YTD_20_PERCENT,YTD_NEG_20_PERCENT,YTD_30_PERCENT,QUARTER_30_PERCENT,
                                            YTD_5_PERCENT,QUARTER_NEG_20_PERCENT,HALF_NEG_30_PERCENT,YTD_NEG_5_PERCENT,
                                            YEAR_200_PERCENT,TODAY_UP,YEAR_NEG_50_PERCENT,QUARTER_UP,TODAY_5_PERCENT,
                                            QUARTER_50_PERCENT,YEAR_NEG_75_PERCENT,YEAR_20_PERCENT,YTD_50_PERCENT,QUARTER_NEG_30_PERCENT,
                                            YEAR_50_PERCENT,HALF_DOWN,QUARTER_NEG_50_PERCENT,TODAY_NEG_5_PERCENT,YEAR_NEG_20_PERCENT,
                                            WEEK_10_PERCENT,TODAY_NEG_10_PERCENT,TODAY_DOWN,WEEK_NEG_10_PERCENT,YTD_DOWN,
                                            HALF_NEG_75_PERCENT,TODAY_10_PERCENT,HALF_NEG_10_PERCENT,QUARTER_10_PERCENT,
                                            YTD_10_PERCENT,YEAR_NEG_10_PERCENT,HALF_NEG_20_PERCENT,YEAR_500_PERCENT,YTD_NEG_50_PERCENT,
                                            HALF_100_PERCENT,YTD_NEG_10_PERCENT,YEAR_10_PERCENT,QUARTER_20_PERCENT,YEAR_UP,
                                            WEEK_30_PERCENT,MONTH_NEG_30_PERCENT,YTD_NEG_75_PERCENT,WEEK_DOWN,HALF_UP,YEAR_300_PERCENT,
                                            MONTH_NEG_20_PERCENT,WEEK_20_PERCENT,HALF_10_PERCENT,YEAR_100_PERCENT,MONTH_DOWN,
                                            HALF_20_PERCENT,WEEK_NEG_20_PERCENT,MONTH_20_PERCENT,YTD_UP,TODAY_15_PERCENT,
                                            YTD_100_PERCENT,MONTH_UP,YEAR_NEG_30_PERCENT,YEAR_30_PERCENT,WEEK_NEG_30_PERCENT,
                                            TODAY_NEG_15_PERCENT,MONTH_30_PERCENT,YEAR_DOWN }
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
        public static enum I20DaySimpleMovingAverage { PRICE_20_PERCENTBELOW_SMA20,PRICE_30_PERCENTABOVE_SMA20,PRICE_10_PERCENTBELOW_SMA20,
                                                        PRICE_40_PERCENTABOVE_SMA20,PRICE_50_PERCENTABOVE_SMA20,SMA20_CROSSED_SMA200,
                                                        SMA20_ABOVE_SMA50,SMA20_CROSSED_SMA50,SMA20_BELOW_SMA50,SMA20_BELOW_SMA200,
                                                        SMA20_CROSSED_SMA200_ABOVE,PRICE_10_PERCENTABOVE_SMA20,SMA20_CROSSED_SMA50_BELOW,
                                                        PRICE_20_PERCENTABOVE_SMA20,SMA20_CROSSED_SMA200_BELOW,SMA20_CROSSED_SMA50_ABOVE,
                                                        PRICE_ABOVE_SMA20,SMA20_ABOVE_SMA200,PRICE_BELOW_SMA20,PRICE_CROSSED_SMA20,
                                                        PRICE_CROSSED_SMA20_BELOW,PRICE_30_PERCENTBELOW_SMA20,PRICE_CROSSED_SMA20_ABOVE,
                                                        PRICE_50_PERCENTBELOW_SMA20,PRICE_40_PERCENTBELOW_SMA20 }
        public static enum I50DaySimpleMovingAverage { PRICE_20_PERCENTBELOW_SMA50,PRICE_30_PERCENTABOVE_SMA50,PRICE_10_PERCENTBELOW_SMA50,
                                                        PRICE_40_PERCENTABOVE_SMA50,PRICE_50_PERCENTABOVE_SMA50,SMA50_ABOVE_SMA20,
                                                        SMA50_CROSSED_SMA200,SMA50_CROSSED_SMA20,SMA50_BELOW_SMA200,SMA50_CROSSED_SMA200_ABOVE,
                                                        PRICE_10_PERCENTABOVE_SMA50,PRICE_20_PERCENTABOVE_SMA50,SMA50_BELOW_SMA20,
                                                        SMA50_CROSSED_SMA200_BELOW,PRICE_ABOVE_SMA50,SMA50_ABOVE_SMA200,
                                                        PRICE_BELOW_SMA50,PRICE_CROSSED_SMA50,SMA50_CROSSED_SMA20_ABOVE,
                                                        PRICE_CROSSED_SMA50_BELOW,PRICE_30_PERCENTBELOW_SMA50,PRICE_CROSSED_SMA50_ABOVE,
                                                        PRICE_50_PERCENTBELOW_SMA50,PRICE_40_PERCENTBELOW_SMA50,SMA50_CROSSED_SMA20_BELOW }
        public static enum I200DaySimpleMovingAverage { PRICE_20_PERCENTBELOW_SMA200,PRICE_30_PERCENTABOVE_SMA200,PRICE_50_PERCENTABOVE_SMA200,
                                                        SMA200_ABOVE_SMA20,SMA200_CROSSED_SMA20,PRICE_90_PERCENTABOVE_SMA200,
                                                        PRICE_70_PERCENTABOVE_SMA200,SMA200_CROSSED_SMA50_BELOW,PRICE_20_PERCENTABOVE_SMA200,
                                                        SMA200_BELOW_SMA20,SMA200_CROSSED_SMA50_ABOVE,SMA200_CROSSED_SMA20_ABOVE,PRICE_60_PERCENTBELOW_SMA200,
                                                        PRICE_80_PERCENTBELOW_SMA200,PRICE_40_PERCENTBELOW_SMA200,SMA200_CROSSED_SMA20_BELOW,
                                                        PRICE_60_PERCENTABOVE_SMA200,PRICE_10_PERCENTBELOW_SMA200,PRICE_40_PERCENTABOVE_SMA200,
                                                        SMA200_ABOVE_SMA50,SMA200_CROSSED_SMA50,SMA200_BELOW_SMA50,PRICE_10_PERCENTABOVE_SMA200,
                                                        PRICE_80_PERCENTABOVE_SMA200,PRICE_ABOVE_SMA200,PRICE_100_PERCENTABOVE_SMA200,
                                                        PRICE_BELOW_SMA200,PRICE_90_PERCENTBELOW_SMA200,PRICE_CROSSED_SMA200,
                                                        PRICE_70_PERCENTBELOW_SMA200,PRICE_30_PERCENTBELOW_SMA200,PRICE_CROSSED_SMA200_BELOW,
                                                        PRICE_CROSSED_SMA200_ABOVE,PRICE_50_PERCENTBELOW_SMA200 }
        public static enum ChangefrompreviousClose { UP_10_PERCENT,DOWN,UP_20_PERCENT,UP_2_PERCENT,UP_1_PERCENT,UP_4_PERCENT,
                                                        UP_3_PERCENT,DOWN_9_PERCENT,DOWN_8_PERCENT,DOWN_5_PERCENT,UP_15_PERCENT,
                                                        DOWN_4_PERCENT,DOWN_7_PERCENT,DOWN_6_PERCENT,DOWN_1_PERCENT,DOWN_2_PERCENT,
                                                        UP,DOWN_3_PERCENT,DOWN_20_PERCENT,DOWN_10_PERCENT,UP_7_PERCENT,UP_8_PERCENT,
                                                        UP_5_PERCENT,UP_6_PERCENT,DOWN_15_PERCENT,UP_9_PERCENT }
        public static enum ChangefromOpen { UP_10_PERCENT,DOWN,UP_20_PERCENT,UP_2_PERCENT,UP_1_PERCENT,UP_4_PERCENT,
                                            UP_3_PERCENT,DOWN_9_PERCENT,DOWN_8_PERCENT,DOWN_5_PERCENT,UP_15_PERCENT,DOWN_4_PERCENT,
                                            DOWN_7_PERCENT,DOWN_6_PERCENT,DOWN_1_PERCENT,DOWN_2_PERCENT,UP,DOWN_3_PERCENT,
                                            DOWN_20_PERCENT,DOWN_10_PERCENT,UP_7_PERCENT,UP_8_PERCENT,UP_5_PERCENT,
                                            UP_6_PERCENT,DOWN_15_PERCENT,UP_9_PERCENT }
        public static enum I20DayHighLow { I10_PERCENTOR_MORE_ABOVE_LOW,I20_PERCENTOR_MORE_ABOVE_LOW,I40_PERCENTOR_MORE_BELOW_HIGH,
                                            I0_TO_10_PERCENTBELOW_HIGH,I15_PERCENTOR_MORE_ABOVE_LOW,I10_PERCENTOR_MORE_BELOW_HIGH,
                                            I5_PERCENTOR_MORE_BELOW_HIGH,I5_PERCENTOR_MORE_ABOVE_LOW,I0_TO_10_PERCENTABOVE_LOW,
                                            I30_PERCENTOR_MORE_ABOVE_LOW,I20_PERCENTOR_MORE_BELOW_HIGH,I50_PERCENTOR_MORE_ABOVE_LOW,
                                            I30_PERCENTOR_MORE_BELOW_HIGH,I0_TO_3_PERCENTABOVE_LOW,I0_TO_5_PERCENTABOVE_LOW,
                                            I0_TO_3_PERCENTBELOW_HIGH,I15_PERCENTOR_MORE_BELOW_HIGH,I0_TO_5_PERCENTBELOW_HIGH,
                                            I40_PERCENTOR_MORE_ABOVE_LOW,NEW_HIGH,NEW_LOW,I50_PERCENTOR_MORE_BELOW_HIGH }
        public static enum I50DayHighLow { I10_PERCENTOR_MORE_ABOVE_LOW,I20_PERCENTOR_MORE_ABOVE_LOW,I40_PERCENTOR_MORE_BELOW_HIGH,
                                            I0_TO_10_PERCENTBELOW_HIGH,I15_PERCENTOR_MORE_ABOVE_LOW,I10_PERCENTOR_MORE_BELOW_HIGH,
                                            I5_PERCENTOR_MORE_BELOW_HIGH,I5_PERCENTOR_MORE_ABOVE_LOW,I0_TO_10_PERCENTABOVE_LOW,
                                            I30_PERCENTOR_MORE_ABOVE_LOW,I20_PERCENTOR_MORE_BELOW_HIGH,I50_PERCENTOR_MORE_ABOVE_LOW,
                                            I30_PERCENTOR_MORE_BELOW_HIGH,I0_TO_3_PERCENTABOVE_LOW,I0_TO_5_PERCENTABOVE_LOW,
                                            I0_TO_3_PERCENTBELOW_HIGH,I15_PERCENTOR_MORE_BELOW_HIGH,I0_TO_5_PERCENTBELOW_HIGH,
                                            I40_PERCENTOR_MORE_ABOVE_LOW,NEW_HIGH,NEW_LOW,I50_PERCENTOR_MORE_BELOW_HIGH }
        public static enum I52WeekHighLow { I10_PERCENTOR_MORE_ABOVE_LOW,I20_PERCENTOR_MORE_ABOVE_LOW,I40_PERCENTOR_MORE_BELOW_HIGH,
                                            I0_TO_10_PERCENTBELOW_HIGH,I15_PERCENTOR_MORE_ABOVE_LOW,I10_PERCENTOR_MORE_BELOW_HIGH,
                                            I5_PERCENTOR_MORE_BELOW_HIGH,I5_PERCENTOR_MORE_ABOVE_LOW,I0_TO_10_PERCENTABOVE_LOW,
                                            I30_PERCENTOR_MORE_ABOVE_LOW,I20_PERCENTOR_MORE_BELOW_HIGH,I50_PERCENTOR_MORE_ABOVE_LOW,
                                            I30_PERCENTOR_MORE_BELOW_HIGH,I0_TO_3_PERCENTABOVE_LOW,I0_TO_5_PERCENTABOVE_LOW,
                                            I0_TO_3_PERCENTBELOW_HIGH,I15_PERCENTOR_MORE_BELOW_HIGH,I0_TO_5_PERCENTBELOW_HIGH,
                                            I40_PERCENTOR_MORE_ABOVE_LOW,NEW_HIGH,NEW_LOW,I50_PERCENTOR_MORE_BELOW_HIGH }
        public static enum Pattern { CHANNEL_UP_STRONG,TRIANGLE_ASCENDING_STRONG,DOUBLE_TOP,WEDGE_STRONG,WEDGE_DOWN_STRONG,
                                        CHANNEL_DOWN,MULTIPLE_BOTTOM,DOUBLE_BOTTOM,TL_SUPPORT,WEDGE_UP_STRONG,TRIANGLE_DESCENDING_STRONG,
                                        HEAD_AND_SHOULDERS_INVERSE,CHANNEL_DOWN_STRONG,WEDGE,TL_RESISTANCE_STRONG,HORIZONTAL_S_R_STRONG,
                                        CHANNEL_UP,MULTIPLE_TOP,WEDGE_DOWN,TRIANGLE_DESCENDING,CHANNEL_STRONG,HEAD_AND_SHOULDERS,
                                        TRIANGLE_ASCENDING,HORIZONTAL_S_R,WEDGE_UP,TL_RESISTANCE,TL_SUPPORT_STRONG,CHANNEL }
        public static enum Candlestick { DRAGONFLY_DOJI,DOJI,LONG_LOWER_SHADOW,SPINNING_TOP_WHITE,INVERTED_HAMMER,SPINNING_TOP_BLACK,
                                        MARUBOZU_BLACK,GRAVESTONE_DOJI,LONG_UPPER_SHADOW,MARUBOZU_WHITE,HAMMER }
        public static enum Beta { I0_TO_1,OVER_0,OVER_0_POINT_5,UNDER_0,OVER_2,OVER_1,OVER_4,UNDER_2,UNDER_1,UNDER_1_POINT_5,
                                    OVER_3,I1_TO_2,OVER_1_POINT_5,UNDER_0_POINT_5,OVER_2_POINT_5,I0_POINT_5_TO_1_POINT_5,I1_TO_1_POINT_5,
                                    I0_POINT_5_TO_1,I0_TO_0_POINT_5 }
        public static enum AverageTrueRange { OVER_0_POINT_5,OVER_2,OVER_1,UNDER_2_POINT_5,UNDER_2,OVER_4,UNDER_1_POINT_5,
                                                UNDER_1,OVER_3,UNDER_4,UNDER_3,OVER_1_POINT_5,OVER_5,UNDER_0_POINT_5,OVER_2_POINT_5,
                                                UNDER_3_POINT_5,UNDER_0_POINT_75,UNDER_0_POINT_25,OVER_4_POINT_5,OVER_0_POINT_75,
                                                UNDER_5,OVER_0_POINT_25,OVER_3_POINT_5,UNDER_4_POINT_5 }
        public static enum AverageVolume { UNDER_750K,OVER_500K,I100K_TO_1M,OVER_200K,OVER_50K,UNDER_50K,I500K_TO_1M,UNDER_1M,
                                            UNDER_500K,OVER_750K,I500K_TO_10M,OVER_100K,OVER_2M,I100K_TO_500K,OVER_400K,
                                            OVER_1M,UNDER_100K,OVER_300K }
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
    
    public FinvizDomainManager(){
        
    }
    public Prospect[] generateProspects(FinvizDomainManager.Signal.SignalSelection signal, Enum... features){
        
        
        ArrayList<String> prospectList = FinvizConnectionManager.getProspectList(signal,features);
        
        if(prospectList.size() > 0)
        {
            Prospect[] prospects = convertToProspectArray(prospectList, signal, features);
        
            return prospects;
        }
        return new Prospect[0];
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
                        /* old
                        case EXCHANGE:
                            System.out.println("Hit Exchange");
                            int randEx = rand.nextInt(Exchange.values().length);
                            System.out.println(Exchange.values()[randEx].toString());
                            featureList.add(Exchange.values()[randEx]);
                            break;
                        case MARKET_CAP:
                            System.out.println("Hit Market Cap");
                            int randMarketCap = rand.nextInt(MarketCap.values().length);
                            System.out.println(MarketCap.values()[randMarketCap].toString());
                            featureList.add(MarketCap.values()[randMarketCap]);
                            break;
                        case OPTION_SHORT:
                            System.out.println("Hit Option Short");
                            int randOptionShort = rand.nextInt(OptionShort.values().length);
                            System.out.println(OptionShort.values()[randOptionShort].toString());
                            featureList.add(OptionShort.values()[randOptionShort]);
                            break;
                        case RELATIVE_VOLUME:
                            System.out.println("Hit Relative Volume");
                            int randRelativeVolume = rand.nextInt(RelativeVolume.values().length);
                            System.out.println(RelativeVolume.values()[randRelativeVolume].toString());
                            featureList.add(RelativeVolume.values()[randRelativeVolume]); 
                            break;    
                            */
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
                                int randPricetoEarningsRatio = rand.nextInt(PricetoEarningsRatio.values().length);
                                featureList.add(PricetoEarningsRatio.values()[randPricetoEarningsRatio]);
                                break;
                        case ForwardPricetoEarningsRatio:
                                int randForwardPricetoEarningsRatio = rand.nextInt(ForwardPricetoEarningsRatio.values().length);
                                featureList.add(ForwardPricetoEarningsRatio.values()[randForwardPricetoEarningsRatio]);
                                break;
                        case PricetoEarningstoGrowth:
                                int randPricetoEarningstoGrowth = rand.nextInt(PricetoEarningstoGrowth.values().length);
                                featureList.add(PricetoEarningstoGrowth.values()[randPricetoEarningstoGrowth]);
                                break;
                        case PricetoSalesRatio:
                                int randPricetoSalesRatio = rand.nextInt(PricetoSalesRatio.values().length);
                                featureList.add(PricetoSalesRatio.values()[randPricetoSalesRatio]);
                                break;
                        case PricetoBookRatio:
                                int randPricetoBookRatio = rand.nextInt(PricetoBookRatio.values().length);
                                featureList.add(PricetoBookRatio.values()[randPricetoBookRatio]);
                                break;
                        case PricetoCashRatio:
                                int randPricetoCashRatio = rand.nextInt(PricetoCashRatio.values().length);
                                featureList.add(PricetoCashRatio.values()[randPricetoCashRatio]);
                                break;
                        case PricetoFreeCashFlowttm:
                                int randPricetoFreeCashFlowttm = rand.nextInt(PricetoFreeCashFlowttm.values().length);
                                featureList.add(PricetoFreeCashFlowttm.values()[randPricetoFreeCashFlowttm]);
                                break;
                        case Epsgrowththisyear:
                                int randEpsgrowththisyear = rand.nextInt(Epsgrowththisyear.values().length);
                                featureList.add(Epsgrowththisyear.values()[randEpsgrowththisyear]);
                                break;
                        case Epsgrowthnextyear:
                                int randEpsgrowthnextyear = rand.nextInt(Epsgrowthnextyear.values().length);
                                featureList.add(Epsgrowthnextyear.values()[randEpsgrowthnextyear]);
                                break;
                        case Epsgrowthpast5years:
                                int randEpsgrowthpast5years = rand.nextInt(Epsgrowthpast5years.values().length);
                                featureList.add(Epsgrowthpast5years.values()[randEpsgrowthpast5years]);
                                break;
                        case Epsgrowthnext5years:
                                int randEpsgrowthnext5years = rand.nextInt(Epsgrowthnext5years.values().length);
                                featureList.add(Epsgrowthnext5years.values()[randEpsgrowthnext5years]);
                                break;
                        case SalesGrowthpast5years:
                                int randSalesGrowthpast5years = rand.nextInt(SalesGrowthpast5years.values().length);
                                featureList.add(SalesGrowthpast5years.values()[randSalesGrowthpast5years]);
                                break;
                        case Epsgrowthqtroverqtr:
                                int randEpsgrowthqtroverqtr = rand.nextInt(Epsgrowthqtroverqtr.values().length);
                                featureList.add(Epsgrowthqtroverqtr.values()[randEpsgrowthqtroverqtr]);
                                break;
                        case Salesgrowthqtroverqtr:
                                int randSalesgrowthqtroverqtr = rand.nextInt(Salesgrowthqtroverqtr.values().length);
                                featureList.add(Salesgrowthqtroverqtr.values()[randSalesgrowthqtroverqtr]);
                                break;
                        case DividendYield:
                                int randDividendYield = rand.nextInt(DividendYield.values().length);
                                featureList.add(DividendYield.values()[randDividendYield]);
                                break;
                        case ReturnonAssetsttm:
                                int randReturnonAssetsttm = rand.nextInt(ReturnonAssetsttm.values().length);
                                featureList.add(ReturnonAssetsttm.values()[randReturnonAssetsttm]);
                                break;
                        case ReturnonEquityttm:
                                int randReturnonEquityttm = rand.nextInt(ReturnonEquityttm.values().length);
                                featureList.add(ReturnonEquityttm.values()[randReturnonEquityttm]);
                                break;
                        case ReturnonInvestmentttm:
                                int randReturnonInvestmentttm = rand.nextInt(ReturnonInvestmentttm.values().length);
                                featureList.add(ReturnonInvestmentttm.values()[randReturnonInvestmentttm]);
                                break;
                        case CurrentRatiomrq:
                                int randCurrentRatiomrq = rand.nextInt(CurrentRatiomrq.values().length);
                                featureList.add(CurrentRatiomrq.values()[randCurrentRatiomrq]);
                                break;
                        case QuickRatiomrq:
                                int randQuickRatiomrq = rand.nextInt(QuickRatiomrq.values().length);
                                featureList.add(QuickRatiomrq.values()[randQuickRatiomrq]);
                                break;
                        case LongTermDebttoEquitymrq:
                                int randLongTermDebttoEquitymrq = rand.nextInt(LongTermDebttoEquitymrq.values().length);
                                featureList.add(LongTermDebttoEquitymrq.values()[randLongTermDebttoEquitymrq]);
                                break;
                        case TotalDebttoEquitymrq:
                                int randTotalDebttoEquitymrq = rand.nextInt(TotalDebttoEquitymrq.values().length);
                                featureList.add(TotalDebttoEquitymrq.values()[randTotalDebttoEquitymrq]);
                                break;
                        case GrossMarginttm:
                                int randGrossMarginttm = rand.nextInt(GrossMarginttm.values().length);
                                featureList.add(GrossMarginttm.values()[randGrossMarginttm]);
                                break;
                        case OperatingMarginttm:
                                int randOperatingMarginttm = rand.nextInt(OperatingMarginttm.values().length);
                                featureList.add(OperatingMarginttm.values()[randOperatingMarginttm]);
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
                        case I20DaySimpleMovingAverage:
                                int randI20DaySimpleMovingAverage = rand.nextInt(I20DaySimpleMovingAverage.values().length);
                                featureList.add(I20DaySimpleMovingAverage.values()[randI20DaySimpleMovingAverage]);
                                break;
                        case I50DaySimpleMovingAverage:
                                int randI50DaySimpleMovingAverage = rand.nextInt(I50DaySimpleMovingAverage.values().length);
                                featureList.add(I50DaySimpleMovingAverage.values()[randI50DaySimpleMovingAverage]);
                                break;
                        case I200DaySimpleMovingAverage:
                                int randI200DaySimpleMovingAverage = rand.nextInt(I200DaySimpleMovingAverage.values().length);
                                featureList.add(I200DaySimpleMovingAverage.values()[randI200DaySimpleMovingAverage]);
                                break;
                        case ChangefrompreviousClose:
                                int randChangefrompreviousClose = rand.nextInt(ChangefrompreviousClose.values().length);
                                featureList.add(ChangefrompreviousClose.values()[randChangefrompreviousClose]);
                                break;
                        case ChangefromOpen:
                                int randChangefromOpen = rand.nextInt(ChangefromOpen.values().length);
                                featureList.add(ChangefromOpen.values()[randChangefromOpen]);
                                break;
                        case I20DayHighLow:
                                int randI20DayHighLow = rand.nextInt(I20DayHighLow.values().length);
                                featureList.add(I20DayHighLow.values()[randI20DayHighLow]);
                                break;
                        case I50DayHighLow:
                                int randI50DayHighLow = rand.nextInt(I50DayHighLow.values().length);
                                featureList.add(I50DayHighLow.values()[randI50DayHighLow]);
                                break;
                        case I52WeekHighLow:
                                int randI52WeekHighLow = rand.nextInt(I52WeekHighLow.values().length);
                                featureList.add(I52WeekHighLow.values()[randI52WeekHighLow]);
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
                                case NASDAQ:
                                    ret += Suffix.EXCHANGE_NASDAQ;
                                    break;
                                case AMEX:
                                    ret += Suffix.EXCHANGE_AMEX;
                                    break;
                                case NYSE:
                                    ret += Suffix.EXCHANGE_NYSE;
                                    break;
                            }
                            break;
                        
                        /* old    
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
                            */
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