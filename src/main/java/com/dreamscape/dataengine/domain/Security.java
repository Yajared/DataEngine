/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

//import com.dreamscape.tradingdayinformation.YahooFinanceHistoricalPriceConnectionManager;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.joda.time.DateTime;



/**
 *
 * @author Jared
 */
@Entity
@Table(name="_security")
public class Security {
    @Id
    private String ticker;
    // private ArrayList<Double> historicalPrices; // not implemented yet
    private Double marketCap;
    private Double enterpriseValue;
    private Double trailingPE;
    private Double forwardPE;
    private Double pegRatio;
    private Double priceSales;
    private Double priceBook;
    private Double eVToRevenue;
    private Double eVToEBITDA;
    private Double assetScore;
    private Double valueScore;
    private Double profitMargin;
    private Double operatingMargin;
    private Double returnOnAssets;
    private Double returnOnEquity;
    private Double revenue;
    private Double revenuePerShare;
    private Double qtrRevenueGrowth;
    private Double grossProfit;
    private Double ebitda;
    private Double netIncomeAvlToCommonShares;
    private Double dilutedEPS;
    private Double qtrEarningsGrowth;
    private Double totalCash;
    private Double totalCashPerShare;
    private Double totalDebt;
    private Double totalDebtToEquity;
    private Double currentRatio;
    private Double bookValuePerShare;
    private Double operatingCashFlow;
    private Double leveredCashFlow;
    private Double beta;
    private Double fiftyTwoWeekChange;
    private Double fiftyTwoWeekHigh;
    private Double fiftyTwoWeekLow;
    private Double fiftyDayMovingAvg;
    private Double twoHundredDayMovingAvg;
    private Double avgVolThreeMon;
    private Double avgVolTenDay;
    private Double sharesOutstanding;
    private Double floatShares;
    private Double percentHeldByInsiders;
    private Double percentHeldByInstitutions;
    private Double sharesShort;
    private Double shortRatio;
    private Double shortPercentOfFloat;
    private Double priorMonthSharesShort;
    private Double forwardAnnualDividendRate;
    private Double forwardAnnualDividendYield;
    private Double fiveYearAvgDividendYield;
    private Double payoutRatio;
    @Transient // TODO: Remove these and actually save the dates
    private DateTime dividendDate;
    @Transient
    private DateTime exDividendDate;
    private Double lastSplitFactor;
    @Transient
    private DateTime lastSplitDate;
    
    
    public Security(){
        
    }
    
//    public ArrayList<Double> getHistoricalPrices(){
//        return this.historicalPrices;
//    }
//    
//    public void setHistoricalPrices(ArrayList<Double> historicalPrices){
//        this.historicalPrices = historicalPrices;
//    }
//    
//    public void populateHistoricalPrices(int daysOfPriceData){
//        DateTime from = DateTime.now().minusDays(daysOfPriceData);
//        YahooFinanceHistoricalPriceConnectionManager connection = new YahooFinanceHistoricalPriceConnectionManager();
//        
//        this.historicalPrices = connection.downloadHistoricalPrices(this.getTicker(), from);
//    }
    
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public DateTime getLastSplitDate() {
        return lastSplitDate;
    }

    public void setLastSplitDate(DateTime lastSplitDate) {
        this.lastSplitDate = lastSplitDate;
    }

    public Double getEnterpriseValue() {
        return enterpriseValue;
    }

    public void setEnterpriseValue(Double enterpriseValue) {
        this.enterpriseValue = enterpriseValue;
    }

    public Double getTrailingPE() {
        return trailingPE;
    }

    public void setTrailingPE(Double trailingPE) {
        this.trailingPE = trailingPE;
    }

    public Double getForwardPE() {
        return forwardPE;
    }

    public void setForwardPE(Double forwardPE) {
        this.forwardPE = forwardPE;
    }

    public Double getPegRatio() {
        return pegRatio;
    }

    public void setPegRatio(Double pegRatio) {
        this.pegRatio = pegRatio;
    }

    public Double getPriceSales() {
        return priceSales;
    }

    public void setPriceSales(Double priceSales) {
        this.priceSales = priceSales;
    }

