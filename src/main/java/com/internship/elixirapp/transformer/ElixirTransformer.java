package com.internship.elixirapp.transformer;

import com.internship.elixirapp.dto.ElixirDto;
import com.internship.elixirapp.entity.Elixir;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ElixirTransformer {

    private IngredientTransformer ingredientTransformer;

    public ElixirDto transformToDto(Elixir elixir) {
        return ElixirDto.builder()
                .withCost(elixir.getCost())
                .withName(elixir.getName())
                .withLevel(elixir.getLevel())
                .withIngredientDtos(elixir.getIngredients().
                        stream()
                        .map(ingredient -> ingredientTransformer.transformToDto(ingredient))
                        .collect(Collectors.toList()))
                .build();
    }

}
