package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsernameIgnoreCase(String username);
}
