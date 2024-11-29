package fr.insa.recettes.vues;

import fr.insa.recettes.modele.Ingredient;
import fr.insa.recettes.modele.Recette;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class RecetteDialog extends Dialog<Recette> {

    private TextField nomField;
    private ComboBox<String> categorieCombo;
    private TextArea instructionsArea;
    private TextField tempsPreparationField;
    private TextField tempsCuissonField;
    private ComboBox<String> niveauDifficulteCombo;
    private CheckBox vegetarienCheck;
    private CheckBox sansGlutenCheck;
    private CheckBox bioCheck;
    private CheckBox pasCherCheck;
    private CheckBox favoriCheck;

    private ComboBox<Ingredient> ingredientComboBox;
    private TextField ingredientQuantiteField;
    private ListView<String> ingredientsListView;
    private ObservableList<String> ingredientsObservableList;
    private List<Ingredient> ingredientsRecette;

    public RecetteDialog(List<Ingredient> inventaire) {
        this(null, inventaire);
    }

    public RecetteDialog(Recette recette, List<Ingredient> inventaire) {
        setTitle("Recette");
        setHeaderText(null);

        ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nomField = new TextField();
        nomField.setPromptText("Nom de la recette");

        categorieCombo = new ComboBox<String>();
        categorieCombo.setPromptText("Catégorie");
        categorieCombo.setItems(FXCollections.observableArrayList("Entrée", "Plat principal", "Dessert"));;

        instructionsArea = new TextArea();
        instructionsArea.setPromptText("Instructions");
        instructionsArea.setPrefRowCount(4);

        tempsPreparationField = new TextField();
        tempsPreparationField.setPromptText("Temps de préparation (min)");

        tempsCuissonField = new TextField();
        tempsCuissonField.setPromptText("Temps de cuisson (min)");

        niveauDifficulteCombo = new ComboBox<>();
        niveauDifficulteCombo.setItems(FXCollections.observableArrayList("Facile", "Intermédiaire", "Difficile"));

        vegetarienCheck = new CheckBox("Végétarien");
        sansGlutenCheck = new CheckBox("Sans gluten");
        bioCheck = new CheckBox("Ingrédients bio");
        pasCherCheck = new CheckBox("Pas cher");
        favoriCheck = new CheckBox("Favori");

        // Gestion des ingrédients
        ingredientComboBox = new ComboBox<>();
        ingredientComboBox.setItems(FXCollections.observableArrayList(inventaire));
        ingredientComboBox.setPromptText("Sélectionner un ingrédient");
        ingredientQuantiteField = new TextField();
        ingredientQuantiteField.setPromptText("Quantité");

        Button ajouterIngredientButton = new Button("Ajouter");
        ajouterIngredientButton.setOnAction(event -> ajouterIngredient());

        HBox ingredientHBox = new HBox(10, ingredientComboBox, ingredientQuantiteField, ajouterIngredientButton);

        ingredientsObservableList = FXCollections.observableArrayList();
        ingredientsListView = new ListView<>(ingredientsObservableList);
        ingredientsListView.setPrefHeight(100);

        Button supprimerIngredientButton = new Button("Supprimer l'ingrédient sélectionné");
        supprimerIngredientButton.setOnAction(event -> supprimerIngredient());

        ingredientsRecette = new ArrayList<>();

        grid.add(new Label("Nom de la recette:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Catégorie:"), 0, 1);
        grid.add(categorieCombo, 1, 1);
        grid.add(new Label("Instructions:"), 0, 2);
        grid.add(instructionsArea, 1, 2);
        grid.add(new Label("Temps de préparation (min):"), 0, 3);
        grid.add(tempsPreparationField, 1, 3);
        grid.add(new Label("Temps de cuisson (min):"), 0, 4);
        grid.add(tempsCuissonField, 1, 4);
        grid.add(new Label("Niveau de difficulté:"), 0, 5);
        grid.add(niveauDifficulteCombo, 1, 5);
        grid.add(vegetarienCheck, 0, 6);
        grid.add(sansGlutenCheck, 1, 6);
        grid.add(bioCheck, 0, 7);
        grid.add(pasCherCheck, 1, 7);
        grid.add(favoriCheck, 0, 8);
        grid.add(new Label("Ingrédients:"), 0, 9);
        grid.add(ingredientHBox, 1, 9);
        grid.add(ingredientsListView, 1, 10);
        grid.add(supprimerIngredientButton, 1, 11);

        if (recette != null) {
            nomField.setText(recette.getNom());
            categorieCombo.setValue(recette.getCategorie());
            instructionsArea.setText(recette.getInstructions());
            tempsPreparationField.setText(String.valueOf(recette.getTempsPreparation()));
            tempsCuissonField.setText(String.valueOf(recette.getTempsCuisson()));
            niveauDifficulteCombo.setValue(recette.getNiveauDifficulte());
            ingredientsRecette = new ArrayList<>(recette.getIngredients());
            vegetarienCheck.setSelected(recette.getIsVegetarien());
            sansGlutenCheck.setSelected(recette.getIsSansGluten());
            bioCheck.setSelected(recette.getIsBio());
            pasCherCheck.setSelected(recette.getIsPasCher());
            favoriCheck.setSelected(recette.getIsFavori());
            for (Ingredient ingredient : ingredientsRecette) {
                ingredientsObservableList.add(ingredient.getNom() + " - " + ingredient.getQuantite() + " " + ingredient.getUnite());
            }
        }

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == validerButtonType) {
                try {
                    String nomRecette = nomField.getText();
                    String categorie = categorieCombo.getValue();
                    String instructions = instructionsArea.getText();
                    int tempsPreparation = Integer.parseInt(tempsPreparationField.getText());
                    int tempsCuisson = Integer.parseInt(tempsCuissonField.getText());
                    String niveauDifficulte = niveauDifficulteCombo.getValue();
                    
                    boolean isVegetarien = vegetarienCheck.isSelected();
                    boolean isSansGluten = sansGlutenCheck.isSelected();
                    boolean isBio = bioCheck.isSelected();
                    boolean isPasCher = pasCherCheck.isSelected();
                    boolean isFavori = favoriCheck.isSelected();
                    
                    if (categorie == null || nomRecette.isEmpty() || categorie.isEmpty() || instructions.isEmpty() || niveauDifficulte == null || ingredientsRecette.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs et ajouter au moins un ingrédient.", ButtonType.OK);
                        alert.showAndWait();
                        return null;
                    }
                    if(recette != null) {
                        recette.setNom(nomRecette);
                        recette.setCategorie(categorie);
                        recette.setInstructions(instructions);
                        recette.setTempsPreparation(tempsPreparation);
                        recette.setTempsCuisson(tempsCuisson);
                        recette.setNiveauDifficulte(niveauDifficulte);
                        recette.setIsVegetarien(isVegetarien);
                        recette.setIsSansGluten(isSansGluten);
                        recette.setIsBio(isBio);
                        recette.setIsPasCher(isPasCher);
                        recette.setIsFavori(isFavori);
                        return recette;
                    }else{
                        return new Recette(nomRecette, categorie, ingredientsRecette, instructions, tempsPreparation, tempsCuisson, niveauDifficulte, isVegetarien, isSansGluten, isBio, isPasCher);
                    }




                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Les temps de préparation et de cuisson doivent être des nombres entiers.", ButtonType.OK);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    }

    private void ajouterIngredient() {
        Ingredient selectedIngredient = ingredientComboBox.getValue();
        String quantiteStr = ingredientQuantiteField.getText();

        if (selectedIngredient == null || quantiteStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner un ingrédient et saisir une quantité.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            double quantite = Double.parseDouble(quantiteStr);
            Ingredient ingredientRecette = new Ingredient(selectedIngredient.getNom(), quantite, selectedIngredient.getUnite());
            ingredientsRecette.add(ingredientRecette);
            ingredientsObservableList.add(ingredientRecette.getNom() + " - " + quantite + " " + ingredientRecette.getUnite());
            ingredientComboBox.getSelectionModel().clearSelection();
            ingredientQuantiteField.clear();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La quantité doit être un nombre.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void supprimerIngredient() {
        int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ingredientsRecette.remove(selectedIndex);
            ingredientsObservableList.remove(selectedIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner un ingrédient à supprimer.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
