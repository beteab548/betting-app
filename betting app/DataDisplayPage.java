import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.ColumnConstraints;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataDisplayPage {

    private Stage stage;
    private String sport;

    public DataDisplayPage(Stage primaryStage, String sport) {
        this.stage = primaryStage;
        this.sport = sport;
    }

    public void show() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        Label titleLabel = new Label(sport );
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        vbox.getChildren().add(titleLabel);

                // Label date = new Label("Date");
                // Label dataLabel = new Label("Game");
                // Label homeBtn = new Label("Home");
                // Label drawBtn = new Label("Draw");
                // Label awayBtn = new Label("Away");

                // gridPane.add(date, 0, 0);
                // gridPane.add(dataLabel, 0, 0);
                // gridPane.add(homeBtn, 7, 0);
                // gridPane.add(drawBtn, 8, 0);
                // gridPane.add(awayBtn,9, 0);

                // ColumnConstraints col1 = new ColumnConstraints();
                // col1.setPercentWidth(15); // Set to 25% width for each button
                // ColumnConstraints col2 = new ColumnConstraints();
                // col1.setPercentWidth(50); // Set to 25% width for each button
                // gridPane.getColumnConstraints().addAll(col1,col2);
                // Add GridPane to VBox
                // vbox.getChildren().add(gridPane);

                GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);

            // Add column headers
            Label date = new Label("Date");
            Label datalabel = new Label("Game");
            Label homebtn = new Label("Home");
            Label drawbtn = new Label("Draw");
            Label awaybtn = new Label("Away");

            gridPane.add(date, 0, 0);
            gridPane.add(datalabel, 1, 0);
            gridPane.add(homebtn, 2, 0);
            gridPane.add(drawbtn, 3, 0);
            gridPane.add(awaybtn, 4, 0);

            // Set column constraints
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(10);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(10);
            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(10);
            ColumnConstraints col5 = new ColumnConstraints();
            col5.setPercentWidth(10);
            gridPane.getColumnConstraints().addAll(col1, col2, col3, col4, col5);

            // Add GridPane to VBox
            vbox.getChildren().add(gridPane);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/daweko", "root", "dawe014");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + sport.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();

            
            // Process the retrieved data
            while (resultSet.next()) {
                String game = resultSet.getString("game");
                String home = resultSet.getString("home");
                String draw = resultSet.getString("draw");
                String away = resultSet.getString("away");

                Button dataLabel = new Button(game);
                Button homeBtn = new Button(home);
                Button drawBtn = new Button(draw);
                Button awayBtn = new Button(away);

                // Set styles for buttons
                dataLabel.setStyle("-fx-text-fill: black;");
                homeBtn.setStyle("-fx-text-fill: black;");
                drawBtn.setStyle("-fx-text-fill: black;");
                awayBtn.setStyle("-fx-text-fill: black;");

                // Create a GridPane for each row of data
                // GridPane gridPane = new GridPane();
                // gridPane.setAlignment(Pos.CENTER);
                // gridPane.setHgap(10);

                // Add buttons to GridPane columns
                gridPane.add(dataLabel, 0, 0);
                gridPane.add(homeBtn, 7, 0);
                gridPane.add(drawBtn, 8, 0);
                gridPane.add(awayBtn,9, 0);

                //  ColumnConstraints col1 = new ColumnConstraints();
                // col1.setPercentWidth(50); // Set to 25% width for each button
                // gridPane.getColumnConstraints().addAll(col1);
                // Add GridPane to VBox
                // vbox.getChildren().add(gridPane);
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions here, e.g., display an error message
        }

        Scene scene = new Scene(vbox, 500, 600);
        scene.setFill(Color.rgb(52, 152, 219));

        stage.setScene(scene);
        stage.setTitle(sport + " Data");
        stage.show();
    }
}
