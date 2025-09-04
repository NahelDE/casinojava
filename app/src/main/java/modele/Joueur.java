package modele;

public class Joueur {
    private String nom;
    private String mdp;
    private double solde;

    public Joueur(String nom, String mdp, double solde) {
        this.nom = nom;
        this.mdp = mdp;
        this.solde = solde;
    }

    public double getSolde() {
        return solde;
    }

   public void setSolde(double solde) {
        this.solde = solde;
   }

   public String getNom() {
        return nom;
   }

   public String getMdp() {
        return mdp;
   }

    @Override
    public String toString() {
        return nom + ";" + mdp + ";" + solde;
    }
}



