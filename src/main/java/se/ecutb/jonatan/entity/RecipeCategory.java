package se.ecutb.jonatan.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class RecipeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}
    )
    @JoinTable(
            name = "recipe_recipe_category",
            joinColumns = @JoinColumn(name = "recipe_category_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes;

    RecipeCategory() {}

    public RecipeCategory(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public RecipeCategory(String categoryName) {
        this(0, categoryName);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return categoryName;
    }

    public void setCategory(String category) {
        this.categoryName = category;
    }

    public List<Recipe> getRecipes() { return recipes; }

    public boolean addRecipe(Recipe recipe) {
        if(recipe==null) return false;
        if (recipes.contains(recipe)) return false;
        return recipes.add(recipe);
    }

    public boolean removeRecipe(Recipe recipe) {
        if(recipe==null) return false;
        return recipes.remove(recipe);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeCategory that = (RecipeCategory) o;
        return categoryId == that.categoryId &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(recipes, that.recipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RecipeCategory{");
        sb.append("categoryId=").append(categoryId);
        sb.append(", category='").append(categoryName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
