package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.ReviewRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for handling application products and error endpoints
 */
@Controller
public class ProductController{

    private static final String PRODUCT_SEARCH_PATH = "/products/search";
    private static final String PRODUCT_PATH = "/products/{productId}";
    private static final String SORT_ASC = "asc";
    private static final String SORT_DESC = "desc";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private Map<String, Comparator<Product>> comparatorStrategies;

    /**
     * @return The Application user template html
     */
    @PostMapping(value = PRODUCT_PATH)
    public String reviewProduct(HttpServletRequest req, Model model) throws IOException
    {
        Product product = getProduct(req.getParameter("productId"));
        String comment = req.getParameter("comment");

        // Get current logged in user and add to model
        UserEntity loggedInUser = controllerUtils.addCurrentUserToModel(model);

        model.addAttribute("product", product);

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
     * @return The product index template html
     */
    @GetMapping(PRODUCT_PATH)
    public String product(@PathVariable Long productId, Model model)
    {
        controllerUtils.addCurrentUserToModel(model);

        Product product = productRepo.findOne(productId);

        if(product == null)
        {
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "The product you are looking for could not be found");
        }

        model.addAttribute("product", product);

        return "product";
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

    @GetMapping(PRODUCT_SEARCH_PATH)
    public String searchProduct(Model model, @RequestParam(required = false) String category, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) String sortCriteria, @RequestParam(required = false) String sortDirection) {

        controllerUtils.addCurrentUserToModel(model);

        // Reset search term if null/empty
        if(searchTerm == null || searchTerm.isEmpty())
            searchTerm = null;

        // Find products based on search term (or all products if null)
        List<Product> products = findProducts(searchTerm);

        // Filter products by category
        try {
            Category cat = Category.valueOf(category);
            products = products.stream().filter(product -> product.getCategory().equals(cat)).collect(Collectors.toList());
        }catch (IllegalArgumentException | NullPointerException e)
        {
            // Ignore. Do not filter by category
            // Reset category parameter
            category = null;
        }


        if (sortDirection == null || sortDirection.isEmpty())
            sortDirection = SORT_ASC;

        if (sortCriteria != null && !sortCriteria.isEmpty() && comparatorStrategies.containsKey(sortCriteria)) {
            Comparator<Product> comparator = comparatorStrategies.get(sortCriteria);
            Collections.sort(products, comparator.reversed());
        } else {
            // Set default comparator
            Map.Entry<String, Comparator<Product>> defaultEntry = comparatorStrategies.entrySet().iterator().next();
            sortCriteria = defaultEntry.getKey();
            Collections.sort(products, defaultEntry.getValue().reversed());
        }

        // Reverse sorting if desc is chosen
        // Ascending is the default if no direction is given
        if (sortDirection.equalsIgnoreCase(SORT_DESC))
            Collections.reverse(products);

        model.addAttribute("productCompareStrategies", comparatorStrategies);
        model.addAttribute("products", products);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("sortCriteria", sortCriteria);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("category", category);
        return "products";

    }

    private List<Product> findProducts(String productName)
    {
        if(productName != null && !productName.isEmpty())
            return productRepo.findAllByUrlContainsIgnoreCaseOrNameContainsIgnoreCase(productName, productName);

        return productRepo.findAll();
    }

}
