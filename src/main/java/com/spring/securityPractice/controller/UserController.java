package com.spring.securityPractice.controller;

import com.spring.securityPractice.model.UserDto;
import com.spring.securityPractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "Hello2";
    }

    @GetMapping("/profile")
    public String userProfile() {

        return "User Profile";
    }

    @GetMapping("/posts")
    public String userPosts() {

        return "User Posts";
    }

    @PostMapping("/comment")
    public String addComment(@RequestBody String comment) {
        // Implement logic to add a comment to a post
        return "Comment added: " + comment;
    }
    @PostMapping("/registration")
    public ResponseEntity<UserDto> register (@RequestBody UserDto userDto) throws Exception {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
}
