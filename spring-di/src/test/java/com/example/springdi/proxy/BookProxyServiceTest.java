package com.example.springdi.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookProxyServiceTest {

    BookService bookService = new BookServiceProxy(new DefalutBookService(new BookRepository()));

    /**
     * 직접 구현
     * 프록시 객체 생성을 하나하나 다 해줘야하고
     * 만약 메서드가 추가된다면 수정이 필요하다.
     */
    @DisplayName("프록시 패턴을 통해 부가 기능을 적용한다.")
    @Test
    void proxy() {
        RealBook book = new RealBook();
        book.setTitle("test");
        bookService.rent(book);
    }
}
