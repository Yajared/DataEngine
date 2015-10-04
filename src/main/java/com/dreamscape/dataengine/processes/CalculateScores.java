package com.dreamscape.dataengine.processes;

import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.dao.SecurityDAO;
import com.dreamscape.dataengine.domain.dao.SecurityDAOHibernateImpl;
import com.dreamscape.utillibrary.parsers.CSVParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jared
 * Mostly code copied from Project Ivory's Ticker.java
 * TODO: Re-write almost all of security to be static
 */
public class CalculateScores {
    private static final double normalUpperBound = 10.0 / 3.0;
    
    public static void calculateAllScores(File fileInCSVFormat)
    {
        BufferedReader br = null;
        List<String> tickerList = null;
        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileInCSVFormat)));
            tickerList = CSVParser.convertToListOfString(br, false);
        }
        catch(FileNotFoundException e){
            System.err.println("File: " + fileInCSVFormat.getAbsolutePath() + " not found!");
            System.err.println(e.getMessage());
            System.exit(0);
        }
        catch(IOException e){
            System.err.println("IO Exception Encountered!");
            System.err.println(e.getMessage());
            System.exit(0);
        }
        SecurityDAO dao = new SecurityDAOHibernateImpl();
        for(String ticker : tickerList)
        {
            Security s = dao.retrieve(ticker);
            Double valueScore = calculateValueScore(s);
            Double assetScore = calculateAssetScore(s);
            
            List<String> columns = new ArrayList<String>();
            List<Object> scores = new ArrayList<Object>();
            
            columns.add("valueScore");
            scores.add(valueScore);
            columns.add("assetScore");
            scores.add(assetScore);
            
            dao.update(s.getTicker(), columns, scores);
        }
    }
    
    public static double calculateValueScore(Security security)
    {
        double ebitdaEv = 10.0;
        double priceToBook = 10.0;
        
        
        if (!String.valueOf(security.getEvToEBITDA()).equals("NaN"))
            ebitdaEv = security.getEvToEBITDA();
        
        if(!String.valueOf(security.getPriceBook()).equals("NaN"))
            priceToBook = security.getPriceBook();
        
        double valueScore = ( (1 - ( Math.abs(ebitdaEv / 10))) + (1 - (priceToBook / 10)) ) * 2;
            //System.out.println("EV/EBITDA: " + security.geteVToEBITDA() + " Price-to-Book: " + security.getPriceToBook() + 
            //        " EV/EBITDA Component: " + (1 - ( Math.abs(security.geteVToEBITDA() / 10))) + 
            //        " Price-to-Book Component: " + (1 - (security.getPriceToBook() / 10)));
            
            if ( valueScore > normalUpperBound )
                valueScore = fitToScale( valueScore );
            
            //System.out.println("Value Score: " + valueScore);
//            System.out.println();
//            System.out.println();            
            
            return valueScore;
    }
