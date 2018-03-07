package com.sysc4806.project.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.*;

/**
 * The UserEntity class of the application.
 *
 * A user has a username and an id. They can browse products, write reviews for
 * products, and view other user reviews. Users can follow other users to see the
 * product reviews they create. A user also has a list of users that are following
 * them.
 *
 */
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity=Review.class, mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviews;

    @NotEmpty
    private String username;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_relations",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<UserEntity> followers;

    @ManyToMany(mappedBy = "followers")
    private List<UserEntity> following;

    /**
     * Constructor of a new user to be persisted in the
     * application.
     * @param n - The username of the new user
     */
    public UserEntity(String n){
        this.username = n;
        this.reviews = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
    }

    /**
     * default constructor for database.
     */
    public UserEntity(){
        this("temp");
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
     * Getter method for the UserEntity's reviews
     * @return  - List of the UserEntity's reviews
     */
    public List<Review> getReviews() { return reviews; }

    /**
     * Setter method for the UserEntity's review list
     * @param reviews - new UserEntity review list
     */
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    /**
     * Getter method for the UserEntity's followers
     * @return  - List of the UserEntity's followers
     */
    public List<UserEntity> getFollowers() { return followers; }

    /**
     * Setter method for the UserEntity's followers
     * @param followers - new UserEntity follower list
     */
    public void setFollowers(List<UserEntity> followers) { this.followers = followers; }

    /**
     * Getter method for the UserEntity's following list
     * @return - UserEntity's following list
     */
    public List<UserEntity> getFollowing() { return following; }

    /**
     * Setter method for the product's category
     * @param following - product's new category
     */
    public void setFollowing(List<UserEntity> following) { this.following = following; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
