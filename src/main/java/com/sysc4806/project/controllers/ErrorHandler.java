package com.sysc4806.project.controllers;

import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.controllers.exceptions.HttpRedirectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorHandler {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * Handle http error exceptions.
     * @param ex The exception object with error details
     * @return The view template
     */
    @ExceptionHandler(HttpErrorException.class)
    public String handleHttpException(Model model, HttpErrorException ex, HttpServletResponse response)
    {
        LOG.info("Handling HttpErrorException. Sending response code: " + ex.getStatus().value());
        model.addAttribute("status", ex.getStatus());
        model.addAttribute("errorMessage", ex.getReason());

        response.setStatus(ex.getStatus().value());
        return "error/status";
    }

    /**
     * Handle redirection exceptions.
     * @param ex The exception object with redirect details
     * @return The view template
     */
    @ExceptionHandler(HttpRedirectException.class)
    public String handleHttpRedirection(HttpRedirectException ex)
    {
        LOG.info("Handling HttpRedirectException. Sending to URL: " + ex.getRedirectUrl());
        return "redirect:/" + ex.getRedirectUrl();
    }
}
