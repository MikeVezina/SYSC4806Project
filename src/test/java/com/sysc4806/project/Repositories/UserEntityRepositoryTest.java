package com.sysc4806.project.Repositories;

import com.sysc4806.project.models.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserEntityRepositoryTest {

    @Autowired
    UserEntityRepository repo;

    @Test
    public void myTest() throws Exception {
        UserEntity userEntity = new UserEntity("test");
        repo.save(userEntity);
    }
}