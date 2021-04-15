package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {

    //Server Variables
    static Socket socket;
    static PrintWriter out = null;
    static BufferedReader in = null;
    static Boolean hasRecievedRole = false;
    static String playerTurn = "2";

//---------------------------------Functions------------------------------------------------------------//

    //The function runs when the button "search for match is clicked"
    private static void searchButtonPress() throws IOException {
        TicTacToe.enableButtons();
        socket = new Socket("localhost", 8080);
        //Create a stream to send a message
        out = new PrintWriter(socket.getOutputStream(), true);
        out.println("PLAYER");

        //Send search message
        out.flush();

        //Create a stream to read messages
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        readMessageFromServer();
    }

    //This is to read whose turn it is
    public static void readMessageFromServer() throws IOException {
        TicTacToe.updateBoard();
        try {
            String playerTurn = null;
            try {
                playerTurn = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (playerTurn.equals("1")) {
                if (hasRecievedRole == false) {
                    TicTacToe.playerTurn = "1";
                    hasRecievedRole = true;
                }
                try {
                    playYourTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (playerTurn.equals("2")) {
                if (hasRecievedRole == false) {
                    TicTacToe.playerTurn = "2";
                    hasRecievedRole = true;
                }
                try {
                    waitYourTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Function to actually play your turn
    public static void playYourTurn() throws IOException {
        TicTacToe.updateBoard();
        System.out.println("It is your turn friend");
        System.out.println("Press a button man");
        TicTacToe.turnFinished = false;

    }

    //Function to send your board to the server
    public static void sendBoardToServer() {
        ArrayList<String> ourBoard;
        ourBoard = TicTacToe.board;
        String board = "";
        for (int i = 0; i < ourBoard.size(); i++) {
            board += ourBoard.get(i) + ",";
        }
        board = board.substring(0, board.length() - 1);
        System.out.println(board);
        out.println(board);
        out.flush();
        try {
            readMessageFromServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TicTacToe.updateBoard();

    }

    //This is the function to wait, once the board is sent, this reads it
    public static void waitYourTurn() throws IOException {
        System.out.println("for board input");
        String[] currentBoard = in.readLine().split(",");
        //store board from server
        TicTacToe.board = new ArrayList<>();
        for (String s : currentBoard) {
            TicTacToe.board.add(s);
        }
        //update board graphics
        TicTacToe.updateBoard();
        System.out.println(currentBoard);

        //waiting.start();
        readMessageFromServer();
    }




//----------------------------------Launch Main----------------------------------------------------------//
    public static void main(String[] args) {
        launch(args);
    }

    //Hello friends, I don't like fxml so I am going to fuck up the ui and make it here, this code will look ugly, sorry in advance!
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Tic Tac Toe");

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
        playerTurn = "2";
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
}
