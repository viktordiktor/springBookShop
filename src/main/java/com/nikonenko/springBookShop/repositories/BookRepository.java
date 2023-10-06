package com.nikonenko.springBookShop.repositories;

import com.nikonenko.springBookShop.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
