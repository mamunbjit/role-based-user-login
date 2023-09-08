package com.spring.securityPractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

    @GetMapping("/custom-login")
    public String showLoginForm() {
        return "custom-login"; // Return the name of your custom login page HTML template
    }
}