//    public double calculateEfficiencyScore()
//    {
//        double useEarnings = security.getEbitda();
//        double useRevenue = security.getRevenue();
//        
//        double opMarg = 0.0;
//        double profMarg = 0.0;
//        
//        double currentRatio = 1.0;
//        double roa = 0.0;
//        double roe = 0.0;
//        
//
//        if (String.valueOf(security.getRevenue()).equals("NaN"))
//        {
//            if(!String.valueOf(security.getAnnualRevenueThisYear()).equals("NaN") && security.getAnnualRevenueThisYear() != null)
//                useRevenue = getAccurateRevenue();
//            else
//                useRevenue = 1.0;
//        }
//        
//        if (String.valueOf(security.getEbitda()).equals("NaN"))
//        {
//            if(!String.valueOf(security.getAnnualEarningsThisYear()).equals("NaN") && security.getAnnualEarningsThisYear() != null)
//                useEarnings = getAccurateEarnings();
//            else
//                useEarnings = useRevenue;
//        }
//        if(useEarnings > useRevenue)
//            useEarnings = useRevenue * .125;
//        
//        if (annualOperatingThisYear != null)
//        {
//            //System.out.println("Operating income: " + security.annualOperatingThisYear);
//            if ((! String.valueOf(quarterlyOperatingThisQuarter).equals("NaN")) && (! String.valueOf(quarterlyOperatingLastQuarter).equals("NaN")) &&
//                    (! String.valueOf(quarterlyOperatingTwoBack).equals("NaN")) && (! String.valueOf(quarterlyOperatingThreeBack).equals("NaN")) && useRevenue > 1.0)
//                opMarg = (quarterlyOperatingThisQuarter + quarterlyOperatingLastQuarter + quarterlyOperatingTwoBack + quarterlyOperatingThreeBack) / Math.abs(useRevenue);          
//            else if (! String.valueOf(annualOperatingThisYear).equals("NaN") && useRevenue > 1.0)
//                opMarg = (annualOperatingThisYear / Math.abs(useRevenue));
//            else if (! String.valueOf(security.getOperatingMargin()).equals("NaN"))
//                    opMarg = security.getOperatingMargin();
//            else
//                opMarg = .15;
//        }
//        else
//        {
//            if (! String.valueOf(security.getOperatingMargin()).equals("NaN"))
//            {
//                opMarg = security.getOperatingMargin();
//            }
//            else
//                opMarg = .15;
//        }
//        
//        if (annualIncomeThisYear != null)
//        {
//            //System.out.println("Annual Income: " + annualIncomeThisYear);
//            if ((! String.valueOf(quarterlyIncomeThisQuarter).equals("NaN")) && (! String.valueOf(quarterlyIncomeLastQuarter).equals("NaN")) &&
//                    (! String.valueOf(quarterlyIncomeTwoBack).equals("NaN")) && (! String.valueOf(quarterlyIncomeThreeBack).equals("NaN")) && useRevenue > 1.0)
//                profMarg = (quarterlyIncomeThisQuarter + quarterlyIncomeLastQuarter + quarterlyIncomeTwoBack + quarterlyIncomeThreeBack) / Math.abs(useRevenue);          
//            else if (! String.valueOf(annualIncomeThisYear).equals("NaN") && useRevenue > 1.0)
//                profMarg = (annualIncomeThisYear / Math.abs(useRevenue));
//            else if (! String.valueOf(security.getProfitMargin()).equals("NaN"))
//            {
//                    profMarg = security.getProfitMargin();
//            }
//            else
//                profMarg = .1;
//        }
//        else
//        {
//            if (! String.valueOf(security.getProfitMargin()).equals("NaN"))
//            {
//                if(security.getProfitMargin() < 1)
//                    profMarg = security.getProfitMargin();
//            }
//            else
//                profMarg = .1;
//        }
//        
//        if (opMarg > 1)
//            opMarg = .15;
//        if (profMarg > 1)
//            profMarg = .1;
//        
//        if (! String.valueOf(security.getCurrentRatio()).equals("NaN"))
//            currentRatio = security.getCurrentRatio();
//        
//        if (! String.valueOf(security.getReturnOnAssets()).equals("NaN"))
//            roa = security.getReturnOnAssets();
//        
//        if (! String.valueOf(security.getReturnOnEquity()).equals("NaN"))
//            roe = security.getReturnOnEquity();
//        
//            //System.out.println("Profit Margin: " + profMarg);
//            //System.out.println("Operating Margin: " + opMarg);
//            //System.out.println("Ebitda Margin: " + (useEarnings / useRevenue));
//            //System.out.println("Return On Equity Component: " + roe*8);
//            //System.out.println("Return On Assets Component: " + roa);
//            //System.out.println();
//            double efficiencyScore = profMarg * 1.5 +  opMarg  + (useEarnings / useRevenue) + 
//                    roe * 8 + roa;
//            
//            //System.out.println("Efficiency Score: " + efficiencyScore);
//
//            if ( efficiencyScore > Ticker.normalUpperBound )
//                efficiencyScore = Ticker.fitToScale( efficiencyScore );
//
//            //System.out.println("Efficiency Score: " + efficiencyScore);
//            
//            //System.out.println();
//            //System.out.println();
//            
//            return efficiencyScore;
//        
//    }
    public static double calculateAssetScore(Security security)
    {
            return ( ( ( security.getTotalCash() - security.getTotalDebt() ) /  security.getMarketCap() ) + ( security.getCurrentRatio() / 10 ));
    }
