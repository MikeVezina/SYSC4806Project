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
public class UserEntityRepositoryTest {

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserEntityRepository userRepo;

    Product product;
    UserEntity userEntity;

    @Before
    public void setUp()throws Exception {
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
    public void grab() throws Exception {
        UserEntity aUser = userRepo.findOne(userEntity.getId());
        assertNotNull(aUser);
    }

    @Test
    public void testFake() throws Exception {
        assertNull(userRepo.findOne((long) 100000));
    }

    @Test
    public void grabProduct() throws Exception {
        assertNotNull(productRepo.findOne(product.getId()));
    }

    @Test
    public void grabReview() throws Exception {
        assertNotNull(reviewRepo.findOne(userEntity.getId()));
    }

    @Test
    public void findByUser() throws Exception{
        assertNotNull(userRepo.findByUsername("test"));
    }
}
