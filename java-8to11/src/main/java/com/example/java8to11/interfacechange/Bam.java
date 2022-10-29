package com.example.java8to11.interfacechange;

public interface Bam {

    default String returnNameUpperCase() {
        return "BAM";
    }
}
