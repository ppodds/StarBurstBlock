module org.ppodds {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens org.ppodds to javafx.fxml;
    exports org.ppodds;
    opens org.ppodds.controllers to javafx.fxml;
    exports org.ppodds.controllers;
}