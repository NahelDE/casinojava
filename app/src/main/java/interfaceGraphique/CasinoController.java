package interfaceGraphique;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import modele.GestionJoueur;
import modele.Joueur;

import java.io.IOException;

public class CasinoController {

    private Joueur joueur;
    private GestionJoueur gestionJoueur;

    @FXML
    private Label soldeLabel;

    @FXML
    private Button btnPileOuFace;

    @FXML
    private Button btnQuitter;

    // Méthode pour initialiser le controller avec le joueur connecté
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
        soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
    }

    public void setGestionJoueur(GestionJoueur gestionJoueur) {
        this.gestionJoueur = gestionJoueur;
    }

    @FXML
    public void ouvrirJeuPileOuFace() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PileOuFaceView.fxml"));
            Parent root = loader.load();

            PileOuFaceController controller = loader.getController();
            controller.setJoueurEtGestion(this.joueur,this.gestionJoueur);
            controller.setCasinoController(this);

            Stage stage = new Stage();
            stage.setTitle("Jeu Pile ou Face");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ouvrirJeuBlackjack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BlackjackView.fxml"));
        Parent root = loader.load();

        BlackjackController controller = loader.getController();
        controller.setJoueurEtGestion(joueur, gestionJoueur);
        controller.setCasinoController(this);

        Stage stage = new Stage();
        stage.setTitle("Blackjack");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void quitterCasino() {
        Stage stage = (Stage) btnQuitter.getScene().getWindow();
        stage.close();
    }

    public void actualiserSolde() {
        soldeLabel.setText("Solde : " + joueur.getSolde() + " €");
    }

}
