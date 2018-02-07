package com.sysc4806.project.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController{

    public static final String ERROR_PATH = "/error";

    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }


    /**
     * @return The error path
     */
    @Override
    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    /**
     * @return The Application error template html
     */
    @RequestMapping(ERROR_PATH)
    public String error()
    {
        return "error";
    }
}
