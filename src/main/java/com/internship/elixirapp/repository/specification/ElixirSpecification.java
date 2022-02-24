package com.internship.elixirapp.repository.specification;

import com.internship.elixirapp.dto.ElixirFilterDto;
import com.internship.elixirapp.entity.Elixir;
import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.security.service.AppUserDetailsService;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.isNull;

public class ElixirSpecification {
    public static Specification<Elixir> filterByUsersId(String id) {
        return isNull(id) ? null : (elixir, cq, cb) -> cb.equal(
                elixir.join("users").get("id"),
               id
        );
    }

    public static Specification<Elixir> filterByName(String name) {
        return isNull(name) ? null : (elixir, cq, cb) -> cb.equal(elixir.get("name"), name);
    }

    public static Specification<Elixir> filterByCostGT(Integer costGT) {
        return isNull(costGT) ? null : (elixir, cq, cb) -> cb.greaterThan(elixir.get("cost"), costGT);
    }

    public static Specification<Elixir> filterByCostLT(Integer costLT) {
        return isNull(costLT) ? null : (elixir, cq, cb) -> cb.lessThan(elixir.get("cost"), costLT);
    }

    public static Specification<Elixir> filterByLevel(Integer level) {
        return isNull(level) ? null : (elixir, cq, cb) -> cb.equal(elixir.get("level"), level);
    }

    public static Specification<Elixir> filterByIngredient(Ingredient ingredient) {
        return isNull(ingredient) ? null : (elixir, cq, cb) -> cb.isMember(ingredient, elixir.get("ingredients"));
    }

    public static Specification<Elixir> buildSpecification(ElixirFilterDto filteringParams) {
        String id = AppUserDetailsService.getCurrentUser().getId();
        String name = filteringParams.getName();
        Integer costGT = filteringParams.getCostGT();
        Integer costLT = filteringParams.getCostLT();
        Integer level = filteringParams.getLevel();

        return Specification
                .where(ElixirSpecification.filterByUsersId(id))
                .and(ElixirSpecification.filterByName(name))
                .and(ElixirSpecification.filterByCostGT(costGT))
                .and(ElixirSpecification.filterByCostLT(costLT))
                .and(ElixirSpecification.filterByLevel(level));
    }
}
