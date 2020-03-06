package se.ecutb.jonatan.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class RecipeIngredient {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String recipeIngredientId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ingredient_ingredient_id")
    private Ingredient ingredient;

    private double amount;

    @Enumerated
    private Measurement measurement;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recipe_recipe_id")
    private Recipe recipe;

    public RecipeIngredient() {
    }

    public RecipeIngredient(String recipeIngredientId, Ingredient ingredient, double amount, Measurement measurement, Recipe recipe) {
        this.recipeIngredientId = recipeIngredientId;
        this.ingredient = ingredient;
        this.amount = amount;
        this.measurement = measurement;
        this.recipe = recipe;
    }

    public RecipeIngredient(Ingredient ingredient, double amount, Measurement measurement, Recipe recipe) {
        this(null, ingredient, amount, measurement, recipe);
    }

    public String getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public void setRecipeIngredientId(String recipeIngredientId) {
        this.recipeIngredientId = recipeIngredientId;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredient that = (RecipeIngredient) o;
        return Double.compare(that.amount, amount) == 0 &&
                Objects.equals(recipeIngredientId, that.recipeIngredientId) &&
                Objects.equals(ingredient, that.ingredient) &&
                measurement == that.measurement &&
                Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeIngredientId, ingredient, amount, measurement, recipe);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecipeIngredient{");
        sb.append("recipeIngredientId='").append(recipeIngredientId).append('\'');
        sb.append(", ingredient=").append(ingredient);
        sb.append(", amount=").append(amount);
        sb.append(", measurement=").append(measurement);
        sb.append(", recipe=").append(recipe);
        sb.append('}');
        return sb.toString();
    }
}
