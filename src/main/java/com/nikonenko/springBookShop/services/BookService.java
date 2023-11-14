package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.repositories.BookRepository;
import com.nikonenko.springBookShop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> findOne(int id){
        return bookRepository.findById(id);
    }

    @Transactional
    public Book save(Book book){
        return bookRepository.save(book);
    }

    @Transactional
    public Book update(int id, Book updatedBook){
        updatedBook.setId_book(id);
        return bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> findByAuthorContaining(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> findByGenreContaining(String genre){
        return bookRepository.findByGenreContainingIgnoreCase(genre);
    }

    public List<Book> findByNameContaining(String name){
        return bookRepository.findByNameContainingIgnoreCase(name);
    }
}
