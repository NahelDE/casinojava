package interfaceGraphique;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.GestionJoueur;
import modele.Joueur;

public class CasinoApp extends Application {

    private GestionJoueur gestionJoueur = new GestionJoueur();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Chargement du LoginView
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(loader.load());

        LoginController loginController = loader.getController();
        loginController.setGestionJoueur(gestionJoueur);

        // Action après connexion réussie
        loginController.setOnLoginSuccess(joueur -> openCasino(primaryStage, joueur));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Connexion");
        primaryStage.show();
    }

    private void openCasino(Stage stage, Joueur joueur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CasinoView.fxml"));
            Scene scene = new Scene(loader.load());

            CasinoController casinoController = loader.getController();
            casinoController.setJoueur(joueur);
            casinoController.setGestionJoueur(gestionJoueur);

            stage.setScene(scene);
            stage.setTitle("Casino");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
