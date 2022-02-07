package com.longbig.multifunction.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/2 4:30 下午
 * @description
 */
@Data
public class GeoJsonDTO {
    private String type;
    private List<GeometryDTO> features;
}
