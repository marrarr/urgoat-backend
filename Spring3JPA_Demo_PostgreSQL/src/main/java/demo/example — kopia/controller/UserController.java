package com.example.controller;

import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("Wyswietlanie strony logowania");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String captcha,
            Model model
    ) {
        try {
            // Verify CAPTCHA (checks if exactly dog1 and dog2 are selected)
            String[] selectedImages = captcha.split(",");
            List<String> validImages = Arrays.asList("dog1", "dog2");
            if (selectedImages.length != 2 || !validImages.containsAll(Arrays.asList(selectedImages))) {
                throw new RuntimeException("Prosze o wybranie obrazow z psem");
            }

            // Store captcha in session for verification
            session.setAttribute("captcha", captcha);
            userService.registerUser(username, email, password);
            model.addAttribute("email", email);
            return "verify";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/verify")
    public String verify(
            @RequestParam(required = false) String email,
            Model model
    ) {
        if (email != null) {
            model.addAttribute("email", email);
        }
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyUser(
            @RequestParam String email,
            @RequestParam String code,
            Model model
    ) {
        boolean verified = userService.verifyUser(email, code);
        if (verified) {
            // Clear session after verification
            session.removeAttribute("captcha");
            return "redirect:/login";
        }
        model.addAttribute("error", "Nieprawidlowy kod lub email");
        model.addAttribute("email", email);
        return "verify";
    }
}