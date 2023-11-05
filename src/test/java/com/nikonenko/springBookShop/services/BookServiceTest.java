package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository mockBookRepository;

    private BookService bookServiceUnderTest;

    @BeforeEach
    void setUp() {
        bookServiceUnderTest = new BookService(mockBookRepository);
    }

    @Test
    void testFindAll() {
        // Setup
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);
        final List<Book> expectedResult = List.of(book);

        // Configure BookRepository.findAll(...).
        final Book book1 = new Book();
        book1.setId_book(0);
        book1.setName("name");
        book1.setAuthor("author");
        book1.setYear(2020);
        book1.setPages(0);
        final List<Book> books = List.of(book1);
        when(mockBookRepository.findAll()).thenReturn(books);

        // Run the test
        final List<Book> result = bookServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindAll_BookRepositoryReturnsNoItems() {
        // Setup
        when(mockBookRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Book> result = bookServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);
        final Optional<Book> expectedResult = Optional.of(book);

        // Configure BookRepository.findById(...).
        final Book book2 = new Book();
        book2.setId_book(0);
        book2.setName("name");
        book2.setAuthor("author");
        book2.setYear(2020);
        book2.setPages(0);
        final Optional<Book> book1 = Optional.of(book2);
        when(mockBookRepository.findById(0)).thenReturn(book1);

        // Run the test
        final Optional<Book> result = bookServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindOne_BookRepositoryReturnsAbsent() {
        // Setup
        when(mockBookRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Book> result = bookServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);

        final Book expectedResult = new Book();
        expectedResult.setId_book(0);
        expectedResult.setName("name");
        expectedResult.setAuthor("author");
        expectedResult.setYear(2020);
        expectedResult.setPages(0);

        // Configure BookRepository.save(...).
        final Book book1 = new Book();
        book1.setId_book(0);
        book1.setName("name");
        book1.setAuthor("author");
        book1.setYear(2020);
        book1.setPages(0);
        final Book entity = new Book();
        entity.setId_book(0);
        entity.setName("name");
        entity.setAuthor("author");
        entity.setYear(2020);
        entity.setPages(0);
        when(mockBookRepository.save(entity)).thenReturn(book1);

        // Run the test
        final Book result = bookServiceUnderTest.save(book);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdate() {
        // Setup
        final Book updatedBook = new Book();
        updatedBook.setId_book(0);
        updatedBook.setName("name");
        updatedBook.setAuthor("author");
        updatedBook.setYear(2020);
        updatedBook.setPages(0);

        final Book expectedResult = new Book();
        expectedResult.setId_book(0);
        expectedResult.setName("name");
        expectedResult.setAuthor("author");
        expectedResult.setYear(2020);
        expectedResult.setPages(0);

        // Configure BookRepository.save(...).
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);
        final Book entity = new Book();
        entity.setId_book(0);
        entity.setName("name");
        entity.setAuthor("author");
        entity.setYear(2020);
        entity.setPages(0);
        when(mockBookRepository.save(entity)).thenReturn(book);

        // Run the test
        final Book result = bookServiceUnderTest.update(0, updatedBook);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        bookServiceUnderTest.delete(0);

        // Verify the results
        verify(mockBookRepository).deleteById(0);
    }

    @Test
    void testFindByAuthorContaining() {
        // Setup
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);
        final List<Book> expectedResult = List.of(book);

        // Configure BookRepository.findByAuthorContainingIgnoreCase(...).
        final Book book1 = new Book();
        book1.setId_book(0);
        book1.setName("name");
        book1.setAuthor("author");
        book1.setYear(2020);
        book1.setPages(0);
        final List<Book> books = List.of(book1);
        when(mockBookRepository.findByAuthorContainingIgnoreCase("author")).thenReturn(books);

        // Run the test
        final List<Book> result = bookServiceUnderTest.findByAuthorContaining("author");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindByAuthorContaining_BookRepositoryReturnsNoItems() {
        // Setup
        when(mockBookRepository.findByAuthorContainingIgnoreCase("author")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Book> result = bookServiceUnderTest.findByAuthorContaining("author");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindByGenreContaining() {
        // Setup
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);
        final List<Book> expectedResult = List.of(book);

        // Configure BookRepository.findByGenreContainingIgnoreCase(...).
        final Book book1 = new Book();
        book1.setId_book(0);
        book1.setName("name");
        book1.setAuthor("author");
        book1.setYear(2020);
        book1.setPages(0);
        final List<Book> books = List.of(book1);
        when(mockBookRepository.findByGenreContainingIgnoreCase("genre")).thenReturn(books);

        // Run the test
        final List<Book> result = bookServiceUnderTest.findByGenreContaining("genre");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindByGenreContaining_BookRepositoryReturnsNoItems() {
        // Setup
        when(mockBookRepository.findByGenreContainingIgnoreCase("genre")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Book> result = bookServiceUnderTest.findByGenreContaining("genre");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindByNameContaining() {
        // Setup
        final Book book = new Book();
        book.setId_book(0);
        book.setName("name");
        book.setAuthor("author");
        book.setYear(2020);
        book.setPages(0);
        final List<Book> expectedResult = List.of(book);

        // Configure BookRepository.findByNameContainingIgnoreCase(...).
        final Book book1 = new Book();
        book1.setId_book(0);
        book1.setName("name");
        book1.setAuthor("author");
        book1.setYear(2020);
        book1.setPages(0);
        final List<Book> books = List.of(book1);
        when(mockBookRepository.findByNameContainingIgnoreCase("name")).thenReturn(books);

        // Run the test
        final List<Book> result = bookServiceUnderTest.findByNameContaining("name");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testFindByNameContaining_BookRepositoryReturnsNoItems() {
        // Setup
        when(mockBookRepository.findByNameContainingIgnoreCase("name")).thenReturn(Collections.emptyList());

        // Run the test
        final List<Book> result = bookServiceUnderTest.findByNameContaining("name");

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
