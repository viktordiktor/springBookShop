package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Order;
import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository mockOrderRepository;

    private OrderService orderServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderServiceUnderTest = new OrderService(mockOrderRepository);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure OrderRepository.findAll(...).
        final Order order = new Order();
        order.setId_order(0);
        order.setAddress("address");
        order.setCity("city");
        order.setPhone("phone");
        order.setOrderDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<Order> orders = List.of(order);
        when(mockOrderRepository.findAll()).thenReturn(orders);

        // Run the test
        final List<Order> result = orderServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_OrderRepositoryReturnsNoItems() {
        // Setup
        when(mockOrderRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Order> result = orderServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure OrderRepository.findById(...).
        final Order order1 = new Order();
        order1.setId_order(0);
        order1.setAddress("address");
        order1.setCity("city");
        order1.setPhone("phone");
        order1.setOrderDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final Optional<Order> order = Optional.of(order1);
        when(mockOrderRepository.findById(0)).thenReturn(order);

        // Run the test
        final Optional<Order> result = orderServiceUnderTest.findOne(0);

        // Verify the results
    }

    @Test
    void testFindOne_OrderRepositoryReturnsAbsent() {
        // Setup
        when(mockOrderRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Order> result = orderServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final Order order = new Order();
        order.setId_order(0);
        order.setAddress("address");
        order.setCity("city");
        order.setPhone("phone");
        order.setOrderDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Run the test
        orderServiceUnderTest.save(order);

        // Verify the results
        verify(mockOrderRepository).save(any(Order.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final Order updatedOrder = new Order();
        updatedOrder.setId_order(0);
        updatedOrder.setAddress("address");
        updatedOrder.setCity("city");
        updatedOrder.setPhone("phone");
        updatedOrder.setOrderDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Run the test
        orderServiceUnderTest.update(0, updatedOrder);

        // Verify the results
        verify(mockOrderRepository).save(any(Order.class));
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        orderServiceUnderTest.delete(0);

        // Verify the results
        verify(mockOrderRepository).deleteById(0);
    }

    @Test
    void testSetAmount() {
        // Setup
        // Run the test
        orderServiceUnderTest.setAmount(0, 0);

        // Verify the results
        verify(mockOrderRepository).setAmount(0, 0);
    }

    @Test
    void testGetIdBookOrder() {
        // Setup
        when(mockOrderRepository.getIdBookOrder(0, 0)).thenReturn(0);

        // Run the test
        final int result = orderServiceUnderTest.getIdBookOrder(0, 0);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testGetBookAmount() {
        // Setup
        when(mockOrderRepository.getBookAmount(0)).thenReturn(0);

        // Run the test
        final Integer result = orderServiceUnderTest.getBookAmount(0);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testFindAllByUser() {
        // Setup
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(0);
        final Person person = new Person();
        user.setPerson(person);

        // Configure OrderRepository.findAllByUser(...).
        final Order order = new Order();
        order.setId_order(0);
        order.setAddress("address");
        order.setCity("city");
        order.setPhone("phone");
        order.setOrderDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final List<Order> orders = List.of(order);
        when(mockOrderRepository.findAllByUser(any(User.class))).thenReturn(orders);

        // Run the test
        final List<Order> result = orderServiceUnderTest.findAllByUser(user);

        // Verify the results
    }

    @Test
    void testFindAllByUser_OrderRepositoryReturnsNoItems() {
        // Setup
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        user.setId_user(0);
        final Person person = new Person();
        user.setPerson(person);

        when(mockOrderRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Order> result = orderServiceUnderTest.findAllByUser(user);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
