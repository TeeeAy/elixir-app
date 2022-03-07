package com.internship.elixirapp.service;

import com.internship.elixirapp.dto.ElixirDto;
import com.internship.elixirapp.dto.ElixirFilterDto;
import com.internship.elixirapp.entity.Elixir;
import com.internship.elixirapp.entity.Ingredient;
import com.internship.elixirapp.entity.User;
import com.internship.elixirapp.repository.ElixirRepository;
import com.internship.elixirapp.security.service.AppUserDetailsService;
import com.internship.elixirapp.transformer.ElixirTransformer;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.internship.elixirapp.repository.specification.ElixirSpecification.buildSpecification;
import static com.internship.elixirapp.repository.specification.ElixirSpecification.filterByIngredient;
import static com.internship.elixirapp.util.RandomUtil.randomInt;
import static java.util.Objects.nonNull;

@Service
@Transactional
@AllArgsConstructor
public class ElixirService {

    private static final String DEFAULT_SORT = "name";
    private static final int MIN_RECIPE_SIZE = 2;
    private static final int MAX_RECIPE_SIZE = 3;

    private final IngredientService ingredientService;

    private UserService userService;

    private ElixirRepository elixirRepository;

    private ElixirTransformer elixirTransformer;

    @Transactional
    public ElixirDto createElixir(String[] ingredientIds) {
        if (ingredientIds.length > MAX_RECIPE_SIZE || ingredientIds.length < MIN_RECIPE_SIZE) {
            throw new IllegalArgumentException("Wrong recipe size :(");
        }
        String userId = AppUserDetailsService.getCurrentUser().getId();
        User user = userService.getUserById(userId);
        List<Ingredient> ingredients = Arrays.stream(ingredientIds)
                .map(ingredientService::getIngredientById)
                .collect(Collectors.toList());

        Elixir elixir = getElixirByIngredients(ingredients);

        if (elixir == null) {
            throw new NoSuchElixirException("No elixir with such recipe :(");
        }
        else if (!user.getIngredients().containsAll(ingredients)) {
            throw new IllegalArgumentException("You don’t have all needed ingredients :(");
        }

        createElixir(user, ingredients, elixir);
        return elixirTransformer.transformToDto(elixir);
    }

    private Elixir getElixirByIngredients(List<Ingredient> ingredients) {
        Specification<Elixir> specification = Specification.where(null);
        ingredients.forEach(i -> specification.and(filterByIngredient(i)));
        List<Elixir> elixirs = elixirRepository.findAll(specification);
        return elixirs.stream()
                .filter(elixir -> areListsEqual(elixir.getIngredients(), ingredients))
                .findFirst().orElse(null);
    }


    private boolean areListsEqual(List<Ingredient> list1, List<Ingredient> list2) {
        List<Ingredient> sortedList1 = list1.stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
        List<Ingredient> sortedList2 = list2.stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
        return sortedList1.equals(sortedList2);
    }

    private void createElixir(User user, List<Ingredient> ingredients, Elixir elixir) {
        ingredients.forEach(
                ingredient -> {
                    if (randomInt(100) <= ingredient.getType().getConsumingProbability()) {
                        user.removeIngredient(ingredient);
                    }
                }
        );
        user.addElixir(elixir);
    }


    public List<ElixirDto> getElixirs(ElixirFilterDto filter) {
        Specification<Elixir> specification = buildSpecification(filter);
        Sort.Direction direction = Sort.Direction.fromOptionalString(filter.getSortDirection())
                .orElse(Sort.DEFAULT_DIRECTION);
        String sortBy = nonNull(filter.getSortBy()) ? filter.getSortBy() : DEFAULT_SORT;
        return elixirRepository.findAll(specification, Sort.by(direction, sortBy))
                .stream()
                .map(elixir -> elixirTransformer.transformToDto(elixir))
                .collect(Collectors.toList());
    }


}
