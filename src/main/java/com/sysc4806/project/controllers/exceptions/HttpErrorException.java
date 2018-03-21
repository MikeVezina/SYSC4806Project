package com.sysc4806.project.controllers.exceptions;

import org.springframework.http.HttpStatus;

public class HttpErrorException extends RuntimeException{
    private HttpStatus status;
    private String reason;

    public HttpErrorException(HttpStatus status, String reason)
    {
        this.reason = reason;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }
}
