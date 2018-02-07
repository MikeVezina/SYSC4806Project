package com.sysc4806.project.api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppInfoRestControllerTest {

    private AppInfoRestController appInfoRestController;
    @Before
    public void setUp()
    {
        appInfoRestController = new AppInfoRestController();
    }

    @Test
    public void testVersion() throws Exception {
        assertTrue("Must match version regex.", appInfoRestController.version().matches("\\d+\\.\\d+\\.\\d+"));
    }

    @Test
    public void authors() throws Exception {
        assertTrue("Must have Alex's name", appInfoRestController.authors().contains("Alex Hoecht"));
        assertTrue("Must have Reid's name", appInfoRestController.authors().contains("Reid Cain-Mondoux"));
        assertTrue("Must have Michael's name", appInfoRestController.authors().contains("Michael Vezina"));
    }

}