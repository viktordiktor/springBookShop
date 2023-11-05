package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Cart;
import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.CartRepository;
import com.nikonenko.springBookShop.repositories.PersonRepository;
import com.nikonenko.springBookShop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PersonRepository personRepository,
                               CartRepository cartRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        personRepository.save(new Person(user, "", ""));
        cartRepository.save(new Cart(user, new ArrayList<>()));
        userRepository.save(user);
    }
}
