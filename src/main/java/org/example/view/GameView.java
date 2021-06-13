package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.example.Checkers;
import org.example.controller.GameController;
import org.example.model.Board;
import org.example.model.Tile.Tile;

public class GameView {

    private Board board;

    @FXML
    private GridPane boardPane;

    @FXML
    public void initialize() {
        board = new Board();
        board.generateBoard();
        generateBoard();
        GameController.getInstance().setBoardPane(boardPane);
    }

    @FXML
    public void restartGame(){
        Checkers.setRoot("mainview");
        GameController.getInstance().startGame();
        GameController.getInstance().run();
    }

    public void generateBoard() {
        int j = 0;
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                Tile tile = board.getTile(col, row);
                boardPane.add(tile.getRectangle(), col, row);
                if (tile.getPiece() != null) {
                    boardPane.add(tile.getPiece().getCircle(), col, row);
                    GridPane.setHalignment(tile.getPiece().getCircle(), HPos.CENTER);
                }
            }
        }

        for (int i = 0; i <= 9; i++) {
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

        for (int i = 0; i <= 9; i++) {
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