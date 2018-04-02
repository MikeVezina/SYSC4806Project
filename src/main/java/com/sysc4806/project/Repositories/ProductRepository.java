package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product findByUrlIgnoreCase(String url);

    List<Product> findAllByUrlContainsIgnoreCaseOrNameContainsIgnoreCase(String urlKeyword, String nameKeyword);
}
