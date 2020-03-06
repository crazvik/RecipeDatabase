package se.ecutb.jonatan.service;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;

import java.util.List;

public interface RecipeCategoryService {
    @Transactional(rollbackFor = RuntimeException.class)
    RecipeCategory createAndSave(String categoryName, int recipeId);
    List<RecipeCategory> readAll();
    RecipeCategory update(int id, String name, List<Recipe> recipes);
    void delete(int id);
}
