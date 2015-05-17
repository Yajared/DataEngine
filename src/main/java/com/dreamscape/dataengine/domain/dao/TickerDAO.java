/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Security;
import com.dreamscape.dataengine.domain.Ticker;
import java.util.List;

/**
 *
 * @author Jared
 */
public interface TickerDAO {
    public Long create(Ticker ticker);
    public Ticker retrieve(String ticker);
    public void update(String ticker, List <String> columnsToUpdate, List <Object> updatedValues);
    public void delete(String ticker);
}
