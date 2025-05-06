//package demo.security.usermanagementapp;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//@SpringBootApplication
//@ComponentScan(basePackages = {
//        "demo.security.config",
//        "demo.security.controller",
//        "demo.security.initializer",
//        "demo.security.repository",
//        "demo.security.service",
//        "demo.security.usermanagementapp"
//})
//@EntityScan(basePackages = {
//        "demo.security.model"})
//@EnableJpaRepositories(basePackages = {
//        "demo.security.repository"
//})
//public class UserManagementApp {
//
//    public static void main(String[] args) {
//        SpringApplication.run(UserManagementApp.class, args);
//    }
//}