//    public double calculateGrowthScore()
//    {
////            System.out.println("Revenue Growth: " + revenueGrowth + " Revenue: " + revenue + 
////                    " Rev G Component: " + ( ( revenueGrowth * revenue * 2 ) / security.getMarketCap() ) );
////            System.out.println("Earnings Growth: " + earningsGrowth + " Ebitda: " + ebitda + 
////                    " Earn G Component: " + ( ( earningsGrowth * ebitda * 3 ) / security.getMarketCap() ) );
////            System.out.println();
//            
//            //double growthScore = ( ( ( revenueGrowth * revenue * 2 ) + ( earningsGrowth * ebitda * 3 ) ) / security.getMarketCap() );
//        Double _revenueGrowth = 0.0;
//        Double _earningsGrowth = 0.0;
//        
//        if(!String.valueOf(revenueGrowth).equals("NaN"))
//            _revenueGrowth = revenueGrowth;
//        else
//            _revenueGrowth = -.01;
//        
//        if(!String.valueOf(earningsGrowth).equals("NaN"))
//            _earningsGrowth = earningsGrowth;
//        else
//            _earningsGrowth = -.01;
//        
//        double growthScore = _revenueGrowth * .4 + _earningsGrowth * .6;
//            if ( growthScore > Ticker.normalUpperBound )
//                growthScore = Ticker.fitToScale( growthScore );
//            
////            System.out.println("Growth Score: " + growthScore);
////            System.out.println();
//                                
//            return growthScore;
//    }
//    public double calculateCombinedGrowthScore()
//    {
//        double _revenueGrowth = security.calculateRevenueGrowth();
//        double _earningsGrowth = security.calculateEarningsGrowth();
//        
////            System.out.println("Revenue Growth: " + security.getRevenueGrowth() + " Revenue: " + security.getRevenue() + 
////                    " Rev G Component: " + ( ( security.getRevenueGrowth() * security.getRevenue() * 2 ) / security.getMarketCap() ) );
////            System.out.println("Earnings Growth: " + security.getEarningsGrowth() + " Ebitda: " + security.getRevenue() + 
////                    " Earn G Component: " + ( ( security.getEarningsGrowth() * security.getEbitda() * 3 ) / security.getMarketCap() ) );
////            System.out.println();
//
//            if(String.valueOf(_earningsGrowth).equals("NaN"))
//                _earningsGrowth = security.getEarningsGrowth();
//            
//            if(String.valueOf(_revenueGrowth).equals("NaN"))
//                _revenueGrowth = security.getRevenueGrowth();
//            
//            //System.out.println("Revenue Growth: " + revenueGrowth + " useRevenue: " + useRevenue + " Revenue: " + security.getRevenue() + " Earnings Growth: " + earningsGrowth + " useEarnings: " + useEarnings + " Earnings: " + security.getEbitda() + " Market Cap: " + marketCap);
//            //double growthScore = ( ( ( revenueGrowth * useRevenue * 2 ) + ( earningsGrowth * useEarnings * 3 ) ) / security.getMarketCap() );
//            
//            double growthScore = _revenueGrowth * .6 + _earningsGrowth * .4;
//            
//            if ( growthScore > Ticker.normalUpperBound )
//                growthScore = Ticker.fitToScale( growthScore );
//            
//            //System.out.println("Growth Score: " + growthScore);
//            //System.out.println();
//            
//            growthScore *= 2; // Double weighting for combined growth score
//                                
//            return growthScore;
//    }
//    private double calculateEarningsGrowth()
//    {
//        double annualEarningsGrowth = 0.0;
//        double quarterlyEarningsGrowth = 0.0;
//        
//        //System.out.println("Annual Earnings security year: " + security.annualEarningsThisYear + " Annual Earnings last year: " + security.getAnnualEarningsLastYear());
//        //System.out.println("Annual Earnings two years back: " + security.annualEarningsTwoBack);
//        
//        if(!String.valueOf(security.annualEarningsThisYear).equals("NaN") && !String.valueOf(security.annualEarningsLastYear).equals("NaN") && !String.valueOf(security.annualEarningsTwoBack).equals("NaN"))
//        {
//            if (annualEarningsLastYear < annualEarningsTwoBack)
//                annualEarningsGrowth = (((annualEarningsThisYear - annualEarningsTwoBack) / Math.abs(annualEarningsTwoBack)) * .65) + (((annualEarningsLastYear - annualEarningsTwoBack)/Math.abs(annualEarningsTwoBack)) * .35);
//            else
//                annualEarningsGrowth = (((annualEarningsThisYear - annualEarningsLastYear) / Math.abs(annualEarningsLastYear)) * .65) + (((annualEarningsLastYear - annualEarningsTwoBack)/Math.abs(annualEarningsTwoBack)) * .35);
//        }
//        else if(!String.valueOf(security.annualEarningsThisYear).equals("NaN") && !String.valueOf(security.annualEarningsLastYear).equals("NaN"))
//            annualEarningsGrowth = (annualEarningsThisYear - annualEarningsLastYear) / Math.abs(annualEarningsLastYear);
//        
//        //System.out.println("Annual Earnings Growth: " + annualEarningsGrowth);
//                
//        //System.out.println("Quarterly Earnings This Quarter: " + quarterlyEarningsThisQuarter);
//        //System.out.println("Last Quarter: " + quarterlyEarningsLastQuarter + " Two Back: " + quarterlyEarningsTwoBack + " Three Back: " + quarterlyEarningsThreeBack);
//        
//        if(!String.valueOf(security.quarterlyEarningsLastQuarter).equals("NaN") && !String.valueOf(security.quarterlyEarningsThisQuarter).equals("NaN") && !String.valueOf(security.quarterlyEarningsTwoBack).equals("NaN") && !String.valueOf(security.quarterlyEarningsThreeBack).equals("NaN"))
//            quarterlyEarningsGrowth = (((quarterlyEarningsThisQuarter - quarterlyEarningsLastQuarter) / Math.abs(quarterlyEarningsLastQuarter)) *.25 ) +
//                    (((quarterlyEarningsLastQuarter - quarterlyEarningsTwoBack) / Math.abs(quarterlyEarningsTwoBack)) * .15) +
//                    (((quarterlyEarningsTwoBack - quarterlyEarningsThreeBack) / Math.abs(quarterlyEarningsThreeBack)) * .15) +
//                    (((quarterlyEarningsThisQuarter - quarterlyEarningsTwoBack) / Math.abs(quarterlyEarningsTwoBack)) *.22 ) +
//                    (((quarterlyEarningsThisQuarter - quarterlyEarningsThreeBack) / Math.abs(quarterlyEarningsThreeBack)) *.23 );
//        else if(!String.valueOf(security.quarterlyEarningsLastQuarter).equals("NaN") && !String.valueOf(security.quarterlyEarningsThisQuarter).equals("NaN") && !String.valueOf(security.quarterlyEarningsTwoBack).equals("NaN"))
//            quarterlyEarningsGrowth = (((quarterlyEarningsThisQuarter - quarterlyEarningsLastQuarter) / Math.abs(quarterlyEarningsLastQuarter)) *.5 ) +
//                    (((quarterlyEarningsLastQuarter - quarterlyEarningsTwoBack) / Math.abs(quarterlyEarningsTwoBack)) * .2) +
//                    (((quarterlyEarningsThisQuarter - quarterlyEarningsTwoBack) / Math.abs(quarterlyEarningsTwoBack)) * .3);
//        else if(!String.valueOf(security.quarterlyEarningsLastQuarter).equals("NaN") && !String.valueOf(security.quarterlyEarningsThisQuarter).equals("NaN"))
//            quarterlyEarningsGrowth = (quarterlyEarningsThisQuarter - quarterlyEarningsLastQuarter) / Math.abs(quarterlyEarningsLastQuarter);
//        else
//            return annualEarningsGrowth;
//            
//        //System.out.println("Quarterly Earnings Growth by quarter: " + (((quarterlyEarningsThisQuarter - quarterlyEarningsLastQuarter) / Math.abs(quarterlyEarningsLastQuarter)) *.25 ) + " " +
//        //        (((quarterlyEarningsLastQuarter - quarterlyEarningsTwoBack) / Math.abs(quarterlyEarningsTwoBack)) * .15) + " " +
//        //        (((quarterlyEarningsTwoBack - quarterlyEarningsThreeBack) / Math.abs(quarterlyEarningsThreeBack)) * .15) + " " +
//        //        (((quarterlyEarningsThisQuarter - quarterlyEarningsTwoBack) / Math.abs(quarterlyEarningsTwoBack)) * .22) + " " +
//        //        (((quarterlyEarningsThisQuarter - quarterlyEarningsThreeBack) / Math.abs(quarterlyEarningsThreeBack)) *.23));
//        //System.out.println();
//        
//        //System.out.println("Earn G: " + ((annualEarningsGrowth * .65) + (quarterlyEarningsGrowth * .35)));
//       
//        return (annualEarningsGrowth * .65) + (quarterlyEarningsGrowth * .35);
//    }
//    
//        private double calculateRevenueGrowth()
//    {
//        double annualRevenueGrowth = 0.0;
//        double quarterlyRevenueGrowth = 0.0;
//        
//        //System.out.println("Annual Revenue security year: " + security.annualRevenueThisYear + " Annual Revenue last year: " + security.getAnnualRevenueLastYear());
//        //System.out.println("Annual Revenue two years back: " + security.annualRevenueTwoBack);
//        
//        if( (!String.valueOf(security.annualRevenueLastYear).equals("NaN")) && (!String.valueOf(security.annualRevenueThisYear).equals("NaN")) && (!String.valueOf(security.annualRevenueTwoBack).equals("NaN")))
//        {
//            if (annualRevenueLastYear < annualRevenueTwoBack)
//                annualRevenueGrowth = (((annualRevenueThisYear - annualRevenueTwoBack) / Math.abs(annualRevenueTwoBack)) * .65) + (((annualRevenueLastYear - annualRevenueTwoBack)/Math.abs(annualRevenueTwoBack)) * .35);
//            else
//                annualRevenueGrowth = (((annualRevenueThisYear - annualRevenueLastYear) / Math.abs(annualRevenueLastYear)) * .65) + (((annualRevenueLastYear - annualRevenueTwoBack)/Math.abs(annualRevenueTwoBack)) * .35);
//        }
//        else if((!String.valueOf(security.annualRevenueLastYear).equals("NaN")) && (!String.valueOf(security.annualRevenueThisYear).equals("NaN")))
//            annualRevenueGrowth = (annualRevenueThisYear - annualRevenueLastYear) / Math.abs(annualRevenueLastYear);
//        
//        //System.out.println("Annual Revenue Growth: " + annualRevenueGrowth);
//        
//        if( (!String.valueOf(security.quarterlyRevenueLastQuarter).equals("NaN")) && (!String.valueOf(security.quarterlyRevenueThisQuarter).equals("NaN")) && (!String.valueOf(security.quarterlyRevenueTwoBack).equals("NaN")) && (!String.valueOf(security.quarterlyRevenueThreeBack).equals("NaN")))
//        {quarterlyRevenueGrowth = (((quarterlyRevenueThisQuarter - quarterlyRevenueLastQuarter) / Math.abs(quarterlyRevenueLastQuarter) *.25 )) +
//                    (((quarterlyRevenueLastQuarter - quarterlyRevenueTwoBack) / Math.abs(quarterlyRevenueTwoBack) * .10)) +
//                    (((quarterlyRevenueTwoBack - quarterlyRevenueThreeBack) / Math.abs(quarterlyRevenueThreeBack) * .10)) +
//                    (((quarterlyRevenueThisQuarter - quarterlyRevenueThreeBack) / Math.abs(quarterlyRevenueThreeBack) * .23)) + 
//                    (((quarterlyRevenueThisQuarter - quarterlyRevenueTwoBack) / Math.abs(quarterlyRevenueTwoBack) * .22));
//        //System.out.println("All quarters here.");
//        }
//        else if((!String.valueOf(security.quarterlyRevenueLastQuarter).equals("NaN")) && (!String.valueOf(security.quarterlyRevenueThisQuarter).equals("NaN")) && (!String.valueOf(security.quarterlyRevenueTwoBack).equals("NaN")))
//        {    quarterlyRevenueGrowth = (((quarterlyRevenueThisQuarter - quarterlyRevenueLastQuarter) / Math.abs(quarterlyRevenueLastQuarter)) *.5) +
//                    (((quarterlyRevenueLastQuarter - quarterlyRevenueTwoBack) / Math.abs(quarterlyRevenueTwoBack)) * .2) + 
//                    (((quarterlyRevenueThisQuarter - quarterlyRevenueTwoBack) / Math.abs(quarterlyRevenueTwoBack)) * .3);
//        
//        //System.out.println("Last three quarters here.");
//        }
//        else if((!String.valueOf(security.quarterlyRevenueLastQuarter).equals("NaN")) && (!String.valueOf(security.quarterlyRevenueThisQuarter).equals("NaN")))
//        {    quarterlyRevenueGrowth = (quarterlyRevenueThisQuarter - quarterlyRevenueLastQuarter) / Math.abs(quarterlyRevenueLastQuarter);
//        //System.out.println("Last two quarters here.");
//        }
//        else
//            return annualRevenueGrowth;
//        
//        //System.out.println("Quarterly Revenue Last Quarter: " + quarterlyRevenueLastQuarter);
//        //System.out.println("Quarterly Revenue This Quarter: " + quarterlyRevenueThisQuarter);
//        //System.out.println("Quarterly Revenue Two Back: " + quarterlyRevenueTwoBack);
//        //System.out.println("Quarterly Revenue Three Back: " + quarterlyRevenueThreeBack);
//
//        //System.out.println("Quarterly Revenue Growth to return: " + quarterlyRevenueGrowth);
//        //System.out.println();
//        
//        //System.out.println("Rev G: " + ((annualRevenueGrowth * .65) + (quarterlyRevenueGrowth * .35)));
//        return (annualRevenueGrowth * .65) + (quarterlyRevenueGrowth * .35);
//    }
//    
//    public double calculateCompositeScore()
//    {
//        return ( security.calculateAssetScore() + security.calculateEfficiencyScore() + security.calculateGrowthScore() + (security.calculateValueScore()) );
//    }
//    
    private static double fitToScale(double num)
    {
        return (Math.log10(num)/Math.log10(normalUpperBound)) + normalUpperBound;
    }
    
    private static boolean isANumber(Double value)
    {
        return !String.valueOf(value).equals("NaN");
    }
