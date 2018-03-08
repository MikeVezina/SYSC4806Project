package com.sysc4806.project.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexControllerTest {

    private IndexController indexController;

    @Before
    public void setUp() {
        indexController = new IndexController();
    }

    @Test
    public void testIndex() {
        assertEquals("Ensure index template name is returned", "index", indexController.index());
    }

    @Test
    public void testReviews() {
        assertEquals("Ensure reviews template name is returned", "reviews", indexController.review());
    }

    @Test
    public void testError() throws Exception {
        assertEquals("Ensure index template name is returned", "error", indexController.error());
    }

    @Test
    public void testGetErrorPath() throws Exception {
        assertEquals("Ensure index template name is returned", IndexController.ERROR_PATH, indexController.getErrorPath());
    }

}