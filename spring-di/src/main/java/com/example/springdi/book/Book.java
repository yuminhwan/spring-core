package com.example.springdi.book;

@MyAnnotation
@MyRuntimeAnnotation(name = "test", number = 200)
public class Book {

    @MyRuntimeAnnotation("value")
    private static String B = "BOOK";

    private static final String C = "BOOK";

    private String a = "a";

    @MyRuntimeAnnotation
    public String d = "d";
    protected String e = "e";

    public Book() {
    }

    public Book(String a, String d, String e) {
        this.a = a;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("g");
    }

    public int h() {
        return 100;
    }
}
