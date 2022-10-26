package com.example.springmvc.web.frontcontroller.v3;

import java.util.Map;

import com.example.springmvc.web.frontcontroller.ModelView;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);
}
