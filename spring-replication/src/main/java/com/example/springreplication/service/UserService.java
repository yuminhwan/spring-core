package com.example.springreplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springreplication.domain.User;
import com.example.springreplication.domain.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long save(String name) {
        User user = new User(name);
        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }
}
