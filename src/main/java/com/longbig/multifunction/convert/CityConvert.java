package com.longbig.multifunction.convert;

import com.google.common.collect.Lists;
import com.longbig.multifunction.api.dto.CityDTO;
import com.longbig.multifunction.api.dto.ShapeListDTO;
import com.longbig.multifunction.helper.GeoHelper;
import com.longbig.multifunction.model.entity.CityWithBLOBs;
import org.mapstruct.*;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/2/5 8:12 下午
 * @description
 */
@Mapper(componentModel = "spring")
public abstract class CityConvert {

    @Mappings({
            @Mapping(source = "city.shape", target = "shape", ignore = true)
    })
    public abstract CityDTO city2DTO(CityWithBLOBs city);

    public ShapeListDTO citys2ListDTO(List<CityWithBLOBs> citys) {
        ShapeListDTO shapeListDTO = new ShapeListDTO();
        shapeListDTO.setTotal(citys.size());
        List<String> shapes = Lists.newArrayList();
        for (CityWithBLOBs city : citys) {
            shapes.add(GeoHelper.wkt2String(city.getShape()));
        }
        shapeListDTO.setShape(shapes);
        return shapeListDTO;
    }
}
