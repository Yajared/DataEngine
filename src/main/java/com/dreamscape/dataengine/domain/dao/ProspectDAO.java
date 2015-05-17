/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain.dao;

import com.dreamscape.dataengine.domain.Prospect;
import java.util.List;

/**
 *
 * @author Jared
 */
public interface ProspectDAO {
    public Long create(Prospect prospect);
    public List<Prospect> getAllProspects();
}
