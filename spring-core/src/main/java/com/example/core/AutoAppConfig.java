package com.example.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // @Bean(name = "memoryMemberRepository")
    // public MemoryMemberRepository memberRepository() {
    //     System.out.println("수동 빈 등록");
    //     return new MemoryMemberRepository();
    // }

}
