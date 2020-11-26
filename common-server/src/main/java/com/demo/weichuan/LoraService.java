package com.demo.weichuan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class LoraService {
    private static final String beacon = "beacon";

    private static final String gps = "gps";

    private static final String none = "none";

    private static final Logger LOGGER = LoggerFactory.getLogger(LoraService.class);


    private static HashMap<String, String> gpsCahe = new HashMap<>();

    @Autowired
    GpsService gpsService;

    public void messageArrived(String topic, MqttMessage message) throws UnsupportedEncodingException {

        String s = new String(message.getPayload());
        String msg = s.substring(11, s.length());
        System.out.println(msg);
        if (!msg.startsWith("{"))
            return;
        JSONObject obj = JSON.parseObject(msg);
        String data = obj.getString("data");
        List<String> dataList = getStrList(data);
        String type = checkData(dataList);
        String dataStr = "";
        for (String string : dataList)
            dataStr = dataStr + string;
        LOGGER.info("message type:" + type);
        if ("beacon".equals(type)) {
            String devEUI = obj.getString("devEUI");
        } else if ("gps".equals(type)) {
            gpsServerStart(topic, obj, dataStr);
            gpsServerEnd(topic, obj, dataStr);
        }
    }

    public void gpsServerStart(String topic, JSONObject obj, String dataStr) {
        String mac = obj.getString("devEUI");
        gpsCahe.put(mac, dataStr);
    }

    public void gpsServerEnd(String topic, JSONObject obj, String dataStr) {
        String mac = obj.getString("devEUI");
        String old = gpsCahe.get(mac);
        if (null != old && !old.isEmpty()) {
            String realData = dataStr;
            gpsCahe.remove(mac);
            realData = hexToAscii(realData);
            LOGGER.info(realData);
            String[] dataArr = realData.split(",");
            if (dataArr != null && dataArr.length > 5) {
                String lat = dataArr[2];
                String lon = dataArr[4];
                Map<String, Object> map = new HashMap<>();
                map.put("lat", lat);
                map.put("lon", lon);
                map.put("time", obj.getString("time"));
                map.put("hig", Double.valueOf(0.0D));
                if (dataArr.length > 9)
                    map.put("hig", dataArr[9]);
                map.put("mac", mac);
                map.put("user", mac);
                gpsService.handle(map);

            }
        }
    }

    private String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public void parseBeaconData(String sip, List<String> listStr) {
        if (listStr.size() < 30) {
            LOGGER.info("parsing beaconData, but data is < 30");
            return;
        }
        int nGuidStart = 9;
        int nGuidEnd = nGuidStart + 16;
        int nMajor = hexStringToInt(listStr.get(nGuidEnd), true) * 256 + hexStringToInt(listStr.get(nGuidEnd + 1), true);
        int nMinor = hexStringToInt(listStr.get(nGuidEnd + 2), true) * 256 + hexStringToInt(listStr.get(nGuidEnd + 3), true);
        String strRSSI = listStr.get(nGuidEnd + 4);
        int nRSSI = hexStringToInt(strRSSI, false) * -1;
        String strBattery = "";
        if (((String) listStr.get(nGuidEnd + 13)).equals(listStr.get(nGuidEnd)) && ((String) listStr
                .get(nGuidEnd + 14)).equals(listStr.get(nGuidEnd + 1))) {
            strBattery = listStr.get(nGuidEnd + 12);
        } else {
            strBattery = listStr.get(nGuidEnd + 13);
        }
        int nBattery = hexStringToInt(strBattery, true);
        HashMap<String, Object> mapOneBeaconData = new HashMap<>();
        mapOneBeaconData.put("major", Integer.valueOf(nMajor));
        mapOneBeaconData.put("minor", Integer.valueOf(nMinor));
        mapOneBeaconData.put("battery", Integer.valueOf(nBattery));
        mapOneBeaconData.put("rssi", Integer.valueOf(nRSSI));
        Map<Object, Object> beaconData = new HashMap<>();
        ArrayList<Map> dataList = new ArrayList<>();
        dataList.add(mapOneBeaconData);
        beaconData.put("time", Long.valueOf((new Date()).getTime()));
        beaconData.put("data", dataList);
        beaconData.put("type", "ibeacon");
        beaconData.put("user", sip);
        String strSendToserver = JSON.toJSONString(beaconData);
        LOGGER.info("will send beacon data to server:" + strSendToserver);
//    SocketUtil.sendDataToServer(this.configObject, strSendToserver);
    }

    private int hexStringToInt(String str, boolean isUn) {
        String mHexStr = "0123456789ABCDEF";
        int nFirst = mHexStr.indexOf(str.charAt(0));
        if (((!isUn ? 1 : 0) & ((nFirst > 8) ? 1 : 0)) != 0)
            nFirst -= 8;
        int iTmp = nFirst * 16;
        iTmp |= mHexStr.indexOf(str.charAt(1));
        return iTmp;
    }

    private double calcDistByRSSI(int rssi) {
        int iRssi = Math.abs(rssi);
        double power = (iRssi - 59) / 20.0D;
        return Math.pow(10.0D, power);
    }

    String checkData(List<String> dataList) {
        String result = "none";
        if ("02".equals(dataList.get(0)) && "01".equals(dataList.get(1))) {
            result = "beacon";
        } else if (dataList.size() > 0 && "47".equals(dataList.get(0)) && "4E".equals(dataList.get(1))) {
            result = "gps";
        }
        return result;
    }

    List<String> getStrList(String str) {
        byte[] b = Base64.decodeBase64(str);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1)
                hex = '0' + hex;
            strings.add(hex.toUpperCase());
        }
        LOGGER.info(strings.toString());
        return strings;
    }

    public static void main(String[] args) {
        String s="02AFBA020004E4956E4287E1";
        LoraService service = new LoraService();
        List<String> strList = service.getStrList(s);
        for (String s1 : strList) {
            String s2 = service.hexToAscii(s1);
            System.out.println("s2 = " + s2);
        }
        System.out.println("strList = " + strList);
    }
}
