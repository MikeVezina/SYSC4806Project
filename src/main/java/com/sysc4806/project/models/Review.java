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

    private String comment;

    /**
     * Constructor of a review
     * @param product - The product to review
     * @param rating - the rating of the product
     */
    public Review(Product product, UserEntity author, int rating){

        this(product, author, rating, "");
    }

    /**
     * Constructor of a review
     * @param rating - the rating of the product
     */
    public Review(Product product, UserEntity author, int rating, String comment){

        this.product = product;
        this.author = author;
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Default Constructor for Database.
     */
    private Review(){
        this(null, null, 0);
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

    private void setId(Long id) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

        return other.author.equals(this.author) && other.product.equals(this.product) && other.rating == this.rating && other
                .comment.equals(this.comment);
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
