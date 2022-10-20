package com.example.springdi.di;

import org.springframework.stereotype.Service;

@Service
public class BookService {

     final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}
