package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.After;
import com.sysc4806.project.security.UserAuthentication;
import com.sysc4806.project.security.UserSecurityService;
import com.sysc4806.project.security.WebSecurityConfig;
import org.junit.Assert;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
@SpringBootTest(classes = {WebSecurityConfig.class, UserSecurityService.class, UserAuthentication.class})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class ReviewRepositoryTest {

    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_URL = "myUrl";
    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserEntityRepository userRepo;

    private Product product;
    private UserEntity userEntity;
    private Review review;

    @Before
    public void setup()
    {
        product = new Product(Category.BOOKS, "tested");
        userEntity = new UserEntity("test");
        review = new Review(product, userEntity, 1);

        userEntity.getReviews().add(review);
        product.getReviews().add(review);
        reviewRepo.save(review);
    }

    @After
    public void tearDown() throws Exception {
        userRepo.delete(userEntity.getId());
    }

    @Test
    public void testFake() throws Exception {
        assertNull(reviewRepo.findOne((long) 100000));
    }

    @Test
    public void testFindProduct() throws Exception {
        assertNotNull(productRepo.findOne(product.getId()));
    }

    @Test
    public void testFindUser() throws Exception {
        assertNotNull(userRepo.findOne(userEntity.getId()));
    }
      
    @Test
    public void testFindReview() throws Exception {
        // Test repo.findOne by ID
        Assert.assertEquals(review, reviewRepo.findOne(review.getId()));
    }

    @Test
    public void testSave() throws Exception {
        Assert.assertTrue("Ensure ID was set correctly", review.getId() > 0);

        // Test repo.findOne by ID
        Assert.assertTrue(reviewRepo.findAll().contains(review));
    }
}
