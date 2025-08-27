package com.example.bookmanagment.repository;

import com.example.bookmanagment.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
