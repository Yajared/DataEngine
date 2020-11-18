package com.dreamscape.dataengine;

import com.dreamscape.dataengine.connections.FinvizConnectionManager;
import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.dao.ProspectDAO;
import com.dreamscape.dataengine.domain.dao.ProspectDAOHibernateImpl;
import com.dreamscape.dataengine.domain.managers.FinvizDomainManager;
import com.dreamscape.dataengine.processes.PopulateGoogleFinancialsData;
import com.dreamscape.dataengine.processes.CalculateScores;
import com.dreamscape.dataengine.processes.PopulateKeyStatisticsData;
import com.dreamscape.dataengine.processes.PortfolioCreator;
import com.dreamscape.dataengine.utils.UtilitySqlQueries;
import com.dreamscape.utillibrary.writers.CSVWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class App 
{
    @SuppressWarnings("CallToThreadDumpStack")
    public static void main( String[] args )
    {
        String process = null;
        final String outPrefix = "C:\\Users\\Jared\\Documents\\";
        try{
            String fileName;
            if(args.length < 2)
            {
                // Running with hard-coded values...
                //fileName = "C:\\Users\\Jared\\Documents\\NetBeansProjects\\DataEngine\\src\\main\\resources\\Russell3000Companies6-15.txt";
                process = "generateProspects";
            }
            else // Running from command line
            {
                process = args[0];
                fileName = args[1];
            }
            
            List<Prospect> prospects;
            switch(process)
            {   
                case "generateProspects":
                    final String MODIFIED_CREDIT_SPREAD_PROSPECTS = "https://finviz.com/screener.ashx?v=111&f=fa_peg_o1,"
                            + "sh_avgvol_o1000,sh_opt_option,sh_price_o100,ta_perf_26wup,ta_rsi_nob60&ft=4&o=price&r=61";
                    
                    final String CREDIT_SPREAD_PROSPECTS = "https://finviz.com/screener.ashx?v=111&f=fa_peg_o1,"
                            + "sh_avgvol_o1000,sh_opt_option,sh_price_o30,"
                            + "sh_relvol_u0.75,ta_perf_26w10o,ta_perf2_4wup,ta_rsi_ob60&ft=4&o=price";
                    final String GROWTH_VALUE_PROSPECTS = "https://finviz.com/screener.ashx?v=111&f=fa_epsqoq_pos,fa_pb_u6,fa_pe_u30,fa_pfcf_u20,fa_roe_pos,fa_salesqoq_pos&ft=4";
                    
                    final String GROWTH_VALUE_NO_PB_LIMIT_PROSPECTS = "https://finviz.com/screener.ashx?v=111&f=fa_epsqoq_pos,fa_pe_u30,fa_pfcf_u20,fa_roe_pos,fa_salesqoq_pos&ft=4";
                    
                    final String GROWTH_VALUE_NO_PB_NO_FCF_NO_ROE_LOW_VOLUME_LIMIT_PROSPECTS = "https://finviz.com/screener.ashx?v=111&f=fa_epsqoq_pos,fa_pe_u30,fa_salesqoq_pos,sh_avgvol_o50&f=ind_stocksonly&ft=4";
                    
                    final String INDUSTRIALS_VALUE_NO_PB_NO_FCF_NO_ROE_NO_FUNDS_PROSPECTS = "https://finviz.com/screener.ashx?v=111&f=fa_epsqoq_pos,fa_pe_u30,fa_salesqoq_pos,sec_industrials,sh_avgvol_o50&ft=4";
                    
                    final boolean WRITE_TO_FILE = true;
                    
                    prospects = FinvizConnectionManager.getProspectListFromUrl(INDUSTRIALS_VALUE_NO_PB_NO_FCF_NO_ROE_NO_FUNDS_PROSPECTS);
                    
                    if(WRITE_TO_FILE){
                        String[][] data = new String[1][prospects.size()];
                        for(int index = 0; index < prospects.size(); index++){
                            data[0][index] = prospects.get(index).getSymbol();
                        }
                        
                        final String path = outPrefix + process + ".csv";
                        CSVWriter.write(path, data, null, null, false);
                        System.out.println("Wrote file to path: " + path);
                    }
                    else {
                        for(Prospect p : prospects){
                            System.out.println(p);
                        }
                    }
                    break;

                case "generateRandomProspects":
                    FinvizDomainManager fdm = new FinvizDomainManager();
                    prospects = fdm.generateRandomProspects();
                    ProspectDAO dao = new ProspectDAOHibernateImpl();
                    dao.createProspects(prospects);
                    break;
                case "populateGoogleFinancials":
                    PopulateGoogleFinancialsData populateGoogleFinancials = new PopulateGoogleFinancialsData();
                    populateGoogleFinancials.populateAll(new File("fileNameHere"));
                    break;
                case "populateKeyStatistics":
                    PopulateKeyStatisticsData populateKeyStats = new PopulateKeyStatisticsData();
                    populateKeyStats.populateAll(new File("fileNameHere"));
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
