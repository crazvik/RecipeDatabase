package se.ecutb.jonatan.dao;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.RecipeInstruction;

import java.util.List;

public interface RecipeInstructionDao {
    @Transactional(rollbackFor = RuntimeException.class)
    RecipeInstruction createAndSave(String instruction);
    List<RecipeInstruction> readAll();
    RecipeInstruction update(int index, String instruction);
    void delete(int index);
}
