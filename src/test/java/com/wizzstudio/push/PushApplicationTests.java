package com.wizzstudio.push;

import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.utils.RedisUtils;
import com.wizzstudio.push.utils.TextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
        redisUtils.hset("name-able","lcy1",String.valueOf(true));
        redisUtils.hset("name-able","lcy2",String.valueOf(true));
        redisUtils.hset("name-able","lcy3",String.valueOf(true));
        redisUtils.hset("name-able","lcy4",String.valueOf(false));
        redisUtils.hset("name-able","lcy5",String.valueOf(false));
        Map<String, Object> nameIds = redisUtils.hKeys("name-id");
        Map<String, Object> nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+nameIds);
        System.out.println("nameAbles:"+nameAbles);
    }

    @Autowired
    private UserService userService;

    @Test
    void testTextUtils(){
        List<String> strings = userService.listNicknamesAble("lcy");
        String convert = TextUtils.convert(strings);
        System.out.println(convert);
    }

    @Test
    void testRedis06(){
        redisUtils.hset("name-able","lcy1",String.valueOf(true));
        redisUtils.hset("name-able","lcy2",String.valueOf(true));
        redisUtils.hset("name-able","lcy3",String.valueOf(true));
        redisUtils.hset("name-able","lcy4",String.valueOf(false));
        redisUtils.hset("name-able","lcy5",String.valueOf(false));
        redisUtils.hset("name-id","lcy1","LiuChaoYang");
        redisUtils.hset("name-id","lcy2","LiuChaoYang");
        redisUtils.hset("name-id","lcy3","LiuChaoYang");
        redisUtils.hset("name-id","lcy4","LiuChaoYang");
        redisUtils.hset("name-id","lcy5","LiuChaoYang");
        Map map = redisUtils.hKeys("name-id");
        Map<String, Object> nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+map);
        System.out.println("nameAbles:"+nameAbles);
    }

}
