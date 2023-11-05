package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.repositories.CartDetailsRepository;
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
class CartDetailsServiceTest {

    @Mock
    private CartDetailsRepository mockCartDetailsRepository;

    private CartDetailsService cartDetailsServiceUnderTest;

    @BeforeEach
    void setUp() {
        cartDetailsServiceUnderTest = new CartDetailsService(mockCartDetailsRepository);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure CartDetailsRepository.findAll(...).
        final CartDetails cartDetails1 = new CartDetails();
        cartDetails1.setId_details(0);
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        cartDetails1.setBook(book);
        final List<CartDetails> cartDetails = List.of(cartDetails1);
        when(mockCartDetailsRepository.findAll()).thenReturn(cartDetails);

        // Run the test
        final List<CartDetails> result = cartDetailsServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_CartDetailsRepositoryReturnsNoItems() {
        // Setup
        when(mockCartDetailsRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<CartDetails> result = cartDetailsServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure CartDetailsRepository.findById(...).
        final CartDetails cartDetails1 = new CartDetails();
        cartDetails1.setId_details(0);
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        cartDetails1.setBook(book);
        final Optional<CartDetails> cartDetails = Optional.of(cartDetails1);
        when(mockCartDetailsRepository.findById(0)).thenReturn(cartDetails);

        // Run the test
        final Optional<CartDetails> result = cartDetailsServiceUnderTest.findOne(0);

        // Verify the results
    }

    @Test
    void testFindOne_CartDetailsRepositoryReturnsAbsent() {
        // Setup
        when(mockCartDetailsRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<CartDetails> result = cartDetailsServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final CartDetails cart = new CartDetails();
        cart.setId_details(0);
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        cart.setBook(book);

        // Run the test
        cartDetailsServiceUnderTest.save(cart);

        // Verify the results
        verify(mockCartDetailsRepository).save(any(CartDetails.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final CartDetails updatedCart = new CartDetails();
        updatedCart.setId_details(0);
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        updatedCart.setBook(book);

        // Run the test
        cartDetailsServiceUnderTest.update(0, updatedCart);

        // Verify the results
        verify(mockCartDetailsRepository).save(any(CartDetails.class));
    }

    @Test
    void testFindByBookAndUser() {
        // Setup
        // Configure CartDetailsRepository.findByBookAndUser(...).
        final CartDetails cartDetails1 = new CartDetails();
        cartDetails1.setId_details(0);
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        cartDetails1.setBook(book);
        final Optional<CartDetails> cartDetails = Optional.of(cartDetails1);
        when(mockCartDetailsRepository.findByBookAndUser(0, 0)).thenReturn(cartDetails);

        // Run the test
        final Optional<CartDetails> result = cartDetailsServiceUnderTest.findByBookAndUser(0, 0);

        // Verify the results
    }

    @Test
    void testFindByBookAndUser_CartDetailsRepositoryReturnsAbsent() {
        // Setup
        when(mockCartDetailsRepository.findByBookAndUser(0, 0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<CartDetails> result = cartDetailsServiceUnderTest.findByBookAndUser(0, 0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testFindByUser() {
        // Setup
        // Configure CartDetailsRepository.findByUser(...).
        final CartDetails cartDetails1 = new CartDetails();
        cartDetails1.setId_details(0);
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        cartDetails1.setBook(book);
        final List<CartDetails> cartDetails = List.of(cartDetails1);
        when(mockCartDetailsRepository.findByUser(0)).thenReturn(cartDetails);

        // Run the test
        final List<CartDetails> result = cartDetailsServiceUnderTest.findByUser(0);

        // Verify the results
    }

    @Test
    void testFindByUser_CartDetailsRepositoryReturnsNoItems() {
        // Setup
        when(mockCartDetailsRepository.findByUser(0)).thenReturn(Collections.emptyList());

        // Run the test
        final List<CartDetails> result = cartDetailsServiceUnderTest.findByUser(0);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        cartDetailsServiceUnderTest.delete(0);

        // Verify the results
        verify(mockCartDetailsRepository).deleteById(0);
    }
}
