package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.UserConnectionRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.controllers.exceptions.HttpRedirectException;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.social.UserConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final String PRODUCT_PATH = "/products/{productId}";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserConnectionRepository connectionRepo;

    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index(Model model, UsernamePasswordAuthenticationToken token)
    {
        UserEntity currentUser = userRepo.findByUsernameIgnoreCase(token.getName());
        if(currentUser != null) {
            List<UserConnection> ssoConnections = connectionRepo.findByUserConnectionKeyUserId(currentUser.getUsername());

            for (UserConnection connection : ssoConnections)
                model.addAttribute(connection.getUserConnectionKey().getProviderId(), connection);

            model.addAttribute("user", currentUser);
        }
        List<Product> products = productRepo.findAll();
        Collections.sort(products);
        model.addAttribute("products",products);
        return "index";
    }

    /**
     * @return The product index template html
     */
    @RequestMapping(PRODUCT_PATH)
    public String product(@PathVariable Long productId, Model model, Principal principal)
    {
        Product product = productRepo.findOne(productId);
        UserEntity loggedInUser = getCurrentUser(principal);

        if(product == null)
        {
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "The product you are looking for could not be found");
        }

        model.addAttribute("product", product);
        model.addAttribute("loggedInUser", loggedInUser);

        return "products";
    }

    /**
     * @return The error path
     */
    @Override
    public String getErrorPath()
    {
        return ERROR_PATH;
    }

    /**
     * @return The Application error template html
     */
    @RequestMapping(ERROR_PATH)
    public String error()
    {
        return "error/error";
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
