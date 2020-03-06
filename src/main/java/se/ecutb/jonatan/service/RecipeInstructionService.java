package se.ecutb.jonatan.service;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.RecipeInstruction;

import java.util.List;

public interface RecipeInstructionService {
    @Transactional(rollbackFor = RuntimeException.class)
    RecipeInstruction createAndSave(String instruction);
    List<RecipeInstruction> readAll();
    RecipeInstruction update(RecipeInstruction instruction, String newInstruction);
    void delete(RecipeInstruction instruction);
}
