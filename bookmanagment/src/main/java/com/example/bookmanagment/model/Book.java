package com.example.bookmanagment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @Embedded
    private Genre genre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Book(String title, String author, Genre genre, Publisher publisher) {
        this.title= title;
        this.author= author;
        this.genre=genre;
        this.publisher= publisher;
    }

    public Book() {
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}
