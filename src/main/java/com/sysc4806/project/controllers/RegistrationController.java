package com.sysc4806.project.controllers;

import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.security.SecurityService;
import com.sysc4806.project.security.UserValidator;
import com.sysc4806.project.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute( "UserEntity", new UserEntity());
        System.out.println("register page");
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserEntity user, BindingResult bindingResult, Model model) {
        System.out.println("here");
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return "login";
        }

        registrationService.registerUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        if (securityService.checkAccess() != null){
            model.addAttribute("loggedIn");
        }

        return "login";
    }

}
