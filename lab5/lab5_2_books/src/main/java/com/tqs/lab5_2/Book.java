package com.tqs.lab5_2;

import java.time.LocalDateTime;

public class Book {
    private final String title;
    private final String author;
    private final LocalDateTime published;

    public Book(String title, String author, LocalDateTime published) {
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getPublished() {
        return published;
    }

    public String toString() {
        return title + " by " + author + " published on " + published;
    }
}
