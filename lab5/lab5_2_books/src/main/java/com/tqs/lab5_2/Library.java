package com.tqs.lab5_2;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class Library {
    private static final List<Book> store = new ArrayList<>();

    public Library() {}

    public void addBook(Book book) {
        store.add(book);
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        for (Book book : store) {
            if (book.getAuthor().equals(author)) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> findBooks(LocalDate initialDate, LocalDate finalDate) {
        List<Book> books = new ArrayList<>();
        for (Book book : store) {
            if (book.getPublished().isAfter(initialDate.atStartOfDay()) && book.getPublished().isBefore(finalDate.atStartOfDay())) {
                books.add(book);
            }
        }
        return books;
    }
}
