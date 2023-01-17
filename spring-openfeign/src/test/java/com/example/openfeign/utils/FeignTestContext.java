package com.example.openfeign.utils;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

import com.example.openfeign.config.OpenFeignConfig;
import com.example.openfeign.config.PropertiesConfig;

@ImportAutoConfiguration({
    OpenFeignConfig.class,
    PropertiesConfig.class,
    FeignAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class
})
public class FeignTestContext {
}
