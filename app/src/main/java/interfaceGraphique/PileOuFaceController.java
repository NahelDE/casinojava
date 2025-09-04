package interfaceGraphique;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modele.Joueur;
import modele.PileOuFace;
import modele.GestionJoueur;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

public class PileOuFaceController {

    private Joueur joueur;
    private GestionJoueur gestionJoueur;
    private CasinoController casinoController;

    private PileOuFace pileOuFace;

    @FXML
    private ImageView pieceImage;

    private Image pileImg = new Image(getClass().getResourceAsStream("/assets/pile.png"));
    private Image faceImg = new Image(getClass().getResourceAsStream("/assets/face.png"));

    @FXML
    private Label soldeLabel;

    @FXML
    private TextField miseField;

    @FXML
    private Button pileButton;

    @FXML
    private Button faceButton;

    @FXML
    private Label resultatLabel;

    // Initialiser le controller avec le joueur et la gestion
    public void setJoueurEtGestion(Joueur joueur, GestionJoueur gestion) {
        this.joueur = joueur;
        this.gestionJoueur = gestion;
        soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
    }

    @FXML
    private void choisirPile() {
        lancerJeu("Pile");
    }

    @FXML
    private void choisirFace() {
        lancerJeu("Face");
    }

    private void lancerJeu(String choixJoueur) {
        if (miseField.getText().isEmpty()) {
            resultatLabel.setText("Veuillez entrer une mise !");
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

        pileOuFace = new PileOuFace(choixJoueur);
        boolean gagne = pileOuFace.jouer();

        Image resultatImg = gagne ? (choixJoueur.equals("Pile") ? pileImg : faceImg)
                : (choixJoueur.equals("Pile") ? faceImg : pileImg);


        RotateTransition rotation = new RotateTransition(Duration.millis(1000), pieceImage);
        rotation.setByAngle(720); // deux tours
        rotation.setOnFinished(event -> {
            pieceImage.setImage(resultatImg); // afficher le résultat final
        });

        ScaleTransition flip = new ScaleTransition(Duration.millis(500), pieceImage);
        flip.setFromX(1);
        flip.setToX(0); // disparaît horizontalement
        flip.setOnFinished(e -> {
            pieceImage.setImage(resultatImg);
            ScaleTransition flipBack = new ScaleTransition(Duration.millis(500), pieceImage);
            flipBack.setFromX(0);
            flipBack.setToX(1); // réapparition
            flipBack.play();
        });
        flip.play();

        rotation.play();

        if (gagne) {
            joueur.setSolde(joueur.getSolde() + mise);
        } else {
            joueur.setSolde(joueur.getSolde() - mise);
        }

        soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
        resultatLabel.setText("La pièce est tombée sur " +
                (gagne ? choixJoueur : (choixJoueur.equals("Pile") ? "Face" : "Pile")) +
                ". " + (gagne ? "Vous avez gagné !" : "Vous avez perdu."));

        // Mettre à jour la HashMap dans GestionJoueur et sauvegarder
        gestionJoueur.inscription(joueur.getNom(), joueur.getMdp()); // assure que la clé existe
        gestionJoueur.sauvegarderJoueur();
    }

    @FXML
    private void quitterJeu() {
        casinoController.actualiserSolde();
        Stage stage = (Stage) soldeLabel.getScene().getWindow();
        stage.close();
    }

    public void setCasinoController(CasinoController casinoController) {
        this.casinoController = casinoController;
    }
}
