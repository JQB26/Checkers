package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.Checkers;
import org.example.controller.GameController;

public class Resultview {

    @FXML
    Label matchResultLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        GameController.getInstance().setWinnerLabel(anchorPane);
    }

    @FXML
    public void restartGame(){
        Checkers.setRoot("mainview");
        GameController.getInstance().startGame();
        GameController.getInstance().run();
    }
}
