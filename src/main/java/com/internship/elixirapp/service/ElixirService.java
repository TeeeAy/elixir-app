package com.internship.elixirapp.service;

import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.repository.ElixirRepository;
import com.internship.elixirapp.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ElixirService {

    private ElixirRepository elixirRepository;

    private IngredientService ingredientService;

    public boolean createElixir(String[] ingredientIds){
        List<Ingredient> ingredientList = new ArrayList<>();
        for(String id: ingredientIds){
            ingredientList.add(ingredientService.getIngredientById(id));
        }
        return true;
    }


}
