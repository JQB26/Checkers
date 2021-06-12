package org.example.view;

import javafx.fxml.FXML;
import org.example.Checkers;

import java.io.IOException;

public class MainView {

    @FXML
    private void switchToSecondary() throws IOException {
        Checkers.setRoot("gameview");
    }
}
