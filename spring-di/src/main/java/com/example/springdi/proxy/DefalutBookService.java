package com.example.springdi.proxy;

public class DefalutBookService implements BookService{

    private final BookRepository repository;

    public DefalutBookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void rent(RealBook book) {
        System.out.println("rent : " + book.getTitle());
    }

    @Override
    public void returnBook(RealBook book) {
        System.out.println("return : " + book.getTitle());
    }
}
