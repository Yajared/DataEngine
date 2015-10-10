/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Security;
import java.util.List;

/**
 *
 * @author Jared
 */
public interface SecurityDAO {
    public Long create(Security security);
    public Security retrieve(String ticker);
    public List<Security> retrieveSecuritiesWithQuery(String query);
    public void update(String ticker, List <String> columnsToUpdate, List <Double> updatedValues);
    public void delete(Long ID);
}