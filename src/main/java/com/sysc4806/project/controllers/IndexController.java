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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController {

    public static final String ERROR_PATH = "/error";
    private static final String PRODUCT_PATH = "/products/{productId}";
    private static final String REVIEW_PATH = "/reviews";

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
    public String index()
    {
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

        model.addAttribute("product", product);

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

        userRepo.save(userEntityAlex);
        userRepo.save(userEntityMichael);
        userRepo.save(userEntityReid);

        Product productMonitor = new Product(Category.MONITORS, "Monitors.com");
        Product productTool = new Product(Category.TOOLS, "Tools.com");
        Product productNintendoSwitch = new Product(Category.ELECTRONICS, "Electronics.com");

        productRepo.save(productMonitor);
        productRepo.save(productTool);
        productRepo.save(productNintendoSwitch);

        addReview(userEntityAlex, productMonitor, 2);
        addReview(userEntityMichael, productMonitor, 3);
        addReview(userEntityReid, productMonitor, 4);

        addReview(userEntityAlex, productTool, 1);
        addReview(userEntityMichael, productTool, 1);
        addReview(userEntityReid, productTool, 2);

        addReview(userEntityAlex, productNintendoSwitch, 5);
        addReview(userEntityMichael, productNintendoSwitch, 5);
        addReview(userEntityReid, productNintendoSwitch, 5);

        return "Successfully Generated Test Data";
    }

    private void addReview(UserEntity entity, Product product, int rating)
    {
        Review review = new Review(product, rating);
        review.setAuthor(entity);
        reviewRepo.save(review);
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
