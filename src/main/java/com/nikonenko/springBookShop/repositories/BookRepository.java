package com.nikonenko.springBookShop.repositories;

import com.nikonenko.springBookShop.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByGenreContainingIgnoreCase(String genre);
    List<Book> findByNameContainingIgnoreCase(String name);
}
