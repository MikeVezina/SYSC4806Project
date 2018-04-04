package com.sysc4806.project.controllers;

import com.sysc4806.project.models.UserRole;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class AdminControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Mock
    private UserDetails adminUserDetails;

    @Mock
    private UserDetails memberUserDetails;

    private MockMvc mvc;

    private static final String ADMIN_USERNAME = "Michael";
    private static final String MEMBER_USERNAME = "member";
    private static final GrantedAuthority LOGGED_IN_ROLE = new SimpleGrantedAuthority("loggedIn");

    @Before
    public void setup()
    {
        adminUserDetails = Mockito.mock(UserDetails.class);
        memberUserDetails = Mockito.mock(UserDetails.class);
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        mockUserDetails(adminUserDetails, ADMIN_USERNAME, LOGGED_IN_ROLE, new SimpleGrantedAuthority(UserRole.ADMIN.name()));
        mockUserDetails(memberUserDetails, MEMBER_USERNAME, LOGGED_IN_ROLE);
    }

    /**
     * Mock user detail methods
     * @param userDetails The UserDetails object to mock
     * @param username The user name of the object
     * @param grantedAuthorities The authorities of the User
     */
    private void mockUserDetails(UserDetails userDetails, String username, GrantedAuthority... grantedAuthorities)
    {
        Mockito.when(userDetails.isEnabled()).thenReturn(true);
        Mockito.when(userDetails.getUsername()).thenReturn(username);
        Mockito.when(userDetails.getPassword()).thenReturn("fake_password");
        Mockito.when(userDetails.getAuthorities()).thenReturn((Collection)Arrays.asList(grantedAuthorities));
    }

    @Test
    public void testAdminControlPanel() throws Exception
    {
        // Ensure the proper status code is returned when the end point is called with various user roles
        mvc.perform(get("/admin")).andExpect(redirectedUrlPattern("**/login")).andDo(print());
        mvc.perform(get("/admin").with(user(memberUserDetails))).andExpect(status().is(HttpStatus.FORBIDDEN.value())).andDo(print());
        mvc.perform(get("/admin").with(user(adminUserDetails))).andExpect(status().isOk()).andDo(print());
    }

}
