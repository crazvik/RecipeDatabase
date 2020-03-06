package se.ecutb.jonatan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import se.ecutb.jonatan.entity.Measurement;
import se.ecutb.jonatan.entity.Recipe;
import se.ecutb.jonatan.entity.RecipeCategory;
import se.ecutb.jonatan.entity.RecipeIngredient;
import se.ecutb.jonatan.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CommandLine implements CommandLineRunner {
    private IngredientService ingredientService;
    private RecipeIngredientService recipeIngredientService;
    private RecipeService recipeService;
    private RecipeInstructionService recipeInstructionService;
    private RecipeCategoryService recipeCategoryService;
    private Scanner scanner = new Scanner(System.in);
    private boolean keepGoing = true;

    @Autowired
    public CommandLine(IngredientService ingredientService, RecipeService recipeService,
                       RecipeIngredientService recipeIngredientService, RecipeInstructionService recipeInstructionService,
                       RecipeCategoryService recipeCategoryService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.recipeInstructionService = recipeInstructionService;
        this.recipeCategoryService = recipeCategoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (keepGoing) {
            System.out.println("\n----------------------------------------------\n" +
                    "Type 1 to work with ingredients\n" +
                    "Type 2 to work with recipe ingredients\n" +
                    "Type 3 to work with recipes\n" +
                    "Type 4 to work with recipe instructions\n" +
                    "Type 5 to work with recipe categories\n" +
                    "Type 6 to quit" +
                    "\n----------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to add new ingredient\n" +
                            "Type 2 to see all ingredients\n" +
                            "Type 3 to update ingredient\n" +
                            "Type 4 to delete ingredient by id\n" +
                            "Type 5 to find ingredient by name\n" +
                            "Type 6 to find ingredients by part of their name\n" +
                            "Type 7 to quit" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter a name for the ingredient: ");
                            System.out.println(ingredientService.createAndSave(scanner.nextLine()));
                            break;
                        case 2:
                            System.out.println(ingredientService.readAll());
                            break;
                        case 3:
                            System.out.println("Enter the id and new name of the ingredient: ");
                            ingredientService.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine());
                            break;
                        case 4:
                            System.out.println("Enter the id of the ingredient you want to remove: ");
                            ingredientService.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 5:
                            System.out.println("Enter the name of the ingredient you want to find: ");
                            System.out.println(ingredientService.findByName(scanner.nextLine()));
                            break;
                        case 6:
                            System.out.println("Enter a part of the name of the ingredient you want to find: ");
                            System.out.println(ingredientService.findByPartOfName(scanner.nextLine()));
                            break;
                        case 7:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 2:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to add new recipe\n" +
                            "Type 2 to see all recipes\n" +
                            "Type 3 to update recipe\n" +
                            "Type 4 to delete recipe by id\n" +
                            "Type 5 to quit" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter a name for the recipe and the index of the instruction you want to use: ");
                            recipeService.createAndSave(scanner.nextLine(), recipeInstructionService.readAll().get(Integer.parseInt(scanner.nextLine())));
                            System.out.println("How many ingredients do you want to add?: ");
                            int numberOfIngredients = Integer.parseInt(scanner.nextLine());
                            List<RecipeIngredient> tempRecipeIngredients = new ArrayList<>();

                            for (int i=0; i<numberOfIngredients; i++) {
                                System.out.println("Enter the id of the ingredient to add, amount, one of following measurements: (L, DL, CL, ML,\n" +
                                        "KG, HG ,G, ST, MSK, TSK), and the id of the recipe to add to: ");
                               tempRecipeIngredients.add(recipeIngredientService.createAndSave(ingredientService.readAll().get(Integer.parseInt(scanner.nextLine())),
                                                Double.parseDouble(scanner.nextLine()),
                                                Measurement.valueOf(scanner.nextLine().toUpperCase()),
                                                recipeService.readAll().get(recipeService.readAll().size()-1))
                                );
                               recipeService.findById(recipeService.readAll().size()-1).setRecipeIngredients(tempRecipeIngredients);
                            }
                            System.out.println("How many categories do you want to add the recipe to?: ");
                            numberOfIngredients = Integer.parseInt(scanner.nextLine());
                            List<RecipeCategory> tempCategories = new ArrayList<>();
                            for (int i=0; i<numberOfIngredients; i++) {
                                System.out.println("Enter a name for the category: ");
                                tempCategories.add(recipeCategoryService.createAndSave(scanner.nextLine(),
                                        recipeService.readAll().size()-1));
                                recipeService.findById(recipeService.readAll().size()-1).setCategories(tempCategories);
                            }
                            break;
                        case 2:
                            System.out.println(recipeService.readAll());
                            break;
                        case 3:
                            System.out.println("Enter the id, new name and instruction id: ");
                            recipeService.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine(), null,
                                    recipeInstructionService.readAll().get(Integer.parseInt(scanner.nextLine())), null);
                            break;
                        case 4:
                            System.out.println("Enter the id of the recipe you want to remove: ");
                            recipeService.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 5:
                            System.out.println(recipeService.findById(13).getCategories());
                            System.out.println("Enter a part of the name of the recipe you want to find: ");
                            System.out.println(recipeService.findByPartOfName(scanner.nextLine()).toString());

                            break;
                        case 6:
                            System.out.println("Enter an ingredient to find recipes for: ");
                            System.out.println(recipeService.findByContainedIngredient(scanner.nextLine()));
                            break;
                        case 7:
                            System.out.println("Enter a category to find recipes in: ");
                            System.out.println(recipeService.findByCategory(scanner.nextLine()));
                            break;
                        case 8:
                            System.out.println(recipeService.findByManyCategories());
                            break;
                        case 9:
                            for (Recipe recipe:
                                 recipeService.readAll()) {
                                System.out.println(recipe.getCategories());
                            }
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 3:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to add new instruction\n" +
                            "Type 2 to see all instructions\n" +
                            "Type 3 to update instruction by id\n" +
                            "Type 4 to delete instruction by id\n" +
                            "Type 5 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter an instruction: ");
                            System.out.println(recipeInstructionService.createAndSave(scanner.nextLine()));
                            break;
                        case 2:
                            System.out.println(recipeInstructionService.readAll().toString());
                            break;
                        case 3:
                            System.out.println("Enter the index of the instruction and new instructions: ");
                            recipeInstructionService.update(recipeInstructionService.readAll().get(Integer.parseInt(scanner.nextLine())), scanner.nextLine());
                            break;
                        case 4:
                            System.out.println("Enter the index of the instruction to delete:");
                            recipeInstructionService.delete(recipeInstructionService.readAll().get(Integer.parseInt(scanner.nextLine())));
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 4:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to add new recipe\n" +
                            "Type 2 to see all recipes\n" +
                            "Type 3 to update recipe\n" +
                            "Type 4 to delete recipe by id\n" +
                            "Type 5 to quit" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter the id of the ingredient to add, amount, one of following measurements: (L, DL, CL, ML,\n" +
                                    "KG, HG ,G, ST, MSK, TSK), and the id of the recipe to add to: ");
                            recipeIngredientService.createAndSave(ingredientService.readAll().get(Integer.parseInt(scanner.nextLine())),
                                    Double.parseDouble(scanner.nextLine()),
                                    Measurement.valueOf(scanner.nextLine().toUpperCase()),
                                    recipeService.readAll().get(Integer.parseInt(scanner.nextLine())));
                            break;
                        case 2:
                            System.out.println(recipeIngredientService.readAll());
                            break;
                        case 3:
                            System.out.println("Enter the index of the recipe ingredient to update, the id of an ingredient to add, amount, one of following measurements: (L, DL, CL, ML,\n" +
                                    "KG, HG ,G, ST, MSK, TSK), and the id of the recipe to add to: ");
                            recipeIngredientService.update(recipeIngredientService.readAll().get(Integer.parseInt(scanner.nextLine())),
                                    ingredientService.readAll().get(Integer.parseInt(scanner.nextLine())),
                                    Double.parseDouble(scanner.nextLine()),
                                    Measurement.valueOf(scanner.nextLine().toUpperCase()),
                                    recipeService.readAll().get(Integer.parseInt(scanner.nextLine())));
                            break;
                        case 4:
                            System.out.println("Enter the index of the recipe ingredient to delete");
                            recipeIngredientService.delete(recipeIngredientService.readAll().get(Integer.parseInt(scanner.nextLine())));
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 5:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to add new category\n" +
                            "Type 2 to see all categories\n" +
                            "Type 3 to update category by id\n" +
                            "Type 4 to delete category by id\n" +
                            "Type 5 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter a category: ");
                            System.out.println(recipeCategoryService.createAndSave(scanner.nextLine(), 0));
                            break;
                        case 2:
                            System.out.println(recipeCategoryService.readAll().toString());
                            break;
                        case 3:
                            System.out.println("Enter the id of the category and new category name: ");
                            recipeCategoryService.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine(), null);
                            break;
                        case 4:
                            System.out.println("Enter the id of the category to delete:");
                            recipeCategoryService.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    System.out.println("Not a valid input");
            }
        }
        scanner.close();
    }
}
