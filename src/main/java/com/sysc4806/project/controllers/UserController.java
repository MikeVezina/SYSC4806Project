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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private static final String USER_PATH = "/user";
    private static final String USER_ID_PATH = "/user/{userId}";
    private static final String ACCOUNT_SETTINGS_PATH = "/account";
    private static final String USER_FOLLOW_PATH = "/user/follow";
    private static final String USER_UNFOLLOW_PATH = "/user/unfollow";
    private static final String USER_SEARCH_PATH = "/users";
    private static final String SORT_ASC = "asc";
    private static final String SORT_DESC = "desc";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private Map<String, Comparator<UserEntity>> comparatorStrategies;

    /**
     * @return The Application user template html
     */
    @RequestMapping(USER_PATH)
    public String currentUser(Model model) throws IOException
    {
        UserEntity loggedInUser = controllerUtils.addCurrentUserToModel(model);
        model.addAttribute("user", loggedInUser);
        return "user";
    }

    /**
     * @return The Application user template html
     */
    @RequestMapping(value = USER_ID_PATH)
    public String user(@PathVariable Long userId, Model model) throws IOException
    {
        UserEntity userEntity = getUser(userId);
        UserEntity loggedInUser = controllerUtils.addCurrentUserToModel(model);

        // Redirect to the current logged in users page if the id matches the current logged in user
        if(userEntity.equals(loggedInUser))
            throw new HttpRedirectException("user");

        model.addAttribute("user", userEntity);

        return "user";
    }

    /**
     * @return The Application user template html
     */
    @RequestMapping(value = ACCOUNT_SETTINGS_PATH)
    public String accountSettings(Model model) throws IOException
    {
        controllerUtils.addCurrentUserToModel(model);
        return "accountSettings";
    }

    /**
     * @return The Application user template html
     */
    @PostMapping(value = USER_FOLLOW_PATH)
    public String followUser(HttpServletRequest req, Model model) throws IOException
    {
        UserEntity userEntity = getUser(req.getParameter("userId"));
        UserEntity loggedInUser = controllerUtils.addCurrentUserToModel(model);

        model.addAttribute("user", userEntity);

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
    public String unFollowUser(HttpServletRequest req, Model model) throws IOException
    {
        UserEntity userEntity = getUser(req.getParameter("userId"));
        UserEntity loggedInUser = controllerUtils.addCurrentUserToModel(model);

        // Check to see if we are currently following the other user
        if(!loggedInUser.equals(userEntity) && loggedInUser.isFollowing(userEntity)) {
            loggedInUser.unfollowUser(userEntity);
            userRepo.save(loggedInUser);
        }

        model.addAttribute("user", userEntity);
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
     * Application template used to display all users
     * @param model
     * @return The application Search User template
     */
    @RequestMapping(value=USER_SEARCH_PATH, method= RequestMethod.GET)
    public String getAllUsers(Model model, @RequestParam(required = false) String sortCriteria, @RequestParam(required = false) String usernameFilter, @RequestParam(required = false) String sortDirection)
    {
        UserEntity loggedInUser = controllerUtils.addCurrentUserToModel(model);

        List<UserEntity> users = findUsers(usernameFilter);
        users.remove(loggedInUser);

        if(sortDirection == null || sortDirection.isEmpty())
            sortDirection = SORT_ASC;

        if(sortCriteria != null && !sortCriteria.isEmpty() && comparatorStrategies.containsKey(sortCriteria)) {
            Comparator<UserEntity> comparator = comparatorStrategies.get(sortCriteria);
            Collections.sort(users, comparator.reversed());
        }
        else {
            // Set default comparator
            Map.Entry<String, Comparator<UserEntity>> defaultEntry = comparatorStrategies.entrySet().iterator().next();
            sortCriteria = defaultEntry.getKey();
            Collections.sort(users, defaultEntry.getValue().reversed());
        }

        // Reverse sorting if desc is chosen
        // Ascending is the default if no direction is given
        if(sortDirection.equalsIgnoreCase(SORT_DESC))
            Collections.reverse(users);

        model.addAttribute("userCompareStrategies", comparatorStrategies);
        model.addAttribute("users", users);
        model.addAttribute("sortCriteria", sortCriteria);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("usernameFilter", usernameFilter);
        return "searchUser";
    }

    private List<UserEntity> findUsers(String userName)
    {
        if(userName != null && !userName.isEmpty())
            return userRepo.findAllByUsernameContainsIgnoreCase(userName);

        return userRepo.findAll();
    }
}
