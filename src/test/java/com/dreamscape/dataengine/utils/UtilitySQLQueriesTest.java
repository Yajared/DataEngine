/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.utils;

import com.dreamscape.dataengine.utils.UtilitySqlQueries;
import com.dreamscape.dataengine.utils.PerformanceTimeFrame;
import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.dao.ProspectDAO;
import com.dreamscape.dataengine.domain.dao.ProspectDAOHibernateImpl;
import com.dreamscape.dataengine.domain.GreaterOrLess;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;

/**
 *
 * @author Jared
 */
public class UtilitySQLQueriesTest {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    @Ignore
    public void canGet90thPercentileOfPerf3Test() {
        List<Prospect> ninetiethPercentile = UtilitySqlQueries.getPercentileGreaterOrLessThanPerformance(PerformanceTimeFrame.Perf3, -1L, .9f, GreaterOrLess.GREATER_THAN);
        assertNotNull(ninetiethPercentile);
        ProspectDAO dao = new ProspectDAOHibernateImpl();
        
        // We're only getting all prospects to test that we are indeed getting very close to the top 10% of performers.
        // In actual usage, for obvious reasons, we wouldn't need to do this or perform the SOPs below.
        List<Prospect> allProspects = dao.getProspectsByPortfolioId(-1L); //Get all prospects in Russell 3000 portfolio
        
        System.out.println("Companies in the ninetieth percentile: ");
        for(int i = 0; i < ninetiethPercentile.size(); i++)
        {
            System.out.print(ninetiethPercentile.get(i).getSymbol() + " ");
            if((i + 1) % 14 == 0) // 14 Tickers per line looks a'ight.
                System.out.println("");
        }
        System.out.println("");
        System.out.println("This group represents " + String.valueOf((ninetiethPercentile.size() * 1.0) / (allProspects.size() * 1.0) * 100.0) + "% of the Portfolio.");
    }
    
    @Ignore
    public void canGetAllSecuritiesInPortfolio()
    {

        List<Security> securities = UtilitySqlQueries.getSecuritiesByPortfolioId(-1L);

        for(Security s : securities)
            System.out.println(s.getTicker());

        System.out.println("Total size of retrieved securities list: " + securities.size());
    }
}
