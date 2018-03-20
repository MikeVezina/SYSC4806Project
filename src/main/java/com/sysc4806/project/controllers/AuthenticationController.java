package com.sysc4806.project.controllers;

import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.models.UserRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class AuthenticationController {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    /**
     * Checks Admin endpoints for Admin privileges.
     * @param pjp The Join Point
     * @param token The Token of the logged in user
     * @return The ResponseBody
     * @throws Throwable
     */
    @Around("args(..,token, model, servletResponse) && @annotation(com.sysc4806.project.AdministratorEndpoint)")
    public String checkAdminAccessAPI(ProceedingJoinPoint pjp, UsernamePasswordAuthenticationToken token, Model model, HttpServletResponse servletResponse) throws Throwable
    {
        authorizeToken(token);
        return (String) pjp.proceed();
    }

    /**
     * Checks the admin privileges of the provided token
     * @param token The token of the current logged in user.
     * @return If the token is successfully authorized
     * @throws HttpErrorException If the user was not authorized successfully
     */
    private void authorizeToken(UsernamePasswordAuthenticationToken token)
    {
        if(token == null || !token.isAuthenticated())
        {
            LOG.info("User is not logged in.");
            throw new HttpErrorException(HttpStatus.UNAUTHORIZED, "The user is not logged in. Please log in.");
        }

        // Iterate and check for admin privileges
        for(GrantedAuthority authority : token.getAuthorities())
        {
            if(authority.getAuthority().equalsIgnoreCase(UserRole.ADMIN.name()))
            {
                LOG.info("Admin Privileges Verified for user: '" + token.getName() + "'.");
                return;
            }
        }

        LOG.info("User '" + token.getName() + "' is not authorized.");
        throw new HttpErrorException(HttpStatus.FORBIDDEN, "You are not authorized to access this page");
    }

}
