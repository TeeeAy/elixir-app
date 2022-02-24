package com.internship.elixirapp.service;

import com.internship.elixirapp.dto.IngredientDto;
import com.internship.elixirapp.dto.IngredientFilterDto;
import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.entity.User;
import com.internship.elixirapp.repository.IngredientRepository;
import com.internship.elixirapp.repository.UserRepository;
import com.internship.elixirapp.security.service.AppUserDetailsService;
import com.internship.elixirapp.transformer.IngredientTransformer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.internship.elixirapp.repository.specification.IngredientSpecification.buildSpecification;
import static java.util.Objects.nonNull;

@Service
@Transactional
@AllArgsConstructor
public class IngredientService {

    private static final String INGREDIENT_NOT_FOUND_MESSAGE = "Ingredient with id='%s' does not exist";

    private static final String DEFAULT_SORT = "name";

    private IngredientRepository ingredientRepository;

    private UserRepository userRepository;

    private IngredientTransformer ingredientTransformer;

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

    public List<IngredientDto> getIngredients(IngredientFilterDto filter) {
        Specification<Ingredient> specification = buildSpecification(filter);
        Sort.Direction direction = Sort.Direction.fromOptionalString(filter.getSortDirection())
                .orElse(Sort.DEFAULT_DIRECTION);
        String sortBy = nonNull(filter.getSortBy()) ? filter.getSortBy() : DEFAULT_SORT;
        return ingredientRepository.findAll(specification, Sort.by(direction, sortBy))
                .stream()
                .map(ingredient -> ingredientTransformer.transformToDto(ingredient))
                .collect(Collectors.toList());
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
