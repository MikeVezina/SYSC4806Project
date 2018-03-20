package com.sysc4806.project.Repositories;

import com.sysc4806.project.enumeration.Category;
import com.sysc4806.project.models.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@databaseTest
public class ProductRepositoryTest {

    @Autowired
    ProductRepository repo;

    @Test
    public void myTest() throws Exception {
        Product product = new Product(Category.BOOKS, "TestURL");
        repo.save(product);
    }

}
