/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Portfolio;
import java.util.List;

/**
 *
 * @author Jared
 */
public interface PortfolioDAO {
    public Long create(Portfolio portfolio);
    public Portfolio retrieve(String query);
    public List<Portfolio> getAllPortfolios();
    public boolean update(Portfolio portfolio);
}
