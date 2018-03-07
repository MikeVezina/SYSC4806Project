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
     * @param rating - the rating of the product
     */
    public Review(int rating){
        this.rating = rating;
    }

    /**
     * Default Constructor for Database.
     */
    public Review(){
        this(2);
    }

    public Long getId() {
        return id;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public Product getProduct() {
        return product;
    }

    public int getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
