/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamscape.dataengine.domain;

import com.dreamscape.dataengine.utils.PerformanceTimeFrame;
import com.dreamscape.dataengine.domain.Prospect;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Jared
 */
public class ProspectTest {
    @Test
    public void validateTimeFrameTest()
    {
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf3));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf5));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf10));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf20));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf30));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf40));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf60));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf120));
        assertTrue(Prospect.validateTimeFrame(PerformanceTimeFrame.Perf240));
    }
}
