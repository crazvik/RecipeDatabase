package se.ecutb.jonatan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;
import se.ecutb.jonatan.entity.RecipeIngredient;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeDaoImpl implements RecipeDao {
    private EntityManager entityManager;

    @Autowired
    public RecipeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Recipe createAndSave(String name) {
        Recipe newRecipe = new Recipe(name);
        entityManager.persist(newRecipe);
        return newRecipe;
    }

    @Override
    public List<Recipe> readAll() {
        Query query = entityManager.createQuery("SELECT recipe FROM Recipe recipe", Recipe.class);
        return query.getResultList();
    }

    @Override
    public Recipe update(int id, String name) {
        Recipe updatedRecipe = entityManager.find(Recipe.class, id);
        updatedRecipe.setRecipeName(name);
        entityManager.merge(updatedRecipe);
        return updatedRecipe;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Recipe.class, id));
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
        return wantedRecipes;
    }

    @Override
    public List<Recipe> findByMultipleCategories() {
        return null;
    }
}
