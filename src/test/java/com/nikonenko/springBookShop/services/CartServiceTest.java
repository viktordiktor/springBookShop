package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Cart;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.CartRepository;
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
class CartServiceTest {

    @Mock
    private CartRepository mockCartRepository;

    private CartService cartServiceUnderTest;

    @BeforeEach
    void setUp() {
        cartServiceUnderTest = new CartService(mockCartRepository);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure CartRepository.findAll(...).
        final Cart cart = new Cart();
        cart.setId_user(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        cart.setUser(user);
        final List<Cart> carts = List.of(cart);
        when(mockCartRepository.findAll()).thenReturn(carts);

        // Run the test
        final List<Cart> result = cartServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_CartRepositoryReturnsNoItems() {
        // Setup
        when(mockCartRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Cart> result = cartServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure CartRepository.findById(...).
        final Cart cart1 = new Cart();
        cart1.setId_user(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        cart1.setUser(user);
        final Optional<Cart> cart = Optional.of(cart1);
        when(mockCartRepository.findById(0)).thenReturn(cart);

        // Run the test
        final Optional<Cart> result = cartServiceUnderTest.findOne(0);

        // Verify the results
    }

    @Test
    void testFindOne_CartRepositoryReturnsAbsent() {
        // Setup
        when(mockCartRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Cart> result = cartServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final Cart cart = new Cart();
        cart.setId_user(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        cart.setUser(user);

        // Run the test
        cartServiceUnderTest.save(cart);

        // Verify the results
        verify(mockCartRepository).save(any(Cart.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final Cart updatedCart = new Cart();
        updatedCart.setId_user(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        updatedCart.setUser(user);

        // Run the test
        cartServiceUnderTest.update(0, updatedCart);

        // Verify the results
        verify(mockCartRepository).save(any(Cart.class));
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        cartServiceUnderTest.delete(0);

        // Verify the results
        verify(mockCartRepository).deleteById(0);
    }
}
