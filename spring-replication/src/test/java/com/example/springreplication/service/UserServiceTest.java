package com.example.springreplication.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springreplication.domain.User;
import com.example.springreplication.domain.UserRepository;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("read-only일 시 slave DB를 사용한다.")
    @Test
    void slaveDB() {
        Long id = userService.save("test");

        User user = userService.findById(id);
        assertThat(user.getName()).isEqualTo("test");
    }

    @DisplayName("read-only가 아닐 시 master DB를 사용한다.")
    @Test
    void masterDB() {
        Long id = userService.save("test");
        assertThat(id).isPositive();
    }
}
