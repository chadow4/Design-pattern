package fr.insa.recettes.modele.rechercheStrategy;

import fr.insa.recettes.modele.*;
import java.util.List;
import java.util.stream.Collectors;

public class RechercheParNomStrategy implements RechercheStrategy {

    public List<Recette> recherche(List<Recette> recettes, String str) {
        return recettes.stream()
                .filter(r -> r.getNom().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toList());
    }
}