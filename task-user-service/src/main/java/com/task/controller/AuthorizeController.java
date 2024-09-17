package com.task.controller;

import com.task.configuration.JwtResponse;
import com.task.entity.User;
import com.task.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizeController {

    @Autowired
    private AuthorizeService authorizeService;

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody User user) throws Exception
    {
        return authorizeService.signup(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@RequestBody JwtResponse.LoginDetails loginDetails)
    {
        return authorizeService.signin(loginDetails);
    }

}
