package se.ecutb.jonatan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.jonatan.entity.RecipeCategory;

@Repository
public interface RecipeCategoryRepository extends CrudRepository<RecipeCategory, Integer> {
}
