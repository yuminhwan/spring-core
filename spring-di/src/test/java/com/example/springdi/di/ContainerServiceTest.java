package com.example.springdi.di;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContainerServiceTest {
    
    @DisplayName("BookRepository 인스턴스 생성 성공")
    @Test
    void getObject_bookRepository() {
        BookRepository bookRepository = ContainerService.getObject(BookRepository.class);

        assertThat(bookRepository).isNotNull();
    }

    @DisplayName("BookService 인스턴스 생성 성공")
    @Test
    void getObject_bookService() {
        BookService bookService = ContainerService.getObject(BookService.class);

        assertAll(
                () -> assertThat(bookService).as("bookService").isNotNull(),
                () -> assertThat(bookService.bookRepository).as("bookRepository").isNotNull()
        );
    }
}
