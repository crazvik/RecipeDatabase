package se.ecutb.jonatan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

@Service
public class CommandLine implements CommandLineRunner {
    private EntityManager entityManager;
    private Scanner scanner = new Scanner(System.in);
    private boolean keepGoing = true;

    @Autowired
    public CommandLine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        while (keepGoing) {
            System.out.println("\n----------------------------------------------\n" +
                    "Type 1 to create new rows\n" +
                    "Type 2 to see all rows\n" +
                    "Type 3 to update recipe\n" +
                    "Type 4 to delete recipe by id\n" +
                    "Type 5 to quit" +
                    "\n----------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to create a number of categories\n" +
                            "Type 2 to create an instruction\n" +
                            "Type 3 to create a new ingredient\n" +
                            "Type 4 to create a new recipe ingredient\n" +
                            "Type 5 to create a new recipe\n" +
                            "Type 6 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("How many categories do you want to add?: ");
                            int number = Integer.parseInt(scanner.nextLine());
                            for (int i = 0; i < number; i++) {
                                RecipeCategory newCategory = new RecipeCategory(scanner.nextLine());
                                entityManager.persist(newCategory);
                            }
                            break;
                        case 2:
                            System.out.println("Enter an instruction: ");
                            RecipeInstruction newInstruction = new RecipeInstruction(scanner.nextLine());
                            entityManager.persist(newInstruction);
                            break;
                        case 3:
                            System.out.println("Enter an ingredient name: ");
                            Ingredient newIngredient = new Ingredient(scanner.nextLine());
                            entityManager.persist(newIngredient);
                            break;
                        case 4:
                            System.out.println("Enter the id of the ingredient to add, amount, one of following measurements: (L, DL, CL, ML,\n" +
                                    "KG, HG ,G, ST, MSK, TSK): ");
                            RecipeIngredient newRecipeIngredient = new RecipeIngredient(entityManager.find(Ingredient.class, Integer.parseInt(scanner.nextLine())),
                                    Double.parseDouble(scanner.nextLine()),
                                    Measurement.valueOf(scanner.nextLine().toUpperCase()));
                            entityManager.persist(newRecipeIngredient);
                            break;
                        case 5:
                            System.out.println("Enter a recipe name: ");
                            Recipe newRecipe = new Recipe(scanner.nextLine());
                            entityManager.persist(newRecipe);
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 2:
                    Query query;
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 for categories\n" +
                            "Type 2 for instructions\n" +
                            "Type 3 for ingredients\n" +
                            "Type 4 for recipe ingredients\n" +
                            "Type 5 for recipes\n" +
                            "Type 6 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            query = entityManager.createQuery("SELECT category FROM RecipeCategory category", RecipeCategory.class);
                            List<RecipeCategory> categories = query.getResultList();
                            for (RecipeCategory category :
                                    categories)
                                System.out.println(category.toString());
                            break;
                        case 2:
                            query = entityManager.createQuery("SELECT instruction FROM RecipeInstruction instruction", RecipeInstruction.class);
                            List<RecipeInstruction> instructions = query.getResultList();
                            for (RecipeInstruction instruction :
                                    instructions) {
                                System.out.println(instruction.toString());
                            }
                            break;
                        case 3:
                            query = entityManager.createQuery("SELECT ingredient FROM Ingredient ingredient", Ingredient.class);
                            List<Ingredient> ingredients = query.getResultList();
                            for (Ingredient ingredient :
                                    ingredients) {
                                System.out.println(ingredient.toString());
                            }
                            break;
                        case 4:
                            query = entityManager.createQuery("SELECT recipeIngredient FROM RecipeIngredient recipeIngredient", RecipeIngredient.class);
                            List<RecipeIngredient> recipeIngredients = query.getResultList();
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredients) {
                                System.out.println(recipeIngredient.toString());
                            }
                            break;
                        case 5:
                            query = entityManager.createQuery("SELECT recipe FROM Recipe recipe", Recipe.class);
                            List<Recipe> recipes = query.getResultList();
                            for (Recipe recipe :
                                    recipes) {
                                System.out.println(recipe.toString());
                            }
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 3:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to update a category\n" +
                            "Type 2 to update an instruction\n" +
                            "Type 3 to update an ingredient\n" +
                            "Type 4 to update a recipe ingredient\n" +
                            "Type 5 to update a recipe\n" +
                            "Type 6 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter the id of the category you want to update: ");
                            RecipeCategory updatedCategory = entityManager.find(RecipeCategory.class, Integer.parseInt(scanner.nextLine()));
                            System.out.println("Enter the new category name: ");
                            updatedCategory.setCategory(scanner.nextLine());
                            entityManager.merge(updatedCategory);
                            break;
                        case 2:
                            System.out.println("Enter the index of the instruction to update: ");
                            query = entityManager.createQuery("SELECT instruction FROM RecipeInstruction instruction", RecipeInstruction.class);
                            List<RecipeInstruction> instructions = query.getResultList();
                            for (RecipeInstruction instruction :
                                    instructions) {
                                System.out.println(instruction.toString());
                            }
                            RecipeInstruction updatedInstruction = instructions.get(Integer.parseInt(scanner.nextLine()));
                            updatedInstruction.setInstructions(scanner.nextLine());
                            entityManager.merge(updatedInstruction);
                            break;
                        case 3:
                            System.out.println("Enter the id of the ingredient you want to update: ");
                            Ingredient updatedIngredient = entityManager.find(Ingredient.class, Integer.parseInt(scanner.nextLine()));
                            System.out.println("Enter the new ingredient name: ");
                            updatedIngredient.setIngredientName(scanner.nextLine());
                            entityManager.merge(updatedIngredient);
                            break;
                        case 4:
                            System.out.println("Enter the index of the recipe ingredient to update: ");
                            query = entityManager.createQuery("SELECT recipeIngredient FROM RecipeIngredient recipeIngredient", RecipeIngredient.class);
                            List<RecipeIngredient> recipeIngredients = query.getResultList();
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredients) {
                                System.out.println(recipeIngredient.toString());
                            }
                            RecipeIngredient updatedRecipeIngredient = recipeIngredients.get(Integer.parseInt(scanner.nextLine()));
                            updatedRecipeIngredient.setAmount(Double.parseDouble(scanner.nextLine()));
                            entityManager.merge(updatedRecipeIngredient);
                            break;
                        case 5:
                            System.out.println("Enter the id of the recipe you want to update: ");
                            Recipe updatedRecipe = entityManager.find(Recipe.class, Integer.parseInt(scanner.nextLine()));
                            System.out.println("Enter the new recipe name: ");
                            updatedRecipe.setRecipeName(scanner.nextLine());
                            query = entityManager.createQuery("SELECT recipeIngredient FROM RecipeIngredient recipeIngredient", RecipeIngredient.class);
                            recipeIngredients = query.getResultList();
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredients) {
                                System.out.println(recipeIngredient.toString());
                            }
                            System.out.println("Enter the index of the ingredient to add: ");
                            updatedRecipe.addIngredient(recipeIngredients.get(Integer.parseInt(scanner.nextLine())));
                            query = entityManager.createQuery("SELECT instruction FROM RecipeInstruction instruction", RecipeInstruction.class);
                            instructions = query.getResultList();
                            for (RecipeInstruction instruction :
                                    instructions) {
                                System.out.println(instruction.toString());
                            }
                            System.out.println("Enter the index of the instruction to add: ");
                            updatedRecipe.setInstruction(instructions.get(Integer.parseInt(scanner.nextLine())));
                            System.out.println("Enter id of the category to add: ");
                            updatedRecipe.addRecipeCategory(entityManager.find(RecipeCategory.class, Integer.parseInt(scanner.nextLine())));
                            entityManager.merge(updatedRecipe);
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 4:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to delete a category\n" +
                            "Type 2 to delete an instruction\n" +
                            "Type 3 to delete an ingredient\n" +
                            "Type 4 to delete a recipe ingredient\n" +
                            "Type 5 to delete a recipe\n" +
                            "Type 6 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                                case 1:
                                    System.out.println("Enter the id of the category to remove: ");
                                    entityManager.remove(entityManager.find(RecipeCategory.class, Integer.parseInt(scanner.nextLine())));
                                    break;
                                case 2:
                                    System.out.println("Enter the index of the instruction to remove: ");
                                    query = entityManager.createQuery("SELECT instruction FROM RecipeInstruction instruction", RecipeInstruction.class);
                                    List<RecipeInstruction> instructions = query.getResultList();
                                    for (RecipeInstruction instruction :
                                            instructions) {
                                        System.out.println(instruction.toString());
                                    }
                                    entityManager.remove(instructions.get(Integer.parseInt(scanner.nextLine())));
                                    break;
                                case 3:
                                    System.out.println("Enter the id of the ingredient to remove: ");
                                    entityManager.remove(entityManager.find(Ingredient.class, Integer.parseInt(scanner.nextLine())));
                                    break;
                                case 4:
                                    System.out.println("Enter the index of the recipe ingredient to remove: ");
                                    query = entityManager.createQuery("SELECT recipeIngredient FROM RecipeIngredient recipeIngredient", RecipeIngredient.class);
                                    List<RecipeIngredient> recipeIngredients = query.getResultList();
                                    for (RecipeIngredient recipeIngredient :
                                            recipeIngredients) {
                                        System.out.println(recipeIngredient.toString());
                                    }
                                    entityManager.remove(recipeIngredients.get(Integer.parseInt(scanner.nextLine())));
                                    break;
                                case 5:
                                    System.out.println("Enter the id of the recipe to remove: ");
                                    entityManager.remove(entityManager.find(Recipe.class, Integer.parseInt(scanner.nextLine())));
                                    break;
                                case 6:
                                    break;
                                default:
                                    System.out.println("Not a valid input");
                    }
                    break;
                case 5:
                    keepGoing = false;
                    break;
            }
            break;
        }
    scanner.close();
    }

}

