package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.ReviewRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.controllers.exceptions.HttpRedirectException;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * Controller for handling application products and error endpoints
 */
@Controller
public class ProductController{

    private static final String PRODUCT_REVIEW_PATH = "/products/{product.id}";
    private static final String PRODUCT_SEARCH_PATH = "/products/search";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    /**
     * @return The Application user template html
     */
    @PostMapping(value = PRODUCT_REVIEW_PATH)
    public String reviewProduct(HttpServletRequest req, Model model, Principal principal) throws IOException
    {
        Product product = getProduct(req.getParameter("productId"));
        String comment = req.getParameter("comment");
        // Get current logged in user
        UserEntity loggedInUser = getCurrentUser(principal);

        model.addAttribute("product", product);
        model.addAttribute("loggedInUser", loggedInUser);

        try {
            int rating = Integer.parseInt(req.getParameter("rating"));

            Review review = loggedInUser.writeReview(product,rating,comment);

            reviewRepo.save(review);
            productRepo.save(product);
            userRepo.save(loggedInUser);

        }
        catch (NumberFormatException nfE)
        {
            // Do nothing
        }

        return "redirect:/products/" + product.getId();
    }

    /**
     * Gets a product with ID 'productId'
     * @param productId The productId of the product to get
     * @return The product with ID productId
     */
    private Product getProduct(Long productId)
    {
        Product product = productRepo.findOne(productId);

        // Check to see what gets obtained from the database
        if(product == null)
        {
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "The user you are looking for could not be found.");
        }

        return product;
    }

    /**
     * Gets a product with ID 'productId'
     * @param productIdStr The productId String of the product to get
     * @return The product with ID productId
     */
    private Product getProduct(String productIdStr)
    {
        if(productIdStr == null || productIdStr.isEmpty())
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "Could not follow the specified user.");

        try {
            Long productId = Long.parseLong(productIdStr);
            return getProduct(productId);
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

    @PostMapping(PRODUCT_SEARCH_PATH)
    private String searchProduct(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model)
    {
        if(searchTerm == null){
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "No Search Term Provided.");
        }

        List<Product> products = productRepo.findAllByUrlContainsIgnoreCaseOrNameContainsIgnoreCase(searchTerm, searchTerm);
        Collections.sort(products);
        model.addAttribute("products", products);
        model.addAttribute("searchTerm", searchTerm);
        return "productSearch";
    }

}
