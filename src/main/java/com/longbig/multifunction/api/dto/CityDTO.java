package com.longbig.multifunction.api.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/5 8:10 下午
 * @description
 */
@ToString
public class CityDTO {
    private String name;
    private List<List<Double>> shape;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<List<Double>> getShape() {
        return shape;
    }

    public void setShape(List<List<Double>> shape) {
        this.shape = shape;
    }

}
