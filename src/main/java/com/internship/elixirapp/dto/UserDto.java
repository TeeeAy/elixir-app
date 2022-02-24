package com.internship.elixirapp.dto;

import com.internship.elixirapp.security.validation.PasswordMatches;
import com.internship.elixirapp.security.validation.ValidEmail;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserDto {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 255)
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

}

