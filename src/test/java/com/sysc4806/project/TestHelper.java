package com.sysc4806.project;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.UserRole;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class TestHelper {
    private static final String DEFAULT_PASSWORD = "test_password";

    @Autowired
    private UserEntityRepository userRepo;

    /**
     * Get user entity from repo, or create (and save) a new user entity for testing
     * @param userName The user name of the user entity
     * @return The loaded or new user entity
     */
    public UserEntity createUserEntity(String userName, String password)
    {
        UserEntity repoUser = userRepo.findByUsernameIgnoreCase(userName);

        if(repoUser == null) {
            repoUser = new UserEntity(userName);
            repoUser.setPassword(password);
            repoUser.setAuthorizationRole(UserRole.MEMBER);
            userRepo.save(repoUser);
        }

        return repoUser;
    }

    /**
     * Get user entity from repo, or create (and save) a new user entity for testing
     * @param userName The user name of the user entity
     * @return The loaded or new user entity
     */
    public UserEntity createUserEntity(String userName)
    {
        return createUserEntity(userName, DEFAULT_PASSWORD);
    }

    /**
     * Create mocked user details
     * @param userDetails The user details object
     * @param username The username of the user
     * @param grantedAuthorities The authorities of the user
     */
    public void mockUserDetails(UserDetails userDetails, String username, String password, GrantedAuthority... grantedAuthorities)
    {
        Mockito.when(userDetails.isEnabled()).thenReturn(true);
        Mockito.when(userDetails.getUsername()).thenReturn(username);
        Mockito.when(userDetails.getPassword()).thenReturn(password);
        Mockito.when(userDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(grantedAuthorities));
    }

    /**
     * Create mocked user details
     * @param userDetails The user details object
     * @param username The username of the user
     * @param grantedAuthorities The authorities of the user
     */
    public void mockUserDetails(UserDetails userDetails, String username, GrantedAuthority... grantedAuthorities)
    {
        mockUserDetails(userDetails, username, DEFAULT_PASSWORD, grantedAuthorities);
    }

}
