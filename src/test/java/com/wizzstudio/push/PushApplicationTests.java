package com.wizzstudio.push;

import com.wizzstudio.push.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PushApplicationTests {

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads() {
    }

    @Test
    void testRedis01(){
        redisUtils.set("name","lcy");
        System.out.println(redisUtils.get("name"));
    }



    @Test
    void testRedis04(){
        Map<String, Object> nameIds = redisUtils.hKeys("name-id");
        Map<String, Object> nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+nameIds);
        System.out.println("nameAbles:"+nameAbles);
    }

    @Test
    void testRedis05(){
        redisUtils.hset("name-able","lcy4",false);
        redisUtils.hset("name-able","lcy5",false);
        Map<String, Object> nameIds = redisUtils.hKeys("name-id");
        Map<String, Object> nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+nameIds);
        System.out.println("nameAbles:"+nameAbles);
    }

}
