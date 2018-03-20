package com.sysc4806.project.models;

import com.sysc4806.project.enumeration.Category;
import org.hibernate.validator.constraints.NotEmpty;
//import javax.persistence.*;
import javax.persistence.*;
import java.util.*;

/**
 * The Product class of the application.
 *
 * A product has a name, a category, and is accessed by a URL.
 * Products have an average rating, out of 10, set by customer
 * reviews. All reviews for this product can be displayed on the
 * page.
 *
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String url;

    @OneToMany(targetEntity=Review.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;

    private Category category;

    /**
     * Constructor of a new product to be persisted in the
     * application.
     * @param c - The category of the new product
     */
    public Product(Category c, String url){
        this.url = url;
        this.reviews = new ArrayList<>();
        this.category = c;
    }

    /**
     * Default constructor for database.
     */
    public Product(){this(Category.DEFAULT, "");}

    public void addUserReview(Review review)
    {
        reviews.add(review);
    }

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
    public int getAvgRating() {
        if(reviews.isEmpty()) return 0;
        return reviews.stream().mapToInt(Review::getRating).sum() / reviews.size();
    }

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
