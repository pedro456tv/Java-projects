package com.example.bookmanagment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Publisher name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Book> books;

    public Publisher(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Publisher() {
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}

