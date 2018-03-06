package com.sysc4806.project.Repositories;

import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repo;

    @Test
    public void myTest() throws Exception {
        Product product = new Product(Category.BOOKS);
        product.setAvgRating(2);
        repo.save(product);
    }

}
