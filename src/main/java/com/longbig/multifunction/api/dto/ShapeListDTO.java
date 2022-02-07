package com.longbig.multifunction.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/6 3:56 下午
 * @description
 */
@Data
public class ShapeListDTO {
    private Integer total;
    private List<String> shape;
}
