package se.ecutb.jonatan.service;

import se.ecutb.jonatan.entity.*;

import java.util.List;

public interface RecipeService {
    Recipe createAndSave(String name, RecipeInstruction instruction);
    List<Recipe> readAll();
    Recipe update(int id, String name, List<RecipeIngredient> ingredients, RecipeInstruction instruction, List<RecipeCategory> categories);
    void delete(int id);
    Recipe findById(int id);
    List<Recipe> findByPartOfName(String partOfName);
    List<Recipe> findByContainedIngredient(String ingredientName);
    List<Recipe> findByCategory(String categoryName);
    List<Recipe> findByManyCategories();
}
