package com.example.usermanagementapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.example.config",
        "com.example.controller",
        "com.example.initializer",
        "com.example.repository",
        "com.example.service",
        "com.example.usermanagementapp"
})
@EntityScan(basePackages = "com.example.model")
@EnableJpaRepositories(basePackages = "com.example.repository")
public class UserManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(demo.security.usermanagementapp.UserManagementApp.class, args);
    }
}