package com.example.bookmanagment.controller;

import com.example.bookmanagment.model.Book;
import com.example.bookmanagment.model.Genre;
import com.example.bookmanagment.model.Publisher;
import com.example.bookmanagment.service.BookService;
import com.example.bookmanagment.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private PublisherService publisherService;

    private Publisher publisher;
    private Book book;

    @BeforeEach
    void setup() {
        publisher = new Publisher("Pub A", "Address A");
        publisher.setId(1L);

        book = new Book("Test Book", "Author X", new Genre("Fiction"), publisher);
        book.setId(1L);
    }

    @Test
    void testListBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/list"))
                .andExpect(model().attributeExists("books"));
    }

    @Test
    void testShowCreateForm() throws Exception {
        when(publisherService.getAllPublishers()).thenReturn(Arrays.asList(publisher));

        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/form"))
                .andExpect(model().attributeExists("book", "publishers"));
    }

    @Test
    void testSaveBook_Valid() throws Exception {
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .param("title", "Test Book")
                        .param("author", "Author X")
                        .param("genre.name", "Fiction")
                        .param("publisher.id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService).saveBook(any(Book.class));
    }

    @Test
    void testShowEditForm() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(publisherService.getAllPublishers()).thenReturn(Arrays.asList(publisher));

        mockMvc.perform(get("/books/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/form"))
                .andExpect(model().attributeExists("book", "publishers"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(get("/books/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        verify(bookService).deleteBook(1L);
    }
}
