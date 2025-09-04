package modele.carte;

import modele.Jeu;

import java.util.ArrayList;
import java.util.List;

import static modele.carte.GestionCarte.calculeMain;

public class Blackjack implements Jeu {

    private GestionCarte paquet;
    private boolean partieFinie = false;
    private double gain; // gain : 1.5 si blackjack initial, 2.0 si victoire normale

    private List<Carte> mainJoueur;
    private List<Carte> mainCroupier;

    public Blackjack() {
        paquet = new GestionCarte();
        paquet.shuffle();

        mainJoueur = new ArrayList<>();
        mainCroupier = new ArrayList<>();

        // distribution initiale
        mainJoueur.add(paquet.piocher());
        mainJoueur.add(paquet.piocher());
        mainCroupier.add(paquet.piocher());
    }


    public boolean jouer() {
        int scoreJoueur = calculeMain(mainJoueur);

        if (scoreJoueur == 21) {
            mainCroupier.add(paquet.piocher());
            int scoreCroupier = calculeMain(mainCroupier);
            if (scoreCroupier == 21) gain = 1.0; // égalité
            else gain = 1.5; // blackjack
            partieFinie = true;
        }

        return partieFinie;
    }

    /** Le joueur tire une carte */
    public void joueurTirer() {
        if (partieFinie) return;
        mainJoueur.add(paquet.piocher());
        if (calculeMain(mainJoueur) > 21) {
            partieFinie = true; // le joueur a dépassé 21
            gain = 0;
        }
    }

    public void joueurRester() {
        if (partieFinie) return;

        while (calculeMain(mainCroupier) < 17) {
            mainCroupier.add(paquet.piocher());
        }

        int scoreJ = calculeMain(mainJoueur);
        int scoreC = calculeMain(mainCroupier);

        if (scoreC > 21 || scoreJ > scoreC) gain = 2.0; // joueur gagne
        else if (scoreJ == scoreC) gain = 1.0; // égalité
        else gain = 0; // joueur perd

        partieFinie = true;
    }

    public void afficherResultat() {
        System.out.println("Main joueur : " + mainJoueur + " = " + calculeMain(mainJoueur));
        System.out.println("Main croupier : " + mainCroupier + " = " + calculeMain(mainCroupier));
        if (gain == 2.0) System.out.println("Vous avez gagné !");
        else if (gain == 1.5) System.out.println("Blackjack !");
        else if (gain == 1.0) System.out.println("Égalité.");
        else System.out.println("Vous avez perdu.");
    }

    public List<Carte> getMainJoueur() {
        return mainJoueur;
    }

    public List<Carte> getMainCroupier() {
        return mainCroupier;
    }

    public boolean isPartieFinie() {
        return partieFinie;
    }

    public double getGain() {
        return gain;
    }
}
