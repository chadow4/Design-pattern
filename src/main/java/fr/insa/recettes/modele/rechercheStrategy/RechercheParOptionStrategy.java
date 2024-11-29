package fr.insa.recettes.modele.rechercheStrategy;

import fr.insa.recettes.modele.*;
import java.util.List;
import java.util.stream.Collectors;

public class RechercheParOptionStrategy implements RechercheStrategy{

    public List<Recette> recherche(List<Recette> recettes, String str) {
        switch (str) {
            case "Vegetarien":
                return recettes.stream().filter(r-> r.getIsVegetarien()).collect(Collectors.toList());
            case "SansGluten" :
                return recettes.stream().filter(r-> r.getIsSansGluten()).collect(Collectors.toList());
            case "Bio":
                return recettes.stream().filter(r-> r.getIsBio()).collect(Collectors.toList());
            case "PasCher":
                return recettes.stream().filter(r-> r.getIsPasCher()).collect(Collectors.toList());
            default:
                return recettes;
        }
    }
}