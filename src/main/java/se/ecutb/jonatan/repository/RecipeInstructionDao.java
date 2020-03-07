package se.ecutb.jonatan.repository;

import se.ecutb.jonatan.entity.RecipeInstruction;

import java.util.List;

public interface RecipeInstructionDao {
    RecipeInstruction createAndSave(String instruction);
    List<RecipeInstruction> readAll();
    RecipeInstruction update(int index, String instruction);
    void delete(int index);
}
