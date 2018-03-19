package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.ReviewRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController {

    public static final String ERROR_PATH = "/error";
    private static final String PRODUCT_PATH = "/products/{productId}";
    private static final String REVIEW_PATH = "/reviews";
    private static final String HOMEPAGE_PATH = "/home";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index(Model model)
    {
        Iterator<Product> prds = productRepo.findAll().iterator();
        List<Product> products = new ArrayList<>();
        while (prds.hasNext())
        {
            products.add(prds.next());
        }
        Collections.sort(products);
        model.addAttribute("products",products);
        return "index";
    }



    /**
     * @return The product index template html
     */
    @RequestMapping(PRODUCT_PATH)
    public String product(@PathVariable Long productId, Model model)
    {
        Product product = productRepo.findOne(productId);

        if(product == null)
        {
            model.addAttribute("errorMessage", "The Product with ID: " + productId + " could not be found.");
            return "404-error";
        }

        model.addAttribute("products", product);

        return "products";
    }


    @RequestMapping(value = "/generate")
    @ResponseBody
    public String generate(BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        if(userRepo.findByUsername("Michael") != null && userRepo.findByUsername("Reid") != null)
            return "Data Already Generated.";


        UserEntity userEntityMichael = new UserEntity("Michael");
        UserEntity userEntityAlex = new UserEntity("Alex");
        UserEntity userEntityReid = new UserEntity("Reid");

        userEntityMichael.setPassword(bCryptPasswordEncoder.encode("michael15"));
        userEntityAlex.setPassword(bCryptPasswordEncoder.encode("michael15"));
        userEntityReid.setPassword(bCryptPasswordEncoder.encode("michael15"));

        userEntityAlex.followUser(userEntityMichael);
        userEntityAlex.followUser(userEntityReid);

        userEntityMichael.followUser(userEntityReid);

        userEntityReid.followUser(userEntityAlex);
        userEntityReid.followUser(userEntityMichael);

        userRepo.save(userEntityAlex);
        userRepo.save(userEntityMichael);
        userRepo.save(userEntityReid);

        Product productMonitor = new Product(Category.MONITORS, "Monitors.com");
        Product productTool = new Product(Category.TOOLS, "Tools.com");
        Product productNintendoSwitch = new Product(Category.ELECTRONICS, "Electronics.com");

        productRepo.save(productMonitor);
        productRepo.save(productTool);
        productRepo.save(productNintendoSwitch);

        userEntityAlex.writeReview(productMonitor,2);
        userEntityMichael.writeReview(productMonitor,3);
        userEntityReid.writeReview(productMonitor,4);

        userEntityAlex.writeReview(productTool,1);
        userEntityMichael.writeReview(productTool,1);
        userEntityReid.writeReview(productTool,3);

        userEntityAlex.writeReview(productNintendoSwitch,5);
        userEntityMichael.writeReview(productNintendoSwitch,5);
        userEntityReid.writeReview(productNintendoSwitch,5);

        for(int i = 0; i  < userEntityAlex.getReviews().size(); i++){
            reviewRepo.save(userEntityAlex.getReviews().get(i));
        }

        for(int i = 0; i  < userEntityMichael.getReviews().size(); i++){
            reviewRepo.save(userEntityMichael.getReviews().get(i));
        }

        for(int i = 0; i  < userEntityReid.getReviews().size(); i++){
            reviewRepo.save(userEntityReid.getReviews().get(i));
        }

        return "Successfully Generated Test Data";
    }

    /**
     * Application template used to display all users
     * @param model
     * @return The application Search User template
     */
    @RequestMapping(value=HOMEPAGE_PATH, method= RequestMethod.GET)
    public String getAllProducts(Model model)
    {
        Iterator<Product> prds = productRepo.findAll().iterator();
        List<Product> products = new ArrayList<>();
        while (prds.hasNext())
        {
            products.add(prds.next());
        }
        Collections.sort(products);
        model.addAttribute("products",products);
        return "index";

    }

    /**
     * @return The review index template html
     */
    @RequestMapping(REVIEW_PATH)
    public String review()
    {
        return "reviews";
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
        return "error";
    }
}
