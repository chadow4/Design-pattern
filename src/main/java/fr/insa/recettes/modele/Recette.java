package fr.insa.recettes.modele;

import java.util.List;
import java.util.Map;

public class Recette {

    private static int maxId = 1;

    private int id;
    private String nom;
    private String categorie;
    private List<Ingredient> ingredients;
    private String instructions;
    private int tempsPreparation;
    private int tempsCuisson;
    private String niveauDifficulte;
    private boolean isVegetarien;
    private boolean isSansGluten;
    private boolean isBio;
    private boolean isPasCher;
    private boolean isFavori;


    public Recette() {
        // Default constructor for JSON deserialization
    }

    public Recette(String nom, String categorie, List<Ingredient> ingredients, String instructions,
                   int tempsPreparation, int tempsCuisson, String niveauDifficulte,boolean isVegetarien, boolean isSansGluten, boolean isBio, boolean isPasCher) {

        this.id = ++maxId;
        this.nom = nom;
        this.categorie = categorie;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.tempsPreparation = tempsPreparation;
        this.tempsCuisson = tempsCuisson;
        this.niveauDifficulte = niveauDifficulte;
        this.isVegetarien = isVegetarien;
        this.isSansGluten = isSansGluten;
        this.isBio = isBio;
        this.isPasCher = isPasCher;
    }

    // Getters and Setters

    public int getMaxId() { return maxId;}

    public int getId() { return id;}

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public int getTempsPreparation() {
        return tempsPreparation;
    }

    public int getTempsCuisson() {
        return tempsCuisson;
    }

    public String getNiveauDifficulte() {
        return niveauDifficulte;
    }

    public boolean getIsVegetarien() { return isVegetarien; }

    public boolean getIsSansGluten() { return isSansGluten; }

    public boolean getIsBio() { return isBio; }

    public boolean getIsPasCher() { return isPasCher; }

    public boolean getIsFavori() { return isFavori;}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMaxId(int newMaxId) { maxId = newMaxId;}

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setTempsPreparation(int tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public void setTempsCuisson(int tempsCuisson) {
        this.tempsCuisson = tempsCuisson;
    }

    public void setNiveauDifficulte(String niveauDifficulte) {
        this.niveauDifficulte = niveauDifficulte;
    }

    public void setIsVegetarien(boolean isVegetarien) { this.isVegetarien = isVegetarien; }

    public void setIsSansGluten(boolean isSansGluten) { this.isSansGluten = isSansGluten; }

    public void setIsBio(boolean isBio) { this.isBio = isBio; }

    public void setIsPasCher(boolean isPasCher) { this.isPasCher = isPasCher; }

    public void setIsFavori(boolean isFavori) { this.isFavori = isFavori;}

    @Override
    public String toString() {
        return this.nom; // or simply 'nom'
    }
}
