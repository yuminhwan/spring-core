package com.example.springdi.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookProxyServiceTest {

    BookService bookService = new BookServiceProxy(new DefalutBookService(new BookRepository()));

    @DisplayName("프록시 패턴을 통해 부가 기능을 적용한다.")
    @Test
    void proxy() {
        RealBook book = new RealBook();
        book.setTitle("test");
        bookService.rent(book);
    }
}
