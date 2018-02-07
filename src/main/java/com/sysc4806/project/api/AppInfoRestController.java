package com.sysc4806.project.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling application info
 */
@RestController
public class AppInfoRestController {

    /**
     * @return Returns the App Version
     */
    @RequestMapping("/api/version")
    public String version()
    {
        return "1.0.0";
    }

    /**
     * @return Returns the App Authors
     */
    @RequestMapping("/api/authors")
    public String authors()
    {
        return "Alex Hoecht, Reid Cain-Mondoux, Michael Vezina";
    }
}
