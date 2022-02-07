package com.longbig.multifunction.controller;

import com.longbig.multifunction.helper.OkHttpUtils;
import com.longbig.multifunction.model.entity.City;
import com.longbig.multifunction.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuyunlong
 * @date 2020/12/28 8:21 下午
 * @description
 */
@RestController
@Slf4j
public class UtilController {

    @GetMapping("/print")
    @CrossOrigin
    public City print() {
        City city = new City();
        city.setName("北京");
        city.setAdcode(100000);
        city.setId(1L);
        return city;
    }

    @GetMapping("/secSkill")
    public String secSkill(@RequestParam("id") Integer id,
                           @RequestParam("pid") Integer pid,
                           @RequestParam("month") String month) throws Exception{
        String act = "GetCustSubscribeDateAll";
//        log.info("id:{} pid:{} month:{}", id, pid, month);
        return OkHttpUtils.get("https://cloud.cn2030.com?act=GetCustSubscribeDateAll" +
                "&pid=105&id=6372&month=202110");

    }

}
