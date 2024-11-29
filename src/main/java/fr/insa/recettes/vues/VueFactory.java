package fr.insa.recettes.vues;


import fr.insa.recettes.controleur.VueInteractive;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class VueFactory {
    public static <T extends VueInteractive> T creerVue(String cheminFXML, GestionnaireVue gestionnaire) throws IOException {
        FXMLLoader loader = new FXMLLoader(VueFactory.class.getResource(cheminFXML));
        BorderPane root = loader.load();
        T controller = loader.getController();
        gestionnaire.ajouterVueInteractive(controller);
        gestionnaire.ajouterEcouteurOrdre(controller);
        controller.initialiserScene(root);
        return controller;
    }
}