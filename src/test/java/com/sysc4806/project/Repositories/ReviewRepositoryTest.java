package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.Review;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.security.UserAuthentication;
import com.sysc4806.project.security.UserSecurityService;
import com.sysc4806.project.security.WebSecurityConfig;
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

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserEntityRepository userRepo;


    @Test
    public void myTest() throws Exception {
        Product product = new Product(Category.BOOKS, "myUrl");
        UserEntity userEntity = new UserEntity("test");
        Review review = new Review(product,1);
        review.setAuthor(userEntity);
        userEntity.getReviews().add(review);
        product.getReviews().add(review);

        reviewRepo.save(review);
    }

}
