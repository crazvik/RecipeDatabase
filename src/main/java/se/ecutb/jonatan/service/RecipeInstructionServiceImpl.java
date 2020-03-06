package se.ecutb.jonatan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.RecipeInstruction;
import se.ecutb.jonatan.repository.RecipeInstructionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeInstructionServiceImpl implements RecipeInstructionService {
    private RecipeInstructionRepository recipeInstructionRepository;

    @Autowired
    public RecipeInstructionServiceImpl(RecipeInstructionRepository recipeInstructionRepository) {
        this.recipeInstructionRepository = recipeInstructionRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RecipeInstruction createAndSave(String  instruction) {
        RecipeInstruction newInstruction = new RecipeInstruction(instruction);
        return recipeInstructionRepository.save(newInstruction);
    }

    @Override
    public List<RecipeInstruction> readAll() {
        List<RecipeInstruction> all = new ArrayList<>();
        recipeInstructionRepository.findAll().forEach(recipeInstruction -> all.add(recipeInstruction));
        return all;
    }

    @Override
    public RecipeInstruction update(RecipeInstruction instruction, String newInstruction) {
        if (recipeInstructionRepository.findById(instruction.getInstructionId()).isPresent()) {
            RecipeInstruction updateInstruction = new RecipeInstruction(instruction.getInstructionId(), newInstruction);
            return recipeInstructionRepository.save(updateInstruction);
        } else System.out.println("Instruction not found");
        return null;
    }

    @Override
    public void delete(RecipeInstruction instruction) {
        recipeInstructionRepository.delete(instruction);
    }
}
