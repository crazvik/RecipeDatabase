package se.ecutb.jonatan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;
import se.ecutb.jonatan.entity.Measurement;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeIngredient;
import se.ecutb.jonatan.repository.RecipeIngredientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RecipeIngredient createAndSave(Ingredient ingredient, double amount, Measurement measurement, Recipe recipe) {
        RecipeIngredient newRecipeIngredient = new RecipeIngredient(ingredient, amount, measurement, recipe);
        return recipeIngredientRepository.save(newRecipeIngredient);
    }

    @Override
    public List<RecipeIngredient> readAll() {
        List<RecipeIngredient> all = new ArrayList<>();
        recipeIngredientRepository.findAll().forEach(recipeIngredient -> all.add(recipeIngredient));
        return all;
    }

    @Override
    public RecipeIngredient update(RecipeIngredient recipeIngredient, Ingredient ingredient, double amount, Measurement measurement, Recipe recipe) {
        if (recipeIngredientRepository.findById(recipeIngredient.getRecipeIngredientId()).isPresent()) {
            RecipeIngredient updatedRecipeIngredient = new RecipeIngredient(recipeIngredient.getRecipeIngredientId(), ingredient, amount, measurement, recipe);
            return recipeIngredientRepository.save(updatedRecipeIngredient);
        } else System.out.println("RecipeIngredient not found");
        return null;
    }

    @Override
    public void delete(RecipeIngredient recipeIngredient) {
        recipeIngredientRepository.delete(recipeIngredient);
    }

    @Override
    public List<RecipeIngredient> findByRecipeId(int id) {
        List<RecipeIngredient> all = new ArrayList<>();
        recipeIngredientRepository.findAll().forEach(recipeIngredient -> {
           if (recipeIngredient.getRecipe().getRecipeId()==id) {
               all.add(recipeIngredient);
           }
        });
        return all;
    }
}
