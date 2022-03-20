package com.longbig.multifunction.controller;

import com.longbig.multifunction.api.dto.GeoDTO;
import com.longbig.multifunction.helper.GeoRedisHelper;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/3/20 6:27 下午
 * @description
 */
@RestController
public class GeoController {

    private final String GEO_KEY = "China";

    @PostMapping("/geo/add")
    public Long addGeo(@RequestBody GeoDTO geoDTO) {
        return GeoRedisHelper.geoadd(GEO_KEY, geoDTO.getLng(), geoDTO.getLat(), geoDTO.getName());
    }

    @GetMapping("/geo/searchByRadius")
    public List<GeoRadiusResponse> searchGeoByRadius(
            @RequestParam("lng") Double lng,
            @RequestParam("lat") Double lat,
            @RequestParam("radius") Double radius) {
        return GeoRedisHelper.geosearchByRadius(GEO_KEY, lng, lat, radius);
    }

    @GetMapping("/geo/searchByBox")
    public List<GeoRadiusResponse> searchGeoByBox(
            @RequestParam("lng") Double lng,
            @RequestParam("lat") Double lat,
            @RequestParam("width") Double width,
            @RequestParam("height") Double heigh) {
        return GeoRedisHelper.geosearchByBox(GEO_KEY, lng, lat, width, heigh);
    }
}
