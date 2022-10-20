package com.example.springdi.proxy;

public class BookServiceProxy implements BookService{

    private final BookService bookService;

    public BookServiceProxy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void rent(RealBook book) {
        System.out.println("advice");
        bookService.rent(book);
        System.out.println("advice");
    }

    @Override
    public void returnBook(RealBook book) {
        bookService.returnBook(book);
    }
}
