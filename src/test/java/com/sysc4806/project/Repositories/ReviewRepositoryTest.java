package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserEntityRepository userRepo;

    Product product;
    Review review;
    UserEntity userEntity;

    @Before
    public void setUp() throws Exception {
        product = new Product(Category.BOOKS, "tested");
        userEntity = new UserEntity("test");
        review = new Review(product,1);
        review.setAuthor(userEntity);
        userEntity.getReviews().add(review);
        product.getReviews().add(review);
        reviewRepo.save(review);
    }

    @After
    public void tearDown() throws Exception {
        userRepo.delete(userEntity.getId());
    }

    @Test
    public void grab() throws Exception {
        Review aReview = reviewRepo.findOne(review.getId());
        assertNotNull(aReview);
    }

    @Test
    public void testFake() throws Exception {
        assertNull(reviewRepo.findOne((long) 100000));
    }

    @Test
    public void grabProduct() throws Exception {
        assertNotNull(productRepo.findOne(product.getId()));
    }

    @Test
    public void grabUser() throws Exception {
        assertNotNull(userRepo.findOne(userEntity.getId()));
    }


}
