package se.ecutb.jonatan.dao;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;
import se.ecutb.jonatan.entity.RecipeIngredient;
import se.ecutb.jonatan.entity.RecipeInstruction;

import java.util.List;

public interface RecipeDao {
    @Transactional(rollbackFor = RuntimeException.class)
    Recipe createAndSave(String name);
    List<Recipe> readAll();
    Recipe update(int id, String name);
    void delete(int id);
    void addRecipeCategory(int recipeId, int categoryId);
    void addRecipeIngredient(int recipeId, RecipeIngredient recipeIngredient);
    void addRecipeInstruction(int recipeId, RecipeInstruction recipeInstruction);

    List<Recipe> findByPartOfName(String partOfName);
    List<Recipe> findByContainedIngredients(String ingredientName);
    List<Recipe> findBySpecificCategory(String categoryName);
    List<Recipe> findByMultipleCategories(List<RecipeCategory> categories);
}
