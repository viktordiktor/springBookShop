package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Cart;
import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.CartRepository;
import com.nikonenko.springBookShop.repositories.PersonRepository;
import com.nikonenko.springBookShop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private CartRepository mockCartRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private RegistrationService registrationServiceUnderTest;

    @BeforeEach
    void setUp() {
        registrationServiceUnderTest = new RegistrationService(mockUserRepository, mockPersonRepository,
                mockCartRepository, mockPasswordEncoder);
    }

    @Test
    void testRegister() {
        // Setup
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(0);
        final Person person = new Person();
        user.setPerson(person);

        when(mockPasswordEncoder.encode("password")).thenReturn("password");

        // Run the test
        registrationServiceUnderTest.register(user);

        // Verify the results
        verify(mockPersonRepository).save(any(Person.class));
        verify(mockCartRepository).save(any(Cart.class));
        verify(mockUserRepository).save(any(User.class));
    }
}
