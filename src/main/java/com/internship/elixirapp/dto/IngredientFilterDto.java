package com.internship.elixirapp.dto;


import com.internship.elixirapp.entity.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class IngredientFilterDto {

    @Size(min = 2, max = 255)
    private String name;
    @Size(min = 2, max = 255)
    private IngredientType type;
    @Size(min = 1)
    private Integer costGT;
    @Size(min = 1)
    private Integer costLT;
    @Size(min = 2, max = 255)
    private String sortBy;
    @Size(min = 2, max = 255)
    private String sortDirection;
}
