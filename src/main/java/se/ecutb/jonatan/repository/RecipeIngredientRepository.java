package se.ecutb.jonatan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.jonatan.entity.RecipeIngredient;

@Repository
public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, String> {
}
