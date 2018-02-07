package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppInfoTest {

    private AppInfo appInfo;
    @Before
    public void setUp() {
        appInfo = AppInfo.getInstance();
    }

    @Test
    public void getInstance() {
        assertEquals("AppInfo.getInstance() must always return the same instance", appInfo, AppInfo.getInstance());
    }

    @Test
    public void testVersion() {
        assertTrue("Must match version regex.", appInfo.getVersion().matches("\\d+\\.\\d+\\.\\d+"));
    }

    @Test
    public void testAuthors() {
        assertTrue("Must have Alex's name", appInfo.getAuthors().contains("Alex Hoecht"));
        assertTrue("Must have Reid's name", appInfo.getAuthors().contains("Reid Cain-Mondoux"));
        assertTrue("Must have Michael's name", appInfo.getAuthors().contains("Michael Vezina"));
    }
}