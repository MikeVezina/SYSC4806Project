package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReviewTest {

    //Test Variables
    private Product testProduct;
    private UserEntity testUserEntity;
    private Review testReview;

    @Before
    public void setup()
    {
        testProduct = new Product();
        testUserEntity = new UserEntity();
        testReview = testUserEntity.writeReview(testProduct, 4);
    }

    @Test
    public void testAuthor(){
        assertEquals("A Review must maintain reference to it author", testUserEntity, testReview.getAuthor());
    }

    @Test
    public void testProduct(){
        assertEquals("A Review must maintain reference to it product", testProduct, testReview.getProduct());
        assertTrue("A Product must maintain a reference to its reviews", testProduct.getReviews().contains(testReview));
    }
}
