package com.nikonenko.springBookShop.repositories;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Integer> {
    @Query("SELECT c FROM CartDetails c WHERE c.book.id_book = ?1 and c.cart.id_user = ?2")
    Optional<CartDetails> findByBookAndUser(Integer idBook, Integer idUser);

    @Query("SELECT c FROM CartDetails c WHERE c.cart.id_user = ?1")
    List<CartDetails> findByUser(Integer idUser);
}
