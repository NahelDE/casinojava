package game;

import java.util.Scanner;

public class CasinoConnection {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        GestionJoueur gj = new GestionJoueur();
        Joueur joueurConnecte = null;
        int choix = 0;

        while (joueurConnecte == null) {
            System.out.println("Bienvenue sur le Casino Java.");

            do {
                System.out.println("1. S'inscrire");
                System.out.println("2. Se connecter");

                System.out.println("Choix : ");
                choix = sc.nextInt();
                sc.nextLine();
            } while (choix < 0 || choix > 2);


            System.out.println("Nom : ");
            String nom = sc.nextLine();

            System.out.println("Mdp : ");
            String mdp = sc.nextLine();

            if (choix == 1) {
                if (gj.inscription(nom,mdp)){
                   System.out.println("Inscription effectuée, vous obtenez 500 crédits en cadeau, connectez-vous.");
                }
                else {
                    System.out.println("Nom déjà utilisé.");
                }
            }
            else if (choix == 2) {
                joueurConnecte = gj.connection(nom, mdp);
                if (joueurConnecte != null){
                    System.out.println("Bienvenue sur le Casino Java " + joueurConnecte.getNom() + ", vous avez actuellement " + joueurConnecte.getSolde() + " sur votre compte, transfert dans le casino...");
                }
                else{
                    System.out.println("Erreur d'identifiants.");
                }
            }

        }
        Casino casino = new Casino(joueurConnecte,gj);
        casino.casino();
        gj.sauvegarderJoueur();
    }
}
