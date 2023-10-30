package com.nikonenko.springBookShop.junit;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.repositories.BookRepository;
import com.nikonenko.springBookShop.services.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setup(){
        bookService = new BookService(bookRepository);
    }

    @Test
    public void findAllTest(){
        List<Book> books = getBooks();
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1, result.get(0).getId_book());
        Assertions.assertEquals(2, result.get(1).getId_book());
    }

    private List<Book> getBooks(){
        Book firstBook = new Book();
        Book secondBook = new Book();

        firstBook.setId_book(1);
        firstBook.setAmount(1);
        firstBook.setImage("/test1");
        firstBook.setGenre("testGenre");
        firstBook.setAuthor("testAuthor");
        firstBook.setYear(2001);
        firstBook.setPages(1);
        firstBook.setPrice(1);

        secondBook.setId_book(2);
        secondBook.setAmount(2);
        secondBook.setImage("/test2");
        secondBook.setGenre("testGenre2");
        secondBook.setAuthor("testAuthor2");
        secondBook.setYear(2002);
        secondBook.setPages(2);
        secondBook.setPrice(2);

        return List.of(firstBook, secondBook);
    }
}
