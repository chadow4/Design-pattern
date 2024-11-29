package fr.insa.recettes.modele;

import fr.insa.recettes.modele.exceptions.*;
import fr.insa.recettes.modele.rechercheStrategy.*;
import java.util.List;

public interface FacadeGestionRecettes {
    void ajouterIngredient(Ingredient ingredient) throws IngredientDejaExistantException, SauvegardeException;

    void modifierIngredient(Ingredient ingredient) throws IngredientIntrouvableException, SauvegardeException;

    void supprimerIngredient(String nom) throws IngredientIntrouvableException, SauvegardeException;

    List<Ingredient> getInventaire();

    void ajouterRecette(Recette recette) throws RecetteDejaExistanteException, SauvegardeException;

    void modifierRecette(Recette recette) throws RecetteIntrouvableException, SauvegardeException;

    void supprimerRecette(int id) throws RecetteIntrouvableException, SauvegardeException;

    List<Recette> rechercherRecettes(String strategy, String str);

    List<Recette> getRecettes();

    List<Recette> getRecettesRealisables();

    List<Ingredient> getIngredientsManquants(Recette recette);

    int getMaxId();

    void sauvegarderDonnees() throws SauvegardeException;

    void chargerDonnees() throws ChargementException;

    List<Recette> executeRechercheStrategy(List<Recette> recettes, String str);

    void setRechercheStrategy(RechercheStrategy strategy);

}
