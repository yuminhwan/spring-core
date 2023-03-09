package com.example.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HelloServiceTest {
    @Test
    void simpleHelloService() {
        // given
        SimpleHelloService helloService = new SimpleHelloService();

        // when
        String sut = helloService.sayHello("Test");

        // then
        assertThat(sut).isEqualTo("Hello Test");
    }

}