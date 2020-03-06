package se.ecutb.jonatan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;
import se.ecutb.jonatan.entity.RecipeIngredient;
import se.ecutb.jonatan.entity.RecipeInstruction;
import se.ecutb.jonatan.repository.RecipeCategoryRepository;
import se.ecutb.jonatan.repository.RecipeIngredientRepository;
import se.ecutb.jonatan.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{
    private RecipeRepository recipeRepository;
    private RecipeIngredientRepository recipeIngredientRepository;
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeIngredientRepository recipeIngredientRepository,
                             RecipeCategoryRepository recipeCategoryRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
    }

    @Override
    public Recipe createAndSave(String name, RecipeInstruction instruction) {
        Recipe newRecipe = new Recipe(name, instruction);
        return recipeRepository.save(newRecipe);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<Recipe> readAll() {
        List<Recipe> all = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe -> all.add(recipe));
        return all;
    }

    @Override
    public Recipe update(int id, String name, List<RecipeIngredient> ingredients, RecipeInstruction instruction, List<RecipeCategory> categories) {
        if (recipeRepository.findById(id).isPresent()) {
            Recipe updatedRecipe = new Recipe(id, name, ingredients, instruction, categories);
            return recipeRepository.save(updatedRecipe);
        } else System.out.println("Recipe not found");
        return null;
    }

    @Override
    public void delete(int id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe findById(int id) {
        return recipeRepository.findById(id).get();
    }

    @Override
    public List<Recipe> findByPartOfName(String partOfName) {
        List<Recipe> name = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe -> {
            if (recipe.getRecipeName().toUpperCase().contains(partOfName.toUpperCase())) {
                name.add(recipe);
            }
        });
        return name;
    }

    @Override
    public List<Recipe> findByContainedIngredient(String ingredientName) {
        List<Recipe> all = new ArrayList<>();
        recipeIngredientRepository.findAll().forEach(recipeIngredient -> {
            if (recipeIngredient.getIngredient().getIngredientName().equalsIgnoreCase(ingredientName)) {
                all.add(recipeIngredient.getRecipe());
            }
        });
        return all;
    }

    @Override
    public List<Recipe> findByCategory(String categoryName) {
        List<Recipe> all = new ArrayList<>();
        List<Recipe> allRecipes = new ArrayList<>();
        Recipe temp = new Recipe();
        for (Recipe recipe: recipeRepository.findAll()) {
            recipeCategoryRepository.findAll().forEach(recipeCategory -> {
                if (recipeCategory.getCategory().equalsIgnoreCase(categoryName)) {
                    all.add(recipe);
                    return;
                }
            });

        }
        return all;
    }

    @Override
    public List<Recipe> findByManyCategories() {
        List<Recipe> all = new ArrayList<>();
        recipeRepository.findAll().forEach(recipe -> {
            if (recipe.getCategories().size()>1) {
                all.add(recipe);
            }
        });
        return all;
    }
}
