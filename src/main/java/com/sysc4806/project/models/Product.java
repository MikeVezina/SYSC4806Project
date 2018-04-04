package com.sysc4806.project.models;

import com.sysc4806.project.enumeration.Category;
import org.hibernate.validator.constraints.NotEmpty;
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
public class Product{

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String url;

    private String name;

    @OneToMany(targetEntity=Review.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Review> reviews;

    private Category category;

    /**
     * Constructor of a new product to be persisted in the
     * application.
     * @param c - The category of the new product
     */
    public Product(Category c, String name, String url){
        this.name = name;
        this.url = url;
        this.reviews = new HashSet<>();
        this.category = c;
    }

    /**
     * Constructor of a new product to be persisted in the
     * application.
     * @param url - The url of the new product
     */
    public Product(String name, String url){
        this(Category.DEFAULT, name, url);
    }

    /**
     * Default constructor for database.
     */
    private Product(){this(Category.DEFAULT, "", "");}

    public void addUserReview(Review review)
    {
        if(reviews.contains(review))
            return;

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
    private void setId(long id) { this.id = id; }

    /**
     * Getter method for the product url
     * @return  - product url
     */
    public String getUrl() { return url; }

    /**
     * @return The Href Safe URL (used for getting a valid url to the product)
     */
    public String getUrlHref()
    {
        if(!this.url.toLowerCase().startsWith(HTTP_PREFIX) && !this.url.toLowerCase().startsWith(HTTPS_PREFIX))
            return HTTP_PREFIX + url;

        return url;
    }

    /**
     * Setter method for the product url
     * @param url - new product url
     */
    public void setUrl(String url) { this.url = url; }

    /**
     * @return Get the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the product name
     * @param name The new product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the product's reviews
     * @return  - List of the product's reviews
     */
    public Set<Review> getReviews() { return reviews; }

    /**
     * Setter method for the product's review list
     * @param reviews - new product review list
     */
    private void setReviews(Set<Review> reviews) { this.reviews = reviews; }

    /**
     * Getter method for the product's Average rating
     * @return - products average rating
     */
    public float getAverageRating() {
        if(reviews.isEmpty()) return 0;
        return reviews.stream().mapToInt(Review::getRating).sum() / (float) reviews.size();
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

    /**
     * Checks to see if two Product Objects are equal
     * @param o The object to compare
     * @return True if o is a Product and shares the same id OR product URL, category, rating total, and same reviews as this Product
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Product))
            return false;

        Product other = (Product) o;

        if(this.id > 0 && other.id > 0)
            return other.id == (this.id);

        return other.url.equals(this.url) && other.category.equals(this.category) && other.getAverageRating() == this.getAverageRating();
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
