module teko.ch.zigbee {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens teko.ch.zigbee to javafx.fxml;
    exports teko.ch.zigbee;
}