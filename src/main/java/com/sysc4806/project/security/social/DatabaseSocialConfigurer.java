package com.sysc4806.project.security.social;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseSocialConfigurer extends SocialConfigurerAdapter {

    private DataSource source;

    public DatabaseSocialConfigurer(DataSource source) {
        this.source = source;
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(source, connectionFactoryLocator, Encryptors.noOpText());
    }

    @Bean
    @Override
    public UserIdSource getUserIdSource() {
        return () -> {
            Authentication curUser = SecurityContextHolder.getContext().getAuthentication();
            return (curUser == null ? null : curUser.getName());
        };
    }
}
