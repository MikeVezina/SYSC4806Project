package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.security.UserAuthentication;
import com.sysc4806.project.security.UserSecurityService;
import com.sysc4806.project.security.WebSecurityConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
@SpringBootTest(classes = {WebSecurityConfig.class, UserSecurityService.class, UserAuthentication.class})
@RunWith(SpringRunner.class)
@DataJpaTest
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
        product = new Product(Category.BOOKS, PRODUCT_NAME, PRODUCT_URL);
        userEntity = new UserEntity("test");
        productRepo.save(product);

        review = new Review(product, userEntity, 1);
        review.setAuthor(userEntity);

        userEntity.getReviews().add(review);
        product.getReviews().add(review);
        reviewRepo.save(review);
    }

    @Test
    public void testFindOne() throws Exception {
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
