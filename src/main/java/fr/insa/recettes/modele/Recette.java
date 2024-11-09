package fr.insa.recettes.modele;

import java.util.List;

public class Recette {
    private String nom;
    private String categorie;
    private List<Ingredient> ingredients;
    private String instructions;
    private int tempsPreparation;
    private int tempsCuisson;
    private String niveauDifficulte;

    public Recette() {
        // Default constructor for JSON deserialization
    }

    public Recette(String nom, String categorie, List<Ingredient> ingredients, String instructions,
                   int tempsPreparation, int tempsCuisson, String niveauDifficulte) {
        this.nom = nom;
        this.categorie = categorie;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.tempsPreparation = tempsPreparation;
        this.tempsCuisson = tempsCuisson;
        this.niveauDifficulte = niveauDifficulte;
    }

    // Getters and Setters
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

    public void setNom(String nom) {
        this.nom = nom;
    }

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

    @Override
    public String toString() {
        return this.nom; // or simply 'nom'
    }
}