package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.access.method.P;

import static org.junit.Assert.*;

public class UserEntityTest {

    public static final String USER_NAME_1 = "user1";
    public static final String USER_NAME_2 = "user2";
    public static final String USER_NAME_3 = "user3";
    public static final String USER_NAME_4 = "user4";

    //Test variables
    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;
    private Product testProduct4;

    private UserEntity testUserEntity1;
    private UserEntity testUserEntity2;
    private UserEntity testUserEntity3;
    private UserEntity testUserEntity4;

    @Before
    public void  setup(){
        testProduct1 = new Product("test_prod_1");
        testProduct2 = new Product("test_prod_2");
        testProduct3 = new Product("test_prod_3");
        testProduct4 = new Product("test_prod_4");

        testUserEntity1 = new UserEntity(USER_NAME_1);
        testUserEntity2 = new UserEntity(USER_NAME_2);
        testUserEntity3 = new UserEntity(USER_NAME_3);
        testUserEntity4 = new UserEntity(USER_NAME_4);

        // Write reviews for user 1
        testUserEntity1.writeReview(testProduct1,3);
        testUserEntity1.writeReview(testProduct2,3);
        testUserEntity1.writeReview(testProduct3,3);
        testUserEntity1.writeReview(testProduct4,3);

        // Write reviews for user 1
        testUserEntity2.writeReview(testProduct1,3);
        testUserEntity2.writeReview(testProduct2,4);
        testUserEntity2.writeReview(testProduct3,3);
        testUserEntity2.writeReview(testProduct4,6);

        // Write reviews for user 3
        testUserEntity3.writeReview(testProduct1,4);
        testUserEntity3.writeReview(testProduct2,4);
        testUserEntity3.writeReview(testProduct3,5);
        testUserEntity3.writeReview(testProduct4,6);

        // Write reviews for user 4
        testUserEntity4.writeReview(testProduct1,3);
        testUserEntity4.writeReview(testProduct2,4);
        testUserEntity4.writeReview(testProduct3,5);
        testUserEntity4.writeReview(testProduct4,6);


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
    public void testJaccardDistance()
    {
        assertEquals("Jaccard Distance with 2 similar reviews", 1 - ((double)2/8), testUserEntity1.calculateJaccardDistance(testUserEntity2), 0.0005);
        assertEquals("Jaccard Distance with 2 similar reviews", 1 - ((double)2/8), testUserEntity2.calculateJaccardDistance(testUserEntity1), 0.0005);
        assertEquals("Jaccard Distance with 0 similar reviews", 1 - ((double)0/8), testUserEntity1.calculateJaccardDistance(testUserEntity3), 0.0005);
        assertEquals("Jaccard Distance with 0 similar reviews", 1 - ((double)0/8), testUserEntity3.calculateJaccardDistance(testUserEntity1), 0.0005);
        assertEquals("Jaccard Distance with 1 similar review", 1 - ((double)1/8), testUserEntity1.calculateJaccardDistance(testUserEntity4), 0.0005);
        assertEquals("Jaccard Distance with 1 similar review", 1 - ((double)1/8), testUserEntity4.calculateJaccardDistance(testUserEntity1), 0.0005);
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
