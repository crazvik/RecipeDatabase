package se.ecutb.jonatan.repository;

import se.ecutb.jonatan.entity.RecipeCategory;

import java.util.List;

public interface RecipeCategoryDao {
    RecipeCategory createAndSave(String name);
    List<RecipeCategory> readAll();
    RecipeCategory update(int id, String name);
    void delete(int id);
}
