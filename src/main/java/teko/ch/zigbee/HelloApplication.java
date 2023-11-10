package teko.ch.zigbee;

import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.baseApi.HueBridgeController;
import teko.ch.zigbee.baseApi.HueBridgeIPFinder;
import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
  //  @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
       // stage.setTitle("Hello!");
       // stage.setScene(scene);
       // stage.show();


        List<String> ipAddresses = HueBridgeIPFinder.getMyIP();
        // Assuming you want to use the first Bridge IP found
        String bridgeIP = ipAddresses.get(0);
        System.out.println("Hue Bridge IP: " + bridgeIP);
        
        String bridgeBaseUrl = "http://" + bridgeIP + "/api/"; // Replace with your Hue Bridge base URL
        ///todo Nico

        String apiKey = "5kAfpCWnRQeUphawHE7yN2Fon4l4ZJjzqvVo788T"; // Replace with your API key

        ///todo Steve
        //String apiKey = "4PCT1TYO3UrSjf7lLsQODWsrjfS-C7m47l0FOCFc"; // Replace with your API key



        HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, apiKey);

                try {
                    controller.setLampState(1, "on", "true");
                    controller.setLampState(1, "xy", "[0.20,0.15]");

                    controller.getLampState(1);



                    // Example: Turn off lamp 2
                    //controller.setLampState(2, "true");

                    // Example: Turn off lamp 3
                    //controller.setLampState(3, "true");

                } catch (IOException e) {
                    e.printStackTrace();
                }

        System.out.println("finish");

    }

    public static void main(String[] args) {

        launch();

    }

 }