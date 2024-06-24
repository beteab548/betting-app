// package application_bett;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationPage {

    private Stage stage;
    // public Connection connection;

    public RegistrationPage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void show() {
        // Establishing connection to the database
       

        // Setting up the registration form components
        Label titleLabel = new Label("Registration");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label phoneLabel = new Label("Phone:");
        phoneLabel.setTextFill(Color.WHITE);
        TextField phoneField = new TextField();

        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.WHITE);
        PasswordField passwordField = new PasswordField();

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setTextFill(Color.WHITE);
        PasswordField confirmPasswordField = new PasswordField();

        Button actionButton = new Button("Register");
        actionButton.setDefaultButton(true);
        actionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        actionButton.setOnAction(e -> {
            // Password validation
            String password = passwordField.getText();
            String phone = phoneField.getText();
            String confirmPassword = confirmPasswordField.getText();
            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match!");
                return;
            }

            // Insert registration data into the database
            try {
                 Class.forName("com.mysql.cj.jdbc.Driver");
             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/daweko", "root", "dawe014");
            
        // } catch (ClassNotFoundException | SQLException e) {
        //     e.printStackTrace();
        //     showError("Failed to connect to the database.");
        //     return;
        // }
                String query = "INSERT INTO users (phone, pass) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
                showSuccess("Registration successful!");
                stage.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Failed to register. Please try again.");
            }catch (ClassNotFoundException f) {
            f.printStackTrace();
            showError("Failed to connect to the database.");
            return;
            }
        });

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            loginPage.show();
        });

        // Creating a GridPane to layout components
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Adding components to the grid pane
        gridPane.add(titleLabel, 0, 0, 2, 1);
        gridPane.add(phoneLabel, 0, 1);
        gridPane.add(phoneField, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(actionButton, 1, 4);
        gridPane.add(loginButton, 1, 5);

        // Aligning title label to center
        GridPane.setHalignment(titleLabel, HPos.CENTER);

        // Adding padding to the grid pane
        gridPane.setPadding(new Insets(20));
        gridPane.setStyle("-fx-background-color: #3498db;");

        // Setting up the scene with background color
        Scene scene = new Scene(gridPane, 500, 400);
        scene.setFill(Color.rgb(52, 152, 219)); // Set background color for the entire scene

        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
