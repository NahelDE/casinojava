package game;

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

            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    lancerPileOuFace();
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
}