    public Double getPriceBook() {
        return priceBook;
    }

    public void setPriceBook(Double priceBook) {
        this.priceBook = priceBook;
    }

    public Double geteVToRevenue() {
        return eVToRevenue;
    }

    public void seteVToRevenue(Double eVToRevenue) {
        this.eVToRevenue = eVToRevenue;
    }

    public Double geteVToEBITDA() {
        return eVToEBITDA;
    }

    public void seteVToEBITDA(Double eVToEBITDA) {
        this.eVToEBITDA = eVToEBITDA;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getOperatingMargin() {
        return operatingMargin;
    }

    public void setOperatingMargin(Double operatingMargin) {
        this.operatingMargin = operatingMargin;
    }

    public Double getReturnOnAssets() {
        return returnOnAssets;
    }

    public void setReturnOnAssets(Double returnOnAssets) {
        this.returnOnAssets = returnOnAssets;
    }

    public Double getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(Double returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getRevenuePerShare() {
        return revenuePerShare;
    }

    public void setRevenuePerShare(Double revenuePerShare) {
        this.revenuePerShare = revenuePerShare;
    }

    public Double getQtrRevenueGrowth() {
        return qtrRevenueGrowth;
    }

    public void setQtrRevenueGrowth(Double qtrRevenueGrowth) {
        this.qtrRevenueGrowth = qtrRevenueGrowth;
    }

    public Double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(Double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public Double getEbitda() {
        return ebitda;
    }

    public void setEbitda(Double ebitda) {
        this.ebitda = ebitda;
    }

    public Double getNetIncomeAvlToCommonShares() {
        return netIncomeAvlToCommonShares;
    }

    public void setNetIncomeAvlToCommonShares(Double netIncomeAvlToCommonShares) {
        this.netIncomeAvlToCommonShares = netIncomeAvlToCommonShares;
    }

    public Double getDilutedEPS() {
        return dilutedEPS;
    }

    public void setDilutedEPS(Double dilutedEPS) {
        this.dilutedEPS = dilutedEPS;
    }

    public Double getQtrEarningsGrowth() {
        return qtrEarningsGrowth;
    }

    public void setQtrEarningsGrowth(Double qtrEarningsGrowth) {
        this.qtrEarningsGrowth = qtrEarningsGrowth;
    }

    public Double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Double totalCash) {
        this.totalCash = totalCash;
    }

    public Double getTotalCashPerShare() {
        return totalCashPerShare;
    }

    public void setTotalCashPerShare(Double totalCashPerShare) {
        this.totalCashPerShare = totalCashPerShare;
    }

    public Double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public Double getTotalDebtToEquity() {
        return totalDebtToEquity;
    }

    public Double getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(Double currentRatio) {
        this.currentRatio = currentRatio;
    }
    
    public void setTotalDebtToEquity(Double totalDebtToEquity) {
        this.totalDebtToEquity = totalDebtToEquity;
    }

    public Double getBookValuePerShare() {
        return bookValuePerShare;
    }

    public void setBookValuePerShare(Double bookValuePerShare) {
        this.bookValuePerShare = bookValuePerShare;
    }

    public Double getOperatingCashFlow() {
        return operatingCashFlow;
    }

    public void setOperatingCashFlow(Double operatingCashFlow) {
        this.operatingCashFlow = operatingCashFlow;
    }

    public Double getLeveredCashFlow() {
        return leveredCashFlow;
    }

    public void setLeveredCashFlow(Double leveredCashFlow) {
        this.leveredCashFlow = leveredCashFlow;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getFiftyTwoWeekChange() {
        return fiftyTwoWeekChange;
    }

    public void setFiftyTwoWeekChange(Double fiftyTwoWeekChange) {
        this.fiftyTwoWeekChange = fiftyTwoWeekChange;
    }

    public Double getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(Double fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public Double getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(Double fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public Double getFiftyDayMovingAvg() {
        return fiftyDayMovingAvg;
    }

    public void setFiftyDayMovingAvg(Double fiftyDayMovingAvg) {
        this.fiftyDayMovingAvg = fiftyDayMovingAvg;
    }

    public Double getTwoHundredDayMovingAvg() {
        return twoHundredDayMovingAvg;
    }

    public void setTwoHundredDayMovingAvg(Double twoHundredDayMovingAvg) {
        this.twoHundredDayMovingAvg = twoHundredDayMovingAvg;
    }

    public Double getAvgVolThreeMon() {
        return avgVolThreeMon;
    }

    public void setAvgVolThreeMon(Double avgVolThreeMon) {
        this.avgVolThreeMon = avgVolThreeMon;
    }

    public Double getAvgVolTenDay() {
        return avgVolTenDay;
    }

    public void setAvgVolTenDay(Double avgVolTenDay) {
        this.avgVolTenDay = avgVolTenDay;
    }

    public Double getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(Double sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public Double getFloatShares() {
        return floatShares;
    }

    public void setFloatShares(Double floatShares) {
        this.floatShares = floatShares;
    }

    public Double getPercentHeldByInsiders() {
        return percentHeldByInsiders;
    }

    public void setPercentHeldByInsiders(Double percentHeldByInsiders) {
        this.percentHeldByInsiders = percentHeldByInsiders;
    }

    public Double getPercentHeldByInstitutions() {
        return percentHeldByInstitutions;
    }

    public void setPercentHeldByInstitutions(Double percentHeldByInstitutions) {
        this.percentHeldByInstitutions = percentHeldByInstitutions;
    }

    public Double getSharesShort() {
        return sharesShort;
    }

    public void setSharesShort(Double sharesShort) {
        this.sharesShort = sharesShort;
    }

    public Double getShortRatio() {
        return shortRatio;
    }

    public void setShortRatio(Double shortRatio) {
        this.shortRatio = shortRatio;
    }

    public Double getShortPercentOfFloat() {
        return shortPercentOfFloat;
    }

    public void setShortPercentOfFloat(Double shortPercentOfFloat) {
        this.shortPercentOfFloat = shortPercentOfFloat;
    }

    public Double getPriorMonthSharesShort() {
        return priorMonthSharesShort;
    }

    public void setPriorMonthSharesShort(Double priorMonthSharesShort) {
        this.priorMonthSharesShort = priorMonthSharesShort;
    }

    public Double getForwardAnnualDividendRate() {
        return forwardAnnualDividendRate;
    }

    public void setForwardAnnualDividendRate(Double forwardAnnualDividendRate) {
        this.forwardAnnualDividendRate = forwardAnnualDividendRate;
    }

    public Double getForwardAnnualDividendYield() {
        return forwardAnnualDividendYield;
    }

    public void setForwardAnnualDividendYield(Double forwardAnnualDividendYield) {
        this.forwardAnnualDividendYield = forwardAnnualDividendYield;
    }

    public Double getFiveYearAvgDividendYield() {
        return fiveYearAvgDividendYield;
    }

    public void setFiveYearAvgDividendYield(Double fiveYearAvgDividendYield) {
        this.fiveYearAvgDividendYield = fiveYearAvgDividendYield;
    }

    public Double getPayoutRatio() {
        return payoutRatio;
    }

    public void setPayoutRatio(Double payoutRatio) {
        this.payoutRatio = payoutRatio;
    }

    public DateTime getDividendDate() {
        return dividendDate;
    }

    public void setDividendDate(DateTime dividendDate) {
        this.dividendDate = dividendDate;
    }

    public DateTime getExDividendDate() {
        return exDividendDate;
    }

    public void setExDividendDate(DateTime exDividendDate) {
        this.exDividendDate = exDividendDate;
    }

    public Double getLastSplitFactor() {
        return lastSplitFactor;
    }

    public void setLastSplitFactor(Double lastSplitFactor) {
        this.lastSplitFactor = lastSplitFactor;
    }

    public Double getAssetScore() {
        return assetScore;
    }

    public void setAssetScore(Double assetScore) {
        this.assetScore = assetScore;
    }

    public Double getValueScore() {
        return valueScore;
    }

    public void setValueScore(Double valueScore) {
        this.valueScore = valueScore;
    }
    
}
