package com.sysc4806.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling application index
 */
@Controller
public class IndexController {

    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
}
