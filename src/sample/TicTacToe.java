package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class TicTacToe extends Main{

    //Getting image
    private static final Image topLeftImage = new Image("TicTacToeGridTopLeft.png");
    private static final Image topMiddleImage = new Image("TicTacToeGridTopMiddle.png");
    private static final Image topRightImage = new Image("TicTacToeGridTopRight.png");
    private static final Image middleLeftImage = new Image("TicTacToeGridMiddleLeft.png");
    private static final Image middleMiddleImage = new Image("TicTacToeGridMiddleMiddle.png");
    private static final Image middleRightImage = new Image("TicTacToeGridMiddleRight.png");
    private static final Image bottomLeftImage = new Image("TicTacToeGridBottomLeft.png");
    private static final Image bottomMiddleImage = new Image("TicTacToeGridBottomMiddle.png");
    private static final Image bottomRightImage = new Image("TicTacToeGridBottomRight.png");
    private static final Image player1 = new Image("EX.gif");
    private static final Image player2 = new Image("OH.png");
    public static boolean turnFinished = false;
    public static ArrayList<Button> buttons = new ArrayList<>();
    static ArrayList<String> board = new ArrayList<>();
    public static String playerTurn;

    static Button topLeft = new Button(), topMiddle = new Button(), topRight = new Button();
    static Button middleLeft = new Button(), middleMiddle = new Button(), middleRight = new Button();
    static Button bottomLeft = new Button(), bottomMiddle = new Button(), bottomRight = new Button();

    public static GridPane getTicTacToe(String playerTurned) {
        playerTurn = playerTurned;
        GridPane ticTacGrid = new GridPane();
        Text test = new Text("TESTING TEXT");
        ticTacGrid.setAlignment(Pos.TOP_LEFT);
        ticTacGrid.setPadding(new Insets(50,50,50,50));

        //Initialize board array
        for(int i = 0; i<9; i++) {
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
        /*
        Button topLeft = new Button(), topMiddle = new Button(), topRight = new Button();
        Button middleLeft = new Button(), middleMiddle = new Button(), middleRight = new Button();
        Button bottomLeft = new Button(), bottomMiddle = new Button(), bottomRight = new Button();

        buttons.add(topLeft); buttons.add(topMiddle); buttons.add(topRight);
        buttons.add(middleLeft); buttons.add(middleMiddle); buttons.add(middleRight);
        buttons.add(bottomLeft); buttons.add(bottomMiddle); buttons.add(bottomRight);*/

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
        ticTacGrid.add(middleMiddle, 1,1);
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
        bottomLeft.setOnAction(e -> tap(playerTurn, bottomLeft,6));
        bottomMiddle.setOnAction(e -> tap(playerTurn, bottomMiddle, 7));
        bottomRight.setOnAction(e -> tap(playerTurn, bottomRight, 8));

        return ticTacGrid;
    }

    private static void tap(String option, Button button, int location) {
        board.set(location, option);
        if(option.equals("1")){
            button.setDisable(true);
            button.setGraphic(new ImageView(player1));
            button.setVisible(true);
        }
        if(option.equals("2")){
            button.setDisable(true);
            button.setGraphic(new ImageView(player2));
            button.setVisible(true);
        }
        Main.sendBoardToServer();
        turnFinished = true;
    }

    public static void updateBoard(){
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

    public static void updateButton(Button button, String val){
        //System.out.println("val is being read as: " +val);
        if (val.equals("0")){
            //do nothing mane
        } else if (val.equals("1")){
            //System.out.println("button val is now "+val);
            button.setDisable(true);
            button.setGraphic(new ImageView(player1));
            button.setVisible(true);
        } else if (val.equals("2")){
            //System.out.println("button val is now "+val);
            button.setDisable(true);
            button.setGraphic(new ImageView(player2));
            button.setVisible(true);
        }
    }

}
