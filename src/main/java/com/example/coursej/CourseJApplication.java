package com.example.coursej;

import com.example.coursej.config.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class CourseJApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseJApplication.class, args);
    }

}
//TODO improve add,put and delete in all controllers
//TODO Spring Security
//TODO improve SWAGGER
//TODO implement pagination
//TODO create review service and controller
//TODO improve services
//TODO implement caching
//TODO implement scheduling
