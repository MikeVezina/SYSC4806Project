package com.sysc4806.project.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IndexControllerTest {

    private IndexController indexController;

    @Before
    public void setUp() throws Exception {
        indexController = new IndexController();
    }

    @Test
    public void index() throws Exception {
        assertEquals("Ensure index template name is returned", "index", indexController.index());
    }

}