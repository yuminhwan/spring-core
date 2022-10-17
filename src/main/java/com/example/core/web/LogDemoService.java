package com.example.core.web;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.example.core.common.MyLogger;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void logic(String id) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
