package com.voiceforge.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/api/admin/hello")
    public String adminHello() {
        return "Hello Admin";
    }

    @GetMapping("/api/user/hello")
    public String userHello() {
        return "Hello User";
    }
}
