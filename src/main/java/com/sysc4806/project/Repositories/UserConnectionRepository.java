package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.social.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserConnectionRepository extends JpaRepository<UserConnection, Long>
{
    List<UserConnection> findByUserConnectionKeyUserId(String userId);
}
