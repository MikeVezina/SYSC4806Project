package com.sysc4806.project.controllers.api;

import com.sysc4806.project.models.AppInfo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppInfoRestControllerTest {

    private RestAPIController restAPIController;

    @Before
    public void setUp()
    {
        restAPIController = new RestAPIController();
    }

    @Test
    public void testAppInformation()
    {
        assertEquals("Must be equal to AppInfo singleton instance", AppInfo.getInstance(), restAPIController.appInformation());
    }
}