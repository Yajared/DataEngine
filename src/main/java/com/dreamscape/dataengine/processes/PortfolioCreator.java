/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.processes;

import com.dreamscape.dataengine.domain.Portfolio;
import org.joda.time.DateTime;
/**
 *
 * @author Jared
 */
public class PortfolioCreator {
    public static void createPortfolio(){
        String query = "from Security";
        DateTime date = new DateTime(2015, 8, 31, 17, 0);
        Portfolio portfolio = Portfolio.createPortfolioFromQuery(-1L, query, date);
        portfolio.savePortfolioOfProspects();
    }
}
