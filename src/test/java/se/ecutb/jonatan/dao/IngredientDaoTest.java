package se.ecutb.jonatan.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.ecutb.jonatan.entity.Ingredient;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
class IngredientDaoTest {

    @Autowired
    TestEntityManager tem;
    @Autowired
    IngredientDao ingredientDao;

    @BeforeEach
    void setUp() {
        Ingredient testIngredient = new Ingredient("Test");
        ingredientDao.createAndSave(testIngredient.getIngredientName());
    }

    @Test
    void createAndSave() {
        assertEquals("Test", ingredientDao.findByName("test"));
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