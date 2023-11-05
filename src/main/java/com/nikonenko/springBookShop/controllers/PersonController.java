package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.secutiry.UserDetails;
import com.nikonenko.springBookShop.services.PersonService;
import com.nikonenko.springBookShop.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PersonController {
    public final UserService userService;
    public final PersonService personService;

    public PersonController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute Person updatedPerson){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userDetails.user();

        updatedPerson.setUser(user);
        personService.update(id, updatedPerson);

        return "redirect:/users/profile";
    }
}
