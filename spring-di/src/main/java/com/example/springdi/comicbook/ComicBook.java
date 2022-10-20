package com.example.springdi.comicbook;

public class ComicBook {

    public static String A = "A";

    private String B = "B";

    public ComicBook() {
    }

    public ComicBook(String b) {
        B = b;
    }

    private void c() {
        System.out.println("C");
    }

    public int sum(int left, int right) {
        return left + right;
    }
}
