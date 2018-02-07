package com.sysc4806.project.controllers.api;

import com.sysc4806.project.models.AppInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling API requests
 */
@RestController
public class RestAPIController {
    private final static String API_BASE_URL = "/api";

    /**
     * @return Maps API information endpoint to App information as JSON Object
     */
    @RequestMapping(API_BASE_URL + "/information")
    public AppInfo appInformation()
    {
        return AppInfo.getInstance();
    }
}
