package com.internship.elixirapp.service;

import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.entity.User;
import com.internship.elixirapp.repository.IngredientRepository;
import com.internship.elixirapp.repository.UserRepository;
import com.internship.elixirapp.security.service.AppUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class IngredientService {

    private static final String INGREDIENT_NOT_FOUND_MESSAGE = "Ingredient with id='%s' does not exist";

    private IngredientRepository ingredientRepository;

    private UserRepository userRepository;

    public boolean buyIngredient(String id) {
        User user = AppUserDetailsService.getCurrentUser();
        Ingredient ingredient = getIngredientById(id);
        if (user.getCoins() >= getIngredientById(id).getCost()) {
            user.addIngredient(ingredient);
            user.setCoins(user.getCoins() - ingredient.getCost());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean sellIngredient(String id) {
        User user = AppUserDetailsService.getCurrentUser();
        Ingredient ingredient = getIngredientById(id);
        if (user.getIngredients().contains(ingredient)) {
            user.removeIngredient(ingredient);
            user.setCoins(user.getCoins() + ingredient.getCost());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public Ingredient getIngredientById(String id) {
        return ingredientRepository.findById(id)
                .orElseThrow(
                        () -> new IngredientNotFoundException(String.format(INGREDIENT_NOT_FOUND_MESSAGE, id)
                        )
                );
    }

}
