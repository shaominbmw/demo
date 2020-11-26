package com.demo.weichuan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class GpsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GpsService.class);

    //  @Resource
//  private UgpsApiService ugpsApiService;
//
//  @Resource
//  UserInfoManagerFromOldDb userInfoMan;
//    @Autowired
//    LocationDao locationDao;

    public boolean handle(Map<String, Object> data) {
        LOGGER.info("===gps:" + data);
        String devEUI = data.get("mac").toString();
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(6);
        nf.setRoundingMode(RoundingMode.UP);
        String lon = nf.format(convertDuFenToHuDu(data.get("lon").toString()));
        String lat = nf.format(convertDuFenToHuDu(data.get("lat").toString()));
        String user = String.valueOf(data.get("user"));
        Map<String, Object> mapOneData = new HashMap<>();
        mapOneData.put("lon", lon);
        mapOneData.put("lat", lat);
        mapOneData.put("serverTime", Long.valueOf(System.currentTimeMillis()));
        mapOneData.put("time", Long.valueOf(System.currentTimeMillis()));
        mapOneData.put("type", "ugps");
        mapOneData.put("hig", data.get("hig"));
//        Location location = new Location();
//        double[] doubles = GPSUtil.gps84_To_Gcj02(Double.parseDouble(lat), Double.parseDouble(lon));
//        location.setLat(String.format("%.6f", doubles[0]));
//        location.setLon(String.format("%.6f", doubles[1]));
//        location.setUser(user);
//        location.setTime(System.currentTimeMillis());
//        locationDao.addLocation(location);
        LOGGER.info("will call ugpsApiService.handle({})", mapOneData);
//      this.ugpsApiService.handle(mapOneData);
//    }
        return true;
    }

    private String getStr(Object obj) {
        return (obj == null) ? null : obj.toString();
    }

    public String formatDateStr(String dateText) {
        dateText = dateText.split("\\.")[0];
        dateText = dateText.replace("T", " ");
        return dateText;
    }

    public Long formatDate(String dateText) {
        try {
            dateText = dateText.split("\\.")[0];
            dateText = dateText.replace("T", " ");
            SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return Long.valueOf(sdfDateFormat.parse(dateText).getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double convertDuFenToHuDu(String str) {
        LOGGER.info("convertDuFenToHuDu parse string:", str);
        int dotPosition = str.indexOf(".");
        String zsBuFen = str.substring(0, dotPosition - 2);
        String xsBuFen = str.substring(dotPosition - 2);
        double zhengSu = Double.parseDouble(xsBuFen) / 60.0D;
        double xiaoShu = Double.parseDouble(zsBuFen);
        return xiaoShu + zhengSu;
    }
}
