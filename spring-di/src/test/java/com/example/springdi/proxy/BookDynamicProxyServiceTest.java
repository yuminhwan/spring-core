package com.example.springdi.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookDynamicProxyServiceTest {

    BookService bookService = (BookService)Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[] {BookService.class},
        new InvocationHandler() {
            BookService bookService = new DefalutBookService(new BookRepository());

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("rent")) {
                    System.out.println("advice");
                    Object invoke = method.invoke(bookService, args);
                    System.out.println("advice");
                    return invoke;
                }
                return method.invoke(bookService, args);
            }
        });

    /**
     * 다이나믹 프록시
     * 객체 생성이 필요없다.
     * 하지만 모든 메서드에 적용되기 때문에
     * 필요한 곳에서만 적용되도록 분기가 필요하다.
     * -> 유연하지 않다.
     *
     * 또한, 인터페이스만 가능하다. 클래스는 불가능
     */
    @DisplayName("다이나믹 프록시를 통해 프록시 객체를 생성할 수 있다.")
    @Test
    void dynamicProxy() {
        RealBook book = new RealBook();
        book.setTitle("test");
        bookService.rent(book);
        bookService.returnBook(book);
    }
}
