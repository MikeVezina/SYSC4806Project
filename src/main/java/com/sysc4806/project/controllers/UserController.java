package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.controllers.exceptions.HttpRedirectException;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Controller
public class UserController {

    private static final String USER_PATH = "/user";
    private static final String USER_ID_PATH = "/user/{userId}";
    private static final String USER_FOLLOW_PATH = "/user/follow";
    private static final String USER_UNFOLLOW_PATH = "/user/unfollow";

    @Autowired
    private UserEntityRepository userRepo;

    /**
     * @return The Application user template html
     */
    @RequestMapping(USER_PATH)
    public String currentUser(Model model, Principal principal) throws IOException
    {
        UserEntity loggedInUser = getCurrentUser(principal);

        model.addAttribute("user", loggedInUser);
        model.addAttribute("loggedInUser", loggedInUser);
        return "user";
    }

    /**
     * @return The Application user template html
     */
    @RequestMapping(value = USER_ID_PATH)
    public String user(@PathVariable Long userId, Model model, Principal principal) throws IOException
    {
        UserEntity userEntity = getUser(userId);
        UserEntity loggedInUser = getCurrentUser(principal);

        model.addAttribute("user", userEntity);
        model.addAttribute("loggedInUser", loggedInUser);

        // Redirect to the current logged in users page if the id matches the current logged in user
        if(userEntity.equals(loggedInUser))
            throw new HttpRedirectException("user");

        return "user";
    }

    /**
     * @return The Application user template html
     */
    @PostMapping(value = USER_FOLLOW_PATH)
    public String followUser(HttpServletRequest req, Model model, Principal principal) throws IOException
    {
        UserEntity userEntity = getUser(req.getParameter("userId"));
        UserEntity loggedInUser = getCurrentUser(principal);

        model.addAttribute("user", userEntity);
        model.addAttribute("loggedInUser", loggedInUser);

        // Check to see if we are currently following the other user
        if(loggedInUser.equals(userEntity) || loggedInUser.isFollowing(userEntity))
            return "redirect:/user/" + userEntity.getId();

        loggedInUser.followUser(userEntity);
        userRepo.save(loggedInUser);

        return "redirect:/user/" + userEntity.getId();
    }

    /**
     * Endpoint for unfollowing a user
     * @return The Application user template html
     */
    @PostMapping(value = USER_UNFOLLOW_PATH)
    public String unFollowUser(HttpServletRequest req, Model model, Principal principal) throws IOException
    {
        UserEntity userEntity = getUser(req.getParameter("userId"));
        UserEntity loggedInUser = getCurrentUser(principal);


        // Check to see if we are currently following the other user
        if(!loggedInUser.equals(userEntity) && loggedInUser.isFollowing(userEntity)) {
            loggedInUser.unfollowUser(userEntity);
            userRepo.save(loggedInUser);
        }

        model.addAttribute("user", userEntity);
        model.addAttribute("loggedInUser", loggedInUser);
        return "redirect:/user/" + userEntity.getId();
    }

    /**
     * Gets a user with ID 'userId'
     * @param userId The userId of the user to get
     * @return The user with ID userId
     */
    private UserEntity getUser(Long userId)
    {
        UserEntity userEntity = userRepo.findOne(userId);

        // Check to see what gets obtained from the database
        if(userEntity == null)
        {
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "The user you are looking for could not be found.");
        }

        return userEntity;
    }

    /**
     * Gets a user with ID 'userId'
     * @param userIdStr The userId String of the user to get
     * @return The user with ID userId
     */
    private UserEntity getUser(String userIdStr)
    {
        if(userIdStr == null || userIdStr.isEmpty())
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "Could not follow the specified user.");

        try {
            Long userId = Long.parseLong(userIdStr);
            return getUser(userId);
        }
        catch (NumberFormatException nfE)
        {
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "Could not follow the specified user.");
        }


    }

    /**
     * Gets the current logged in user
     * @param principal The principal passed in through spring security
     * @return The currently logged in user
     */
    private UserEntity getCurrentUser(Principal principal)
    {
        // Get current logged in user
        UserEntity loggedInUser = userRepo.findByUsernameIgnoreCase(principal.getName());


        // Check to see if the user is logged in.
        if(loggedInUser == null)
        {
            // Redirect if the user was not found
            throw new HttpRedirectException("login");
        }

        return loggedInUser;
    }

}
