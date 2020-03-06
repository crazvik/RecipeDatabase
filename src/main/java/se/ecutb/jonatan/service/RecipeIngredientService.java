package se.ecutb.jonatan.service;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;
import se.ecutb.jonatan.entity.Measurement;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientService {
    @Transactional(rollbackFor = RuntimeException.class)
    RecipeIngredient createAndSave(Ingredient ingredient, double amount, Measurement measurement, Recipe recipe);
    List<RecipeIngredient> readAll();
    RecipeIngredient update(RecipeIngredient recipeIngredient, Ingredient ingredient, double amount, Measurement measurement, Recipe recipe);
    void delete(RecipeIngredient recipeIngredient);
    List<RecipeIngredient> findByRecipeId(int id);
}
