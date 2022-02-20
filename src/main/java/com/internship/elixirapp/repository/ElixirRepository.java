package com.internship.elixirapp.repository;

import com.internship.elixirapp.entity.Elixir;
import com.internship.elixirapp.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElixirRepository extends JpaRepository<String, Elixir> {

    @Query()
    List<Ingredient> getRecipe();
}
