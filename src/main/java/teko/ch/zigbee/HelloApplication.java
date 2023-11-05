package teko.ch.zigbee;

import javafx.application.Application;
import javafx.stage.Stage;
import teko.ch.zigbee.baseApi.HueBridgeController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HelloApplication extends Application {
  //  @Override
    public void start(Stage stage) throws IOException {
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
       // Scene scene = new Scene(fxmlLoader.load(), 320, 240);
       // stage.setTitle("Hello!");
       // stage.setScene(scene);
       // stage.show();

         String bridgeBaseUrl = "http://192.168.1.103/api/"; // Replace with your Hue Bridge base URL
        String apiKey = "5kAfpCWnRQeUphawHE7yN2Fon4l4ZJjzqvVo788T"; // Replace with your API key

        HueBridgeController controller = new HueBridgeController(bridgeBaseUrl, apiKey);

                try {
                    controller.setLampState(1, "on", "false");
                    controller.setLampState(1, "xy", "[0.20,0.55]");

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