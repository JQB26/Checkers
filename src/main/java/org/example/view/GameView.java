package org.example.view;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import org.example.model.Board;
import org.example.model.Tile.Tile;

public class GameView {

    private Board board = new Board();

    @FXML
    GridPane boardPane;

    @FXML
    public void initialize() {
        board.generateBoard();
        generateBoard();
    }

    public void generateBoard(){
        for(int row = 0; row <= 9; row++){
            for(int col = 0; col <= 9; col++){
                Tile tile = board.getTile(col, row);
                boardPane.add(tile.getRectangle(), col, row);
                if(tile.getPiece() != null) {
                    boardPane.add(tile.getPiece().getPawn(), col, row);
                    GridPane.setHalignment(tile.getPiece().getPawn(), HPos.CENTER);
                }
            }
        }
    }



}
