package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "product", path = "product")
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Product findByUrlIgnoreCase(String url);
}
