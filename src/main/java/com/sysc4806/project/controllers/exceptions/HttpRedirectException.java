package com.sysc4806.project.controllers.exceptions;

public class HttpRedirectException extends RuntimeException{
    private String redirectUrl;

    public HttpRedirectException(String redirectUrlTemplate)
    {
        this.redirectUrl = redirectUrlTemplate;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
