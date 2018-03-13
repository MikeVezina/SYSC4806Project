package com.sysc4806.project.controllers;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import com.sysc4806.project.security.SecurityService;
import com.sysc4806.project.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController{

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserEntityRepository userRepo;

    @GetMapping("/registration")
    public String registration(UserEntity userEntity) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid UserEntity user, BindingResult bindingResult, Model model) {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            bindingResult.rejectValue("username", "error.username", "alreadyTaken");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
