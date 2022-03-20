package com.longbig.multifunction.helper;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

/**
 * @author yuyunlong
 * @date 2022/3/20 6:08 下午
 * @description
 */
@Slf4j
public class GeoRedisHelper {
    private static JedisPool jedisPool;

    //    public JedisPool getJedisPool() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxWaitMillis(10000);
//        jedisPoolConfig.setMaxTotal(100);
//        jedisPoolConfig.setMaxIdle(10);
//        return jedisPool;
//    }
    static {
        jedisPool = new JedisPool("localhost", 6379);
    }

    /**
     * 新增经纬度元素
     * @param key
     * @param lng
     * @param lat
     * @param member
     * @return
     */
    public static long geoadd(String key, Double lng, Double lat, String member) {
        Jedis jedis = jedisPool.getResource();
        try {
            long total = jedis.geoadd(key, lng, lat, member);
            return total;
        } catch (Exception e) {
            log.error("geoadd error, e={}", e);
        }
        return -1;
    }

    /**
     * 圆形范围查询,单位：km
     * @param key
     * @param lng
     * @param lat
     * @param radius
     * @return
     */
    public static List<GeoRadiusResponse> geosearchByRadius(String key, Double lng, Double lat, Double radius) {
        Jedis jedis = jedisPool.getResource();
        try {
            GeoSearchParam param = GeoSearchParam.geoSearchParam()
                    .fromLonLat(lng, lat)
                    .byRadius(radius, GeoUnit.KM)
                    .withCoord()
                    .withHash()
                    .withDist()
                    .asc();
            List<GeoRadiusResponse> geoResponse = jedis.geosearch(key, param);
            return geoResponse;
        } catch (Exception e) {
            log.error("geoadd error, e={}", e);
        }
        return null;
    }

    /**
     * 矩形范围查询，单位：km
     * @param key
     * @param lng
     * @param lat
     * @param width
     * @param height
     * @return
     */
    public static List<GeoRadiusResponse> geosearchByBox(String key, Double lng, Double lat, Double width, Double height) {
        Jedis jedis = jedisPool.getResource();
        try {
            GeoSearchParam param = GeoSearchParam.geoSearchParam()
                    .fromLonLat(lng, lat)
                    .byBox(width, height, GeoUnit.KM)
                    .withCoord()
                    .withHash()
                    .withDist()
                    .asc();
            List<GeoRadiusResponse> geoResponse = jedis.geosearch(key, param);
            return geoResponse;
        } catch (Exception e) {
            log.error("geoadd error, e={}", e);
        }
        return null;
    }}
