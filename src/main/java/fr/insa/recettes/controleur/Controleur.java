package fr.insa.recettes.controleur;

import fr.insa.recettes.modele.*;
import fr.insa.recettes.modele.exceptions.*;
import fr.insa.recettes.vues.GestionnaireVue;

import java.util.*;

public class Controleur implements LanceurOrdre {

    private Map<TypeOrdre, List<EcouteurOrdre>> ecouteursOrdres;
    private FacadeGestionRecettes facadeGestionRecettes;

    public Controleur(GestionnaireVue gestionnaireVue, FacadeGestionRecettes facadeGestionRecettes) {
        this.facadeGestionRecettes = facadeGestionRecettes;
        ecouteursOrdres = new HashMap<>();
        for (TypeOrdre type : TypeOrdre.values()) {
            ecouteursOrdres.put(type, new ArrayList<>());
        }
        gestionnaireVue.setControleur(this);
        gestionnaireVue.setAbonnement(this);
    }

    @Override
    public void abonnement(EcouteurOrdre ecouteurOrdre, TypeOrdre... types) {
        for (TypeOrdre type : types) {
            ecouteursOrdres.get(type).add(ecouteurOrdre);
        }
    }

    @Override
    public void fireOrdre(TypeOrdre e) {
        for (EcouteurOrdre ecouteur : ecouteursOrdres.get(e)) {
            ecouteur.traiter(e);
        }
    }

    public void run() {
        fireOrdre(TypeOrdre.DATA_LOAD);
        fireOrdre(TypeOrdre.SHOW_ACCUEIL);
    }

    // Methods for user actions

    public void ajouterIngredient(String nom, double quantite, String unite) {
        try {
            Ingredient ingredient = new Ingredient(nom, quantite, unite);
            facadeGestionRecettes.ajouterIngredient(ingredient);
            fireOrdre(TypeOrdre.INGREDIENT_AJOUTE);
        } catch (IngredientDejaExistantException e) {
            fireOrdre(TypeOrdre.ERREUR_INGREDIENT_EXISTANT);
        } catch (SauvegardeException e) {
            throw new RuntimeException(e);
        }
    }

    public void modifierIngredient(Ingredient ingredient) {
        try {
            facadeGestionRecettes.modifierIngredient(ingredient);
            fireOrdre(TypeOrdre.INGREDIENT_MODIFIE);
        } catch (IngredientIntrouvableException | SauvegardeException e) {
            fireOrdre(TypeOrdre.ERREUR_INGREDIENT_INTROUVABLE);
        }
    }

    public void supprimerIngredient(String nom) {
        try {
            facadeGestionRecettes.supprimerIngredient(nom);
            fireOrdre(TypeOrdre.INGREDIENT_SUPPRIME);
        } catch (IngredientIntrouvableException e) {
            fireOrdre(TypeOrdre.ERREUR_INGREDIENT_INTROUVABLE);
        } catch (SauvegardeException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouterRecette(String nom, String categorie, List<Ingredient> ingredients, String instructions,
                               int tempsPreparation, int tempsCuisson, String niveauDifficulte,
                               boolean isVegetarien, boolean isSansGluten, boolean isBio, boolean isPasCher) {
        try {


            Recette recette = new Recette(nom, categorie, ingredients, instructions,
                    tempsPreparation, tempsCuisson, niveauDifficulte,
                    isVegetarien, isSansGluten, isBio, isPasCher);
            facadeGestionRecettes.ajouterRecette(recette);
            fireOrdre(TypeOrdre.RECETTE_AJOUTEE);
        } catch (RecetteDejaExistanteException | SauvegardeException e) {
            fireOrdre(TypeOrdre.ERREUR_RECETTE_EXISTANTE);
        }
    }

    public void modifierRecette(Recette recette) {
        try {

            facadeGestionRecettes.modifierRecette(recette);
            fireOrdre(TypeOrdre.RECETTE_MODIFIEE);
        } catch (RecetteIntrouvableException | SauvegardeException e) {
            fireOrdre(TypeOrdre.ERREUR_RECETTE_INTROUVABLE);
        }
    }

    public void supprimerRecette(int id) {
        try {
            facadeGestionRecettes.supprimerRecette(id);
            fireOrdre(TypeOrdre.RECETTE_SUPPRIMEE);
        } catch (RecetteIntrouvableException | SauvegardeException e) {
            fireOrdre(TypeOrdre.ERREUR_RECETTE_INTROUVABLE);
        }
    }

    public List<Ingredient> getInventaire() {
        return facadeGestionRecettes.getInventaire();
    }

    public List<Recette> getRecettes() {
        return facadeGestionRecettes.getRecettes();
    }

    public List<Recette> getRecettesRealisables() {
        return facadeGestionRecettes.getRecettesRealisables();
    }

    public List<Recette> rechercherRecettesParNom(String nom) {
        return facadeGestionRecettes.rechercherRecettes("Nom", nom);
    }

    public List<Recette> filtrerRecettesParCategorie(String categorie) {
        return facadeGestionRecettes.rechercherRecettes("Categorie", categorie);
    }

    public List<Recette> filtrerRecettesParOption(String option) {
        return facadeGestionRecettes.rechercherRecettes("Option", option);
    }

    public void initRecetteId() {
        Recette r = new Recette();
        r.setMaxId(facadeGestionRecettes.getMaxId());
        System.out.println("INIT : "+ r.getMaxId());
    }



    public List<Ingredient> getIngredientsManquants(Recette recette) {
        return facadeGestionRecettes.getIngredientsManquants(recette);
    }



    public Recette getRecetteParNom(String nom) {
        for (Recette recette : facadeGestionRecettes.getRecettes()) {
            if (recette.getNom().equalsIgnoreCase(nom)) {
                return recette;
            }
        }
        return null;
    }
}
