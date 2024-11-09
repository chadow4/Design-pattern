package fr.insa.recettes.modele.exceptions;

public class RecetteDejaExistanteException extends Exception {
    public RecetteDejaExistanteException(String s) {
        super("La recette existe déjà dans la base de données.");
    }
}