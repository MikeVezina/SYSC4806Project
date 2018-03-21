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
import java.util.List;

import static org.junit.Assert.*;


@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
@SpringBootTest(classes = {WebSecurityConfig.class, UserSecurityService.class, UserAuthentication.class})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@Transactional
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProductRepositoryTest {

    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_URL = "TestURL";
    @Autowired
    ProductRepository repo;
  
    @Autowired
    UserEntityRepository userRepo;

    @Autowired
    ReviewRepository reviewRepo;

    private Product product;
    private UserEntity userEntity;


    @Before
    public void setUp() {
        product = new Product(Category.BOOKS, PRODUCT_NAME, PRODUCT_URL);
        userEntity = new UserEntity();
        userEntity.writeReview(product, 3);
        repo.save(product);
    }

    @After
    public void tearDown() {
        userRepo.delete(userEntity.getId());
    }

    @Test
    public void testFake() throws Exception {
        assertNull(repo.findOne((long) 100000));
    }

    @Test
    public void testFindReview() throws Exception {
        assertNotNull(reviewRepo.findOne(product.getReviews().get(0).getId()));
    }

    public void testFindByURL() throws Exception {
        // Test retrieve data
        Assert.assertEquals(product, repo.findByUrlIgnoreCase(product.getUrl()));
    }

    @Test
    public void testFindProduct() throws Exception {
        // Test repo.findOne by ID
        Assert.assertEquals(product, repo.findOne(product.getId()));
    }

    @Test
    public void testSave() throws Exception {
        Assert.assertTrue("Ensure ID was set correctly", product.getId() > 0);

        // Test repo.findOne by ID
        Assert.assertTrue(repo.findAll().contains(product));
    }

}
