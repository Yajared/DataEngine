/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.connections;

import com.dreamscape.utillibrary.connections.Domain;
import java.io.InputStream;

/**
 *
 * @author Jared
 */
public class GoogleFinancialsConnectionManager {
    private String googleFinancialsURL = "https://www.google.com/finance?q=NYSE%3A~&fstype=ii";
    public String downloadFinancialsData(String ticker){
        String url = googleFinancialsURL.replace("~", ticker);
        System.out.println(url);
        Domain domain = new Domain(url);
        InputStream is = domain.getPageContentAsInputStream();
        String content = Domain.convertInputStreamContentToBlobString(is);
        //System.out.println(content);
        //if(content.contains("There is no Key Statistics data available for " + ticker))
        //    return null;
        
        return content;
    }
}
