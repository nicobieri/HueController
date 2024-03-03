module teko.ch.zigbee {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires java.sql;
    requires java.desktop;

    opens teko.ch.zigbee to javafx.fxml;
    exports teko.ch.zigbee; // Export the teko.ch.zigbee module itself to javafx.graphics
    exports teko.ch.zigbee.baseApi to com.fasterxml.jackson.databind;
    exports teko.ch.zigbee.data to com.fasterxml.jackson.databind;
}
