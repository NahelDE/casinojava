package modele.carte;

public class Carte {
    private String stringValeur;
    private int intValeur;
    private String couleur;

    public Carte(String stringValeur, String couleur) {
        this.stringValeur = stringValeur;
        this.couleur = couleur;

        switch (stringValeur) {
            case "a":
                intValeur = 11;
                break;
            case "r":
                intValeur = 10;
                break;
            case "d":
                intValeur = 10;
                break;
            case "v":
                intValeur = 10;
                break;
            default:
                intValeur = Integer.parseInt(stringValeur);
                break;
        }
    }

    public String getValeur() {
        return stringValeur;
    }

    public void setIntValeur(int intValeur) {
        this.intValeur = intValeur;
    }

    public int getIntValeur() {
        return intValeur;
    }

    public String getCouleur() {
        return couleur;
    }

    @Override
    public String toString() {
       return stringValeur+""+couleur;
    }
}
