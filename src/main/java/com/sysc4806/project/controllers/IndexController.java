package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.controllers.exceptions.HttpErrorException;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final String PRODUCT_PATH = "/products/{productId}";
    private static final String REVIEW_PATH = "/reviews";

    @Autowired
    private UserEntityRepository userRepo;

    @Autowired
    private ProductRepository productRepo;

    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index(Model model, UsernamePasswordAuthenticationToken token)
    {
        UserEntity currentUser = userRepo.findByUsernameIgnoreCase(token.getName());
        Iterator<Product> prds = productRepo.findAll().iterator();
        List<Product> products = new ArrayList<>();
        if(currentUser != null) {
            model.addAttribute("user", currentUser);
        }
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
            throw new HttpErrorException(HttpStatus.NOT_FOUND, "The product you are looking for could not be found");
        }

        model.addAttribute("product", product);

        return "products";
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
        return "error/error";
    }
}
