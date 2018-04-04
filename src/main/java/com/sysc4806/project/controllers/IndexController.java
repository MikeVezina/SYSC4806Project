package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.comparators.products.ProductAverageRatingComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

/**
 * Controller for handling application index and error endpoints
 */
@Controller
public class IndexController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private ProductAverageRatingComparator productAverageRatingComparator;

    /**
     * @return The Application index template html
     */
    @RequestMapping("/")
    public String index(Model model)
    {
        controllerUtils.addCurrentUserToModel(model);

        List<Product> products = productRepo.findAll();
        List<Product> topProducts = products.subList(0, Math.min(15, products.size()));
        model.addAttribute("products", topProducts);
        Collections.sort(topProducts, productAverageRatingComparator.reversed());
        return "index";
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
