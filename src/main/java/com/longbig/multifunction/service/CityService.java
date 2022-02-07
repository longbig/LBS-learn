package com.longbig.multifunction.service;

import com.google.common.collect.Lists;
import com.longbig.multifunction.api.dto.CityDTO;
import com.longbig.multifunction.api.dto.ShapeListDTO;
import com.longbig.multifunction.convert.CityConvert;
import com.longbig.multifunction.helper.FileUtils;
import com.longbig.multifunction.helper.GeoHelper;
import com.longbig.multifunction.helper.JacksonHelper;
import com.longbig.multifunction.mapper.generator.CityMapper;
import com.longbig.multifunction.model.dto.GeoJsonDTO;
import com.longbig.multifunction.model.dto.GeoProperty;
import com.longbig.multifunction.model.dto.Geometry;
import com.longbig.multifunction.model.dto.GeometryDTO;
import com.longbig.multifunction.model.entity.City;
import com.longbig.multifunction.model.entity.CityWithBLOBs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author yuyunlong
 * @date 2022/2/2 3:36 下午
 * @description
 */
@Service
@Slf4j
public class CityService {

    @Autowired
    private CityMapper cityMapper;
    @Autowired
    private CityConvert cityConvert;


    public void readCityFromJson(String path) {
        List<String> files = FileUtils.getFiles(path);
        if (CollectionUtils.isEmpty(files)) {
            log.warn("path:{} is empty", path);
            return;
        }
        for (String file : files) {
            readAndInsert2DB(file);
        }
        log.info("readCityFromJson:{} success", path);
    }

    private void readAndInsert2DB(String fileName) {
        log.info("read file:{}", fileName);
        List<String> strings = FileUtils.readFileToStringList(fileName);
        GeoJsonDTO geoJsonDTO = null;
        for (String json : strings) {
            try {
                geoJsonDTO = JacksonHelper.asObject(json, GeoJsonDTO.class);
                List<GeometryDTO> features = geoJsonDTO.getFeatures();
                for (GeometryDTO geometryDTO : features) {
                    GeoProperty geoProperty = geometryDTO.getProperties();
                    Geometry geometry = geometryDTO.getGeometry();
                    List<List<List<List<Double>>>> coordinates = geometry.getCoordinates();
                    List<List<Double>> shape = coordinates.get(0).get(0);
                    CityWithBLOBs city = new CityWithBLOBs();
                    city.setName(geoProperty.getName());
                    city.setAdcode(geoProperty.getAdcode());
                    city.setShape(GeoHelper.list2Wkt(shape));
                    cityMapper.insertSelective(city);
                    log.info("insert city:{}", city.getName());
                }
            } catch (Exception e) {
                log.error("error,fileName:{}", fileName);
                continue;
            }
        }
    }

    public CityDTO queryByPoint(Double lng, Double lat) {
        String wkt = GeoHelper.point2Wkt(lng, lat);
        CityWithBLOBs city = cityMapper.queryByPoint(wkt);
        if (Objects.isNull(city)) {
            return null;
        }
        CityDTO cityDTO = cityConvert.city2DTO(city);
        cityDTO.setShape(GeoHelper.transfer2List(city.getShape()));
        return cityDTO;
    }

    public ShapeListDTO queryByAdcode(Integer adcode) {
        List<CityWithBLOBs> citys = cityMapper.queryByAdcode(adcode);
        if (CollectionUtils.isEmpty(citys)) {
            return null;
        }
        ShapeListDTO result = cityConvert.citys2ListDTO(citys);
        return result;
    }
}
