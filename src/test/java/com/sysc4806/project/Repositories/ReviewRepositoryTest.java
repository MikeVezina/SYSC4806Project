package com.sysc4806.project.Repositories;

import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:test.properties")
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserEntityRepository userRepo;

    @Test
    public void myTest() throws Exception {
        Product product = new Product(Category.BOOKS, "");
        UserEntity userEntity = new UserEntity("test");
        Review review = new Review(product,1);
        review.setAuthor(userEntity);
        userEntity.getReviews().add(review);
        product.getReviews().add(review);

        reviewRepo.save(review);
    }

}
