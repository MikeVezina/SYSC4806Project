package com.sysc4806.project.Repositories;

import com.sysc4806.project.App;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.security.UserAuthentication;
import com.sysc4806.project.security.UserSecurityService;
import com.sysc4806.project.security.WebSecurityConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = App.class)
@SpringBootTest(classes = {WebSecurityConfig.class, UserSecurityService.class, UserAuthentication.class})
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserEntityRepositoryTest {

    @Autowired
    UserEntityRepository repo;

    private UserEntity userEntity;

    @Before
    public void setUp()
    {
        userEntity = new UserEntity("test");
        repo.save(userEntity);
    }

    @Test
    public void testFindOne() throws Exception {
        // Test repo.findOne by ID
        Assert.assertEquals(userEntity, repo.findOne(userEntity.getId()));
    }

    @Test
    public void testSave() throws Exception {
        Assert.assertTrue("Ensure ID was set correctly", userEntity.getId() > 0);

        // Test repo.findOne by ID
        Assert.assertTrue(repo.findAll().contains(userEntity));
    }
}
