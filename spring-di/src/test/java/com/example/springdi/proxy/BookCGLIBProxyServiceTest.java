package com.example.springdi.proxy;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

class BookCGLIBProxyServiceTest {

    /**
     * CGLIB
     * 클래스라도 프록시 생성 가능
     *
     * 상속이 불가능 즉, final class나 private 생성자를 갖는 class는 적용하지 못함.
     */
    @DisplayName("다이나믹 프록시를 통해 프록시 객체를 생성할 수 있다.")
    @Test
    void dynamicProxy() {
        MethodInterceptor handler = new MethodInterceptor() {
            BookService bookService = new DefalutBookService(new BookRepository());

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws
                Throwable {
                if (method.getName().equals("rent")) {
                    System.out.println("advice");
                    Object invoke = method.invoke(bookService, objects);
                    System.out.println("advice");
                    return invoke;
                }
                return method.invoke(bookService, objects);
            }
        };

        BookService bookService = (BookService)Enhancer.create(BookService.class, handler);

        RealBook book = new RealBook();
        book.setTitle("test");
        bookService.rent(book);
        bookService.returnBook(book);
    }
}
