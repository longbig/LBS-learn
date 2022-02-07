package com.longbig.multifunction.service;

import com.uber.h3core.H3Core;
import com.uber.h3core.util.GeoCoord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author yuyunlong
 * @date 2021/12/5 3:13 下午
 * @description
 */
@Service
public class H3Service {

    public void testH3() throws IOException {
        H3Core h3 = H3Core.newInstance();
        double lat = 37.775938728915946;
        double lng = -122.41795063018799;
        int res = 9;

        String hexAddr = h3.geoToH3Address(lat, lng, res);
        System.out.println(hexAddr);
        long hex = h3.geoToH3(lat,lng, res);
        List<GeoCoord> geoCoords = h3.h3ToGeoBoundary(hex);
        System.out.println(hex);
        System.out.println(geoCoords);
    }

    public static void main(String[] args) {
        H3Service test = new H3Service();
        try {
            test.testH3();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
