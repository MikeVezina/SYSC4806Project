package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user_entity", path = "user_entity")
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

}
