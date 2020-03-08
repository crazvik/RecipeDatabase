package se.ecutb.jonatan.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;
import se.ecutb.jonatan.entity.RecipeIngredient;
import se.ecutb.jonatan.entity.RecipeInstruction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeDaoImpl implements RecipeDao {
    private EntityManager entityManager;

    @Autowired
    public RecipeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Recipe createAndSave(String name) {
        Recipe newRecipe = new Recipe(name);
        entityManager.persist(newRecipe);
        entityManager.close();
        return newRecipe;
    }

    @Override
    public List<Recipe> readAll() {
        Query query = entityManager.createQuery("SELECT recipe FROM Recipe recipe", Recipe.class);
        entityManager.close();
        return query.getResultList();
    }

    @Override
    @Transactional
    public Recipe update(int id, String name) {
        Recipe updatedRecipe = entityManager.find(Recipe.class, id);
        updatedRecipe.setRecipeName(name);
        entityManager.merge(updatedRecipe);
        entityManager.close();
        return updatedRecipe;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Recipe.class, id));
    }

    @Override
    public void addRecipeCategory(int recipeId, int categoryId) {
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        RecipeCategory category = entityManager.find(RecipeCategory.class, categoryId);
        recipe.addRecipeCategory(category);
        entityManager.close();
        entityManager.merge(recipe);
    }

    @Override
    public void addRecipeIngredient(int recipeId, RecipeIngredient recipeIngredient) {
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        recipe.addIngredient(recipeIngredient);
        entityManager.merge(recipe);
        entityManager.close();
    }

    @Override
    public void addRecipeInstruction(int recipeId, RecipeInstruction recipeInstruction) {
        Recipe recipe = entityManager.find(Recipe.class, recipeId);
        recipe.setInstruction(recipeInstruction);
        entityManager.merge(recipe);
        entityManager.close();
    }

    @Override
    public List<Recipe> findByPartOfName(String partOfName) {
        List<Recipe> wantedRecipes = new ArrayList<>();
        for (Recipe recipe:
             readAll()) {
            if (recipe.getRecipeName().toUpperCase().contains(partOfName.toUpperCase())) {
                wantedRecipes.add(recipe);
            }
        }
        entityManager.close();
        return wantedRecipes;
    }

    @Override
    public List<Recipe> findByContainedIngredients(String ingredientName) {
        List<Recipe> wantedRecipes = new ArrayList<>();
        int i=0;
        for (Recipe recipe:
                readAll()) {
            for (RecipeIngredient ingredient:
                 readAll().get(i).getRecipeIngredients()) {
                if(ingredient.getIngredient()==null) {
                    continue;
                }
                if (ingredient.getIngredient().getIngredientName().equalsIgnoreCase(ingredientName)) {
                    wantedRecipes.add(recipe);
                }
            }
            i++;
        }
        entityManager.close();
        return wantedRecipes;
    }

    @Override
    public List<Recipe> findBySpecificCategory(String categoryName) {
        List<Recipe> wantedRecipes = new ArrayList<>();
        int i=0;
        for (Recipe recipe :
             readAll()) {
            for (RecipeCategory category:
                    readAll().get(i).getCategories()) {
                if (category.getCategory()==null) {
                    continue;
                }
                if (category.getCategory().equalsIgnoreCase(categoryName)) {
                    wantedRecipes.add(recipe);
                }
            }
            i++;
        }
        entityManager.close();
        return wantedRecipes;
    }

    @Override
    public List<Recipe> findByMultipleCategories(List<RecipeCategory> categories) {
        List<Recipe> wantedRecipes = new ArrayList<>();
        int count=0;
        for (Recipe recipe:
             readAll()) {
            for (RecipeCategory category:
                 categories) {
                if (recipe.getCategories().contains(category)) {
                    wantedRecipes.add(recipe);
                }
            }
        }
        entityManager.close();
        return wantedRecipes;
    }
}
