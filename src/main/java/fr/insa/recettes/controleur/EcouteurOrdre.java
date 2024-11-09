package fr.insa.recettes.controleur;

public interface EcouteurOrdre {
    void setAbonnement(LanceurOrdre g);
    void traiter(TypeOrdre e);
}