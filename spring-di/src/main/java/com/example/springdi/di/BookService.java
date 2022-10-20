package com.example.springdi.di;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Inject
    BookRepository bookRepository;
}
