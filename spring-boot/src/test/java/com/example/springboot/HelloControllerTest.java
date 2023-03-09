package com.example.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HelloControllerTest {
    @Test
    void helloController() {
        // given
        HelloController helloController = new HelloController(name -> name);

        // when
        String sut = helloController.hello("Test");

        // then
        assertThat(sut).isEqualTo("Test");
    }

    @Test
    void failHelloController() {
        // given
        HelloController helloController = new HelloController(name -> name);

        // when
        // then
        assertThatThrownBy(() -> helloController.hello(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fail2HelloController() {
        // given
        HelloController helloController = new HelloController(name -> name);

        // when
        // then
        assertThatThrownBy(() -> helloController.hello(""))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
