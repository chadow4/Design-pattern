package fr.insa.recettes.controleur;

public interface LanceurOrdre {
    void abonnement(EcouteurOrdre ecouteurOrdre, TypeOrdre... types);
    void fireOrdre(TypeOrdre e);
}