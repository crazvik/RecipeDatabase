package se.ecutb.jonatan.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class IngredientDaoImpl implements IngredientDao {
    private EntityManager entityManager;

    @Autowired
    public IngredientDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Ingredient createAndSave(String name) {
        Ingredient newIngredient = new Ingredient(name);
        entityManager.persist(newIngredient);
        entityManager.close();
        return newIngredient;
    }

    @Override
    public List<Ingredient> readAll() {
        Query query = entityManager.createQuery("SELECT ingredient FROM Ingredient ingredient", Ingredient.class);
        entityManager.close();
        return query.getResultList();
    }

    @Override
    @Transactional
    public Ingredient update(int id, String name) {
        Ingredient updatedIngredient = entityManager.find(Ingredient.class, id);
        updatedIngredient.setIngredientName(name);
        entityManager.merge(updatedIngredient);
        entityManager.close();
        return updatedIngredient;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Ingredient.class, id));
        entityManager.close();
    }

    @Override
    public List<Ingredient> findByName(String name) {
        Query query = entityManager.createQuery(("SELECT ingredient FROM Ingredient ingredient WHERE UPPER(ingredient.ingredientName) = UPPER(:name)"), Ingredient.class);
        query.setParameter("name", name);
        entityManager.close();
        return query.getResultList();
    }

    @Override
    public List<Ingredient> findByPartOfName(String partOfName) {
        Query query = entityManager.createQuery(("SELECT ingredient FROM Ingredient ingredient WHERE UPPER(ingredient.ingredientName) LIKE UPPER(CONCAT('%',:partOfName,'%'))"), Ingredient.class);
        query.setParameter("partOfName", partOfName);
        entityManager.close();
        return query.getResultList();
    }
}
