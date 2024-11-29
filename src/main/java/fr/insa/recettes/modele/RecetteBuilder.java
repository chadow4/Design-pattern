package fr.insa.recettes.modele;

import java.util.List;
import java.util.Objects;

public class RecetteBuilder {
    private Recette recette;
    public RecetteBuilder() {
        this.recette = new Recette();
    }
    public RecetteBuilder(Recette recette) {
        if(recette == null) {
            this.recette = new Recette();
        } else {
            this.recette = recette;
        }
    }

    public RecetteBuilder setNom(String nom) {
        this.recette.setNom(nom);
        return this;
    }

    public RecetteBuilder setCategorie(String categorie) {
        this.recette.setCategorie(categorie);
        return this;
    }

    public RecetteBuilder setIngredients(List<Ingredient> ingredients) {
        this.recette.setIngredients(ingredients);
        return this;
    }

    public RecetteBuilder setInstructions(String instructions) {
        this.recette.setInstructions(instructions);
        return this;
    }

    public RecetteBuilder setTempsPreparation(int tempsPreparation) {
        this.recette.setTempsPreparation(tempsPreparation);
        return this;
    }

    public RecetteBuilder setTempsCuisson(int tempsCuisson) {
        this.recette.setTempsCuisson(tempsCuisson);
        return this;
    }

    public RecetteBuilder setNiveauDifficulte(String niveauDifficulte) {
        this.recette.setNiveauDifficulte(niveauDifficulte);
        return this;
    }

    public RecetteBuilder setIsVegetarien(boolean isVegetarien) {
        this.recette.setIsVegetarien(isVegetarien);
        return this;
    }

    public RecetteBuilder setIsSansGluten(boolean isSansGluten) {
        this.recette.setIsSansGluten(isSansGluten);
        return this;
    }
    public RecetteBuilder setIsBio(boolean isBio) {
        this.recette.setIsBio(isBio);
        return this;
    }

    public RecetteBuilder setIsPasCher(boolean isPasCher) {
        this.recette.setIsPasCher(isPasCher);
        return this;
    }

    public RecetteBuilder setIsFavori(boolean isFavori) {
        this.recette.setIsFavori(isFavori);
        return this;
    }

    public Recette build() {
        Recette result = this.recette;
        this.recette = new Recette();
        return result;
    }

    public void reset(){
        this.recette = new Recette();
    }
}
