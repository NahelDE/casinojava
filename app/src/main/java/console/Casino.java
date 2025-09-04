package console;

import modele.GestionJoueur;
import modele.Joueur;
import modele.PileOuFace;
import modele.carte.GestionCarte;
import modele.carte.Carte;
import modele.carte.Blackjack;

import java.util.Scanner;

public class Casino {
    Joueur joueurCourant;
    GestionJoueur gj;

    double mise;

    public Casino(Joueur joueurCourant ,GestionJoueur gj) {
        this.joueurCourant = joueurCourant;
        this.gj = gj;
    }

    public void casino() {
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        do {
            System.out.println(joueurCourant.getNom() + ", " +joueurCourant.getSolde() +" crédits");
            System.out.println("Choisissez votre jeu!");
            System.out.println("0. Quitter");
            System.out.println("1. Pile ou face");
            System.out.println("2. BlackJack");

            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    lancerPileOuFace();
                case 2:
                    lancerBlackjack();
                case 3:
                    //lancerBlackJack;
            }
        } while (choix != 0);

        System.out.println("Merci de votre visite ,sauvegarde en cours...");
    }

    private void lancerPileOuFace(){
        Scanner sc = new Scanner(System.in);
        String choix;

        System.out.println("Bienvenue dans le pile ou face ! Vous devez choisir soit Pile soit Face, si vous gagnez , vous obtenez 2 fois votre mise.");


        do {
            System.out.println("Vous avez "+joueurCourant.getSolde() + " crédits. Entrez votre mise : ");
            mise = sc.nextDouble();
            sc.nextLine();
        } while (mise > joueurCourant.getSolde());
        System.out.println("Vous misez "+mise+" crédits.");

        joueurCourant.setSolde(joueurCourant.getSolde()-mise); //on enleve la mise du compte

        do {
            System.out.println("Entrez pile ou face : ");
            choix = sc.nextLine();
        } while (!(choix.equalsIgnoreCase("Pile") || choix.equalsIgnoreCase("Face")));

        PileOuFace p = new PileOuFace(choix);
        if (p.jouer()){
            joueurCourant.setSolde(joueurCourant.getSolde()+mise*2); //on ajoute le double de la mise

            p.afficherResultat();
            System.out.println("Bravo, vous avez gagné " +(mise*2) + "crédits!");
        }
        else {
            p.afficherResultat();
            System.out.println("Dommage, vous avez perdu " +(mise) + "crédits...");
        }
        gj.sauvegarderJoueur();
    }

    private void lancerBlackjack() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bienvenue dans le Blackjack ! Vous devez essayer de battre le croupier sans dépasser 21.");

        double mise;
        do {
            System.out.println("Vous avez " + joueurCourant.getSolde() + " crédits. Entrez votre mise : ");
            mise = sc.nextDouble();
            sc.nextLine(); // pour consommer la ligne
        } while (mise <= 0 || mise > joueurCourant.getSolde());

        // On retire la mise immédiatement
        joueurCourant.setSolde(joueurCourant.getSolde() - mise);

        Blackjack bj = new Blackjack();
        boolean finTour = false;

        while (!finTour) {
            System.out.println("Main joueur : " + bj.getMainJoueur() + " = " + GestionCarte.calculeMain(bj.getMainJoueur()));
            System.out.println("Main croupier : [" + bj.getMainCroupier().get(0) + ", ?]"); // on montre juste la 1ère carte du croupier

            System.out.println("Voulez-vous tirer (T) ou rester (R) ?");
            String action = sc.nextLine();

            if (action.equalsIgnoreCase("T")) {
                bj.joueurTirer();
                if (GestionCarte.calculeMain(bj.getMainJoueur()) > 21) {
                    finTour = true;
                }
            } else if (action.equalsIgnoreCase("R")) {
                bj.joueurRester();
                finTour = true;
            } else {
                System.out.println("Commande invalide !");
            }
        }

        bj.afficherResultat();

        // Calcul du gain
        double gain = bj.getGain();
        if (gain == 2.0) { // victoire normale
            joueurCourant.setSolde(joueurCourant.getSolde() + mise * 2);
        } else if (gain == 1.5) { // blackjack initial
            joueurCourant.setSolde(joueurCourant.getSolde() + mise * 2.5);
        } else if (gain == 1.0) { // égalité
            joueurCourant.setSolde(joueurCourant.getSolde() + mise); // on rend la mise
        } // sinon le joueur perd, rien à ajouter

        System.out.println("Solde actuel : " + joueurCourant.getSolde() + " crédits.");
        gj.sauvegarderJoueur();
    }



}
