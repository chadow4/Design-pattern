package fr.insa.recettes.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.insa.recettes.modele.FacadeGestionRecettesImpl;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {

    private static final String DATA_FILE = "data.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void sauvegarderDonnees(FacadeGestionRecettesImpl facade) throws IOException {
        try (FileWriter writer = new FileWriter(DATA_FILE)) {
            gson.toJson(facade, writer);
        }
    }

    public static FacadeGestionRecettesImpl chargerDonnees() throws IOException {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            System.out.println("Le fichier de données n'existe pas. Initialisation d'un nouvel inventaire et de recettes.");
            return new FacadeGestionRecettesImpl(); // Renvoie une instance vide si le fichier est absent
        }
        try (FileReader reader = new FileReader(dataFile)) {
            return gson.fromJson(reader, FacadeGestionRecettesImpl.class);
        }
    }
}
