package com.dreamscape.dataengine;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.dao.ProspectDAO;
import com.dreamscape.dataengine.domain.dao.ProspectDAOHibernateImpl;
import com.dreamscape.dataengine.domain.managers.FinvizDomainManager;
import com.dreamscape.dataengine.processes.PopulateGoogleFinancialsData;
import com.dreamscape.dataengine.processes.CalculateScores;
import com.dreamscape.dataengine.processes.PopulateKeyStatisticsData;
import java.io.File;
import java.io.IOException;

public class App 
{
    @SuppressWarnings("CallToThreadDumpStack")
    public static void main( String[] args )
    {
        try{
            String fileName = null;
            if(args[0].equals("generateProspects"))
            {
                FinvizDomainManager fdm = new FinvizDomainManager();
                Prospect[] prospects = fdm.generateRandomProspects();
                ProspectDAO dao = new ProspectDAOHibernateImpl();
                dao.createProspects(prospects);
            }
            else if(args.length < 2)
            {
                System.err.println("File name containing tickers expected! Exiting...");
                System.exit(1);
            }
            else
                fileName = args[1];
            switch(args[0])
            {   
                case "populateGoogleFinancials":
                    PopulateGoogleFinancialsData populateGoogleFinancials = new PopulateGoogleFinancialsData();
                    populateGoogleFinancials.populateAll(new File(fileName));
                    break;
                case "populateKeyStatistics":
                    PopulateKeyStatisticsData populateKeyStats = new PopulateKeyStatisticsData();
                    populateKeyStats.populateAll(new File(fileName));
                    break;
                case "calculateScores":
                    CalculateScores.calculateAllScores(new File(fileName));
                    break;
                default:
                    System.err.println("Run parameter expected! Please specify the process you would like to run.");
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
