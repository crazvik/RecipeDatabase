package se.ecutb.jonatan.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.Ingredient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class IngredientDaoImpl implements IngredientDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public IngredientDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Ingredient createAndSave(String name) {
        Ingredient newIngredient = new Ingredient(name);
        entityManager.persist(newIngredient);
        return newIngredient;
    }

    @Override
    public List<Ingredient> readAll() {
        Query query = entityManager.createQuery("SELECT ingredient FROM Ingredient ingredient", Ingredient.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Ingredient update(int id, String name) {
        Ingredient updatedIngredient = entityManager.find(Ingredient.class, id);
        updatedIngredient.setIngredientName(name);
        entityManager.merge(updatedIngredient);
        return updatedIngredient;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Ingredient.class, id));
    }

    @Override
    public List<Ingredient> findByName(String name) {
        Query query = entityManager.createQuery(("SELECT ingredient FROM Ingredient ingredient WHERE UPPER(ingredient.ingredientName) = UPPER(:name)"), Ingredient.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Ingredient> findByPartOfName(String partOfName) {
        Query query = entityManager.createQuery(("SELECT ingredient FROM Ingredient ingredient WHERE UPPER(ingredient.ingredientName) LIKE UPPER(CONCAT('%',:partOfName,'%'))"), Ingredient.class);
        query.setParameter("partOfName", partOfName);
        return query.getResultList();
    }
}
