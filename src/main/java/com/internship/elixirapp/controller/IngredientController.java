package com.internship.elixirapp.controller;

import com.internship.elixirapp.dto.IngredientDto;
import com.internship.elixirapp.dto.IngredientFilterDto;
import com.internship.elixirapp.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if (ingredientService.buyIngredient(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure");
    }

    @DeleteMapping("/sell/{id}")
    public ResponseEntity<String> sellIngredient(@PathVariable String id) {
        if (ingredientService.sellIngredient(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Success");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failure");
    }


    @GetMapping("/get/all")
    public List<IngredientDto> getIngredients(@RequestBody @Valid IngredientFilterDto filter) {
        return ingredientService.getIngredients(filter);
    }




}