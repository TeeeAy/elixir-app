package com.internship.elixirapp.controller;

import com.internship.elixirapp.dto.UserDto;
import com.internship.elixirapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserRegistrationController {

    private UserService userService;

    @PostMapping("/user/registration")
    public String registerUser(@Valid @RequestBody UserDto userDto) {
       return userService.registerNewUserAccount(userDto);
    }

}
