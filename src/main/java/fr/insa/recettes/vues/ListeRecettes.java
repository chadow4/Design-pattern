package fr.insa.recettes.vues;

import fr.insa.recettes.controleur.*;
import fr.insa.recettes.modele.Ingredient;
import fr.insa.recettes.modele.Recette;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ListeRecettes implements VueInteractive, EcouteurOrdre {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ListView<String> recettesListView;

    @FXML
    private TextArea detailsRecetteArea;

    @FXML
    private TextField rechercheField;

    @FXML
    private ComboBox<String> filtreCategorieCombo;

    private ObservableList<String> recettesObservableList;

    private Scene scene;
    private Controleur controleur;

    private List<Recette> recettesAffichees;

    public static ListeRecettes creer(GestionnaireVue g) throws IOException {
        FXMLLoader loader = new FXMLLoader(ListeRecettes.class.getResource("listeRecettes.fxml"));
        BorderPane root = loader.load();
        ListeRecettes vue = loader.getController();
        g.ajouterVueInteractive(vue);
        g.ajouterEcouteurOrdre(vue);
        vue.initialiserScene(root);
        return vue;
    }

    private void initialiserScene(BorderPane root) {
        this.scene = new Scene(root);
        recettesObservableList = FXCollections.observableArrayList();
        recettesListView.setItems(recettesObservableList);

        recettesListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            afficherDetailsRecette(newValue.intValue());
        });

        // Initialize category filter ComboBox
        filtreCategorieCombo.setItems(FXCollections.observableArrayList("Toutes", "Entrée", "Plat principal", "Dessert"));
        filtreCategorieCombo.setValue("Toutes");
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
        chargerListeRecettes();
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        g.abonnement(this, TypeOrdre.DATA_LOAD, TypeOrdre.RECETTE_AJOUTEE, TypeOrdre.RECETTE_MODIFIEE, TypeOrdre.RECETTE_SUPPRIMEE,
                TypeOrdre.INGREDIENT_AJOUTE, TypeOrdre.INGREDIENT_MODIFIE, TypeOrdre.INGREDIENT_SUPPRIME);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch (e) {
            case DATA_LOAD:
            case RECETTE_AJOUTEE:
            case RECETTE_MODIFIEE:
            case RECETTE_SUPPRIMEE:
            case INGREDIENT_AJOUTE:
            case INGREDIENT_MODIFIE:
            case INGREDIENT_SUPPRIME:
                chargerListeRecettes();
                break;
        }
    }

    private void chargerListeRecettes() {
        List<Recette> recettes = controleur.getRecettes();
        List<Recette> recettesRealisables = controleur.getRecettesRealisables();

        recettesAffichees = recettes;

        recettesObservableList.clear();

        for (Recette recette : recettesAffichees) {
            String statut = "";
            if (recettesRealisables.contains(recette)) {
                statut = "[Faisable] ";
            } else {
                statut = "[Incomplet] ";
            }
            recettesObservableList.add(statut + recette.getNom());
        }
    }

    private void afficherDetailsRecette(int index) {
        if (index >= 0 && index < recettesAffichees.size()) {
            Recette recette = recettesAffichees.get(index);
            StringBuilder details = new StringBuilder();
            details.append("Nom: ").append(recette.getNom()).append("\n");
            details.append("Catégorie: ").append(recette.getCategorie()).append("\n");
            if(recette.getIsVegetarien() || recette.getIsSansGluten() || recette.getIsBio() || recette.getIsPasCher()){
                details.append("Options supplémentaires :\n ");
                if (recette.getIsVegetarien()) {details.append("- Vegetarien\n");
                if (recette.getIsSansGluten()) { details.append("- Sans gluten\n");}
                if (recette.getIsBio()) {details.append("- Bio\n");}
                if (recette.getIsPasCher()) {details.append("- Pas cher\n");}
                }
            }
            details.append("Temps de préparation: ").append(recette.getTempsPreparation()).append(" min\n");
            details.append("Temps de cuisson: ").append(recette.getTempsCuisson()).append(" min\n");
            details.append("Niveau de difficulté: ").append(recette.getNiveauDifficulte()).append("\n");
            details.append("Ingrédients:\n");
            for (Ingredient ingredient : recette.getIngredients()) {
                details.append("- ").append(ingredient.getNom())
                        .append(": ").append(ingredient.getQuantite())
                        .append(" ").append(ingredient.getUnite()).append("\n");
            }
            details.append("Instructions:\n").append(recette.getInstructions());

            // Add missing ingredients if recipe is incomplete
            if (!controleur.getRecettesRealisables().contains(recette)) {
                List<Ingredient> ingredientsManquants = controleur.getIngredientsManquants(recette);
                details.append("\n\nIngrédients manquants:\n");
                for (Ingredient ingredient : ingredientsManquants) {
                    details.append("- ").append(ingredient.getNom())
                            .append(": ").append(ingredient.getQuantite())
                            .append(" ").append(ingredient.getUnite()).append("\n");
                }
            }

            detailsRecetteArea.setText(details.toString());
        } else {
            detailsRecetteArea.clear();
        }
    }

    public void rechercherRecette(ActionEvent event) {
        String recherche = rechercheField.getText().toLowerCase();
        String categorieFiltre = filtreCategorieCombo.getValue();

        List<Recette> recettesFiltrees = controleur.getRecettes();

        if (!categorieFiltre.equals("Toutes")) {
            recettesFiltrees = controleur.filtrerRecettesParCategorie(categorieFiltre);
        }

        if (!recherche.isEmpty()) {
            recettesFiltrees= controleur.rechercherRecettesParNom(recherche);

        }

        recettesAffichees = recettesFiltrees;

        recettesObservableList.clear();

        List<Recette> recettesRealisables = controleur.getRecettesRealisables();

        for (Recette recette : recettesAffichees) {
            String statut = "";
            if (recettesRealisables.contains(recette)) {
                statut = "[Faisable] ";
            } else {
                statut = "[Incomplet] ";
            }
            recettesObservableList.add(statut + recette.getNom());
        }
    }

    public void retourAccueil(ActionEvent event) {
        controleur.fireOrdre(TypeOrdre.SHOW_ACCUEIL);
    }

    public void ajouterRecette(ActionEvent event) {
        RecetteDialog dialog = new RecetteDialog(controleur.getInventaire());
        dialog.setTitle("Ajouter une recette");
        dialog.showAndWait().ifPresent(recette -> {
            controleur.ajouterRecette(recette.getNom(), recette.getCategorie(), recette.getIngredients(), recette.getInstructions(),
                    recette.getTempsPreparation(), recette.getTempsCuisson(), recette.getNiveauDifficulte(),
                    recette.getIsVegetarien(), recette.getIsSansGluten(), recette.getIsBio(), recette.getIsPasCher());
        });
    }

    public void modifierRecette(ActionEvent event) {
        int selectedIndex = recettesListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < recettesAffichees.size()) {
            Recette recette = recettesAffichees.get(selectedIndex);
            RecetteDialog dialog = new RecetteDialog(recette, controleur.getInventaire());
            dialog.setTitle("Modifier la recette");
            dialog.showAndWait().ifPresent(recetteModifiee -> {
                System.out.println("notre recette modified " + recetteModifiee + " " + recetteModifiee.getId());
                controleur.modifierRecette(recetteModifiee);
            });
        } else {
            afficherErreur("Aucune recette sélectionnée", "Veuillez sélectionner une recette à modifier.");
        }
    }

    public void supprimerRecette(ActionEvent event) {
        int selectedIndex = recettesListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < recettesAffichees.size()) {
            Recette recette = recettesAffichees.get(selectedIndex);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer cette recette ?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation de suppression");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    controleur.supprimerRecette(recette.getId());
                }
            });
        } else {
            afficherErreur("Aucune recette sélectionnée", "Veuillez sélectionner une recette à supprimer.");
        }
    }

    private void afficherErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(titre);
        alert.showAndWait();
    }
}
