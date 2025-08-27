package com.example.bookmanagment.controller;

import com.example.bookmanagment.model.Book;
import com.example.bookmanagment.model.Genre;
import com.example.bookmanagment.service.BookService;
import com.example.bookmanagment.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PublisherService publisherService;

    private final Set<Genre> Genres = new HashSet<>(Set.of(
            new Genre("Fiction"),
            new Genre("Non-Fiction"),
            new Genre("Biography"),
            new Genre("Science"),
            new Genre("Fantasy"),
            new Genre("Drama"),
            new Genre("Education"),
            new Genre("Comedy"),
            new Genre("Other")
    ));

    public BookController(BookService bookService, PublisherService publisherService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello from BookController!";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);

        model.addAttribute("genreOptions", getGenreOptions());

        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "books/form";
    }

    @PostMapping
    public String saveBook(@Valid @ModelAttribute Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("genreOptions", getGenreOptions());
            model.addAttribute("publishers", publisherService.getAllPublishers());
            return "books/form";
        }
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("genreOptions", getGenreOptions());
        model.addAttribute("publishers", publisherService.getAllPublishers());
        return "books/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    private List<Genre> getGenreOptions() {
        return List.copyOf(Genres);
    }
}
