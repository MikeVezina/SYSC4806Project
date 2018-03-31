package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class RegistrationController{

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserEntityRepository userRepo;

    private ProviderSignInUtils signInUtils;

    @Autowired
    public RegistrationController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository) {
        signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @GetMapping("/registration")
    public String registration(UserEntity userEntity, WebRequest request) {
        Connection<?> connection = signInUtils.getConnectionFromSession(request);

        if (connection != null) {
            userEntity.setUsername(connection.getDisplayName());
        }
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid UserEntity user, BindingResult bindingResult, Model model, WebRequest request) {
        if(userRepo.findByUsernameIgnoreCase(user.getUsername().toLowerCase()) != null) {
            bindingResult.rejectValue("username", "error.username", "alreadyTaken");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        Connection<?> connection = signInUtils.getConnectionFromSession(request);

        if (connection != null) {
            signInUtils.doPostSignUp(user.getUsername(), request);
        }

        registrationService.registerUser(user);



        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(UsernamePasswordAuthenticationToken token)
    {
            if(token != null)
                return "redirect:/";
            else
                return "login";
    }

}
