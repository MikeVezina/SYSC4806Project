package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product findByUrlIgnoreCase(String url);
}
