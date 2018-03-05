package com.sysc4806.project.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.*;

/**
 * The User class of the application.
 *
 * A user has a username and an id. They can browse products, write reviews for
 * products, and view other user reviews. Users can follow other users to see the
 * product reviews they create. A user also has a list of users that are following
 * them.
 *
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<Review> reviews;

    @NotEmpty
    private String username;

    private List<User> followers;
    private List<User> following;

    /**

     * Constructor of a new user to be persisted in the
     * application.
     * @param n - The username of the new user
     */
    public User(String n){
        this.username = n;
        this.reviews = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }



    /**
     * Getter method for the user id
     * @return  - user id
     */
    public long getId() { return id; }

    /**
     * Setter method for the user id
     * @param id - new user id
     */
    public void setId(long id) { this.id = id; }

    /**
     * Getter method for the username
     * @return  - username
     */
    public String getUsername() { return username; }

    /**
     * Setter method for the username
     * @param username - new username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Getter method for the User's reviews
     * @return  - List of the User's reviews
     */
    public List<Review> getReviews() { return reviews; }

    /**
     * Setter method for the User's review list
     * @param reviews - new User review list
     */
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    /**
     * Getter method for the User's followers
     * @return  - List of the User's followers
     */
    public List<User> getFollowers() { return followers; }

    /**
     * Setter method for the User's followers
     * @param followers - new User follower list
     */
    public void setFollowers(List<User> followers) { this.followers = followers; }

    /**
     * Getter method for the User's following list
     * @return - User's following list
     */
    public List<User> getFollowing() { return following; }

    /**
     * Setter method for the product's category
     * @param following - product's new category
     */
    public void setFollowing(List<User> following) { this.following = following; }

}
