package com.sysc4806.project.security;

import com.sysc4806.project.Repositories.UserEntityRepository;
import com.sysc4806.project.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {


    private final UserEntityRepository userRepo;

    @Autowired
    public UserValidator(UserEntityRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserEntity user = (UserEntity) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "No Whitespace or Empty Username");
        if(user.getUsername().length() < 1 || user.getUsername().length() > 32){
            errors.rejectValue("username", "userForm.invalidSize");
        }
        if(userRepo.findByUsername(user.getUsername()) != null){
            errors.rejectValue("username", "userForm.alreadyTaken");
        }
        ValidationUtils.rejectIfEmpty(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "userForm.passwordTooSmall");
        }
    }
}
