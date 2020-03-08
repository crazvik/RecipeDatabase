package se.ecutb.jonatan.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.RecipeInstruction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RecipeInstructionDaoImpl implements RecipeInstructionDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public RecipeInstructionDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
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
    @Transactional
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
