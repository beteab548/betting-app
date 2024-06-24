import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WelcomePage {

    private Stage stage;
     private VBox dataContainer; // Define dataContainer

    public WelcomePage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void show() {
        Label welcomeLabel = new Label("Betting");
        welcomeLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;-fx-font-style: italic; -fx-text-fill: white;");

        Button footballButton = createNavigationButton("Football");
        Button volleyballButton = createNavigationButton("Volleyball");
        Button basketballButton = createNavigationButton("Basketball");
        Button mybet = createNavigationButton("MyBet");

        // Button myBetButton = createMyBetButton();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.ALWAYS);
        // col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        ColumnConstraints col5 = new ColumnConstraints();
        col5.setHgrow(Priority.ALWAYS);
         String imagePath = "images/photo_2024-05-09_23-30-46.jpg"; // Replace "image.jpg" with your image file name
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);
        gridPane.getColumnConstraints().addAll(col1, col2, col3);

        gridPane.add(welcomeLabel, 1, 0,4,4);
        gridPane.add(footballButton, 0, 4);
        gridPane.add(volleyballButton, 1, 4);
        gridPane.add(basketballButton, 2, 4);
        gridPane.add(mybet, 3, 4);

        // gridPane.setMargin(myBetButton, new Insets(10));
         VBox vbox = new VBox( gridPane,imageView);
        vbox.setSpacing(20);
          

        gridPane.setStyle("-fx-background-color: #3498db; -fx-padding: 20px;");

        Scene scene = new Scene(vbox, 500, 600);
        scene.setFill(Color.rgb(52, 152, 219));

        stage.setScene(scene);
        stage.setTitle("MyBet");
        stage.show();
    }

    private Button createNavigationButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-size: 16px; -fx-pref-width: 150px;");
         button.setOnAction(event -> {
            // Retrieve data from the database when a navigation button is clicked
            // fetchDataFromDatabase(text);
             DataDisplayPage dataDisplayPage = new DataDisplayPage(stage, text);
            dataDisplayPage.show();
        });
        return button;
       
    }

    private Button createMyBetButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 16px;");
        button.setOnAction(event -> {
            // Add MyBet button logic here
            System.out.println("Navigating to My Bet");
        });
        return button;
    }
        private void fetchDataFromDatabase(String sport) {
        // Clear previous data
        dataContainer.getChildren().clear();

        // Establishing connection to the database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/daweko", "root", "dawe014");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + sport.toLowerCase() );
            ResultSet resultSet = preparedStatement.executeQuery();

            // Process the retrieved data
            while (resultSet.next()) {
                String playerName = resultSet.getString("game");
                // int playerScore = resultSet.getInt("score");
                Label dataLabel = new Label("Game: " + playerName);
                dataLabel.setStyle("-fx-text-fill: white;");
                dataContainer.getChildren().add(dataLabel);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
