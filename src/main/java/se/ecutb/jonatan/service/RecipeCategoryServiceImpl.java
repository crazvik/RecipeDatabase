package se.ecutb.jonatan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;
import se.ecutb.jonatan.repository.RecipeCategoryRepository;
import se.ecutb.jonatan.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService {
    private RecipeCategoryRepository recipeCategoryRepository;
    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeCategoryServiceImpl(RecipeCategoryRepository recipeCategoryRepository, RecipeRepository recipeRepository) {
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RecipeCategory createAndSave(String categoryName, int recipeId) {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe -> {
            if(recipe.getRecipeId()==recipeId) {
                recipes.add(recipe);
            }
        });
        RecipeCategory recipeCategory = new RecipeCategory(categoryName, recipes);
        return recipeCategoryRepository.save(recipeCategory);
    }

    @Override
    public List<RecipeCategory> readAll() {
        List<RecipeCategory> all = new ArrayList<>();
        recipeCategoryRepository.findAll().forEach(recipeCategory -> all.add(recipeCategory));
        return all;
    }

    @Override
    public RecipeCategory update(int id, String name, List<Recipe> recipes) {
        if (recipeCategoryRepository.findById(id).isPresent()) {
            RecipeCategory updatedCategory = new RecipeCategory(id, name, recipes);
            return recipeCategoryRepository.save(updatedCategory);
        } else System.out.println("Category not found");
        return null;
    }

    @Override
    public void delete(int id) {
        recipeCategoryRepository.deleteById(id);
    }
}
