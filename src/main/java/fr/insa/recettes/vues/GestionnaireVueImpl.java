package fr.insa.recettes.vues;

import fr.insa.recettes.controleur.*;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionnaireVueImpl extends GestionnaireVue {

    private Accueil accueil;
    private Inventaire inventaire;
    private CreationRecette creationRecette;
    private ListeRecettes listeRecettes;

    public GestionnaireVueImpl(Stage stage) throws IOException {
        super(stage);
        accueil = Accueil.creer(this);
        inventaire = Inventaire.creer(this);
        creationRecette = CreationRecette.creer(this);
        listeRecettes = ListeRecettes.creer(this);
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        super.setAbonnement(g);
        g.abonnement(this, TypeOrdre.SHOW_ACCUEIL, TypeOrdre.SHOW_INVENTAIRE,
                TypeOrdre.SHOW_CREATION_RECETTE, TypeOrdre.SHOW_LISTE_RECETTES);
    }

    @Override
    public void traiter(TypeOrdre e) {
        switch (e) {
            case SHOW_ACCUEIL:
                getStage().setScene(accueil.getScene());
                getStage().show();
                break;
            case SHOW_INVENTAIRE:
                getStage().setScene(inventaire.getScene());
                getStage().show();
                break;
            case SHOW_CREATION_RECETTE:
                getStage().setScene(creationRecette.getScene());
                getStage().show();
                break;
            case SHOW_LISTE_RECETTES:
                getStage().setScene(listeRecettes.getScene());
                getStage().show();
                break;
            // Other cases...
        }
    }
}
