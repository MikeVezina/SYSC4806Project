package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.After;
import com.sysc4806.project.models.UserEntity;
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
public class UserEntityRepositoryTest {

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserEntityRepository userRepo;

    private Product product;
    private UserEntity userEntity;

    @Before
    public void setUp()
    {
        userEntity = new UserEntity("test");
        product = new Product(Category.BOOKS, "tested");
        userEntity = new UserEntity("test");
        userEntity.writeReview(product, 5);
        userRepo.save(userEntity);
    }
  
    @After
    public void tearDown() throws Exception {
        userRepo.delete(userEntity.getId());
    }

    @Test
    public void testFake() throws Exception {
        assertNull(userRepo.findOne((long) 100000));
    }

    @Test
    public void testFindProduct() throws Exception {
        Assert.assertEquals(product, productRepo.findOne(product.getId()));
    }

    @Test
    public void testFindReview() throws Exception {
        Assert.assertEquals(review, reviewRepo.findOne(userEntity.getId()));
    }

    @Test
    public void testFindUser() throws Exception {
        // Test repo.findOne by ID
        Assert.assertEquals(userEntity, repo.findOne(userEntity.getId()));
    }

    @Test
    public void testSave() throws Exception {
        Assert.assertTrue("Ensure ID was set correctly", userEntity.getId() > 0);

        // Test repo.findOne by ID
        Assert.assertTrue(repo.findAll().contains(userEntity));
    }
}
