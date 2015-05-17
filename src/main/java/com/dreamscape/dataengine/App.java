package com.dreamscape.dataengine;

import com.dreamscape.dataengine.processes.PopulateKeyStatisticsData;
import com.dreamscape.dataengine.processes.PopulateGoogleFinancialsData;
import com.dreamscape.dataengine.processes.CalculateScores;
import java.io.File;
import java.io.IOException;

public class App 
{
    @SuppressWarnings("CallToThreadDumpStack")
    public static void main( String[] args )
    {
        PopulateGoogleFinancialsData populate = new PopulateGoogleFinancialsData();
        //PopulateKeyStatisticsData populate = new PopulateKeyStatisticsData();
        File file = new File("C:\\MyValuePicksFor2015-4-17-15.csv");
       // try{
            //populate.populateAll(file);
            //Security s = new Security();
            //s.populateHistoricalPrices(30);
            //if(false)
            //    throw new IOException();
            CalculateScores.calculateAllScores(file);
        //}
//        catch(IOException e){
//            System.err.println("IO Exception when trying to populate all companies' data!");
//            e.printStackTrace();
//            System.exit(0);
//        }
    }
}
