package com.sysc4806.project.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReviewTest {

    private static final String TEST_PRODUCT_URL = "test_product_url";
    private static final String TEST_PRODUCT_NAME = "test_product_name";
    private static final String TEST_USER = "test_user";

    //Test Variables
    private Product testProduct;
    private UserEntity testUserEntity;
    private Review testReview;

    @Before
    public void setup()
    {
        testProduct = new Product(TEST_PRODUCT_NAME, TEST_PRODUCT_URL);
        testUserEntity = new UserEntity(TEST_USER);
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
