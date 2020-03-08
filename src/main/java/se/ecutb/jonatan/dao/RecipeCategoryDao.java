package se.ecutb.jonatan.dao;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.RecipeCategory;

import java.util.List;

public interface RecipeCategoryDao {
    @Transactional(rollbackFor = RuntimeException.class)
    RecipeCategory createAndSave(String name);
    List<RecipeCategory> readAll();
    RecipeCategory update(int id, String name);
    void delete(int id);
}
