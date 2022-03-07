package com.internship.elixirapp.controller;

import com.internship.elixirapp.dto.IngredientDto;
import com.internship.elixirapp.dto.IngredientFilterDto;
import com.internship.elixirapp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @PutMapping("/buy/{id}")
    public ResponseEntity<String> buyIngredient(@PathVariable String id) {
        return ingredientService.buyIngredient(id);
    }

    @DeleteMapping("/sell/{id}")
    public ResponseEntity<String> sellIngredient(@PathVariable String id) {
        return ingredientService.sellIngredient(id);
    }


    @GetMapping("/get/all")
    public List<IngredientDto> getIngredients(@RequestBody @Valid IngredientFilterDto filter) {
        return ingredientService.getIngredients(filter);
    }


}