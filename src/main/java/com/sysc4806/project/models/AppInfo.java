package com.sysc4806.project.models;

import java.util.Arrays;
import java.util.List;

public class AppInfo {
    private static AppInfo globalInstance;
    private static final String[] AUTHORS = new String[]{"Alex Hoecht", "Reid Cain-Mondoux", "Michael Vezina"};
    private static final String APP_VERSION = "1.0.0";

    private AppInfo()
    {
        // Empty private constructor
    }

    /**
     * @return Application authors
     */
    public List<String> getAuthors()
    {
        return Arrays.asList(AUTHORS);
    }

    /**
     * @return Application version
     */
    public String getVersion()
    {
        return APP_VERSION;
    }

    /**
     * @return Single instance of AppInfo
     */
    public static AppInfo getInstance()
    {
        if(globalInstance == null)
            globalInstance = new AppInfo();

        return globalInstance;
    }
}
