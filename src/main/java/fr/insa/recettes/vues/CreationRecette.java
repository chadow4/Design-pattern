package fr.insa.recettes.vues;

import fr.insa.recettes.controleur.*;
import fr.insa.recettes.modele.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreationRecette implements VueInteractive, EcouteurOrdre {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField nomRecetteField;

    @FXML
    private ComboBox<String> categorieField;

    @FXML
    private TextArea instructionsArea;

    @FXML
    private TextField tempsPreparationField;

    @FXML
    private TextField tempsCuissonField;

    @FXML
    private ComboBox<String> niveauDifficulteCombo;

    @FXML
    private CheckBox optionVegetarienCheck;

    @FXML
    private CheckBox optionSansGlutenCheck;

    @FXML
    private CheckBox optionBioCheck;

    @FXML
    private CheckBox optionPasCherCheck;

    @FXML
    private ComboBox<Ingredient> ingredientComboBox;

    @FXML
    private TextField ingredientQuantiteField;

    @FXML
    private ListView<String> ingredientsListView;

    private ObservableList<String> ingredientsObservableList;

    private Scene scene;
    private Controleur controleur;

    private List<Ingredient> ingredientsRecette;


    public static CreationRecette creer(GestionnaireVue g) throws IOException {
        return VueFactory.creerVue("creationRecette.fxml", g);
    }

    public void initialiserScene(BorderPane root) {
        this.scene = new Scene(root);
        ingredientsObservableList = FXCollections.observableArrayList();
        ingredientsListView.setItems(ingredientsObservableList);
        categorieField.setItems(FXCollections.observableArrayList("Entrée", "Plat principal", "Dessert"));
        niveauDifficulteCombo.setItems(FXCollections.observableArrayList("Facile", "Intermédiaire", "Difficile"));

        ingredientsRecette = new ArrayList<>();
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public Controleur getControleur() {
        return controleur;
    }

    @Override
    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
        // Populate ingredientComboBox with ingredients from inventory
        ingredientComboBox.setItems(FXCollections.observableArrayList(controleur.getInventaire()));
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.INGREDIENT_AJOUTE, TypeOrdre.RECETTE_AJOUTEE, TypeOrdre.ERREUR_RECETTE_EXISTANTE);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch (e) {
            case INGREDIENT_AJOUTE:
                // Update ingredientComboBox when a new ingredient is added
                ingredientComboBox.setItems(FXCollections.observableArrayList(controleur.getInventaire()));
                break;
            case RECETTE_AJOUTEE:
                afficherConfirmation("Recette ajoutée", "La recette a été ajoutée avec succès.");
                nettoyerChamps();
                break;
            case ERREUR_RECETTE_EXISTANTE:
                afficherErreur("Erreur", "Une recette avec ce nom existe déjà.");
                break;
        }
    }

    public void ajouterIngredient(ActionEvent event) {
        Ingredient selectedIngredient = ingredientComboBox.getValue();
        String quantiteStr = ingredientQuantiteField.getText();

        if (selectedIngredient == null || quantiteStr.isEmpty()) {
            afficherErreur("Champs vides", "Veuillez sélectionner un ingrédient et saisir une quantité.");
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
            afficherErreur("Format incorrect", "La quantité doit être un nombre.");
        }
    }

    public void supprimerIngredient(ActionEvent event) {
        int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ingredientsRecette.remove(selectedIndex);
            ingredientsObservableList.remove(selectedIndex);
        } else {
            afficherErreur("Aucun ingrédient sélectionné", "Veuillez sélectionner un ingrédient à supprimer.");
        }
    }

    public void ajouterRecette(ActionEvent event) {
        String nomRecette = nomRecetteField.getText();
        String categorie = categorieField.getValue();
        String instructions = instructionsArea.getText();
        String tempsPreparationStr = tempsPreparationField.getText();
        String tempsCuissonStr = tempsCuissonField.getText();
        String niveauDifficulte = niveauDifficulteCombo.getValue();
        boolean isVegetarien = optionVegetarienCheck.isSelected();
        boolean isSansGluten = optionSansGlutenCheck.isSelected();
        boolean isBio = optionBioCheck.isSelected();
        boolean isPasCher = optionPasCherCheck.isSelected();

        if (nomRecette.isEmpty() || categorie == null || categorie.isEmpty() || instructions.isEmpty() ||
                tempsPreparationStr.isEmpty() || tempsCuissonStr.isEmpty() || niveauDifficulte == null ||
                ingredientsRecette.isEmpty()) {
            afficherErreur("Champs vides", "Veuillez remplir tous les champs et ajouter au moins un ingrédient.");
            return;
        }

        try {
            int tempsPreparation = Integer.parseInt(tempsPreparationStr);
            int tempsCuisson = Integer.parseInt(tempsCuissonStr);

            controleur.ajouterRecette(nomRecette, categorie, ingredientsRecette, instructions,
                    tempsPreparation, tempsCuisson, niveauDifficulte,
                    isVegetarien, isSansGluten, isBio, isPasCher);
        } catch (NumberFormatException e) {
            afficherErreur("Format incorrect", "Le temps de préparation et de cuisson doivent être des nombres entiers.");
        }
    }

    private void afficherConfirmation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(titre);
        alert.showAndWait();
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(titre);
        alert.showAndWait();
    }

    private void nettoyerChamps() {
        nomRecetteField.clear();
        categorieField.setValue(null);
        instructionsArea.clear();
        tempsPreparationField.clear();
        tempsCuissonField.clear();
        niveauDifficulteCombo.getSelectionModel().clearSelection();
        ingredientsRecette.clear();
        ingredientsObservableList.clear();
    }

    public void retourAccueil(ActionEvent event) {
        controleur.fireOrdre(TypeOrdre.SHOW_ACCUEIL);
    }
}
