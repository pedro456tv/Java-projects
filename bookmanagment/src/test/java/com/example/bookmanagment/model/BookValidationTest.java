package com.example.bookmanagment.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenTitleIsNull_thenValidationFails() {
        Book book = new Book(null, "Author A", new Genre("Fiction"), new Publisher("Publisher", "Address"));

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
    }

    @Test
    void whenAuthorIsEmpty_thenValidationFails() {
        Book book = new Book("Title", "", new Genre("Fiction"), new Publisher("Publisher", "Address"));

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertFalse(violations.isEmpty());
    }

    @Test
    void whenAllFieldsValid_thenValidationPasses() {
        Book book = new Book("Valid Title", "Author A", new Genre("Fiction"), new Publisher("Publisher", "Address"));

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }
}
