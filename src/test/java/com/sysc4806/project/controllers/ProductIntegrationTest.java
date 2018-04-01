package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.ProductRepository;
import com.sysc4806.project.TestHelper;
import com.sysc4806.project.models.Product;
import com.sysc4806.project.models.UserRole;
import org.flywaydb.test.annotation.FlywayTest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@FlywayTest
@TestPropertySource(locations = "classpath:test.properties")
public class ProductIntegrationTest {

    private static final String TEST_USER_1 = "test_user_1";
    private static final String TEST_PASSWORD = "fake_password";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private TestHelper testHelper;

    @Autowired
    private ProductRepository productRepo;

    private UserDetails testUserDetails;
    private Product product;

    @Before
    public void setup()
    {
        testUserDetails = Mockito.mock(UserDetails.class);

        // Create test product and save to repo
        product = new Product("test_product", "test_product_url");
        productRepo.save(product);

        // Create test user and save to repo
        testHelper.createUserEntity(TEST_USER_1, TEST_PASSWORD);

        // Create a token for test user 1
        testHelper.mockUserDetails(testUserDetails, TEST_USER_1, TEST_PASSWORD, UserRole.MEMBER);

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

    }

    /**
     * Tests the product page results
     * @throws Exception
     */
    @Test
    public void testProductEndpoints() throws Exception
    {
        // Ensure the user view is returned when a logged in user attempts to access a user page
        mvc.perform(get("/products/" + product.getId()).with(user(testUserDetails))).andExpect(status().isOk()).andExpect(view().name("products")).andExpect(model().attributeExists("product")).andDo(print());

        // Ensure we get redirected to login page when no user token is provided
        mvc.perform(get("/products/" + product.getId())).andExpect(status().isFound()).andExpect(redirectedUrlPattern("**/login")).andDo(print());

        // Ensure we get a page not found when looking for a product with an invalid ID
        mvc.perform(get("/products/" + 0).with(user(testUserDetails))).andExpect(status().isNotFound()).andExpect(view().name("error/status")).andDo(print());

    }

    @Test
    public void testProductSearch() throws Exception
    {
        // Ensure the search view is returned when a logged in user attempts to access the search page
        mvc.perform(post("/products/search").param("searchTerm", "test").with(csrf()).with(user(testUserDetails))).andExpect(status().isOk()).andExpect(view().name("productSearch")).andExpect(model().attributeExists("products")).andDo(print());

        // Ensure we get redirected to login page when no user token is provided
        mvc.perform(post("/products/search").with(csrf())).andExpect(status().isFound()).andExpect(redirectedUrlPattern("**/login")).andDo(print());

        // Ensure we get a page not found when looking for a product with an invalid ID
        mvc.perform(post("/products/search").with(csrf()).with(user(testUserDetails))).andExpect(status().isNotFound()).andExpect(view().name("error/status")).andDo(print());

    }

}
