package fr.insa.recettes.modele;

import fr.insa.recettes.modele.exceptions.*;
import fr.insa.recettes.modele.rechercheStrategy.*;
import fr.insa.recettes.utils.DataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class FacadeGestionRecettesImpl implements FacadeGestionRecettes {

    private List<Ingredient> inventaire;
    private List<Recette> recettes;
    private RechercheStrategy rechercheStrategy;

    private static FacadeGestionRecettesImpl instance = null;

    public FacadeGestionRecettesImpl() {
        inventaire = new ArrayList<>();
        recettes = new ArrayList<>();
    }

    public static synchronized FacadeGestionRecettesImpl getInstance() {
        if (instance == null) {
            try {
                instance = DataManager.chargerDonnees();
            } catch (IOException e) {
                System.out.println("Le fichier de données n'a pas pu être chargé : " + e.getMessage());
                instance = new FacadeGestionRecettesImpl();
            }
        }
        return instance;
    }

    @Override
    public void ajouterIngredient(Ingredient ingredient) throws IngredientDejaExistantException, SauvegardeException {
        for (Ingredient i : inventaire) {
            if (i.getNom().equalsIgnoreCase(ingredient.getNom())) {
                throw new IngredientDejaExistantException("L'ingrédient existe déjà.");
            }
        }
        inventaire.add(ingredient);
        sauvegarderDonnees();
    }

    @Override
    public void modifierIngredient(Ingredient ingredient) throws IngredientIntrouvableException, SauvegardeException {
        boolean found = false;
        for (int i = 0; i < inventaire.size(); i++) {
            if (inventaire.get(i).getNom().equalsIgnoreCase(ingredient.getNom())) {
                inventaire.set(i, ingredient);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IngredientIntrouvableException("Ingrédient introuvable.");
        }
        sauvegarderDonnees();
    }

    @Override
    public void supprimerIngredient(String nom) throws IngredientIntrouvableException, SauvegardeException {
        boolean removed = inventaire.removeIf(i -> i.getNom().equalsIgnoreCase(nom));
        if (!removed) {
            throw new IngredientIntrouvableException("Ingrédient introuvable.");
        }
        sauvegarderDonnees();
    }

    @Override
    public List<Recette> rechercherRecettes(String strategy, String str){
        switch(strategy){
            case "Nom" :
                this.setRechercheStrategy(new RechercheParNomStrategy());
                break;
            case "Categorie" :
                this.setRechercheStrategy(new RechercheParCategorieStrategy());
                break;
            case "Option":
                this.setRechercheStrategy(new RechercheParOptionStrategy());
                break;

            case "Favori":
                this.setRechercheStrategy(new RechercheParFavoriStrategy());
                break;
            default:
                break;
        }
        return this.executeRechercheStrategy(this.getRecettes(), str);
    }

    @Override
    public List<Ingredient> getInventaire() {
        return new ArrayList<>(inventaire);
    }

    @Override
    public void ajouterRecette(Recette recette) throws RecetteDejaExistanteException, SauvegardeException {
        for (Recette r : recettes) {
            if (r.getId() == recette.getId()) {
                throw new RecetteDejaExistanteException("La recette existe déjà.");
            }
        }
        recettes.add(recette);
        sauvegarderDonnees();
    }

    @Override
    public void modifierRecette(Recette recette) throws RecetteIntrouvableException, SauvegardeException {

        boolean found = false;
        for (int i = 0; i < recettes.size(); i++) {
            System.out.println(" ID : "+recettes.get(i).getId() + " "+ recette.getId());
            if (recettes.get(i).getId() == recette.getId()) {
                recettes.set(i, recette);

                System.out.println(recettes.get(i).getNom()+" "+recettes.get(i).getCategorie());
                found = true;
                break;
            }
        }


        if (!found) {
            throw new RecetteIntrouvableException("Recette introuvable.");
        }
        sauvegarderDonnees();
    }

    @Override
    public void supprimerRecette(int id) throws RecetteIntrouvableException, SauvegardeException {
        boolean removed = recettes.removeIf(r -> r.getId() == id);
        if (!removed) {
            throw new RecetteIntrouvableException("Recette introuvable.");
        }
        sauvegarderDonnees();
    }

    @Override
    public List<Recette> getRecettes() {
        return new ArrayList<>(recettes);
    }

    @Override
    public List<Recette> getRecettesRealisables() {
        List<Recette> recettesRealisables = new ArrayList<>();
        for (Recette recette : recettes) {
            if (getIngredientsManquants(recette).isEmpty()) {
                recettesRealisables.add(recette);
            }
        }
        return recettesRealisables;
    }

    @Override
    public List<Ingredient> getIngredientsManquants(Recette recette) {
        List<Ingredient> ingredientsManquants = new ArrayList<>();

        for (Ingredient ingredientRecette : recette.getIngredients()) {
            boolean ingredientTrouve = false;
            for (Ingredient ingredientInventaire : inventaire) {
                if (ingredientInventaire.getNom().equalsIgnoreCase(ingredientRecette.getNom())) {
                    if (ingredientInventaire.getQuantite() >= ingredientRecette.getQuantite()) {
                        ingredientTrouve = true;
                        break;
                    } else {
                        // Quantité insuffisante
                        Ingredient ingredientManquant = new Ingredient(
                                ingredientRecette.getNom(),
                                ingredientRecette.getQuantite() - ingredientInventaire.getQuantite(),
                                ingredientRecette.getUnite()
                        );
                        ingredientsManquants.add(ingredientManquant);
                        ingredientTrouve = true;
                        break;
                    }
                }
            }
            if (!ingredientTrouve) {
                // Ingrédient manquant
                ingredientsManquants.add(ingredientRecette);
            }
        }
        return ingredientsManquants;
    }

    @Override
    public int getMaxId() {
        int max_id = 0;
        for (Recette recette : recettes) {
            if (recette.getId() > max_id) {
                max_id = recette.getId();
            }
        }
        return max_id;
    }



    @Override
    public void sauvegarderDonnees() throws SauvegardeException {
        try {
            DataManager.sauvegarderDonnees(this);
        } catch (Exception e) {
            throw new SauvegardeException("Erreur lors de la sauvegarde des données.");
        }
    }

    @Override
    public void chargerDonnees() throws ChargementException {
        try {
            instance = DataManager.chargerDonnees();
        } catch (Exception e) {
            throw new ChargementException("Erreur lors du chargement des données.");
        }
    }

    public void setRechercheStrategy(RechercheStrategy rechercheStrategy) {
        this.rechercheStrategy = rechercheStrategy;
    }

    public List<Recette> executeRechercheStrategy(List<Recette> recettes, String str) {
        return this.rechercheStrategy.recherche(this.getRecettes(), str);
    }
}
