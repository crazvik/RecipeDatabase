package se.ecutb.jonatan.dao;

import se.ecutb.jonatan.entity.Ingredient;

import java.util.List;

public interface IngredientDao {
    Ingredient createAndSave(String name);
    List<Ingredient> readAll();
    Ingredient update(int id, String name);
    void delete(int id);
  //  @Query("SELECT ingredient FROM Ingredient ingredient WHERE UPPER(ingredient.ingredientName) = UPPER(:name)")
    List<Ingredient> findByName(String name);

//    @Query("SELECT ingredient FROM Ingredient ingredient WHERE UPPER(ingredient.ingredientName) LIKE UPPER(CONCAT('%',:partOfName,'%'))")
    List<Ingredient> findByPartOfName(String partOfName);
}
