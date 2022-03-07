package com.internship.elixirapp.service;

import com.internship.elixirapp.dto.IngredientDto;
import com.internship.elixirapp.dto.IngredientFilterDto;
import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.entity.User;
import com.internship.elixirapp.repository.IngredientRepository;
import com.internship.elixirapp.security.service.AppUserDetailsService;
import com.internship.elixirapp.transformer.IngredientTransformer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final String INGREDIENT_BOUGHT_SUCCESSFULLY_MESSAGE = "You have successfully bought ingredient %s";

    private static final String INGREDIENT_SOLD_SUCCESSFULLY_MESSAGE = "You have successfully sold ingredient %s";

    private static final String INGREDIENT_BOUGHT_UNSUCCESSFULLY_MESSAGE = "You don't have enough" +
            " money to buy that ingredient";

    private static final String INGREDIENT_SOLD_UNSUCCESSFULLY_MESSAGE = "You don't have that ingredient";

    private static final String DEFAULT_SORT = "name";

    private UserService userService;

    private IngredientRepository ingredientRepository;

    private IngredientTransformer ingredientTransformer;

    public ResponseEntity<String> buyIngredient(String id) {
        String userId = AppUserDetailsService.getCurrentUser().getId();
        User user = userService.getUserById(userId);
        Ingredient ingredient = getIngredientById(id);
        if (buyIngredient(user, ingredient)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format(INGREDIENT_BOUGHT_SUCCESSFULLY_MESSAGE, ingredient.getName()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(INGREDIENT_BOUGHT_UNSUCCESSFULLY_MESSAGE);
    }

    private boolean buyIngredient(User user, Ingredient ingredient){
        if (user.getCoins() >= ingredient.getCost()) {
            user.addIngredient(ingredient);
            user.setCoins(user.getCoins() - ingredient.getCost());
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


    private boolean sellIngredient(User user, Ingredient ingredient) {
        if (user.getIngredients().contains(ingredient)) {
            user.removeIngredient(ingredient);
            user.setCoins(user.getCoins() + ingredient.getCost());
            return true;
        }
        return false;
    }

    public ResponseEntity<String> sellIngredient(String id){
        String userId = AppUserDetailsService.getCurrentUser().getId();
        User user = userService.getUserById(userId);
        Ingredient ingredient = getIngredientById(id);
        if (sellIngredient(user, ingredient)) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(String.format(INGREDIENT_SOLD_SUCCESSFULLY_MESSAGE, ingredient.getName()));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(INGREDIENT_SOLD_UNSUCCESSFULLY_MESSAGE);
    }

    public Ingredient getIngredientById(String id) {
        return ingredientRepository.findById(id)
                .orElseThrow(
                        () -> new IngredientNotFoundException(String.format(INGREDIENT_NOT_FOUND_MESSAGE, id)
                        )
                );
    }

}
