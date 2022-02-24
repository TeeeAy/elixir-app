package com.internship.elixirapp.dto;

import com.internship.elixirapp.entity.IngredientType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class IngredientDto {

    private String id;

    private String name;

    private IngredientType type;

    private Integer cost;
}
