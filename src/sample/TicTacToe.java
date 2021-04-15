package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TicTacToe extends Main {

    //Getting image of board grids
    private static final Image topLeftImage = new Image("TicTacToeGridTopLeft.png");
    private static final Image topMiddleImage = new Image("TicTacToeGridTopMiddle.png");
    private static final Image topRightImage = new Image("TicTacToeGridTopRight.png");
    private static final Image middleLeftImage = new Image("TicTacToeGridMiddleLeft.png");
    private static final Image middleMiddleImage = new Image("TicTacToeGridMiddleMiddle.png");
    private static final Image middleRightImage = new Image("TicTacToeGridMiddleRight.png");
    private static final Image bottomLeftImage = new Image("TicTacToeGridBottomLeft.png");
    private static final Image bottomMiddleImage = new Image("TicTacToeGridBottomMiddle.png");
    private static final Image bottomRightImage = new Image("TicTacToeGridBottomRight.png");

    //Image of the X and O
    private static final Image player1 = new Image("EX.gif");
    private static final Image player2 = new Image("OH.png");

    //Conditional Variables
    public static boolean turnFinished = false;
    public static ArrayList<Button> buttons = new ArrayList<>();
    public static String playerTurn;
    static ArrayList<String> board = new ArrayList<>();
    public static boolean gameOver = false;

    //Button Variables
    static Button topLeft = new Button(), topMiddle = new Button(), topRight = new Button();
    static Button middleLeft = new Button(), middleMiddle = new Button(), middleRight = new Button();
    static Button bottomLeft = new Button(), bottomMiddle = new Button(), bottomRight = new Button();

//---------------------------------Functions------------------------------------------------------------//

    //Initialize the state of the board
    public static GridPane getTicTacToe(String playerTurned) {
        playerTurn = playerTurned;
        GridPane ticTacGrid = new GridPane();
        Text test = new Text("TESTING TEXT");
        ticTacGrid.setAlignment(Pos.TOP_LEFT);
        ticTacGrid.setPadding(new Insets(50, 50, 50, 50));

        //Initialize board array
        for (int i = 0; i < 9; i++) {
            board.add("0");
        }
        System.out.println(board.size());

        //Setting image
        ticTacGrid.add(new ImageView(topLeftImage), 0, 0);
        ticTacGrid.add(new ImageView(topMiddleImage), 1, 0);
        ticTacGrid.add(new ImageView(topRightImage), 2, 0);
        ticTacGrid.add(new ImageView(middleLeftImage), 0, 1);
        ticTacGrid.add(new ImageView(middleMiddleImage), 1, 1);
        ticTacGrid.add(new ImageView(middleRightImage), 2, 1);
        ticTacGrid.add(new ImageView(bottomLeftImage), 0, 2);
        ticTacGrid.add(new ImageView(bottomMiddleImage), 1, 2);
        ticTacGrid.add(new ImageView(bottomRightImage), 2, 2);

        //Declaring Buttons

        //Setting buttons to be initially inactive
        topLeft.setDisable(true);
        topMiddle.setDisable(true);
        topRight.setDisable(true);
        middleLeft.setDisable(true);
        middleMiddle.setDisable(true);
        middleRight.setDisable(true);
        bottomLeft.setDisable(true);
        bottomMiddle.setDisable(true);
        bottomRight.setDisable(true);

        //Setting button sizes (According to image sizes)
        topLeft.setMinSize(127, 129);
        topMiddle.setMinSize(127, 129);
        topRight.setMinSize(128, 129);
        middleLeft.setMinSize(127, 129);
        middleMiddle.setMinSize(127, 129);
        middleRight.setMinSize(128, 129);
        bottomLeft.setMinSize(127, 130);
        bottomMiddle.setMinSize(127, 130);
        bottomRight.setMinSize(128, 130);

        //Adding button to image
        ticTacGrid.add(topLeft, 0, 0);
        ticTacGrid.add(topMiddle, 1, 0);
        ticTacGrid.add(topRight, 2, 0);
        ticTacGrid.add(middleLeft, 0, 1);
        ticTacGrid.add(middleMiddle, 1, 1);
        ticTacGrid.add(middleRight, 2, 1);
        ticTacGrid.add(bottomLeft, 0, 2);
        ticTacGrid.add(bottomMiddle, 1, 2);
        ticTacGrid.add(bottomRight, 2, 2);
        ticTacGrid.setGridLinesVisible(true);

        //Making buttons invisible
        ticTacGrid.getStylesheets().add("Colors.css");

        //Button Actions
        topLeft.setOnAction(e -> tap(playerTurn, topLeft, 0));
        topMiddle.setOnAction(e -> tap(playerTurn, topMiddle, 1));
        topRight.setOnAction(e -> tap(playerTurn, topRight, 2));
        middleLeft.setOnAction(e -> tap(playerTurn, middleLeft, 3));
        middleMiddle.setOnAction(e -> tap(playerTurn, middleMiddle, 4));
        middleRight.setOnAction(e -> tap(playerTurn, middleRight, 5));
        bottomLeft.setOnAction(e -> tap(playerTurn, bottomLeft, 6));
        bottomMiddle.setOnAction(e -> tap(playerTurn, bottomMiddle, 7));
        bottomRight.setOnAction(e -> tap(playerTurn, bottomRight, 8));

        return ticTacGrid;
    }

    //Enables the buttons on click
    public static void enableButtons() {
        topLeft.setDisable(false);
        topMiddle.setDisable(false);
        topRight.setDisable(false);
        middleLeft.setDisable(false);
        middleMiddle.setDisable(false);
        middleRight.setDisable(false);
        bottomLeft.setDisable(false);
        bottomMiddle.setDisable(false);
        bottomRight.setDisable(false);
    }

    //Click the button and set image
    private static void tap(String option, Button button, int location) {
        board.set(location, option);
        checkEndGame(option);
        if (option.equals("1")) {
            button.setDisable(true);
            button.setGraphic(new ImageView(player1));
            button.setVisible(true);
        }
        if (option.equals("2")) {
            button.setDisable(true);
            button.setGraphic(new ImageView(player2));
            button.setVisible(true);
        }
        Main.sendBoardToServer();
        checkEndGame(option);
        turnFinished = true;
    }

    //Sets the win or tie conditions for the board
    public static void checkEndGame(String option) {
        if (board.get(0).equals(option) && board.get(1).equals(option) && board.get(2).equals(option)) {
            wonGame(option);
        } else if (board.get(3).equals(option) && board.get(4).equals(option) && board.get(5).equals(option)) {
            wonGame(option);
        } else if (board.get(6).equals(option) && board.get(7).equals(option) && board.get(8).equals(option)) {
            wonGame(option);
        } else if (board.get(0).equals(option) && board.get(3).equals(option) && board.get(6).equals(option)) {
            wonGame(option);
        } else if (board.get(1).equals(option) && board.get(4).equals(option) && board.get(7).equals(option)) {
            wonGame(option);
        } else if (board.get(2).equals(option) && board.get(5).equals(option) && board.get(8).equals(option)) {
            wonGame(option);
        } else if (board.get(0).equals(option) && board.get(4).equals(option) && board.get(8).equals(option)) {
            wonGame(option);
        } else if (board.get(2).equals(option) && board.get(4).equals(option) && board.get(6).equals(option)) {
            wonGame(option);
        } else if (!board.get(0).equals("0") && !board.get(1).equals("0") && !board.get(2).equals("0")
                && !board.get(3).equals("0") && !board.get(4).equals("0") && !board.get(5).equals("0")
                && !board.get(6).equals("0") && !board.get(7).equals("0") && !board.get(8).equals("0")) {
            tieGame(option);
        }
    }


    //Checks if the game is won
    public static void wonGame(String option) {
        // ServerThread.gameIsOver = true ?

        System.out.println("GAME OVER TEST LINE");
        gameOver = true;
        Main.sendBoardToServer();
        try {
            Main.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage winStage = new Stage();
        Text win = new Text(100, 100, "Player " + option + " wins!");

        Button exitButton = new Button("Exit");
        exitButton.setMinWidth(50);
        exitButton.setMinHeight(25);
        exitButton.setOnAction(e -> System.exit(1));

        win.setFont(new Font(20));
        Group group = new Group(win, exitButton);
        winStage.setScene(new Scene(group, 300, 300));
        winStage.show();

    }

    //Checks if the game is a tie
    public static void tieGame(String option) {
        try {
            Main.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage tieStage = new Stage();
        Text tie = new Text(50, 100, "The game ends in a tie!");

        Button exitButton = new Button("Exit");
        exitButton.setMinWidth(50);
        exitButton.setMinHeight(25);
        exitButton.setOnAction(e -> System.exit(1));

        tie.setFont(new Font(20));
        Group group = new Group(tie, exitButton);
        tieStage.setScene(new Scene(group, 300, 300));
        tieStage.show();

    }

    //Updates the state of the board
    public static void updateBoard() {
        String yourNum = null;
        if(playerTurn == "1") yourNum = "2";
        if(playerTurn == "2") yourNum = "1";
        checkEndGame(yourNum);
        System.out.println("updating local board");
        System.out.println("Local board is now: " + board.toString());
        updateButton(topLeft, board.get(0));
        updateButton(topMiddle, board.get(1));
        updateButton(topRight, board.get(2));
        updateButton(middleLeft, board.get(3));
        updateButton(middleMiddle, board.get(4));
        updateButton(middleRight, board.get(5));
        updateButton(bottomLeft, board.get(6));
        updateButton(bottomMiddle, board.get(7));
        updateButton(bottomRight, board.get(8));

    }

    //Checks if the button grid has been clicked
    public static void updateButton(Button button, String val) {
        //System.out.println("val is being read as: " +val);
        if (val.equals("0")) {
            //do nothing mane
        } else if (val.equals("1")) {
            //System.out.println("button val is now "+val);
            button.setDisable(true);
            button.setGraphic(new ImageView(player1));
            button.setVisible(true);
        } else if (val.equals("2")) {
            //System.out.println("button val is now "+val);
            button.setDisable(true);
            button.setGraphic(new ImageView(player2));
            button.setVisible(true);
        }
    }

}
