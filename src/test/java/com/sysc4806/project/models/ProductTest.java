package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ProductTest
{
    //Test Variables
    private Product testProduct;
    private Product testProduct2;
    private UserEntity testUserEntity;
    @Before
    public void setup() {
        testProduct = new Product();
        testProduct2 = new Product();
        testUserEntity = new UserEntity();
        testUserEntity.writeReview(testProduct, 4);
    }

    @Test
    public void testUrl() {
        String testUrl = "newProduct";
        assertNotNull("Product must not have a null Url", testProduct.getUrl());
        testProduct.setUrl(testUrl);
        assertEquals("Product setUrl not working properly", testUrl, testProduct.getUrl());
    }

    @Test
    public void testReviewList()
    {
        assertNotNull("Product can not have a null review list", testProduct.getReviews());
        assertTrue("Review not properly added to Product review list", testProduct.getReviews().size() == 1);
    }

    @Test
    public void testProductRating(){
        assertTrue("Product average rating should be initialized to 0", testProduct2.getAvgRating()==0);
        testUserEntity.writeReview(testProduct2,2);
        assertTrue("Product average should be updated when a new review is written", testProduct2.getAvgRating() == 2);
        testUserEntity.writeReview(testProduct2,4);
        assertTrue("Product's average rating should be the average of it's review ratings", testProduct2.getAvgRating() == 3);
    }
}
