package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Main extends Application {
    Socket socket;
    PrintWriter out = null;
    BufferedReader in = null;
    //Hello friends, I don't like fxml so I am going to fuck up the ui and make it here, this code will look ugly, sorry in advance!
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

        //Set up BorderPane
        BorderPane root = new BorderPane();

        //Declare buttons
        Button searchButton = new Button("Search for match");
        searchButton.setMinWidth(200);
        searchButton.setMinHeight(100);
        Button exitButton = new Button("Exit");
        exitButton.setMinWidth(200);
        exitButton.setMinHeight(100);

        //Set up button
        searchButton.setOnAction(e -> {
            try {
                searchButtonPress();
            } catch (IOException ioException) {
                System.out.println("Server is not open");
            }
        });
        exitButton.setOnAction(e -> exitButtonPress());

        //Setting up top panel
        GridPane topPanel = new GridPane();
        topPanel.setAlignment(Pos.TOP_LEFT);
        topPanel.setHgap(10);
        topPanel.setVgap(10);
        topPanel.setPadding(new Insets(5, 5, 5, 5));


        //Set up bottom panel
        GridPane bottomPanel = new GridPane();
        bottomPanel.setAlignment(Pos.TOP_LEFT);
        bottomPanel.setHgap(10);
        bottomPanel.setVgap(10);
        bottomPanel.setPadding(new Insets(5, 5, 5, 5));

        //Adding buttons to panel
        topPanel.add(searchButton, 0, 0);
        bottomPanel.add(exitButton, 29, 0);

        //Tic Tac Toe shtuff
        String playerTurn = "2";
        //TODO: UPDATE PLAYERTURN FROM SERVER WHILE GAME IS RUNNING
        GridPane TicTacToeUI = TicTacToe.getTicTacToe(playerTurn);
        //SERVER DOES SOMETHING
        root.setCenter(TicTacToeUI);
        System.out.println(root.getCenter().boundsInLocalProperty());

        //Adding Panels to BorderPane
        root.setTop(topPanel);
        root.setBottom(bottomPanel);

        //Setting up scene
        primaryStage.setScene(new Scene(root, 500, 725));
        primaryStage.show();
    }

    private void exitButtonPress() {
        System.exit(1);
    }

    private void searchButtonPress() throws IOException {

        //Create a stream to send a message
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("PLAYER");

        //Send search message
        out.flush();

        //Create a stream to read messages
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        readMessageFromServer();
    }

    public void readMessageFromServer() {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
