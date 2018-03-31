package com.sysc4806.project.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

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
public class UserEntity implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity=Review.class, mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Review> reviews;

    @Size(min=1, max=32)
    @Column(unique = true)
    private String username;

    @Size(min=8)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_relations",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<UserEntity> followers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,  mappedBy = "followers")
    private Set<UserEntity> following;

    private UserRole authorizationRole;

    /**
     * Constructor of a new user to be persisted in the
     * application.
     * @param n - The username of the new user
     */
    public UserEntity(String n){
        this.reviews = new HashSet<>();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
        this.authorizationRole = UserRole.MEMBER;
        this.username = n;
    }

    /**
     * default constructor for database.
     */
    private UserEntity(){
        this("");
    }


    /**
     * A method for creating a new User Review
     * @param product - The product the review is about
     * @param rating - The rating of the product
     */

    public Review writeReview(Product product, int rating, String comment)
    {
        Review review = product.getReviews().stream().filter(r -> r.getAuthor().equals(this)).findFirst().orElse(new Review(product, this, rating, comment));

        review.setRating(rating);
        review.setComment(comment);

        // Create a new review on product
        product.addUserReview(review);
        reviews.add(review);

        return review;
    }

    /**
     * A method for creating a new User Review
     * @param product - The product the review is about
     * @param rating - The rating of the product
     */
    public Review writeReview(Product product, int rating)
    {
        return writeReview(product, rating, "");
    }

    /**
     * A method to allow users to follow each other.
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
     * Calculates the Jaccard index of similar reviews between two users
     * @param otherUser The other user
     * @return The Jaccard Coefficient/index (A value between 0 and 1).
     */
    private double calculateJaccardIndex(UserEntity otherUser)
    {
        // Total of all reviews
        long unionSize = otherUser.reviews.size() + this.reviews.size();
        long intersectionSize = 0;

        // Filter all reviews that are for the same product and are the same rating
        for(Review otherReview : otherUser.reviews)
            intersectionSize += this.reviews.stream().filter(review -> review.getProduct().equals(otherReview.getProduct()) && review.getRating() == otherReview.getRating()).count();

        // Will return between 0 and 1
        return ((double)intersectionSize / (double)unionSize);
    }

    /**
     * Calculates the Jaccard distance of similar reviews between two users
     * @param otherUser The other user
     * @return A value between 0 and 1. 1 being the furthest possible distance
     */
    public double calculateJaccardDistance(UserEntity otherUser)
    {
        return 1 - calculateJaccardIndex(otherUser);
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
    public boolean hasFollower(UserEntity user)
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
    public Set<Review> getReviews() { return reviews; }

    /**
     * Setter method for the UserEntity's review list
     * @param reviews - new UserEntity review list
     */
    private void setReviews(Set<Review> reviews) { this.reviews = reviews; }

    /**
     * Getter method for the UserEntity's followers
     * @return  - List of the UserEntity's followers
     */
    public Set<UserEntity> getFollowers() { return followers; }

    /**
     * Setter method for the UserEntity's followers
     * @param followers - new UserEntity follower list
     */
    private void setFollowers(Set<UserEntity> followers) { this.followers = followers; }

    /**
     * Getter method for the UserEntity's following list
     * @return - UserEntity's following list
     */
    public Set<UserEntity> getFollowing() { return following; }

    /**
     * Setter method for the product's category
     * @param following - product's new category
     */
    private void setFollowing(Set<UserEntity> following) { this.following = following; }

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
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (!(o instanceof UserEntity))
            return false;

        UserEntity other = (UserEntity) o;

        if(this.id > 0 && other.id > 0)
            return other.id == this.id;

        return other.username.equals(this.username);
    }


    @Override
    public int compareTo(Object compareUser) {
        if(compareUser == this)
            return 0;

        if(!(compareUser instanceof UserEntity))
            return 1;

        int compareFollowers = ((UserEntity)compareUser).getFollowers().size();
        return compareFollowers - this.followers.size();
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
