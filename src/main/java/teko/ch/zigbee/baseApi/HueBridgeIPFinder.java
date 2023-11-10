package teko.ch.zigbee.baseApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class HueBridgeIPFinder {

    public static List<String> getMyIP() {
        List<String> ipAddresses = new ArrayList<>();
        try {
            URL url = new URL("https://discovery.meethue.com/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ipAddress = jsonObject.getString("internalipaddress");
                ipAddresses.add(ipAddress);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddresses;
    }
}
