package com.codeofcarbon.todoapp.controllers;

import com.codeofcarbon.todoapp.entities.User;
import com.codeofcarbon.todoapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> signUp(@Valid @RequestBody User user) {
        userService.addNewUser(user);
        return ResponseEntity.ok().build();
    }
}
