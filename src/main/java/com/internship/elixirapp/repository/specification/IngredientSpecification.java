package com.internship.elixirapp.repository.specification;

import com.internship.elixirapp.dto.IngredientFilterDto;
import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.entity.IngredientType;
import com.internship.elixirapp.security.service.AppUserDetailsService;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.isNull;

public class IngredientSpecification {
    public static Specification<Ingredient> filterByUsersId(String id) {
        return isNull(id) ? null : (ingredient, cq, cb) -> cb.equal(
                ingredient.join("users").get("id"),
                id
        );
    }

    public static Specification<Ingredient> filterByName(String name) {
        return isNull(name) ? null : (ingredient, cq, cb) -> cb.equal(ingredient.get("name"), name);
    }

    public static Specification<Ingredient> filterByType(IngredientType type) {
        return isNull(type) ? null : (ingredient, cq, cb) -> cb.equal(ingredient.get("type"), type);
    }

    public static Specification<Ingredient> filterByCostGT(Integer costGT) {
        return isNull(costGT) ? null : (ingredient, cq, cb) -> cb.greaterThan(ingredient.get("cost"), costGT);
    }

    public static Specification<Ingredient> filterByCostLT(Integer costLT) {
        return isNull(costLT) ? null : (ingredient, cq, cb) -> cb.lessThan(ingredient.get("cost"), costLT);
    }

    public static Specification<Ingredient> buildSpecification(IngredientFilterDto filter) {
        String id = AppUserDetailsService.getCurrentUser().getId();
        String name = filter.getName();
        IngredientType type = filter.getType();
        Integer costGT = filter.getCostGT();
        Integer costLT = filter.getCostLT();

        return Specification
                .where(IngredientSpecification.filterByUsersId(id))
                .and(IngredientSpecification.filterByName(name))
                .and(IngredientSpecification.filterByType(type))
                .and(IngredientSpecification.filterByCostGT(costGT))
                .and(IngredientSpecification.filterByCostLT(costLT));
    }
}
