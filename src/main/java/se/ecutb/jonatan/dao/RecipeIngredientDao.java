package se.ecutb.jonatan.dao;

import se.ecutb.jonatan.entity.Measurement;
import se.ecutb.jonatan.entity.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientDao {
    RecipeIngredient createAndSave(int ingredientId, double amount, Measurement measurement);
    List<RecipeIngredient> readAll();
    RecipeIngredient update(int index, double amount, Measurement measurement);
    void delete(int index);
}
