package fr.insa.recettes.modele.exceptions;

public class IngredientIntrouvableException extends Exception {
    public IngredientIntrouvableException(String s) {
        super("L'ingrédient n'a pas été trouvé dans l'inventaire.");
    }
}