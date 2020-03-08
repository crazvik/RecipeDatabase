package se.ecutb.jonatan.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.RecipeCategory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RecipeCategoryDaoImpl implements RecipeCategoryDao {
    private EntityManager entityManager;

    @Autowired
    public RecipeCategoryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RecipeCategory createAndSave(String name) {
        RecipeCategory newCategory = new RecipeCategory(name);
        entityManager.persist(newCategory);
        entityManager.close();
        return newCategory;
    }

    @Override
    public List<RecipeCategory> readAll() {
        Query query = entityManager.createQuery("SELECT category FROM RecipeCategory category", RecipeCategory.class);
        entityManager.close();
        return query.getResultList();
    }

    @Override
    @Transactional
    public RecipeCategory update(int id, String name) {
        RecipeCategory updatedCategory = entityManager.find(RecipeCategory.class, id);
        updatedCategory.setCategory(name);
        entityManager.merge(updatedCategory);
        entityManager.close();
        return updatedCategory;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(RecipeCategory.class, id));
        entityManager.close();
    }
}
