package com.longbig.multifunction.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/2 4:31 下午
 * @description
 */
@Data
public class GeometryDTO {
    private String type;
    private GeoProperty properties;
    private Geometry geometry;
}
