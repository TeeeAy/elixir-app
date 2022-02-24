package com.internship.elixirapp.transformer;

import com.internship.elixirapp.dto.IngredientDto;
import com.internship.elixirapp.entity.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientTransformer {

    public IngredientDto transformToDto(Ingredient ingredient){
        return IngredientDto.builder()
                .withCost(ingredient.getCost())
                .withId(ingredient.getId())
                .withName(ingredient.getName())
                .withType(ingredient.getType())
                .build();

    }


}
