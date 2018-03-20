package com.sysc4806.project.models;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Size;
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

    @Size(min=1, max=32)
    @Column(unique = true)
    private String username;

    @Size(min=8)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_relations",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<UserEntity> followers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,  mappedBy = "followers")
    private List<UserEntity> following;

    private UserRole authorizationRole;

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
        this.authorizationRole = UserRole.MEMBER;
    }

    /**
     * default constructor for database.
     */
    public UserEntity(){
        this("temp");
    }


    /**
     * A method for creating a new User Review
     * @param product - The product the review is about
     * @param rating - The rating of the product
     */
    public void writeReview(Product product,int rating)
    {
        Review newReview = new Review(product, rating);
        newReview.setAuthor(this);
        product.addUserReview(newReview);
        reviews.add(newReview);
    }

    /**
     * A method to allow users to follow eachother.
     * @param user - the user to be followed
     */
    public void followUser(UserEntity user)
    {
        if(this.following.contains(user) || this.equals(user))
            return;

        this.following.add(user);
        user.addFollower(this);
    }

    /**
     * A method to allow users to follow eachother.
     * @param user - the user to be followed
     */
    public void unfollowUser(UserEntity user)
    {
        if(!this.following.contains(user) || this.equals(user))
            return;

        this.following.remove(user);
        user.removeFollower(this);
    }

    /**
     * @param user The user to check if they are a follower
     * @return True if the specified user is following this user
     */
    public boolean isFollower(UserEntity user)
    {
        return this.followers.contains(user);
    }

    /**
     * @param user The user to check if they are being followed
     * @return True if the specified this user is following the specified user
     */
    public boolean isFollowing(UserEntity user)
    {
        return this.following.contains(user);
    }

    /**
     * Removes a Follower
     * @param userEntity The user to remove from followers
     */
    private void removeFollower(UserEntity userEntity)
    {
        if(!this.followers.contains(userEntity) || this.equals(userEntity))
            return;

        this.followers.remove(userEntity);
    }

    /**
     * A method to add a user to a User's list of followers
     * @param user
     */
    private void addFollower(UserEntity user)
    {
        if(this.followers.contains(user) || this.equals(user))
            return;

        followers.add(user);
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


    /**
     * Get the authorization role of the user
     * @return The authorization role
     */
    public UserRole getAuthorizationRole() {
        return authorizationRole;
    }

    /**
     * Set the authorization role of the user
     * @param authorizationRole
     */
    public void setAuthorizationRole(UserRole authorizationRole) {
        this.authorizationRole = authorizationRole;
    }

    /**
     * Checks to see if two UserEntity Objects are equal
     * @param o The object to compare
     * @return True if o is a user entity and shares the same id and username as this user
     */
    @Override
    public boolean equals(Object o)
    {
        if(o == this)
            return true;

        if(!(o instanceof UserEntity))
            return false;

        UserEntity other = (UserEntity) o;

        return other.id == this.id && other.username.equals(this.username);
    }
}
