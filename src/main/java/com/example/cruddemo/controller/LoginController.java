package com.example.cruddemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        return "/home/login";
    }
}
