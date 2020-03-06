package se.ecutb.jonatan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;
import se.ecutb.jonatan.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Ingredient createAndSave(String name) {
        Ingredient ingredient = new Ingredient(name);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> readAll() {
        List<Ingredient> all = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredient -> all.add(ingredient));
        return all;
    }

    @Override
    public Ingredient update(int id, String name) {
        if (ingredientRepository.findById(id).isPresent()) {
            Ingredient updatedIngredient = new Ingredient(id, name);
            return ingredientRepository.save(updatedIngredient);
        } else System.out.println("Ingredient not found");
        return null;
    }

    @Override
    public void delete(int id) {
        ingredientRepository.deleteById(id);
    }

    @Override
    public List<Ingredient> findByName(String nextLine) {
        List<Ingredient> name = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredient -> {
           if (ingredient.getIngredientName().equalsIgnoreCase(nextLine)) {
               name.add(ingredient);
           }
        });
        return name;
    }

    @Override
    public List<Ingredient> findByPartOfName(String nextLine) {
        List<Ingredient> name = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredient -> {
            if (ingredient.getIngredientName().toUpperCase().contains(nextLine.toUpperCase())) {
                name.add(ingredient);
            }
        });
        return name;
    }
}
