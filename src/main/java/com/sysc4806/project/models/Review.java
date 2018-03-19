package com.sysc4806.project.models;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity author;

    @ManyToOne
    private Product product;

    private int rating;

    /**
     * Constructor of a review
     * @param product - The product to review
     * @param rating - the rating of the product
     */
    public Review(Product product, int rating){

        this.product = product;
        this.rating = rating;
    }

    /**
     * Constructor of a review
     * @param rating - the rating of the product
     */
    public Review(int rating){
        this(null, rating);
    }

    /**
     * Default Constructor for Database.
     */
    public Review(){
        this(null, 0);
    }

    /**
     * Getter method for the Review Id
     * @return review id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method for the Review id
     * @param id new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for the Review author
     * @return review author
     */
    public UserEntity getAuthor() {
        return author;
    }

    /**
     * Setter method for the Review author
     * @param author new author
     */
    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    /**
     * Getter method for the Review product
     * @return review product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Setter method for the Review product
     * @param product new product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Getter method for the Review rating
     * @return review rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Setter method for the Review rating
     * @param rating new rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

}
