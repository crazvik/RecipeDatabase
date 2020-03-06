package se.ecutb.jonatan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.jonatan.entity.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
}
