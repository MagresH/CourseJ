package com.example.coursej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)

public class CourseJApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseJApplication.class, args);
    }

}
