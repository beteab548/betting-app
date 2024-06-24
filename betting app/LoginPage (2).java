import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage {

    private Stage stage;
    private Connection connection;

    public LoginPage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void show() {
        // Establishing connection to the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/daweko", "root", "dawe014");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            showError("Failed to connect to the database.");
            return;
        }

        // Setting up the login form components
        Label titleLabel = new Label("Welcome!");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setTextFill(Color.WHITE);
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();

        Button actionButton = new Button("Login");
        actionButton.setDefaultButton(true);
        actionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        actionButton.setOnAction(e -> loginUser(usernameField.getText(), passwordField.getText()));

        Button switchButton = new Button("Register");
        switchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        switchButton.setOnAction(e -> {
            RegistrationPage registrationPage = new RegistrationPage(stage);
            registrationPage.show();
        });

        // Creating a GridPane to layout components
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Adding components to the grid pane
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(actionButton, 1, 3);
        gridPane.add(switchButton, 1, 4);

        // Aligning title label to center
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        // Adding padding to the grid pane
        gridPane.setPadding(new Insets(20));
        gridPane.setStyle("-fx-background-color: #096a2e;");

        // Loading image from a relative path
        String imagePath = "images/photo_2024-05-09_23-30-46.jpg"; // Replace "image.jpg" with your image file name
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        // Creating a VBox to hold the image and the grid pane
        VBox vbox = new VBox(imageView, gridPane);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);

        // Setting up the scene with background color
        Scene scene = new Scene(vbox, 500, 600);
        scene.setFill(Color.rgb(9, 106, 46)); // Set background color for the entire scene to #096a2e

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void loginUser(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE phone = ? AND pass = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Successful login, navigate to welcome scene
                WelcomePage welcomePage = new WelcomePage(stage);
                welcomePage.show();
            } else {
                showError("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Failed to login. Please try again.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
