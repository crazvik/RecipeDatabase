package se.ecutb.jonatan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.ecutb.jonatan.entity.*;
import se.ecutb.jonatan.dao.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CommandLine implements CommandLineRunner {
    private IngredientDao ingredientDao;
    private RecipeIngredientDao recipeIngredientDao;
    private RecipeInstructionDao recipeInstructionDao;
    private RecipeCategoryDao recipeCategoryDao;
    private RecipeDao recipeDao;
    private Scanner scanner = new Scanner(System.in);
    private boolean keepGoing = true;
    int i;

    @Autowired
    public CommandLine(IngredientDao ingredientDao, RecipeIngredientDao recipeIngredientDao,
                       RecipeInstructionDao recipeInstructionDao, RecipeCategoryDao recipeCategoryDao,
                       RecipeDao recipeDao) {
        this.ingredientDao = ingredientDao;
        this.recipeIngredientDao = recipeIngredientDao;
        this.recipeInstructionDao = recipeInstructionDao;
        this.recipeCategoryDao = recipeCategoryDao;
        this.recipeDao = recipeDao;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        while (keepGoing) {
            System.out.println("\n----------------------------------------------\n" +
                    "Type 1 to create new table rows\n" +
                    "Type 2 to see all table rows\n" +
                    "Type 3 to update table rows\n" +
                    "Type 4 to delete table by id or index\n" +
                    "Type 5 to find recipes or ingredients\n" +
                    "Type 6 to quit" +
                    "\n----------------------------------------------");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to create a number of categories\n" +
                            "Type 2 to create a number of instructions\n" +
                            "Type 3 to create a number of ingredients\n" +
                            "Type 4 to create a number of recipe ingredients\n" +
                            "Type 5 to create a number of recipes\n" +
                            "Type 6 to add categories to a recipe\n" +
                            "Type 7 to add ingredients to a recipe\n" +
                            "Type 8 to add an instruction to a recipe\n" +
                            "Type 9 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("How many categories do you want to add?: ");
                            int number = Integer.parseInt(scanner.nextLine());
                            for (i = 0; i < number; i++) {
                                System.out.println("Enter a category name");
                                System.out.println(recipeCategoryDao.createAndSave(scanner.nextLine()).toString());
                            }
                            break;
                        case 2:
                            System.out.println("How many instructions do you want to add?: ");
                            number = Integer.parseInt(scanner.nextLine());
                            for (i = 0; i < number; i++) {
                                System.out.println("Enter an instruction");
                                System.out.println(recipeInstructionDao.createAndSave(scanner.nextLine()).toString());
                            }
                            break;
                        case 3:
                            System.out.println("How many ingredients do you want to add?: ");
                            number = Integer.parseInt(scanner.nextLine());
                            for (i = 0; i < number; i++) {
                                System.out.println("Enter an ingredient name");
                                System.out.println(ingredientDao.createAndSave(scanner.nextLine()).toString());
                            }
                            break;
                        case 4:
                            System.out.println("How many recipe ingredients do you want to add?: ");
                            number = Integer.parseInt(scanner.nextLine());
                            for (i = 0; i < number; i++) {
                                System.out.println("Enter the id of the wanted ingredient, a new amount and one of following measurements: (L, DL, CL, ML,\n" +
                                        "KG, HG ,G, ST, MSK, TSK): ");
                                System.out.println(recipeIngredientDao.createAndSave(Integer.parseInt(scanner.nextLine()), Double.parseDouble(scanner.nextLine()),
                                        Measurement.valueOf(scanner.nextLine().toUpperCase())));
                            }
                            break;
                        case 5:
                            System.out.println("How many recipes do you want to add?: ");
                            number = Integer.parseInt(scanner.nextLine());
                            for (i = 0; i < number; i++) {
                                System.out.println("Enter a recipe name");
                                System.out.println(recipeDao.createAndSave(scanner.nextLine()));
                            }
                            break;
                        case 6:
                            System.out.println("Enter the id of the recipe you want to add categories to");
                            int subChoice = Integer.parseInt(scanner.nextLine());
                            System.out.println("How many categories do you want to add the recipe to?: ");
                            choice = Integer.parseInt(scanner.nextLine());
                            for (i = 0; i < choice; i++) {
                                System.out.println("Enter the id of the category to add the recipe to");
                                recipeDao.addRecipeCategory(subChoice, Integer.parseInt(scanner.nextLine()));
                            }
                            break;
                        case 7:
                            System.out.println("Enter the id of the recipe you want to add ingredients to: ");
                            subChoice = Integer.parseInt(scanner.nextLine());
                            System.out.println("How many ingredients do you want to add?: ");
                            choice = Integer.parseInt(scanner.nextLine());
                            i=0;
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredientDao.readAll()) {
                                System.out.println(i++ + " " + recipeIngredient.toString());
                            }
                            System.out.println("\nEnter the index of the ingredient to add: ");
                            for(i=0; i<choice; i++) {
                                recipeDao.addRecipeIngredient(subChoice, recipeIngredientDao.readAll().get(Integer.parseInt(scanner.nextLine())));
                            }
                            break;
                        case 8:
                            System.out.println("Enter the id of the recipe you want to add an instruction to: ");
                            subChoice = Integer.parseInt(scanner.nextLine());
                            i=0;
                            for (RecipeInstruction instruction :
                                    recipeInstructionDao.readAll()) {
                                System.out.println(i++ + " " + instruction.toString());
                            }
                            System.out.println("\nEnter the index of the instruction to add: ");
                            recipeDao.addRecipeInstruction(subChoice, recipeInstructionDao.readAll().get(Integer.parseInt(scanner.nextLine())));
                            break;
                        case 9:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;

                case 2:
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
                            for (RecipeCategory category :
                                    recipeCategoryDao.readAll())
                                System.out.println(category.toString());
                            break;
                        case 2:
                            for (RecipeInstruction instruction :
                                    recipeInstructionDao.readAll()) {
                                System.out.println(instruction.toString());
                            }
                            break;
                        case 3:
                            for (Ingredient ingredient :
                                    ingredientDao.readAll()) {
                                System.out.println(ingredient.toString());
                            }
                            break;
                        case 4:
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredientDao.readAll()) {
                                System.out.println(recipeIngredient.toString());
                            }
                            break;
                        case 5:
                            for (Recipe recipe :
                                    recipeDao.readAll()) {
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
                            System.out.println("Enter the id of the category you want to update and a new category name: ");
                            System.out.println(recipeCategoryDao.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine()));
                            break;
                        case 2:
                            System.out.println("Enter the index of the instruction to update and the new instruction: ");
                            i=0;
                            for (RecipeInstruction instruction :
                                    recipeInstructionDao.readAll()) {
                                System.out.println(i++ + " " + instruction.toString());
                            }
                            System.out.println(recipeInstructionDao.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine()));
                            break;
                        case 3:
                            System.out.println("Enter the id of the ingredient you want to update and a new ingredient name: ");
                            System.out.println(ingredientDao.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine()));
                            break;
                        case 4:
                            System.out.println("Enter the index of the recipe ingredient to update: ");
                            i=0;
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredientDao.readAll()) {
                                System.out.println(i++ + " " + recipeIngredient.toString());
                            }
                            System.out.println("Enter a new amount and one of following measurements: (L, DL, CL, ML,\n" +
                                    "KG, HG ,G, ST, MSK, TSK): ");
                            System.out.println(recipeIngredientDao.update(Integer.parseInt(scanner.nextLine()), Double.parseDouble(scanner.nextLine()),
                                    Measurement.valueOf(scanner.nextLine())));
                            break;
                        case 5:
                            System.out.println("Enter the id of the recipe you want to update and a new recipe name");
                            System.out.println(recipeDao.update(Integer.parseInt(scanner.nextLine()), scanner.nextLine()));
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
                            recipeCategoryDao.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 2:
                            System.out.println("Enter the index of the instruction to remove: ");
                            i=0;
                            for (RecipeInstruction instruction :
                                    recipeInstructionDao.readAll()) {
                                System.out.println(i++ + " " + instruction.toString());
                            }
                            recipeInstructionDao.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 3:
                            System.out.println("Enter the id of the ingredient to remove: ");
                            ingredientDao.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 4:
                            System.out.println("Enter the index of the recipe ingredient to remove: ");
                            i=0;
                            for (RecipeIngredient recipeIngredient :
                                    recipeIngredientDao.readAll()) {
                                System.out.println(i++ + " " + recipeIngredient.toString());
                            }
                            recipeIngredientDao.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 5:
                            System.out.println("Enter the id of the recipe to remove: ");
                            recipeDao.delete(Integer.parseInt(scanner.nextLine()));
                            break;
                        case 6:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 5:
                    System.out.println("\n----------------------------------------------\n" +
                            "Type 1 to find an ingredient by it's exact name\n" +
                            "Type 2 to find a group of ingredients with a part of their names\n" +
                            "Type 3 to find a group of recipes with a part of their names\n" +
                            "Type 4 to find a group of recipes with a specific ingredient\n" +
                            "Type 5 to find a group of recipes with a specific category\n" +
                            "Type 6 to find a group of recipes that has more than one category\n" +
                            "Type 7 to go back" +
                            "\n----------------------------------------------");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Enter ingredient name: ");
                            System.out.println(ingredientDao.findByName(scanner.nextLine()).toString());
                            break;
                        case 2:
                            System.out.println("Enter any string: ");
                            String partOfName = scanner.nextLine();
                            for (Ingredient ingredient:
                                 ingredientDao.findByPartOfName(partOfName)) {
                                System.out.println(ingredient);
                            }
                            break;
                        case 3:
                            System.out.println("Enter any string: ");
                            partOfName = scanner.nextLine();
                            for (Recipe recipe:
                                    recipeDao.findByPartOfName(partOfName)) {
                                System.out.println(recipe);
                            }
                            break;
                        case 4:
                            System.out.println("Enter an ingredient name to find recipes for: ");
                            String ingredientName = scanner.nextLine();
                            for (Recipe recipe:
                                    recipeDao.findByContainedIngredients(ingredientName)) {
                                System.out.println(recipe);
                            }
                            break;
                        case 5:
                            System.out.println("Enter a category name to find recipes for: ");
                            String categoryName = scanner.nextLine();
                            for (Recipe recipe:
                                    recipeDao.findBySpecificCategory(categoryName)) {
                                System.out.println(recipe);
                            }
                            break;
                        case 6:
                            List<RecipeCategory> categories = new ArrayList<>();
                            System.out.println("Enter how many categories you want to look for and their indexes: ");
                            i=0;
                            for (RecipeCategory category : recipeCategoryDao.readAll()) {
                                System.out.println(i++ + " " + category);
                            }
                            choice = Integer.parseInt(scanner.nextLine());
                            for (i=0; i<choice; i++) {
                                categories.add(recipeCategoryDao.readAll().get(Integer.parseInt(scanner.nextLine())));
                            }
                            for (Recipe recipe:
                                    recipeDao.findByMultipleCategories(categories)) {
                                System.out.println(recipe);
                            }
                            break;
                        case 7:
                            break;
                        default:
                            System.out.println("Not a valid input");
                    }
                    break;
                case 6:
                    keepGoing = false;
                    break;
            }
        }
    scanner.close();
    }
}