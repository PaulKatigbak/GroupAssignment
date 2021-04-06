package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TicTacToe extends Main{

    public static GridPane getTicTacToe() {
        GridPane ticTacGrid = new GridPane();
        Text test = new Text("TESTING TEXT");
        ticTacGrid.setAlignment(Pos.TOP_LEFT);
        ticTacGrid.setPadding(new Insets(50,50,50,50));

        //Getting image
        Image topLeftImage = new Image("TicTacToeGridTopLeft.png");
        Image topMiddleImage = new Image("TicTacToeGridTopMiddle.png");
        Image topRightImage = new Image("TicTacToeGridTopRight.png");
        Image middleLeftImage = new Image("TicTacToeGridMiddleLeft.png");
        Image middleMiddleImage = new Image("TicTacToeGridMiddleMiddle.png");
        Image middleRightImage = new Image("TicTacToeGridMiddleRight.png");
        Image bottomLeftImage = new Image("TicTacToeGridBottomLeft.png");
        Image bottomMiddleImage = new Image("TicTacToeGridBottomMiddle.png");
        Image bottomRightImage = new Image("TicTacToeGridBottomRight.png");

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
        Button topLeft = new Button(), topMiddle = new Button(), topRight = new Button();
        Button middleLeft = new Button(), middleMiddle = new Button(), middleRight = new Button();
        Button bottomLeft = new Button(), bottomMiddle = new Button(), bottomRight = new Button();

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
        topLeft.setVisible(false);
        topMiddle.setVisible(false);
        topRight.setVisible(false);
        middleLeft.setVisible(false);
        middleMiddle.setVisible(false);
        middleRight.setVisible(false);
        bottomLeft.setVisible(false);
        bottomMiddle.setVisible(false);
        bottomRight.setVisible(false);

        //Button Actions
        topLeft.setOnAction(e -> tap("x"));

        return ticTacGrid;
    }

    private static void tap(String x) {
        
    }
}
