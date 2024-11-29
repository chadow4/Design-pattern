package fr.insa.recettes.controleur;

import javafx.scene.layout.BorderPane;

public interface VueInteractive extends EcouteurOrdre {
    Controleur getControleur();
    void setControleur(Controleur controleur);
    void initialiserScene(BorderPane root);
}
