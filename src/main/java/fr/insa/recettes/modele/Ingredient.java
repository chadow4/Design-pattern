package fr.insa.recettes.modele;

public class Ingredient {
    private String nom;
    private double quantite;
    private String unite;

    public Ingredient() {
    }

    public Ingredient(String nom, double quantite, String unite) {
        this.nom = nom;
        this.quantite = quantite;
        this.unite = unite;
    }

    public String getNom() {
        return nom;
    }

    public double getQuantite() {
        return quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    @Override
    public String toString() {
        return this.nom; // ou simplement 'nom'
    }
}