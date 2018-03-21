package com.sysc4806.project.models;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserEntity author;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    /**
     * Checks to see if two Review Objects are equal
     * @param o The object to compare
     * @return True if o is a review and shares the same id OR product, author and rating as this review
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof Review))
            return false;

        Review other = (Review) o;

        if(this.id > 0 && other.id > 0)
            return other.id == (this.id);

        return other.author.equals(this.author) && other.product.equals(this.product) && other.rating == this.rating;
    }

}
