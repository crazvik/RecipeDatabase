package se.ecutb.jonatan.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recipeId;
    private String recipeName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RecipeIngredient> recipeIngredients;

    @OneToOne
    private RecipeInstruction instruction;


    @ManyToMany(
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "recipe_recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_category_id")
    )
    private List<RecipeCategory> categories = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(int recipeId, String recipeName, List<RecipeIngredient> recipeIngredients, RecipeInstruction instruction, List<RecipeCategory> categories) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.instruction = instruction;
        this.categories = categories;
    }

    public Recipe(String recipeName, List<RecipeIngredient> recipeIngredients, RecipeInstruction instruction, List<RecipeCategory> categories) {
        this(0, recipeName, recipeIngredients, instruction, categories);
    }

    public Recipe(String recipeName, RecipeInstruction instruction) {
        this(0, recipeName, null, instruction, null);
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
        if(recipeIngredients==null) recipeIngredients = new ArrayList<>();
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<RecipeIngredient> ingredient) {
        this.recipeIngredients = ingredient;
    }

    public void removeRecipeIngredients(RecipeIngredient ingredient) {
        if(recipeIngredients.contains(ingredient)) {
            recipeIngredients.remove(ingredient);
        }
    }

    public RecipeInstruction getInstruction() {
        return instruction;
    }

    public void setInstruction(RecipeInstruction instruction) {
        this.instruction = instruction;
    }

    public List<RecipeCategory> getCategories() {
        if (categories==null) categories = new ArrayList<>();
        return categories;
    }

    public void setCategories(List<RecipeCategory> category) {
        this.categories = category;
    }

    public boolean addStatusCode(RecipeCategory category){
        if(categories == null) categories = new ArrayList<>();
        if(category == null) return false;
        if(categories.contains(category)){
            return false;
        }
        categories.add(category);
        return true;
    }

    public boolean removeCategories(RecipeCategory category) {
        if(categories==null) categories = new ArrayList<>();
        if(category==null) return false;
        return categories.remove(category);
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
