package interfaceGraphique;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modele.GestionJoueur;
import modele.Joueur;

import java.util.function.Consumer;

public class LoginController {

    private GestionJoueur gestionJoueur;
    private Consumer<Joueur> loginSuccessCallback;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label messageLabel;

    public void setGestionJoueur(GestionJoueur gestionJoueur) {
        this.gestionJoueur = gestionJoueur;
    }

    public void setOnLoginSuccess(Consumer<Joueur> callback) {
        this.loginSuccessCallback = callback;
    }

    @FXML
    private void initialize() {
        // Bouton connexion
        loginButton.setOnAction(e -> {
            String nom = usernameField.getText().trim();
            String mdp = passwordField.getText().trim();
            Joueur joueur = gestionJoueur.connection(nom, mdp);
            if (joueur != null) {
                messageLabel.setText("Connexion réussie !");
                if (loginSuccessCallback != null) {
                    loginSuccessCallback.accept(joueur);
                }
            } else {
                messageLabel.setText("Nom ou mot de passe incorrect.");
            }
        });

        // Bouton inscription
        registerButton.setOnAction(e -> {
            String nom = usernameField.getText().trim();
            String mdp = passwordField.getText().trim();
            if (gestionJoueur.inscription(nom, mdp)) {
                messageLabel.setText("Inscription réussie ! Vous pouvez vous connecter.");
            } else {
                messageLabel.setText("Nom déjà utilisé, choisissez-en un autre.");
            }
        });
    }
}
