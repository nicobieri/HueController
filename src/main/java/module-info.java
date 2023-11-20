module teko.ch.zigbee {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens teko.ch.zigbee to javafx.fxml;
    exports teko.ch.zigbee;
}