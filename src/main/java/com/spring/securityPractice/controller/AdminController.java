package com.spring.securityPractice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PostMapping("/create-post")
    public String createPost(@RequestBody String postContent) {

        return "Post created: " + postContent;
    }

    @DeleteMapping("/delete-post/{postId}")
    public String deletePost(@PathVariable Long postId) {

        return "Post deleted with ID: " + postId;
    }

    @GetMapping("/view-reports")
    public String viewReports() {

        return "Reports";
    }
}

