package se.ecutb.jonatan.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.ecutb.jonatan.entity.Ingredient;

import static org.junit.Assert.assertEquals;

@DataJpaTest
class IngredientDaoTest {

    @Autowired private TestEntityManager tem;
    @Autowired private IngredientDao ingredientDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createAndSave() {
        Ingredient testIngredient = new Ingredient("Test");
        tem.persist(testIngredient);
        assertEquals("test", testIngredient.getIngredientName());
    }

    @Test
    void readAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findByPartOfName() {
    }
}