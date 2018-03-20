package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.method.P;

import static org.junit.Assert.*;

public class UserEntityTest {

    public static final String USER_NAME_1 = "user1";
    public static final String USER_NAME_2 = "user2";

    //Test variables
    private Product testProduct;
    private UserEntity testUserEntity1;
    private UserEntity testUserEntity2;
    @Before
    public void  setup(){
        testProduct = new Product();
        testUserEntity1 = new UserEntity(USER_NAME_1);
        testUserEntity2 = new UserEntity(USER_NAME_2);
        testUserEntity1.writeReview(testProduct,3);
        testUserEntity2.followUser(testUserEntity1);
    }

    @Test
    public void testUserReviews(){
        assertNotNull("A UserEntity should not have a null list of reviews", testUserEntity1.getReviews());
        assertTrue("A user review should be added to their list of reviews", testUserEntity1.getReviews().size() == 1);
    }

    @Test
    public void testUsername(){
        assertNotNull("A UserEntity must have a username", testUserEntity1.getUsername());
        assertTrue("A Username must be larger then 1 character", testUserEntity1.getUsername().length() > 1);
        assertTrue("A Username must be smaller then 32 characters", testUserEntity1.getUsername().length() < 32);
    }

    @Test
    public void testFollowers(){
        assertNotNull("A UserEntity must not have a null list of followers", testUserEntity1.getFollowers());
        assertTrue("When a UserEntity follows another, the UserEntity should be added to the other's list of followers", testUserEntity1.getFollowers().contains(testUserEntity2));
    }

    @Test
    public void testFollowing(){
        assertNotNull("A UserEntity must not have a null list of following", testUserEntity1.getFollowing());
        assertTrue("When a UserEntity follows another, the other should be added to the UserEntity's list of following", testUserEntity2.getFollowing().contains(testUserEntity1));
    }
}
