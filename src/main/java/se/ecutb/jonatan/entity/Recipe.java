package se.ecutb.jonatan.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeId;
    private String recipeName;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL},
            mappedBy = "recipe",
            orphanRemoval = true
    )
    private List<RecipeIngredient> recipeIngredients;

    @OneToOne(orphanRemoval = true)
    private RecipeInstruction instruction;


    @ManyToMany(
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "recipe_recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_category_id")
    )
    private List<RecipeCategory> categories = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public boolean addIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient.getRecipe() != null) {
            return false;
        }
        recipeIngredient.setRecipe(this);
        return recipeIngredients.add(recipeIngredient);
    }

    public boolean removeIngredient(RecipeIngredient recipeIngredient) {
        if(recipeIngredient.getRecipe()==null) return false;
        if(recipeIngredient.getRecipe()!=this) return false;
        recipeIngredient.setRecipe(null);
        return recipeIngredients.remove(recipeIngredient);
    }

    public RecipeInstruction getInstruction() {
        return instruction;
    }

    public void setInstruction(RecipeInstruction instruction) {
        this.instruction = instruction;
    }

    public List<RecipeCategory> getCategories() { return categories; }

    public void setCategories(List<RecipeCategory> categories) {
        this.categories = categories;
    }

    public boolean addRecipeCategory(RecipeCategory category){
        if(categories == null) categories = new ArrayList<>();
        if(category == null) return false;
        if(categories.contains(category)){
            return false;
        }
        categories.add(category);
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe{");
        sb.append("recipeId=").append(recipeId);
        sb.append(", recipeName='").append(recipeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
