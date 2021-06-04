module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.piece;
    opens org.example.piece to javafx.fxml;
    exports org.example.piece.enums;
    opens org.example.piece.enums to javafx.fxml;
}