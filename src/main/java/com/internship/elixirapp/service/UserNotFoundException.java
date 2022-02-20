package com.internship.elixirapp.service;

public class UserNotFoundException extends RuntimeException{

    UserNotFoundException(String message){
        super(message);
    }

}
