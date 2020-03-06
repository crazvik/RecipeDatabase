package se.ecutb.jonatan.service;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    @Transactional(rollbackFor = RuntimeException.class)
    Ingredient createAndSave(String name);
    List<Ingredient> readAll();
    Ingredient update(int id, String name);
    void delete(int id);
    List<Ingredient> findByName(String nextLine);
    List<Ingredient> findByPartOfName(String nextLine);
}

