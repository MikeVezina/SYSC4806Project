package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class UserController {

    private static final String USER_PATH = "/user";
    private static final String USER_ID_PATH = "/user/{userId}";
    private static final String USER_FOLLOW_PATH = "/user/{currentUserId}/follow";
    private static final String USER_SEARCH_PATH = "/searchUser";

    @Autowired
    private UserEntityRepository userRepo;

    /**
     * @return The Application user template html
     */
    @RequestMapping(USER_PATH)
    public String currentUser(Model model, Principal principal) throws IOException
    {
        UserEntity loggedInUser = userRepo.findByUsername(principal.getName());

        if(loggedInUser == null)
        {
            model.addAttribute("errorMessage", "You are not logged in.");
            return "404-error";
        }

        model.addAttribute("user", loggedInUser);
        model.addAttribute("loggedInUser", loggedInUser);
        return "user";
    }

    /**
     * @return The Application user template html
     */
    @RequestMapping(USER_ID_PATH)
    public String user(@PathVariable Long userId, Model model, Principal principal) throws IOException
    {
        UserEntity userEntity = userRepo.findOne(userId);

        if(userEntity == null)
        {
            model.addAttribute("errorMessage", "The User with ID: " + userId + " could not be found.");
            return "404-error";
        }

        UserEntity loggedInUser = userRepo.findByUsername(principal.getName());

        model.addAttribute("user", userEntity);
        model.addAttribute("loggedInUser", loggedInUser);
        return "user";
    }

    /**
     * @return The Application user template html
     */
    @RequestMapping(USER_FOLLOW_PATH)
    public String followUser(@PathVariable Long currentUserId, Model model, Principal principal) throws IOException
    {
        UserEntity userEntity = userRepo.findOne(currentUserId);

        if(userEntity == null)
        {
            model.addAttribute("errorMessage", "Could not follow. The User with ID: " + currentUserId + " could not be found.");
            return "404-error";
        }

        UserEntity loggedInUser = userRepo.findByUsername(principal.getName());

        if(loggedInUser == null)
        {
            model.addAttribute("errorMessage", "You are not logged in.");
            return "redirect:/login";
        }

        loggedInUser.followUser(userEntity);
        userRepo.save(loggedInUser);

        model.addAttribute("user", userEntity);
        model.addAttribute("loggedInUser", loggedInUser);
        return "redirect:/user/" + userEntity.getId();
    }

    /**
     * Application template used to display all users
     * @param model
     * @return The application Search User template
     */
    @RequestMapping(value=USER_SEARCH_PATH, method= RequestMethod.GET)
    public String getAllUsers(Model model)
    {
        Iterator<UserEntity> usrs = userRepo.findAll().iterator();
        List<UserEntity> users = new ArrayList<>();
        while (usrs.hasNext())
        {
            users.add(usrs.next());
        }
        Collections.sort(users);
        model.addAttribute("userEntities",users);
        return "searchUser";

    }
}
