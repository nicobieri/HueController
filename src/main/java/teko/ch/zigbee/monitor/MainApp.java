//package teko.ch.zigbee.monitor;
//
//package com.digi.xbee.example;
//
//import com.digi.xbee.api.WiFiDevice;
//import com.digi.xbee.api.XBeeDevice;
//import com.digi.xbee.api.exceptions.XBeeException;
//import com.digi.xbee.api.models.XBeeProtocol;
//
//public class MainApp {
//        private static final String PORT="COM1";
//        private static final int BAUD_RATE=9600;
//        private static final String DATA_TO_SEND="HelloXBeeWorld!";
//
//        public static void main(String[]args){
//        XBeeDevice myDevice = new XBeeDevice(PORT,BAUD_RATE);
//        byte[] dataToSend = DATA_TO_SEND.getBytes();
//        try {
//            myDevice.open();
//            System.out.format("Sendingbroadcastdata:'%s'",new String (dataToSend));
//        if (myDevice.getXBeeProtocol() == XBeeProtocol.XBEE_WIFI){
//            myDevice.close();
//        myDevice = new WiFiDevice(PORT,BAUD_RATE);
//        myDevice.open();
//        ((WiFiDevice)myDevice).sendBroadcastIPData(0x2616,dataToSend);
//        } else
//            myDevice.sendBroadcastData(dataToSend);
//        System.out.println(">>Success");
//        } catch (XBeeException e) {
//        System.out.println(">>Error");
//        e.printStackTrace();
//        System.exit(1);
//        } finally {
//            myDevice.close();
//        }
//        }
//}