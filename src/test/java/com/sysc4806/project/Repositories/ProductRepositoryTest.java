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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@Transactional
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repo;

    @Autowired
    UserEntityRepository userRepo;

    @Autowired
    ReviewRepository reviewRepo;

    Product product;
    UserEntity userEntity;


    @Before
    public void setUp() {
        product = new Product(Category.BOOKS, "TestURL");
        userEntity = new UserEntity();
        userEntity.writeReview(product, 3);
        repo.save(product);
    }

    @After
    public void tearDown() {
        userRepo.delete(userEntity.getId());
    }

    @Test
    public void save() throws Exception {
        Product productTEMP = new Product(Category.BOOKS, "Tested");
        repo.save(product);
    }

    @Test
    public void grab() throws Exception {
        Product aProduct = repo.findOne(product.getId());
        assertNotNull(aProduct);
    }

    @Test
    public void testFake() throws Exception {
        assertNull(repo.findOne((long) 100000));
    }

    @Test
    public void grabReview() throws Exception {
        assertNotNull(reviewRepo.findOne(product.getReviews().get(0).getId()));
    }

    @Test
    public void grabUser() throws Exception {
        assertNotNull(userRepo.findOne(userEntity.getId()));
    }

}
