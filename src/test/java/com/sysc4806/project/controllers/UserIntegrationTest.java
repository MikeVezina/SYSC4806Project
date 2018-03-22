package com.sysc4806.project.controllers;

import com.sysc4806.project.TestHelper;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.UserRole;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class UserIntegrationTest {

    private static final String TEST_USER_1 = "test_user_1";
    private static final String TEST_USER_2 = "test_user_2";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private TestHelper testHelper;

    private UserEntity testUser1;
    private UserEntity testUser2;
    private UserDetails testUserDetails;

    @Before
    public void setup()
    {
        testUserDetails = Mockito.mock(UserDetails.class);

        // Create a token for test user 1 only
        testHelper.mockUserDetails(testUserDetails, TEST_USER_1, UserRole.MEMBER);

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        loadUsersFromRepo();
    }


    private void loadUsersFromRepo()
    {
        testUser1 = testHelper.createUserEntity(TEST_USER_1);
        testUser2 = testHelper.createUserEntity(TEST_USER_2);
    }

    /**
     * Tests the user page results
     * @throws Exception
     */
    @Test
    public void testUserEndpoints() throws Exception
    {
        // Ensure the user view is returned when a logged in user attempts to access a user page
        mvc.perform(get("/user").with(user(testUserDetails))).andExpect(status().isOk()).andExpect(view().name("user")).andExpect(model().attributeExists("user", "loggedInUser")).andDo(print());

        // Ensure we get redirected to login page when no user token is provided
        mvc.perform(get("/user")).andExpect(status().isFound()).andExpect(redirectedUrlPattern("**/login")).andDo(print());

        // Ensure that user/{currentUserId} redirects to /user
        mvc.perform(get("/user/" + testUser1.getId()).with(user(testUserDetails))).andExpect(redirectedUrl("/user")).andDo(print());
    }

    /**
     * Tests the follow/unfollow post requests
     * @throws Exception
     */
    @Test
    public void testFollowActionEndpoints() throws Exception
    {
        // Ensure that user can follow another user
        mvc.perform(post("/user/follow").with(csrf()).param("userId", String.valueOf(testUser2.getId())).with(user(testUserDetails))).andExpect(redirectedUrl("/user/" + testUser2.getId())).andDo(print());

        // Reload from repo
        loadUsersFromRepo();

        // Assert that user1 is following user2
        Assert.assertTrue("Test user 1 must be following test user 2",  testUser1.isFollowing(testUser2));

        // Ensure that user can NOT follow themselves
        mvc.perform(post("/user/follow").with(csrf()).param("userId", String.valueOf(testUser1.getId())).with(user(testUserDetails))).andExpect(redirectedUrl("/user/" + testUser1.getId())).andDo(print());

        // Ensure that user can unfollow another user
        mvc.perform(post("/user/unfollow").with(csrf()).param("userId", String.valueOf(testUser2.getId())).with(user(testUserDetails))).andExpect(redirectedUrl("/user/" + testUser2.getId())).andDo(print());

        // Reload from repo
        loadUsersFromRepo();

        // Assert that user1 is following user2
        Assert.assertTrue("Test user 1 must NOT be following test user 2", !testUser1.isFollowing(testUser2));
    }
}
