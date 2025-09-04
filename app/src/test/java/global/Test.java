package global;

import modele.carte.Blackjack;
import modele.carte.Carte;
import modele.carte.GestionCarte;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println("==== Test GestionCarte ====");
        GestionCarte paquet = new GestionCarte();
        System.out.println("Paquet initial : " + paquet);
        Carte c1 = paquet.piocher();
        System.out.println("Carte piochée : " + c1 + " (" + c1.getIntValeur() + ")");
        System.out.println("Paquet après pioche : " + paquet);

        System.out.println("\n==== Test Blackjack ====");
        Blackjack bj = new Blackjack();

        // Affiche mains initiales
        System.out.println("Main joueur initiale : " + bj.getMainJoueur());
        System.out.println("Main croupier initiale : " + bj.getMainCroupier());

        // Vérifie le blackjack initial
        if (bj.jouer()) {
            System.out.println("Blackjack initial détecté !");
        } else {
            System.out.println("Pas de blackjack initial.");
        }

        // Test Tirer et Rester
        bj.joueurTirer();
        System.out.println("Main joueur après tirage : " + bj.getMainJoueur());

        bj.joueurRester();
        System.out.println("Main croupier après tirage automatique : " + bj.getMainCroupier());

        // Affiche résultat final
        bj.afficherResultat();

        // Vérifie que la partie est finie
        System.out.println("Partie finie ? " + bj.isPartieFinie());
        System.out.println("Gain : " + bj.getGain());
    }
}
