package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.UserConnectionRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.controllers.exceptions.HttpRedirectException;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.social.UserConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ControllerUtils {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserConnectionRepository connectionRepo;

    @Autowired
    private UserEntityRepository userRepo;

    public UserEntity getCurrentUser()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || !auth.isAuthenticated())
        {
            LOG.info("User is not logged in.");
            throw new HttpErrorException(HttpStatus.UNAUTHORIZED, "The user is not logged in. Please log in.");
        }

        // Get current logged in user
        UserEntity loggedInUser = userRepo.findByUsernameIgnoreCase(auth.getName());

        // Check to see if the user is logged in.
        if(loggedInUser == null) {
            LOG.info("Current logged in user does not exist.");

            // Remove current security context of user
            SecurityContextHolder.clearContext();

            // Redirect if the user was not found
            throw new HttpRedirectException("login");
        }

        return loggedInUser;
    }

    /**
     * Gets the user and places all necessary information in the model
     * @param model
     * @return
     */
    public UserEntity addCurrentUserToModel(Model model)
    {
        // Get current logged in user
        UserEntity loggedInUser = getCurrentUser();

        if(model == null)
            return loggedInUser;

        List<UserConnection> ssoConnections = connectionRepo.findByUserConnectionKeyUserId(loggedInUser.getUsername());

        for (UserConnection connection : ssoConnections)
            model.addAttribute(connection.getUserConnectionKey().getProviderId(), connection);

        model.addAttribute("loggedInUser", loggedInUser);

        return loggedInUser;
    }
}
