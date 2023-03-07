package com.example.springboot;

import java.util.Objects;

public class HelloController {
    public String hello(String name) {
        final SimpleHelloService helloService = new SimpleHelloService();

        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
