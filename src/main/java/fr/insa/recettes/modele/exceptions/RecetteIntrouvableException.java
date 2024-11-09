package fr.insa.recettes.modele.exceptions;

public class RecetteIntrouvableException extends Exception {
    public RecetteIntrouvableException(String s) {
        super("La recette n'a pas été trouvée dans la base de données.");
    }
}