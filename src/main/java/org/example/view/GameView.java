package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.Checkers;
import org.example.model.Board;
import org.example.model.Tile.Tile;

import java.net.URL;
import java.util.ResourceBundle;

public class GameView implements Initializable{

    private Board board = new Board();

    public Node[][] getGridPaneArray() {
        return gridPaneArray;
    }

    private Node[][] gridPaneArray = null;

    @FXML
    private GridPane boardPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        board.generateBoard();
        generateBoard();
        this.gridPaneArray = new Node[11][11];
        for(Node node : this.boardPane.getChildren()){
            this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
        }
    }

    public void generateBoard(){
        for(int row = 0; row <= 9; row++){
            for(int col = 0; col <= 9; col++){
                Tile tile = board.getTile(col, row);
                boardPane.add(tile.getRectangle(), col, row);
                if(tile.getPiece() != null) {
                    boardPane.add(tile.getPiece().getCircle(), col, row);
                    GridPane.setHalignment(tile.getPiece().getCircle(), HPos.CENTER);
                }
            }
        }

        for(int i = 0; i <= 9; i++){
            Rectangle rectangle = new Rectangle();
            Text text = new Text();
            rectangle.setFill(Color.BLACK);
            rectangle.setHeight(70);
            rectangle.setWidth(10);
            text.setText(String.valueOf(i));
            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-weight: bold");
            GridPane.setHalignment(text, HPos.CENTER);
            boardPane.add(rectangle, 10, i);
            boardPane.add(text, 10, i);
        }

        for(int i = 0; i <= 9; i++){
            Rectangle rectangle = new Rectangle();
            Text text = new Text();
            rectangle.setFill(Color.BLACK);
            rectangle.setHeight(10);
            rectangle.setWidth(70);
            text.setText(String.valueOf(i));
            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-weight: bold");
            GridPane.setHalignment(text, HPos.CENTER);
            boardPane.add(rectangle, i, 10);
            boardPane.add(text, i, 10);
        }

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.BLACK);
        rectangle.setHeight(10);
        rectangle.setWidth(10);
        boardPane.add(rectangle, 10, 10);
    }
}