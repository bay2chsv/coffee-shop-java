package com.example.restfulapi.controller;

import com.example.restfulapi.service.AuthSevice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthSevice authSevice;
    public AuthController(AuthSevice authSevice) {
        this.authSevice = authSevice;
    }
}
