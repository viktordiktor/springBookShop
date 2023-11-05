package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository mockPersonRepository;

    private PersonService personServiceUnderTest;

    @BeforeEach
    void setUp() {
        personServiceUnderTest = new PersonService(mockPersonRepository);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure PersonRepository.findAll(...).
        final Person person = new Person();
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        person.setUser(user);
        person.setId_user(0);
        final List<Person> people = List.of(person);
        when(mockPersonRepository.findAll()).thenReturn(people);

        // Run the test
        final List<Person> result = personServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_PersonRepositoryReturnsNoItems() {
        // Setup
        when(mockPersonRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Person> result = personServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure PersonRepository.findById(...).
        final Person person1 = new Person();
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        person1.setUser(user);
        person1.setId_user(0);
        final Optional<Person> person = Optional.of(person1);
        when(mockPersonRepository.findById(0)).thenReturn(person);

        // Run the test
        final Optional<Person> result = personServiceUnderTest.findOne(0);

        // Verify the results
    }

    @Test
    void testFindOne_PersonRepositoryReturnsAbsent() {
        // Setup
        when(mockPersonRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Person> result = personServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final Person person = new Person();
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        person.setUser(user);
        person.setId_user(0);

        // Run the test
        personServiceUnderTest.save(person);

        // Verify the results
        verify(mockPersonRepository).save(any(Person.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final Person updatedPerson = new Person();
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        updatedPerson.setUser(user);
        updatedPerson.setId_user(0);

        // Run the test
        personServiceUnderTest.update(0, updatedPerson);

        // Verify the results
        verify(mockPersonRepository).save(any(Person.class));
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        personServiceUnderTest.delete(0);

        // Verify the results
        verify(mockPersonRepository).deleteById(0);
    }
}
