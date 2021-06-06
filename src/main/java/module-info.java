module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.model.piece;
    opens org.example.model.piece to javafx.fxml;
    exports org.example.model.piece.enums;
    opens org.example.model.piece.enums to javafx.fxml;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;
    exports org.example.model;
    opens org.example.model to javafx.fxml;
    exports org.example.model.Tile;
    opens org.example.model.Tile to javafx.fxml;
    exports org.example.view;
    opens org.example.view to javafx.fxml;
    exports org.example.model.position;
    opens org.example.model.position to javafx.fxml;
}