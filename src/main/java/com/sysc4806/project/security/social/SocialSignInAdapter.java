package com.sysc4806.project.security.social;

import com.sysc4806.project.Repositories.UserConnectionRepository;
import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.social.UserConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;

@Component
public class SocialSignInAdapter implements org.springframework.social.connect.web.SignInAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserConnectionRepository connectionRepository;

    @Autowired
    private UserEntityRepository repo;

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest nativeWebRequest) {

        UserEntity userEntity = repo.findByUsernameIgnoreCase(userId);

        if (userEntity == null) {
            // If the user entity no longer exists, remove all social links to the account
            List<UserConnection> connectionList = connectionRepository.findByUserConnectionKeyUserId(userId);

            connectionRepository.delete(connectionList);

            // Return null (do not redirect)
            return null;
        }

        // Retrieve the details of the SSO user account
        UserDetails details = userDetailsService.loadUserByUsername(userEntity.getUsername());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities()));

        // Return null (do not redirect)
        return null;

    }
}
