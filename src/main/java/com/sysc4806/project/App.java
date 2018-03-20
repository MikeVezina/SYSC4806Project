package com.sysc4806.project;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

/**
 * Start Spring boot application
 */
@SpringBootApplication
public class App 
{

    @Autowired
    UserEntityRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    /**
     * Generate default administrator accounts on startup
     */
    @PostConstruct
    public void init()
    {
        UserEntity userEntityMichael = new UserEntity("Michael");
        UserEntity userEntityAlex = new UserEntity("Alex");
        UserEntity userEntityReid = new UserEntity("Reid");

        userEntityMichael.setPassword(bCryptPasswordEncoder.encode("passw0rd"));
        userEntityAlex.setPassword(bCryptPasswordEncoder.encode("passw0rd"));
        userEntityReid.setPassword(bCryptPasswordEncoder.encode("passw0rd"));

        /* Set Roles */
        userEntityReid.setAuthorizationRole(UserRole.ADMIN);
        userEntityAlex.setAuthorizationRole(UserRole.ADMIN);
        userEntityMichael.setAuthorizationRole(UserRole.ADMIN);

        userRepo.save(userEntityMichael);
        userRepo.save(userEntityAlex);
        userRepo.save(userEntityReid);
    }

}
