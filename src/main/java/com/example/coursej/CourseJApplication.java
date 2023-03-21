package com.example.coursej;

import com.example.coursej.config.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CourseJApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseJApplication.class, args);
    }

}
//TODO improve add,put and delete in all controllers
//TODO Spring Security
//TODO SWAGGER
//TODO pagination
//TODO review service and controller
//TODO improve services

