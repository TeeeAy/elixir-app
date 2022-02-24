package com.internship.elixirapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IngredientType {

    HERB(75), POWDER(50),
    LIQUID(100), SOLID(0);

    private int consumingProbability;

}
