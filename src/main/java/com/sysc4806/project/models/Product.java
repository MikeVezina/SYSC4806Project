package com.sysc4806.project.models;

/**
 * The Product class of the application.
 *
 * A product has a name, a category, and is accessed by a URL.
 * Products have an average rating, out of 10, set by customer
 * reviews. All reviews for this product can be displayed on the
 * page.
 *
 */
import com.sysc4806.project.enumeration.Category;
//import javax.persistence.*;
import java.util.*;


public class Product {

    private long id;

    private String url = "/product"+id;
    //OnetoMany
    private List<Review> reviews;

    private int avgRating;
    private Category category;

    /**

     * Constructor of a new product to be persisted in the
     * application.
     * @param c - The category of the new product
     */
    public Product(Category c){
        this.reviews = new ArrayList<Review>();
        this.avgRating = 0;
        this.category = c;
    }

    /**
     * Default constructor
     */
    public Product(){this(Category.DEFAULT);}


    /**
     * Getter method for the product id
     * @return  - product id
     */
    public long getId() { return id; }

    /**
     * Setter method for the product id
     * @param id - new product id
     */
    public void setId(long id) { this.id = id; }

    /**
     * Getter method for the product url
     * @return  - product url
     */
    public String getUrl() { return url; }

    /**
     * Setter method for the product url
     * @param url - new product url
     */
    public void setUrl(String url) { this.url = url; }

    /**
     * Getter method for the product's reviews
     * @return  - List of the product's reviews
     */
    public List<Review> getReviews() { return reviews; }

    /**
     * Setter method for the product's review list
     * @param reviews - new product review list
     */
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    /**
     * Getter method for the product's Average rating
     * @return - products average rating
     */
    public int getAvgRating() { return avgRating; }

    /**
     * Setter method for the product's average rating
     * @param avgRating - product's new average rating
     */
    public void setAvgRating(int avgRating) { this.avgRating = avgRating; }

    /**
     * Getter method for the product's category
     * @return - product's category
     */
    public Category getCategory() { return category; }

    /**
     * Setter method for the product's category
     * @param category - product's new category
     */
    public void setCategory(Category category) { this.category = category; }

}
