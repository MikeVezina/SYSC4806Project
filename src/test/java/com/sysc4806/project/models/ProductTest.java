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
    private UserEntity testUserEntity2;

    @Before
    public void setup() {
        testProduct = new Product("product_name","product_url_1");
        testProduct2 = new Product("product_name_2", "product_url_2");
        testUserEntity = new UserEntity("user_1");
        testUserEntity2 = new UserEntity("user_2");

        testUserEntity.writeReview(testProduct, 4, "Wow!");
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
        assertEquals("Product average rating should be initialized to 0", 0, testProduct2.getAverageRating());
        testUserEntity.writeReview(testProduct2,2, "Solid Meh!");
        assertEquals("Product average should be updated when a new review is written", 2,testProduct2.getAverageRating());
        testUserEntity2.writeReview(testProduct2, 4, "Would recommend to a friend");
        assertEquals("Product's average rating should reflect multiple ratings", (2 + 4 ) / 2, testProduct2.getAverageRating());

    }
}
