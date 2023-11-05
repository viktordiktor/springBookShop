package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.UserRepository;
import com.nikonenko.springBookShop.secutiry.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername() {
        // Setup
        final User user = new User();
        user.setEmail("username");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(1);
        final Person person = new Person();
        user.setPerson(person);
        final UserDetails expectedResult = new UserDetails(user);


        when(mockUserRepository.findByEmail("username")).thenReturn(Optional.of(user));

        // Run the test
        final UserDetails result = userServiceUnderTest.loadUserByUsername("username");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testLoadUserByUsername_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByEmail("username")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceUnderTest.loadUserByUsername("username"))
                .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure UserRepository.findAll(...).
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(0);
        final Person person = new Person();
        user.setPerson(person);
        final List<User> users = List.of(user);
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        final List<User> result = userServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure UserRepository.findById(...).
        final User user1 = new User();
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setRole("role");
        user1.setId_user(0);
        final Person person = new Person();
        user1.setPerson(person);
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findById(0)).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.findOne(0);

        // Verify the results
    }

    @Test
    void testFindOne_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(0);
        final Person person = new Person();
        user.setPerson(person);

        // Run the test
        userServiceUnderTest.save(user);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final User updatedUser = new User();
        updatedUser.setEmail("email");
        updatedUser.setPassword("password");
        updatedUser.setRole("role");
        updatedUser.setId_user(0);
        final Person person = new Person();
        updatedUser.setPerson(person);

        final Person person1 = new Person();
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(0);
        person1.setUser(user);

        // Run the test
        userServiceUnderTest.update(0, updatedUser, person1);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        userServiceUnderTest.delete(0);

        // Verify the results
        verify(mockUserRepository).deleteById(0);
    }
}
