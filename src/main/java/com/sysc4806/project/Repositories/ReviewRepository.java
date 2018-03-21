package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>
{
}
