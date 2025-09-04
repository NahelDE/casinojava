package modele.carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GestionCarte {
    private String valeur [] = {"a","r","d","v","10","9","8","7","6","5","4","3","2"};
    private String couleur [] = {"C","K","P","T"};

    private List<Carte> paquet;

    public GestionCarte() {
        paquet = new ArrayList<Carte>();

        for (int i=0;i< couleur.length;i++) {
            for (int j=0;j<valeur.length;j++) {
                paquet.add(new Carte(valeur[j],couleur[i]));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(paquet);
    }

    public Carte piocher(){
        return paquet.remove(0);
    }

    @Override
    public String toString() {
        String res = "";
        for (Carte carte : paquet) {
            res = res + carte.toString() + " ";
        }
        return res;
    }

    public static int calculeMain(List<Carte> main) {
        int res = 0;
        for (Carte c : main) res += c.getIntValeur();

        if (res > 21) {
            for (Carte c : main) {
                if (c.getValeur().equalsIgnoreCase("a") && c.getIntValeur() == 11) {
                    c.setIntValeur(1);
                    res = 0;
                    for (Carte cc : main) res += cc.getIntValeur();
                    if (res <= 21) break; // stop si on repasse <=21
                }
            }
        }
        return res;
    }
}
