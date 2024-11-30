package fr.insa.recettes.modele.rechercheStrategy;

import fr.insa.recettes.modele.Recette;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RechercheParFavoriStrategy implements RechercheStrategy{
    public List<Recette> recherche(List<Recette> recettes, String str) {
        if( recettes != null){

            return recettes.stream()
                    .filter(r-> r.getIsFavori())
                    .collect(Collectors.toList());
        }else{
            return null ;
        }
    }
}
