package com.example.coursej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class CourseJApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseJApplication.class, args);
    }

}


