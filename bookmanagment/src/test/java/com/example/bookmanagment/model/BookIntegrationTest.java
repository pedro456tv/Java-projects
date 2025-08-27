package com.example.bookmanagment.model;

import com.example.bookmanagment.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenSaveBook_thenItIsPersisted() {
        Book book = new Book("Integration Book", "Test Author", new Genre("Fiction"), new Publisher("Pub", "Addr"));
        Book saved = bookRepository.save(book);

        assertNotNull(saved.getId());
        assertEquals("Integration Book", saved.getTitle());
    }

    @Test
    void whenFindAll_thenAllBooksAreReturned() {
        bookRepository.save(new Book("Book A", "Author A", new Genre("Drama"), new Publisher("Pub A", "Addr A")));
        bookRepository.save(new Book("Book B", "Author B", new Genre("Horror"), new Publisher("Pub B", "Addr B")));

        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }
}
