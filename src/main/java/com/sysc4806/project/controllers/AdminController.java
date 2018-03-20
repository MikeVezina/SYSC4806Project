package com.sysc4806.project.controllers;

import com.sysc4806.project.AdministratorEndpoint;
import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.ReviewRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


/**
 * Controller for handling all pages for admins.
 * Includes endpoints for generating fake test data, if needed.
 */
@Controller
public class AdminController {

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    private final static String ADMIN_TEMPLATE_BASE_DIR = "admin/";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    /**
     * Gets the admin control panel page
     * @param token The current (logged in) user's token
     * @return The template that will be displayed for the user
     */
    @RequestMapping(value = "/admin")
    @AdministratorEndpoint
    public String admin(UsernamePasswordAuthenticationToken token, Model model, HttpServletResponse servletResponse)
    {
        return ADMIN_TEMPLATE_BASE_DIR + "admin";
    }

    @RequestMapping(value = "/exception")
    public String getError(Model model)
    {
        throw new HttpErrorException(HttpStatus.FORBIDDEN, "The user is forbidden");
    }

    /**
     * Gets the admin control panel page
     * @param token The current (logged in) user's token
     * @return The template that will be displayed for the user
     */
    @RequestMapping(value = "/admin/users")
    @AdministratorEndpoint
    public String allUsers(UsernamePasswordAuthenticationToken token, Model model, HttpServletResponse servletResponse)
    {
        model.addAttribute("users", userRepo.findAll());
        return ADMIN_TEMPLATE_BASE_DIR + "users";
    }

    /**
     * Generate Fake Test Data
     * @param bCryptPasswordEncoder The password encoder. Generates salted + hashed passwords for fake accounts.
     * @param token The current (logged in) user's token.
     * @return The Response Body String.
     */
    @RequestMapping(value = "/admin/generate")
    @ResponseBody
    @AdministratorEndpoint
    public String generate(BCryptPasswordEncoder bCryptPasswordEncoder, UsernamePasswordAuthenticationToken token, Model model, HttpServletResponse servletResponse)
    {
        LOG.info("Generating Data");

        /* Add All User Entities */
        UserEntity userEntityMichael = userRepo.findByUsernameIgnoreCase("michael");
        UserEntity userEntityAlex = userRepo.findByUsernameIgnoreCase("alex");
        UserEntity userEntityReid = userRepo.findByUsernameIgnoreCase("reid");
        UserEntity userEntityJustin = new UserEntity("Justin");
        UserEntity userEntityAndrew = new UserEntity("Andrew");
        UserEntity userEntityNoah = new UserEntity("Noah");

        /* Set User Passwords */
        userEntityJustin.setPassword(bCryptPasswordEncoder.encode("password"));
        userEntityAndrew.setPassword(bCryptPasswordEncoder.encode("password"));
        userEntityNoah.setPassword(bCryptPasswordEncoder.encode("password"));

        /* Set Followers*/
        userEntityMichael.followUser(userEntityNoah);
        userEntityMichael.followUser(userEntityAlex);
        userEntityMichael.followUser(userEntityReid);

        userEntityAlex.followUser(userEntityMichael);
        userEntityAlex.followUser(userEntityReid);
        userEntityAlex.followUser(userEntityJustin);

        userEntityReid.followUser(userEntityMichael);
        userEntityReid.followUser(userEntityAndrew);
        userEntityReid.followUser(userEntityAlex);

        userEntityAndrew.followUser(userEntityReid);
        userEntityAndrew.followUser(userEntityMichael);
        userEntityAndrew.followUser(userEntityAlex);

        userEntityJustin.followUser(userEntityMichael);

        userEntityNoah.followUser(userEntityMichael);
        userEntityNoah.followUser(userEntityAlex);

        /* Add User to DB */
        userEntityMichael = addUser(userEntityMichael);
        userEntityAlex = addUser(userEntityAlex);
        userEntityReid = addUser(userEntityReid);
        userEntityJustin = addUser(userEntityJustin);
        userEntityAndrew = addUser(userEntityAndrew);
        userEntityNoah = addUser(userEntityNoah);

        /* Add All Products */
        Product productMonitor = new Product(Category.MONITORS, "Monitors.com");
        Product productTool = new Product(Category.TOOLS, "Tools.com");
        Product productNintendoSwitch = new Product(Category.ELECTRONICS, "Electronics.com");

        productMonitor = addProduct(productMonitor);
        productTool = addProduct(productTool);
        productNintendoSwitch = addProduct(productNintendoSwitch);


        userEntityAlex.writeReview(productMonitor,2);
        userEntityMichael.writeReview(productMonitor,3);
        userEntityReid.writeReview(productMonitor,4);
        userEntityJustin.writeReview(productMonitor, 5);
        userEntityNoah.writeReview(productMonitor, 2);

        userEntityAlex.writeReview(productTool,1);
        userEntityMichael.writeReview(productTool,1);
        userEntityReid.writeReview(productTool,3);
        userEntityNoah.writeReview(productMonitor, 2);

        userEntityAlex.writeReview(productNintendoSwitch,5);
        userEntityMichael.writeReview(productNintendoSwitch,5);
        userEntityReid.writeReview(productNintendoSwitch,5);
        userEntityAndrew.writeReview(productNintendoSwitch, 5);


        return "Generated Successfully!" +
                "<br>Stats:" +
                "<br>Users: " + userRepo.count() +
                "<br>Products: " + productRepo.count() +
                "<br>Reviews: " + reviewRepo.count();
    }

    /**
     * Add a user, or if the username already exists retrieve the current user.
     * Only used for data generation
     * @param entity The user to add (or retrieve)
     * @return The UserEntitiy added/retrieved.
     */
    private UserEntity addUser(UserEntity entity)
    {
        UserEntity foundUser = userRepo.findByUsernameIgnoreCase(entity.getUsername());
        if(foundUser == null)
        {
            foundUser = entity;
        }

        return foundUser;
    }

    /**
     * Add a product, or if the product URL already exists retrieve the current product.
     * Only used for data generation
     * @param entity The product to add (or retrieve)
     * @return The product added/retrieved.
     */
    private Product addProduct(Product entity)
    {
        Product foundProd = productRepo.findByUrlIgnoreCase(entity.getUrl());
        if(foundProd == null)
        {
            foundProd = entity;
        }

        return foundProd;
    }
}
