package fr.insa.recettes.modele.rechercheStrategy;

import fr.insa.recettes.modele.*;
import java.util.List;
import java.util.stream.Collectors;

public interface RechercheStrategy {
    List<Recette> recherche(List<Recette> recettes, String str);
}