package com.longbig.multifunction.rtree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.google.common.collect.Lists;
import com.longbig.multifunction.helper.GeoHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuyunlong
 * @date 2022/3/10 8:40 上午
 * @description
 */
public class RTreeService {
    //北京市东城区和西城区的边界
    private String dongcheng = "MULTIPOLYGON(((116.38059 39.871148,116.399097 39.872205,116.397612 39.898675,116.396086 39.89944,116.395563 39.907995,116.392259 39.907881,116.392175 39.92242,116.399474 39.923574,116.396692 39.928306,116.396169 39.94006,116.394266 39.940629,116.393346 39.957355,116.38678 39.957014,116.387658 39.96093,116.389498 39.96314,116.40788 39.962182,116.407504 39.973995,116.411101 39.97146,116.411415 39.964928,116.414196 39.962182,116.424861 39.962279,116.429002 39.957274,116.429483 39.950155,116.436698 39.949245,116.435422 39.952121,116.442239 39.9497,116.440566 39.945295,116.446338 39.946205,116.443703 39.936663,116.443682 39.928664,116.434314 39.92868,116.434983 39.913964,116.436488 39.902042,116.448722 39.903246,116.446819 39.900042,116.447154 39.894186,116.450876 39.894088,116.450939 39.890249,116.444059 39.890038,116.445648 39.879283,116.44364 39.87284,116.442574 39.87188,116.423209 39.872824,116.413652 39.871148,116.41589 39.863645,116.41246 39.858942,116.406856 39.859967,116.3955 39.858682,116.394956 39.862734,116.387888 39.867372,116.380632 39.866054,116.38059 39.871148,116.38059 39.871148)))";
    private String xicheng = "MULTIPOLYGON(((116.387658 39.96093,116.38678 39.957014,116.393346 39.957355,116.394266 39.940629,116.396169 39.94006,116.396692 39.928306,116.399474 39.923574,116.392175 39.92242,116.392259 39.907881,116.395563 39.907995,116.396086 39.89944,116.397612 39.898675,116.399097 39.872205,116.38059 39.871148,116.35058 39.86869,116.349472 39.873588,116.344286 39.873653,116.341567 39.876159,116.335273 39.875183,116.326636 39.876859,116.321324 39.875199,116.320759 39.881512,116.32582 39.891111,116.325799 39.896789,116.337301 39.89739,116.335356 39.898448,116.334645 39.922664,116.333056 39.938565,116.327953 39.942369,116.332889 39.944092,116.341442 39.941979,116.35171 39.94375,116.351814 39.950854,116.355265 39.951796,116.35698 39.944466,116.371974 39.948594,116.370384 39.967902,116.380401 39.968178,116.380903 39.972712,116.394099 39.972858,116.394162 39.969397,116.390084 39.968406,116.387658 39.96093,116.387658 39.96093)))";
    private RTree<String, Rectangle> tree = RTree.minChildren(3).maxChildren(6).create();
    private Map<String, String> localShapeCache = new ConcurrentHashMap<>();

    public void build() {
        //0. 构建mock数据，这里只是把上面两条多边形封装到对象里
        List<CityDTO> sourceData = buildCityDTOs();
        for (CityDTO sourceDatum : sourceData) {
            //1. 对每个多边形,获取对应的外接矩形，存入R树
            tree = tree.add(sourceDatum.getName(), buildRectFromWkt(sourceDatum.getShape()));
            //2. 将每个多边形的数据也存到内存中
            localShapeCache.put(sourceDatum.getName(), sourceDatum.getShape());
        }
    }

    /**
     * 输入点坐标，查询命中的多边形name
     * @param lng
     * @param lat
     * @return
     */
    public String search(Double lng, Double lat) {
        Point point = Geometries.point(lng, lat);
        //r树粗筛一遍
        Iterator<Entry<String, Rectangle>> iterator = tree.search(point).toBlocking().toIterable().iterator();
        //射线法对粗筛的多边形精确计算
        while (iterator.hasNext()) {
            Entry<String, Rectangle> entry = iterator.next();
            String name = entry.value();
            //获取多边形wkt
            String wkt = localShapeCache.get(name);
            //射线法判断
            PointDTO p = new PointDTO();
            p.setLng(lng);
            p.setLat(lat);
            if (isInPolygon(p, GeoHelper.transfer2List(wkt))) {
                return name;
            }
        }
        return null;
    }

    /**
     * 射线法判断点是否在多边形内
     * @param pointDTO
     * @param polygon
     * @return
     */
    private boolean isInPolygon(PointDTO pointDTO, List<List<Double>> polygon) {
        int nCross = 0;
        for (int i = 0; i < polygon.size(); i++) {
            List<Double> p1 = polygon.get(i);
            List<Double> p2 = polygon.get((i + 1) % polygon.size());
            Double lng1 = p1.get(0);
            Double lat1 = p1.get(1);
            Double lng2 = p2.get(0);
            Double lat2 = p2.get(1);
            //p1p2 与 y = p0.y平行
            if (lng1.equals(lng2)) {
                continue;
            }
            //交点在p1p2的延长线上
            if (pointDTO.getLng() < Math.min(lng1, lng2)) {
                continue;
            }
            //交点在p1p2的延长线上
            if (pointDTO.getLng() >= Math.max(lng1, lng2)) {
                continue;
            }
            // 求交点的X坐标
            double x = (pointDTO.getLng() - lng1) * (lat2 - lat1) / (lng2 - lng1) + lat1;
            if (x > pointDTO.getLat()) {
                //只统计单边
                nCross++;
            }
        }
        //单边交点为奇数，点在多边形内
        return (nCross % 2 == 1);
    }

    /**
     * 获取多边形的外接矩形
     * @param wkt
     * @return
     */
    public Rectangle buildRectFromWkt(String wkt) {
        double minLng = 180.00;
        double minLat = 90;
        double maxLng = -180.00;
        double maxLat = -90.00;
        //wkt格式数据转为点 list
        List<List<Double>> polygon = GeoHelper.transfer2List(wkt);
        for (List<Double> points : polygon) {
            Double lng = points.get(0);
            Double lat = points.get(1);
            if (lng < minLng) {
                minLng = lng;
            }
            if (lng > maxLng) {
                maxLng = lng;
            }
            if (lat < minLat) {
                minLat = lat;
            }
            if (lat > maxLat) {
                maxLat = lat;
            }
        }
        return Geometries.rectangle(minLng, minLat, maxLng, maxLat);
    }



    private List<CityDTO> buildCityDTOs() {
        List<CityDTO> sourceData = Lists.newArrayList();
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("dongcheng");
        cityDTO.setShape(dongcheng);
        sourceData.add(cityDTO);
        CityDTO cityDTO1 = new CityDTO();
        cityDTO1.setShape(xicheng);
        cityDTO1.setName("xicheng");
        sourceData.add(cityDTO1);
        return sourceData;
    }

}
