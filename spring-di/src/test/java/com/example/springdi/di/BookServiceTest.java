package com.example.springdi.di;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @DisplayName("DI를 통해 빈들이 주입된다.")
    @Test
    void di() {
        assertAll(
            () -> assertThat(bookService).isNotNull(),
            () -> assertThat(bookService.bookRepository).isNotNull()
        );
    }

}
