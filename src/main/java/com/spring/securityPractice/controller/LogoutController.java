package com.spring.securityPractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/custom-login"; // Redirect to the custom login page after logout
    }
}