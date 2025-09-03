package game;
import java.util.Random;

import static java.lang.Thread.sleep;

public class PileOuFace implements Jeu {
    private String resultat; //le resultat du jeu , dans ce cas le cote duquel la piece est tombée
    private String choixJoueur; //le guess du joueur

    private boolean gagne; //sert pour l'affichage

    public PileOuFace(String choix) {
        this.choixJoueur=choix;
    }

    public boolean jouer() {
        Random rand = new Random();
        boolean bool = rand.nextBoolean();
        if (bool) { resultat="pile"; }
        else { resultat="face"; }

        return resultat.equalsIgnoreCase(choixJoueur);
    }


    public void afficherResultat() {
        System.out.println("Vous avez choisi " + choixJoueur + ".");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("La pièce est tombée sur "+resultat+".");

    }
}
