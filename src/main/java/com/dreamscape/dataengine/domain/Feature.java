/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.domain;

import javax.persistence.Transient;
import org.joda.time.DateTime;

/**
 *
 * @author Jared
 */
public enum Feature {
    marketCap, enterpriseValue, trailingPE, forwardPE, pegRatio, priceSales, priceBook, evToRevenue, 
    evToEBITDA, assetScore, valueScore, profitMargin, operatingMargin, returnOnAssets, returnOnEquity, 
    revenue, revenuePerShare, qtrRevenueGrowth, grossProfit, ebitda, netIncomeAvlToCommonShares, dilutedEPS, 
    qtrEarningsGrowth, totalCash, totalCashPerShare, totalDebt, totalDebtToEquity, currentRatio, 
    bookValuePerShare, operatingCashFlow, leveredCashFlow, beta, fiftyTwoWeekChange, fiftyTwoWeekHigh, 
    fiftyTwoWeekLow, fiftyDayMovingAvg, twoHundredDayMovingAvg, avgVolThreeMon, avgVolTenDay, sharesOutstanding, 
    floatShares, percentHeldByInsiders, percentHeldByInstitutions, sharesShort, shortRatio, shortPercentOfFloat, 
    priorMonthSharesShort, forwardAnnualDividendRate, forwardAnnualDividendYield, fiveYearAvgDividendYield, 
    payoutRatio, lastSplitFactor, returnOnInvestedCapital, magicFormulaRatio, qtrOverQtrEarningsGrowth,
    lastTwoQtrEarningsGrowth, lastNineMonthsEarningsGrowth, qtrVsLastYearEarningsGrowth, compositeEarningsGrowth
}
