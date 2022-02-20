package com.internship.elixirapp.service;

public class IngredientNotFoundException extends RuntimeException{

    public IngredientNotFoundException(String message){
        super(message);
    }

}
