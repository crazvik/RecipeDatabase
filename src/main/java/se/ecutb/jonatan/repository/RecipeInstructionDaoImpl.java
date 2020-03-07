package se.ecutb.jonatan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ecutb.jonatan.entity.RecipeInstruction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class RecipeInstructionDaoImpl implements RecipeInstructionDao {
    private EntityManager entityManager;

    @Autowired
    public RecipeInstructionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RecipeInstruction createAndSave(String instruction) {
        RecipeInstruction newInstruction = new RecipeInstruction(instruction);
        entityManager.persist(newInstruction);
        return newInstruction;
    }

    @Override
    public List<RecipeInstruction> readAll() {
        Query query = entityManager.createQuery("SELECT instruction FROM RecipeInstruction instruction", RecipeInstruction.class);
        return query.getResultList();
    }

    @Override
    public RecipeInstruction update(int index, String instruction) {
        RecipeInstruction updateInstruction = readAll().get(index);
        updateInstruction.setInstructions(instruction);
        entityManager.merge(updateInstruction);
        return updateInstruction;
    }

    @Override
    public void delete(int index) {
        entityManager.remove(readAll().get(index));
    }
}
