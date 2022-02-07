package com.longbig.multifunction.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/2 4:37 下午
 * @description
 */
@Data
public class Geometry {
    private String type;
    private List<List<List<List<Double>>>> coordinates;
}
