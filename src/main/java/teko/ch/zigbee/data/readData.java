package teko.ch.zigbee.data;

import teko.ch.zigbee.baseApi.hueBridgeConnector;

import java.util.HashMap;
import java.util.Map;

public class readData {

    String configFilePath = "config.txt";
    Map<String, String> configData = new HashMap<>();
    Map<String, String> readData = ConfigManager.readConfig(configFilePath);


    String ipAddress = readData.get("IP");
    String bridgeKey = readData.get("Key");
    hueBridgeConnector connector = new hueBridgeConnector();

    public String getIpAddress() {
        return ipAddress;
    }

    public String getBridgeKey() {
        return bridgeKey;
    }
}
