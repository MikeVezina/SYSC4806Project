package com.sysc4806.project.controllers;

import com.sysc4806.project.AdministratorEndpoint;
import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.ReviewRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


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

    @Autowired
    private ControllerUtils controllerUtils;

    /**
     * Gets the admin control panel page
     * @param token The current (logged in) user's token
     * @return The template that will be displayed for the user
     */
    @RequestMapping(value = "/admin")
    @AdministratorEndpoint
    public String admin(UsernamePasswordAuthenticationToken token, Model model, HttpServletResponse servletResponse)
    {
        controllerUtils.addCurrentUserToModel(model);
        return ADMIN_TEMPLATE_BASE_DIR + "admin";
    }

    @RequestMapping(value = "/exception")
    public String getError(Model model)
    {
        throw new HttpErrorException(HttpStatus.FORBIDDEN, "The user is forbidden");
    }

    /**
     * Controller method to create new products for the application
     * @param req
     * @param model
     * @param principal
     * @return
     */
    @PostMapping(value = "/admin/createProduct")
    @AdministratorEndpoint
    public String createProduct(HttpServletRequest req, Model model, Principal principal)
    {
        String url = req.getParameter("url");
        String name = req.getParameter("name");

        Category enumCat;
        String category = req.getParameter("category");
        switch(category){
            case"monitors": enumCat = Category.MONITORS;
            break;
            case"electronics":  enumCat = Category.ELECTRONICS;
            break;
            case"books":    enumCat = Category.BOOKS;
            break;
            case"tools":    enumCat = Category.TOOLS;
            break;
            default:    enumCat = Category.DEFAULT;
        }
        Product newProduct = new Product(enumCat,name,url);

        productRepo.save(newProduct);

        return "redirect:/admin";
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

        if(reviewRepo.count() > 0 && productRepo.count() > 0)
            return "No Information Generated. Information already exists";

        UserEntity userEntityJustin = new UserEntity("Justin");
        UserEntity userEntityAndrew = new UserEntity("Andrew");
        UserEntity userEntityNoah = new UserEntity("Noah");

        /* Add User to DB */
        userEntityJustin = userRepo.saveAndFlush(userEntityJustin);
        userEntityAndrew = userRepo.saveAndFlush(userEntityAndrew);
        userEntityNoah = userRepo.saveAndFlush(userEntityNoah);

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
        userEntityMichael = userRepo.saveAndFlush(userEntityMichael);
        userEntityAlex = userRepo.saveAndFlush(userEntityAlex);
        userEntityReid = userRepo.saveAndFlush(userEntityReid);
        userEntityJustin = userRepo.saveAndFlush(userEntityJustin);
        userEntityAndrew = userRepo.saveAndFlush(userEntityAndrew);
        userEntityNoah = userRepo.saveAndFlush(userEntityNoah);

        /* Add All Products */
        Product productMonitor = new Product(Category.MONITORS, "LG Monitor", "Monitors.com");
        Product productTool = new Product(Category.TOOLS, "Wrench", "Tools.com");
        Product productNintendoSwitch = new Product(Category.ELECTRONICS, "Nintendo Switch", "http://www.nintendo.com/switch");

        productRepo.saveAndFlush(productMonitor);
        productRepo.saveAndFlush(productTool);
        productRepo.saveAndFlush(productNintendoSwitch);

        reviewRepo.saveAndFlush(userEntityAlex.writeReview(productMonitor,2, "Terrible product!"));
        reviewRepo.saveAndFlush(userEntityMichael.writeReview(productMonitor,3, "Mediocre"));
        reviewRepo.saveAndFlush(userEntityReid.writeReview(productMonitor,4, "I loved it!"));
        reviewRepo.saveAndFlush(userEntityJustin.writeReview(productMonitor, 5, "Speech-less."));
        reviewRepo.saveAndFlush(userEntityNoah.writeReview(productMonitor, 2));

        reviewRepo.saveAndFlush(userEntityAlex.writeReview(productTool,1));
        reviewRepo.saveAndFlush(userEntityMichael.writeReview(productTool,1, "Refund please!"));
        reviewRepo.saveAndFlush(userEntityReid.writeReview(productTool,3));

        reviewRepo.saveAndFlush(userEntityAlex.writeReview(productNintendoSwitch,5, "AMAZING!"));
        reviewRepo.saveAndFlush(userEntityMichael.writeReview(productNintendoSwitch,5, "GREAT!"));
        reviewRepo.saveAndFlush(userEntityReid.writeReview(productNintendoSwitch,5, "WOW!"));
        reviewRepo.saveAndFlush(userEntityAndrew.writeReview(productNintendoSwitch, 5, "AMAZING!"));

        userRepo.saveAndFlush(userEntityMichael);
        userRepo.saveAndFlush(userEntityAlex);
        userRepo.saveAndFlush(userEntityReid);
        userRepo.saveAndFlush(userEntityAndrew);
        userRepo.saveAndFlush(userEntityNoah);
        userRepo.saveAndFlush(userEntityJustin);

        return "Generated Successfully!" +
                "<br>Stats:" +
                "<br>Users: " + userRepo.count() +
                "<br>Products: " + productRepo.count() +
                "<br>Reviews: " + reviewRepo.count();
    }
}
