package se.ecutb.jonatan.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;
import se.ecutb.jonatan.entity.Measurement;
import se.ecutb.jonatan.entity.RecipeIngredient;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RecipeIngredientDaoImpl implements RecipeIngredientDao {
    private EntityManager entityManager;

    @Autowired
    public RecipeIngredientDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RecipeIngredient createAndSave(int ingredientId, double amount, Measurement measurement) {
        RecipeIngredient newRecipeIngredient = new RecipeIngredient(entityManager.find(Ingredient.class, ingredientId),
                amount, measurement);
        entityManager.persist(newRecipeIngredient);
        entityManager.close();
        return newRecipeIngredient;
    }

    @Override
    public List<RecipeIngredient> readAll() {
        Query query = entityManager.createQuery("SELECT recipeIngredient FROM RecipeIngredient recipeIngredient", RecipeIngredient.class);
        entityManager.close();
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecipeIngredient update(int index, double amount, Measurement measurement) {
        RecipeIngredient updatedRecipeIngredient = readAll().get(index);
        updatedRecipeIngredient.setAmount(amount);
        updatedRecipeIngredient.setMeasurement(measurement);
        entityManager.merge(updatedRecipeIngredient);
        entityManager.close();
        return updatedRecipeIngredient;
    }

    @Override
    public void delete(int index) {
        entityManager.remove(readAll().get(index));
        entityManager.close();
    }
}
