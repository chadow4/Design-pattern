package fr.insa.recettes.modele;

import fr.insa.recettes.modele.exceptions.*;

import java.util.List;

public interface FacadeGestionRecettes {
    void ajouterIngredient(Ingredient ingredient) throws IngredientDejaExistantException, SauvegardeException;

    void modifierIngredient(Ingredient ingredient) throws IngredientIntrouvableException, SauvegardeException;

    void supprimerIngredient(String nom) throws IngredientIntrouvableException, SauvegardeException;


    List<Ingredient> getInventaire();

    void ajouterRecette(Recette recette) throws RecetteDejaExistanteException, SauvegardeException;

    void modifierRecette(Recette recette) throws RecetteIntrouvableException, SauvegardeException;

    void supprimerRecette(String nom) throws RecetteIntrouvableException, SauvegardeException;

    List<Recette> getRecettes();

    List<Recette> rechercherRecettesParNom(String nom);

    List<Recette> filtrerRecettesParCategorie(String categorie);

    List<Recette> getRecettesRealisables();

    List<Ingredient> getIngredientsManquants(Recette recette);

    int getMaxId();

    void sauvegarderDonnees() throws SauvegardeException;

    void chargerDonnees() throws ChargementException;

}
