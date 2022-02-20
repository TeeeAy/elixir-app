package com.internship.elixirapp.service;

public class UserAlreadyExistsException extends RuntimeException{

    UserAlreadyExistsException(String message){
        super(message);
    }
}
