package com.example.bookmanagment.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Genre {

    @NotBlank(message = "Genre name is required")
    private String name;

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                // same instance
        if (o == null || getClass() != o.getClass()) return false;  // null or different class
        Genre genre = (Genre) o;
        return name != null ? name.equals(genre.name) : genre.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
