package fr.insa.recettes.modele.exceptions;

public class IngredientDejaExistantException extends Exception {
    public IngredientDejaExistantException(String s) {
        super("L'ingrédient existe déjà dans l'inventaire.");
    }
}