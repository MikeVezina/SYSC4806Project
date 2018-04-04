package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsernameIgnoreCase(String username);
    List<UserEntity> findAllByUsernameContainsIgnoreCase(String username);
}
