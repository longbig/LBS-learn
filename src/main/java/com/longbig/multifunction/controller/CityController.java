package com.longbig.multifunction.controller;

import com.longbig.multifunction.api.dto.CityDTO;
import com.longbig.multifunction.api.dto.PointDTO;
import com.longbig.multifunction.api.dto.ShapeListDTO;
import com.longbig.multifunction.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yuyunlong
 * @date 2022/2/2 6:32 下午
 * @description
 */
@RestController
@Slf4j
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/transferGeoData")
    public String transferGeoData(@RequestParam("path") String path) {
        log.info("transferGeoData start...");
        cityService.readCityFromJson(path);
        return "success";
    }

    /**
     * 经纬度查行政区
     * @param pointDTO
     * @return
     */
    @PostMapping("/queryByPoint")
    @CrossOrigin
    public CityDTO queryByPoint(@RequestBody PointDTO pointDTO) {
        log.info("queryByPoint,point:{}", pointDTO);
        return cityService.queryByPoint(pointDTO.getLng(), pointDTO.getLat());
    }

    @GetMapping("/queryByAdcode")
    @CrossOrigin
    public ShapeListDTO queryByAdcode(@RequestParam("adcode") Integer adcode) {
        log.info("queryByAdcode,adcode:{}", adcode);
        return cityService.queryByAdcode(adcode);
    }
}
