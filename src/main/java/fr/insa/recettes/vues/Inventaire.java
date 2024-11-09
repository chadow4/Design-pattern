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

public class Inventaire implements VueInteractive, EcouteurOrdre {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableView<Ingredient> ingredientsTableView;

    @FXML
    private TableColumn<Ingredient, String> nomColumn;

    @FXML
    private TableColumn<Ingredient, Double> quantiteColumn;

    @FXML
    private TableColumn<Ingredient, String> uniteColumn;

    private ObservableList<Ingredient> ingredientsObservableList;

    private Scene scene;
    private Controleur controleur;

    public static Inventaire creer(GestionnaireVue g) throws IOException {
        FXMLLoader loader = new FXMLLoader(Inventaire.class.getResource("inventaire.fxml"));
        BorderPane root = loader.load();
        Inventaire vue = loader.getController();
        g.ajouterVueInteractive(vue);
        g.ajouterEcouteurOrdre(vue);
        vue.initialiserScene(root);
        return vue;
    }

    private void initialiserScene(BorderPane root) {
        this.scene = new Scene(root);
        ingredientsObservableList = FXCollections.observableArrayList();
        ingredientsTableView.setItems(ingredientsObservableList);

        // Configure columns
        nomColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        quantiteColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getQuantite()).asObject());
        uniteColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUnite()));

    }

    private void chargerInventaire() {
        ingredientsObservableList.setAll(controleur.getInventaire());
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
        chargerInventaire();
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.INGREDIENT_AJOUTE, TypeOrdre.INGREDIENT_MODIFIE, TypeOrdre.INGREDIENT_SUPPRIME);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch (e) {
            case INGREDIENT_AJOUTE:
            case INGREDIENT_MODIFIE:
            case INGREDIENT_SUPPRIME:
                chargerInventaire();
                break;
        }
    }

    public void ajouterIngredient(ActionEvent event) throws IOException {
        IngredientDialog dialog = new IngredientDialog();
        dialog.setTitle("Ajouter un ingrédient");
        dialog.showAndWait().ifPresent(ingredient -> {
            controleur.ajouterIngredient(ingredient.getNom(), ingredient.getQuantite(), ingredient.getUnite());
        });
    }

    public void modifierIngredient(ActionEvent event) throws IOException {
        Ingredient selectedIngredient = ingredientsTableView.getSelectionModel().getSelectedItem();
        if (selectedIngredient != null) {
            IngredientDialog dialog = new IngredientDialog(selectedIngredient);
            dialog.setTitle("Modifier l'ingrédient");
            dialog.showAndWait().ifPresent(ingredient -> {
                controleur.modifierIngredient(ingredient);
            });
        } else {
            afficherErreur("Aucun ingrédient sélectionné", "Veuillez sélectionner un ingrédient à modifier.");
        }
    }

    public void supprimerIngredient(ActionEvent event) {
        Ingredient selectedIngredient = ingredientsTableView.getSelectionModel().getSelectedItem();
        if (selectedIngredient != null) {
            controleur.supprimerIngredient(selectedIngredient.getNom());
        } else {
            afficherErreur("Aucun ingrédient sélectionné", "Veuillez sélectionner un ingrédient à supprimer.");
        }
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(titre);
        alert.showAndWait();
    }

    public void retourAccueil(ActionEvent event) {
        controleur.fireOrdre(TypeOrdre.SHOW_ACCUEIL);
    }
}
