package com.internship.elixirapp.controller;

import com.internship.elixirapp.entity.User;
import com.internship.elixirapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }




}
