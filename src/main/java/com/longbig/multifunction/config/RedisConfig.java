package com.longbig.multifunction.config;//package com.longbig.multifunction.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @author yuyunlong
// * @date 2022/3/20 5:57 下午
// * @description
// */
//@Configuration
//public class RedisConfig {
//
//    @Bean("jedisPool2")
//    @Primary
//    public JedisPool getJedisPool() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxWaitMillis(10000);
//        jedisPoolConfig.setMaxTotal(100);
//        jedisPoolConfig.setMaxIdle(10);
//        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379);
//        return jedisPool;
//    }
//
//}
