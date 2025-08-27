package com.example.bookmanagment.repository;

import com.example.bookmanagment.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
