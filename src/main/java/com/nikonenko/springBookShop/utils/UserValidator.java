package com.nikonenko.springBookShop.utils;

import com.nikonenko.springBookShop.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserValidator(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        try{
            userDetailsService.loadUserByUsername(user.getEmail());
        } catch(UsernameNotFoundException e){
            return; //Все хорошо
        }
        errors.rejectValue("email", "", "User already exists");
    }
}