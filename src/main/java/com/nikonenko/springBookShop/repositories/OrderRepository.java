package com.nikonenko.springBookShop.repositories;

import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.models.Order;
import com.nikonenko.springBookShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT id_book_order FROM book_order bo WHERE bo.id_book = ?1 AND bo.id_order = ?2",
            nativeQuery = true)
    int getIdBookOrder(Integer idBook, Integer idOrder);

    @Modifying
    @Query(value = "INSERT INTO order_amount (id_book_order, amount) VALUES (?1, ?2)",
            nativeQuery = true)
    void setAmount(Integer idBookOrder, int amount);

    @Query(value = "SELECT amount FROM order_amount oa WHERE oa.id_book_order = ?1",
            nativeQuery = true)
    Integer getBookAmount(Integer idBookOrder);

    List<Order> findAllByUser(User user);
}
