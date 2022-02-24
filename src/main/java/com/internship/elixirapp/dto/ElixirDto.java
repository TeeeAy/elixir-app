package com.internship.elixirapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class ElixirDto {

    private String name;

    private int cost;

    private int level;

    private List<IngredientDto> ingredientDtos;
}
