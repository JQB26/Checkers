module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.piece;
    opens org.example.piece to javafx.fxml;
    exports org.example.MatchHistory;
    opens org.example.MatchHistory to javafx.fxml;
}