package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "review", path = "review")
public interface ReviewRepository extends JpaRepository<Review, Long>{

}
