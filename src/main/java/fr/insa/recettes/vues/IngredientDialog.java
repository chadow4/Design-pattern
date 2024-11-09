package fr.insa.recettes.vues;

import fr.insa.recettes.modele.Ingredient;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class IngredientDialog extends Dialog<Ingredient> {

    private TextField nomField;
    private TextField quantiteField;
    private TextField uniteField;

    public IngredientDialog() {
        this(null);
    }

    public IngredientDialog(Ingredient ingredient) {
        setTitle("Ingrédient");
        setHeaderText(null);

        ButtonType validerButtonType = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(validerButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nomField = new TextField();
        nomField.setPromptText("Nom");
        quantiteField = new TextField();
        quantiteField.setPromptText("Quantité");
        uniteField = new TextField();
        uniteField.setPromptText("Unité");

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Quantité:"), 0, 1);
        grid.add(quantiteField, 1, 1);
        grid.add(new Label("Unité:"), 0, 2);
        grid.add(uniteField, 1, 2);

        if (ingredient != null) {
            nomField.setText(ingredient.getNom());
            quantiteField.setText(String.valueOf(ingredient.getQuantite()));
            uniteField.setText(ingredient.getUnite());
        }

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == validerButtonType) {
                try {
                    String nom = nomField.getText();
                    double quantite = Double.parseDouble(quantiteField.getText());
                    String unite = uniteField.getText();
                    return new Ingredient(nom, quantite, unite);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La quantité doit être un nombre.", ButtonType.OK);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });
    }
}
