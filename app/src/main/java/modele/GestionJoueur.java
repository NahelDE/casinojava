package modele;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GestionJoueur {
    private Map<String , Joueur> joueurs = new HashMap<String , Joueur>(); // dictionnaire en python avec une clé string : le nom et une valeur Joueur qui contient nom mdp et solde
    private String fic = "joueurs.txt";

    public GestionJoueur() {
        chargerJoueur(); //a la creation d'une gestion joueur on load les joueurs dans le fic
    }

    private void chargerJoueur() {
        try (BufferedReader br = new BufferedReader(new FileReader(fic))) {
            String ligne;
            while ((ligne = br.readLine()) != null) { //tant quil reste des ligne
                String [] elements = ligne.split(";");
                if (elements.length == 3) { //si la ligne est conforme
                    String nom = elements[0];
                    String mdp = elements[1];
                    double solde = Double.parseDouble(elements[2]); // on change le string en double
                    joueurs.put(nom,new Joueur(nom,mdp,solde));
                }
                else {
                    System.out.println("Fichier utilisateur corrompu.");
                }
            }
        } catch (IOException e) {
            System.out.println("Aucun joueurs n'est inscrit.");
        }
    }

    public void sauvegarderJoueur() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fic))) {
            for (Joueur j : joueurs.values()) {
                bw.write(j.toString()); // on ecrit la ligne avec la methode qui met tout les attribut separee par des ;
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Sauvegarde échouée.");
        }
    }

    public boolean inscription(String nom, String mdp){
        if (joueurs.containsKey(nom)){ //si un joueur a deja le nom , on refuse l'inscri
            return false;
        }
        else {
            Joueur j = new Joueur(nom, mdp, 500);
            joueurs.put(nom, j); //integration dans la hashmap
            sauvegarderJoueur(); //on le met dans le fic
            return true;
        }
    }

    public Joueur connection(String nom, String mdp) {
        Joueur j = joueurs.get(nom); //on prend les info du joueur qui possede ce nom
        if (j != null && mdp.equals(j.getMdp())){ //verif md
            return j;
        }
        else {
            return null;
        }
    }
}
