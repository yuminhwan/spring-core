package com.example.springdi.proxy;

public interface BookService {
    void rent(RealBook book);

    void returnBook(RealBook book);
}
