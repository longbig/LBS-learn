//package com.longbig.multifunction.third;
//
//import com.longbig.multifunction.config.FeignConfig;
//import feign.Headers;
//import feign.Param;
//import feign.RequestLine;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
///**
// * 知苗易约 微信小程序
// */
//@FeignClient(name = "zhiMiaoYY", url = "", configuration = FeignConfig.class)
//public interface ZhiMiaoYiYue {
//
//    @RequestLine("GET /sc/wx/HandlerSubscribe.ashx")
//    String HandlerSubscribe(@Param("act") String act,
//                            @Param("id") Integer id,
//                            @Param("pid") Integer pid,
//                            @Param("month") String month);
//
//}
