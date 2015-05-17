/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dreamscape.dataengine.domain;

import java.util.regex.Pattern;

/**
 *
 * @author Jared
 */
public class Metric {
    private String type;
    private Pattern pattern;
    private String rawText;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Metric(){
        
    }
    public Metric(String type, Pattern pattern, String rawText){
        this.type = type;
        this.pattern = pattern;
        this.rawText = rawText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
    
}
