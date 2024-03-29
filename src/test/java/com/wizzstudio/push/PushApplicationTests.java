package com.wizzstudio.push;

import com.wizzstudio.push.dao.UserDao;
import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.utils.RedisUtils;
import com.wizzstudio.push.utils.TextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Map map = redisUtils.hKeys("name-id");
        Map nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+map);
        System.out.println("nameAbles:"+nameAbles);
        boolean b = (boolean) nameAbles.get("lcy1");
        System.out.println(b);
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
        String convert = TextUtils.convertList(strings);
        System.out.println(convert);
    }

    @Test
    void testRedis06(){
        redisUtils.hset("name-able","lcy1",true);
        redisUtils.hset("name-able","lcy2",true);
        redisUtils.hset("name-able","lcy3",true);
        redisUtils.hset("name-able","lcy4",false);
        redisUtils.hset("name-able","lcy5",false);
        redisUtils.hset("name-id","lcy1","LiuChaoYang");
        redisUtils.hset("name-id","lcy2","LiuChaoYang");
        redisUtils.hset("name-id","lcy3","LiuChaoYang");
        redisUtils.hset("name-id","lcy4","LiuChaoYang");
        redisUtils.hset("name-id","lcy5","LiuChaoYang");
        Map map = redisUtils.hKeys("name-id");
        Map<String, Object> nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+map);
        System.out.println("nameAbles:"+nameAbles);
        boolean b  = (boolean) nameAbles.get("lcy1");
        System.out.println(b);
    }

    @Test
    void testRedis07(){
        redisUtils.hset("key","field",1);
        int back = (int) redisUtils.hget("key", "field");
        System.out.println(back);
    }

    @Autowired
    private UserDao userDao;

    @Test
    void testRedis08(){
        for (int i = 0; i < 5; i++) {
            userService.addNickname("LiuChaoYang","lcy"+i);
        }
        userDao.updateStatusByNickname("lcy3",false);
        userDao.updateStatusByNickname("lcy4",false);
        Map map = redisUtils.hKeys("name-id");
        Map nameAbles = redisUtils.hKeys("name-able");
        System.out.println("nameIds:"+map);
        System.out.println("nameAbles:"+nameAbles);
    }

    @Test
    void testRegex(){
        String[] name = new String[7];
         name[0] = "lcy123";
         name[1] = "lcy";
         name[2] = "12";
         name[3] = "l1";
         name[4] = "lcy12345678901241";
         name[5] = "lcy123456789";
         name[6] = "l";
        String pattern = "^[a-zA-Z0-9]{2,12}$";
        for (int i = 0; i < 7; i++) {
            boolean matches = name[i].matches(pattern);
            System.out.println(name[i]+":"+matches);
        }
    }
}
