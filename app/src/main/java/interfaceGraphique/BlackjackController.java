package interfaceGraphique;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Joueur;
import modele.GestionJoueur;
import modele.carte.Blackjack;
import modele.carte.Carte;

public class BlackjackController {

    private CasinoController casinoController;
    private Joueur joueur;
    private GestionJoueur gestionJoueur;
    private Blackjack blackjack;

    @FXML
    private Label soldeLabel;

    @FXML
    private TextField miseField;

    @FXML
    private Button tirerButton;

    @FXML
    private Button resterButton;

    @FXML
    private Label mainJoueurLabel;

    @FXML
    private Label mainCroupierLabel;

    @FXML
    private Label resultatLabel;

    public void setJoueurEtGestion(Joueur joueur, GestionJoueur gestion) {
        this.joueur = joueur;
        this.gestionJoueur = gestion;
        soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
        blackjack = new Blackjack();
        updateLabels();
    }

    @FXML
    private void tirer() {
        if (miseField.getText().isEmpty()) {
            resultatLabel.setText("Entrez votre mise d'abord !");
            return;
        }

        double mise;
        try {
            mise = Double.parseDouble(miseField.getText());
        } catch (NumberFormatException e) {
            resultatLabel.setText("Mise invalide !");
            return;
        }

        if (mise <= 0 || mise > joueur.getSolde()) {
            resultatLabel.setText("Mise non autorisée !");
            return;
        }

        // Débiter la mise uniquement si premier tirage
        if (!tirerButton.isDisabled()) {
            joueur.setSolde(joueur.getSolde() - mise);
        }

        blackjack.joueurTirer();
        updateLabels();

        if (blackjack.isPartieFinie()) {
            blackjack.joueurRester();
            resultatLabel.setText(getMessageResultat(mise));
            soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
            tirerButton.setDisable(true);
            resterButton.setDisable(true);

            // Sauvegarde
            gestionJoueur.sauvegarderJoueur();
        }
    }

    @FXML
    private void rester() {
        double mise;
        try {
            mise = Double.parseDouble(miseField.getText());
        } catch (NumberFormatException e) {
            resultatLabel.setText("Mise invalide !");
            return;
        }

        blackjack.joueurRester();
        updateLabels();

        resultatLabel.setText(getMessageResultat(mise));
        soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
        tirerButton.setDisable(true);
        resterButton.setDisable(true);

        // Sauvegarde
        gestionJoueur.sauvegarderJoueur();
    }

    @FXML
    private void quitter() {
        casinoController.actualiserSolde();
        Stage stage = (Stage) soldeLabel.getScene().getWindow();
        stage.close();
    }

    private void updateLabels() {
        mainJoueurLabel.setText("Main Joueur : " + blackjack.getMainJoueur());
        mainCroupierLabel.setText("Main Croupier : " + blackjack.getMainCroupier());
    }

    private String getMessageResultat(double mise) {
        double gain = blackjack.getGain();
        if (gain == 2.0) {
            joueur.setSolde(joueur.getSolde() + mise * 2);
            return "Vous avez gagné ! Gain : " + (mise * 2);
        } else if (gain == 1.5) {
            joueur.setSolde(joueur.getSolde() + mise * 1.5);
            return "Blackjack ! Gain : " + (mise * 1.5);
        } else if (gain == 1.0) {
            joueur.setSolde(joueur.getSolde() + mise); // égalité : restitution de la mise
            return "Égalité.";
        } else {
            return "Vous avez perdu.";
        }
    }
    public void setCasinoController(CasinoController casinoController) {
        this.casinoController = casinoController;
    }
}
