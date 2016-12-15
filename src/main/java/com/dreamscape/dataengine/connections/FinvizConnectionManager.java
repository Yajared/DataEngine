/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.connections;

import com.dreamscape.dataengine.domain.Prospect;
import com.dreamscape.utillibrary.connections.Domain;
import static com.dreamscape.dataengine.domain.managers.FinvizDomainManager.*;

import com.dreamscape.dataengine.parsers.FinvizParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared
 */
public class FinvizConnectionManager {
    private static final String BASE_EXPORT_URL = "http://www.finviz.com/screener.ashx?v=111&";
   
    public static List<Prospect> getProspectList(Signal.SignalSelection signal, Enum... features){
        String urlSuffix = signalAndFeaturesToBlobString(true, signal, features);
        String completeURL = BASE_EXPORT_URL + urlSuffix;
        System.out.println("Exporting resource for URL Suffix: " + urlSuffix);
        String completeURLWithPagination = new String(completeURL);
        
        List<Prospect> prospectList = new ArrayList<>();
        
        int i = 1;
        int totalTickers = 0;
        do
        { 
            FinvizParser fp = new FinvizParser();

            if(i > 1)
                completeURLWithPagination = completeURL + "&r=" + String.valueOf(20*(i - 1) + 1);
                
            Domain domain = new Domain(completeURLWithPagination);
            InputStream is = domain.getPageContentAsInputStream();
            String content = Domain.convertInputStreamContentToBlobString(is);
            
            if(i == 1)
                totalTickers = fp.parseTotalTickersCount(content);
            
            System.out.println("Complete URL: " + completeURLWithPagination);
            
            if(totalTickers > 0)
            {
                prospectList = fp.parseProspects(content);
            }
            else
                System.out.println("No companies found with the following criteria: " + urlSuffix);
        } while(totalTickers > (20 * i++));
        
        return prospectList;
    }
}
