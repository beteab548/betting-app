// package application_bett;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import application.LoginPage;
// import application.RegistrationPage;


public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginPage loginPage = new LoginPage(primaryStage);
        loginPage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
