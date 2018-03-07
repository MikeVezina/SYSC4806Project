package com.sysc4806.project.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController {

    public static final String ERROR_PATH = "/error";
    private static final String USER_PATH = "/users";
    private static final String PRODUCT_PATH = "/products";
    private static final String REVIEW_PATH = "/reviews";
    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    /**
     * @return The Application user template html
     */
    @RequestMapping(USER_PATH)
    public String user()
    {
        return "users";
    }

    /**
     * @return The product index template html
     */
    @RequestMapping(PRODUCT_PATH)
    public String product()
    {
        return "products";
    }

    /**
     * @return The review index template html
     */
    @RequestMapping(REVIEW_PATH)
    public String review()
    {
        return "reviews";
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
