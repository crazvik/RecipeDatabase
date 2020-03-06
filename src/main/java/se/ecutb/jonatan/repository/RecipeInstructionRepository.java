package se.ecutb.jonatan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.ecutb.jonatan.entity.RecipeInstruction;

@Repository
public interface RecipeInstructionRepository extends CrudRepository<RecipeInstruction, String> {
}
