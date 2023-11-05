package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.PersonRepository;
import com.nikonenko.springBookShop.repositories.UserRepository;
import com.nikonenko.springBookShop.secutiry.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new UserDetails(user.get());
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(int id){
        return userRepository.findById(id);
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser, Person person){
        updatedUser.setId_user(id);
        updatedUser.setPassword(new BCryptPasswordEncoder().encode(updatedUser.getPassword()));
        updatedUser.setPerson(person);
        userRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id){
        userRepository.deleteById(id);
    }
}