//    private Double getAccurateRevenue(){
//        Double useRevenue = 1.0;
//        if(String.valueOf(security.getRevenue()).equals("NaN"))
//            {
//                if(String.valueOf(security.getAnnualRevenueThisYear()).equals("NaN"))
//                    useRevenue = security.getAnnualRevenueThisYear();
//            }
//            else
//            {
//                if(!(String.valueOf(security.getQuarterlyRevenueThisQuarter()).equals("NaN")) && !(String.valueOf(security.getQuarterlyRevenueLastQuarter()).equals("NaN"))
//                        && !(String.valueOf(security.getQuarterlyRevenueTwoBack()).equals("NaN")) && !(String.valueOf(security.getQuarterlyRevenueThreeBack()).equals("NaN"))
//                        && (security.getQuarterlyRevenueThisQuarter() + security.getQuarterlyRevenueLastQuarter() + security.getQuarterlyRevenueTwoBack() + security.getQuarterlyRevenueThreeBack()) < security.getAnnualRevenueThisYear())
//                {
//                    useRevenue = security.getQuarterlyRevenueThisQuarter() + security.getQuarterlyRevenueLastQuarter() + security.getQuarterlyRevenueTwoBack() + security.getQuarterlyRevenueThreeBack();
//                }
//                else
//                {
//                    useRevenue = security.getRevenue();
//                }
//            }
//        return useRevenue;
//    }
//    private Double getAccurateEarnings(){
//        
//        Double useEarnings = 1.0;
//            if(String.valueOf(security.getEbitda()).equals("NaN"))
//            {
//                if(String.valueOf(security.getAnnualEarningsThisYear()).equals("NaN"))
//                    useEarnings = security.getAnnualEarningsThisYear();
//            }
//            else
//            {
//                if(!(String.valueOf(security.getQuarterlyEarningsThisQuarter()).equals("NaN")) && !(String.valueOf(security.getQuarterlyEarningsLastQuarter()).equals("NaN"))
//                        && !(String.valueOf(security.getQuarterlyEarningsTwoBack()).equals("NaN")) && !(String.valueOf(security.getQuarterlyEarningsThreeBack()).equals("NaN"))
//                        && (security.getQuarterlyEarningsThisQuarter() + security.getQuarterlyEarningsLastQuarter() + security.getQuarterlyEarningsTwoBack() + security.getQuarterlyEarningsThreeBack()) < security.getAnnualEarningsThisYear())
//                {
//                    useEarnings = security.getQuarterlyEarningsThisQuarter() + security.getQuarterlyEarningsLastQuarter() + security.getQuarterlyEarningsTwoBack() + security.getQuarterlyEarningsThreeBack();
//                }
//                else
//                {
//                    useEarnings = security.getEbitda();
//                }
//            }
//                    
//            return useEarnings;
//    }
}
