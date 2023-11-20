module teko.ch.zigbee {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires java.desktop;


    opens teko.ch.zigbee to javafx.fxml;
    exports teko.ch.zigbee;
}