package com.example.bookmanagment.service;

import com.example.bookmanagment.model.Book;
import com.example.bookmanagment.model.Genre;
import com.example.bookmanagment.model.Publisher;
import com.example.bookmanagment.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book("Book A", "Author A", new Genre("Fiction"), new Publisher("Pub A", "Addr A")),
                new Book("Book B", "Author B", new Genre("Science"), new Publisher("Pub B", "Addr B"))
        );
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        Book book = new Book("Book A", "Author A", new Genre("Fiction"), new Publisher("Pub A", "Addr A"));
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(1L);
        assertTrue(result.isPresent());
        assertEquals("Book A", result.get().getTitle());
        verify(bookRepository).findById(1L);
    }

    @Test
    void testSaveBook() {
        Book book = new Book("Book A", "Author A", new Genre("Fiction"), new Publisher("Pub A", "Addr A"));
        when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.saveBook(book);
        assertEquals("Book A", saved.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void testDeleteBook() {
        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }
}
