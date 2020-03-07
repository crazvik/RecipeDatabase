package se.ecutb.jonatan.repository;

import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;

import java.util.List;

public interface RecipeDao {
    Recipe createAndSave(String name);
    List<Recipe> readAll();
    Recipe update(int id, String name);
    void delete(int id);
    List<Recipe> findByPartOfName(String partOfName);
    List<Recipe> findByContainedIngredients(String ingredientName);
    List<Recipe> findBySpecificCategory(String categoryName);
    List<Recipe> findByMultipleCategories(List<RecipeCategory> categories);
}
