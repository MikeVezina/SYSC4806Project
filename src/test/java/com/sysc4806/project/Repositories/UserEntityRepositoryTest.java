package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.models.UserEntity;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@RunWith(SpringRunner.class)
@Rollback
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest
@SpringBootTest(classes = App.class)
@TestPropertySource(locations = "classpath:test.properties")
public class UserEntityRepositoryTest {

    @Autowired
    UserEntityRepository repo;

    @Test
    public void myTest() throws Exception {
        UserEntity userEntity = new UserEntity("test");
        repo.save(userEntity);
    }
}
