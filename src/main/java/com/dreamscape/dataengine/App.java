package com.dreamscape.dataengine;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.dao.ProspectDAO;
import com.dreamscape.dataengine.domain.dao.ProspectDAOHibernateImpl;
import com.dreamscape.dataengine.domain.managers.FinvizDomainManager;
import com.dreamscape.dataengine.processes.PopulateGoogleFinancialsData;
import com.dreamscape.dataengine.processes.CalculateScores;
import com.dreamscape.dataengine.processes.PopulateKeyStatisticsData;
import com.dreamscape.dataengine.processes.PortfolioCreator;
import dreamscape.analysisengine.UtilitySqlQueries;
import java.io.File;
import java.io.IOException;

public class App 
{
    @SuppressWarnings("CallToThreadDumpStack")
    public static void main( String[] args )
    {
        String process = null;
        try{
            String fileName;
            if(args.length < 2)
            {
                // Running with hard-coded values...
                fileName = "C:\\Users\\Jared\\Documents\\NetBeansProjects\\DataEngine\\src\\main\\resources\\Russell3000Companies6-15.txt";
                process = "calculateScores";
            }
            else // Running from command line
            {
                process = args[0];
                fileName = args[1];
            }
            switch(process)
            {   
                case "generateProspects":
                    FinvizDomainManager fdm = new FinvizDomainManager();
                    Prospect[] prospects = fdm.generateRandomProspects();
                    ProspectDAO dao = new ProspectDAOHibernateImpl();
                    dao.createProspects(prospects);
                    break;
                case "populateGoogleFinancials":
                    PopulateGoogleFinancialsData populateGoogleFinancials = new PopulateGoogleFinancialsData();
                    populateGoogleFinancials.populateAll(new File(fileName));
                    break;
                case "populateKeyStatistics":
                    PopulateKeyStatisticsData populateKeyStats = new PopulateKeyStatisticsData();
                    populateKeyStats.populateAll(new File(fileName));
                    break;
                case "calculateScores":
                    Long portfolioId = -1L; // Russell 3000 with data populated on 8/31/15
                    CalculateScores.calculateAllScores(UtilitySqlQueries.getSecuritiesByPortfolioId(portfolioId));
                    break;
                case "portfolioCreator":
                    PortfolioCreator.createPortfolio();
                    break;
                default:
                    System.err.println("Run parameter expected! Please specify the process you would like to run.");
            }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.err.println("Incorrect parameter format. Expected: [process name] [input file]");
            System.exit(1);
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
