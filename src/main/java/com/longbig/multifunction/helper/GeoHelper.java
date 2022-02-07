package com.longbig.multifunction.helper;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yuyunlong
 * @date 2022/2/2 5:20 下午
 * @description
 */
@Slf4j
public class GeoHelper {

    public static String list2String(List<List<Double>> polygon) {
        StringBuffer polygonStr = new StringBuffer();
        for (int i = 0; i < polygon.size(); i++) {
            List<Double> point = polygon.get(i);
            polygonStr.append(point.get(0))
                    .append(",")
                    .append(point.get(1))
                    .append(";");
        }
        return polygonStr.toString();
    }

    public static String list2Wkt(List<List<Double>> polygon) {
        StringBuffer polygonStr = new StringBuffer();
        polygonStr.append("MULTIPOLYGON(((");
        for (int i = 0; i < polygon.size(); i++) {
            List<Double> point = polygon.get(i);
            polygonStr.append(point.get(0))
                    .append(" ")
                    .append(point.get(1))
                    .append(",");
        }
        List<Double> point1 = polygon.get(0);
        polygonStr.append(point1.get(0)).append(" ").append(point1.get(1)).append(")))");
        log.info(polygonStr.toString());
        return polygonStr.toString();
    }

    public static String point2Wkt(Double lng, Double lat) {
        if (Objects.isNull(lng) || Objects.isNull(lat)) {
            return null;
        }
        return "Point(" + lng + " " + lat + ")";
    }

    public static List<List<Double>> transfer2List(String shape) {
        if (StringUtils.isBlank(shape)) {
            return null;
        }
        shape = shape.replace("MULTIPOLYGON(((", "");
        shape = shape.replace(")))", "");
        String[] array = shape.split(",");
        List<List<Double>> list = Lists.newArrayList();
        for (int i = 0; i < array.length; i++) {
            List<Double> point = new ArrayList<>(2);
            String p1 = array[i];
            String[] p = p1.split(" ");
            point.add(Double.valueOf(p[0]));
            point.add(Double.valueOf(p[1]));
            list.add(point);
        }
        System.out.println(list);
        return list;
    }

    public static String wkt2String(String wkt) {
        if (StringUtils.isBlank(wkt)) {
            return null;
        }
        wkt = wkt.replace("MULTIPOLYGON(((", "");
        wkt = wkt.replace(")))", "");
        return wkt;
    }


}
