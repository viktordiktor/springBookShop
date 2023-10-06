package com.nikonenko.springBookShop.repositories;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBook(Book book);
}
