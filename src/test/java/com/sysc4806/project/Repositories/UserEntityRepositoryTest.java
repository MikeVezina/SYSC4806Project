package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@databaseTest
public class UserEntityRepositoryTest {

    @Autowired
    UserEntityRepository repo;

    @Test
    public void myTest() throws Exception {
        UserEntity userEntity = new UserEntity("test");
        repo.save(userEntity);
    }
}
