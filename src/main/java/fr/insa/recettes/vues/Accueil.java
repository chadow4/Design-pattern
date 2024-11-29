package fr.insa.recettes.vues;

import fr.insa.recettes.controleur.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Accueil implements VueInteractive {

    @FXML
    private BorderPane borderPane;

    private Scene scene;
    private Controleur controleur;

    public static Accueil creer(GestionnaireVue g) throws IOException {
        FXMLLoader loader = new FXMLLoader(Accueil.class.getResource("accueil.fxml"));
        BorderPane root = loader.load();
        Accueil vue = loader.getController();
        g.ajouterVueInteractive(vue);
        vue.initialiserScene(root);
        return vue;
    }

    private void initialiserScene(BorderPane root) {
        this.scene = new Scene(root);
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
    }

    // Event handlers
    public void accederInventaire(ActionEvent event) {
        controleur.fireOrdre(TypeOrdre.SHOW_INVENTAIRE);
    }

    public void accederRecettes(ActionEvent event) {
        controleur.fireOrdre(TypeOrdre.SHOW_LISTE_RECETTES);
    }
    
}
