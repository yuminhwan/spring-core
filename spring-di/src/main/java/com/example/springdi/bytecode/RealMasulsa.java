package com.example.springdi.bytecode;

/**
 * 클래스 파일이 아닌 클래스 로딩시에 바뀌게 된다.
 * 즉, 메모리에 로딩될 시 변경되어서 들어오게 됨.
 */
public class RealMasulsa {
    public static void main(String[] args) {
        System.out.println(new Moja().pullOut());
    }
}
