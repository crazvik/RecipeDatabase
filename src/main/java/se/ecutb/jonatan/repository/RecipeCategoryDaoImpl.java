package se.ecutb.jonatan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.ecutb.jonatan.entity.RecipeCategory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class RecipeCategoryDaoImpl implements RecipeCategoryDao {
    private EntityManager entityManager;

    @Autowired
    public RecipeCategoryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RecipeCategory createAndSave(String name) {
        RecipeCategory newCategory = new RecipeCategory(name);
        entityManager.persist(newCategory);
        return newCategory;
    }

    @Override
    public List<RecipeCategory> readAll() {
        Query query = entityManager.createQuery("SELECT category FROM RecipeCategory category", RecipeCategory.class);
        return query.getResultList();
    }

    @Override
    public RecipeCategory update(int id, String name) {
        RecipeCategory updatedCategory = entityManager.find(RecipeCategory.class, id);
        updatedCategory.setCategory(name);
        entityManager.merge(updatedCategory);
        return updatedCategory;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(RecipeCategory.class, id));
    }
}
