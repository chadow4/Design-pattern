package fr.insa.recettes.vues;

import fr.insa.recettes.controleur.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public abstract class GestionnaireVue implements EcouteurOrdre {
    private Stage stage;
    private Collection<EcouteurOrdre> ecouteursOrdres;
    private Collection<VueInteractive> vuesInteractives;

    public GestionnaireVue(Stage stage) {
        this.stage = stage;
        this.ecouteursOrdres = new ArrayList<>();
        this.vuesInteractives = new ArrayList<>();
    }

    public Stage getStage() {
        return stage;
    }

    public void ajouterVueInteractive(VueInteractive vueInteractive) {
        this.vuesInteractives.add(vueInteractive);
    }

    public void ajouterEcouteurOrdre(EcouteurOrdre ecouteurOrdre) {
        this.ecouteursOrdres.add(ecouteurOrdre);
    }

    public Collection<VueInteractive> getVuesInteractives() {
        return vuesInteractives;
    }

    public void setControleur(Controleur c) {
        for (VueInteractive v : vuesInteractives) {
            v.setControleur(c);
        }
    }

    @Override
    public void setAbonnement(LanceurOrdre g) {
        for (EcouteurOrdre e : ecouteursOrdres) {
            e.setAbonnement(g);
        }
    }
}
