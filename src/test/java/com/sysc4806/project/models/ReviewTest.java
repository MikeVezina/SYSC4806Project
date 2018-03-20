package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReviewTest {

    //Test Variables
    private Product testProduct;
    private UserEntity testUserEntity;
    @Before
    public void setup()
    {
        testProduct = new Product();
        testUserEntity = new UserEntity();
        testUserEntity.writeReview(testProduct, 4);
    }

    @Test
    public void testAuthour(){
        assertEquals("A Review must maintain reference to it author", testUserEntity, testProduct.getReviews().get(0).getAuthor());
    }

    @Test
    public void testProduct(){
        assertEquals("A Review must maintain reference to it product", testProduct, testUserEntity.getReviews().get(0).getProduct());
    }
}
