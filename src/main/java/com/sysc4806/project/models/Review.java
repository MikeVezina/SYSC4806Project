package com.sysc4806.project.models;

//import javax.persistence.*;


public class Review {

    private Long id;
    //ManytoOne
    private User author;
    //ManytoOne
    private Product product;
    private int rating;

    /**
     * Constructor of a review
     * @param rating - the rating of the product
     */
    public Review(int rating){
        this.rating = rating;
    }


    public Long getId() {
        return id;
    }

    public User getAuthor() {
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

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
