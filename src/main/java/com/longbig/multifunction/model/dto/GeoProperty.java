package com.longbig.multifunction.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/2 4:33 下午
 * @description
 */
@Data
public class GeoProperty {
    /**
     * 区编码
     */
    private Integer adcode;
    private String name;
    private List<Double> center;
    private List<Double> centroid;
    private Integer childrenNum;
    private String level;
    private GeoParentDTO parent;
    private Integer subFeatureIndex;
    private List<Integer> acroutes;

}
