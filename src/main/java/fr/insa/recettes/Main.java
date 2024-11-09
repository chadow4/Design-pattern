package fr.insa.recettes;

import fr.insa.recettes.controleur.Controleur;
import fr.insa.recettes.modele.FacadeGestionRecettes;
import fr.insa.recettes.modele.FacadeGestionRecettesImpl;
import fr.insa.recettes.vues.GestionnaireVue;
import fr.insa.recettes.vues.GestionnaireVueImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        GestionnaireVue gestionnaireVue = new GestionnaireVueImpl(stage);
        FacadeGestionRecettes facadeGestionRecettes = FacadeGestionRecettesImpl.getInstance();
        Controleur controleur = new Controleur(gestionnaireVue, facadeGestionRecettes);
        controleur.run();
    }

    public static void main(String[] args) {
        // Lance l'application JavaFX
        launch(args);
    }
}
