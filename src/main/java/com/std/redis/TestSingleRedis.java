package com.std.redis;

import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

/**
 * redis测试
 *
 * @author zhaojy
 * @create-time 2018-03-26
 */
public class TestSingleRedis {
    private static Jedis jedis;     // 单独链接一台机器
    private static ShardedJedis shardedJedis;   // [分片模式]主从或者哨兵模式，3.0以前使用
    private static ShardedJedisPool pool;   // 连接池方式

    private final String REDIS_URL = "192.168.199.248";
    private final int REDIS_PORT = 6379;

    private String USER_TABLE = "USER_TABLE";
    private String SET_USER_AGE_25 = "SET_USER_AGE_25";
    private String SET_USER_SEX_MALE = "SET_USER_SEX_MALE";

    @Test
    public void singleTest() {
        jedis = new Jedis(REDIS_URL, REDIS_PORT);
        System.out.println(jedis);
        List<String> mget = jedis.mget("name", "age");
        for (String tmpStr : mget) {
            System.out.println(tmpStr);
        }

        jedis.set("sex", "male");

        Map<String, String> map = new HashMap<>();
        map.put("name", "John");
        map.put("sex", "male");
        map.put("age", "18");
        jedis.hmset("use.John", map);
    }

    /**
     * 需求：有500w的用户数据，放到了redis中，现在有个需求：
     * 要求从redis中查询出“age=25 and sex=male”的所有数据。
     * redis的数据结构怎么设计？
     */
    @Test
    public void test_2() {
        jedis = new Jedis(REDIS_URL, REDIS_PORT);

        List<String> userAgeList_25 = new ArrayList<>();
        List<String> useSexMaleList = new ArrayList<>();

        // 第一步，所有的数据
        Map<String, String> dataMap = new HashMap<>();
        String id_1 = UUID.randomUUID().toString();
        User user_1 = new User(id_1, "zhangsan", 18, "male");
        String userStr_1 = objToJson(user_1);

        dataMap.put(id_1, userStr_1);
        // male
        useSexMaleList.add(id_1);

        String id_2 = UUID.randomUUID().toString();
        User user_2 = new User(id_2, "lisi", 18, "male");
        String userStr_2 = objToJson(user_2);

        dataMap.put(id_2, userStr_2);
        useSexMaleList.add(id_2);

        String id_3 = UUID.randomUUID().toString();
        User user_3 = new User(id_3, "wangwu", 25, "male");
        String userStr_3 = objToJson(user_3);
        dataMap.put(id_3, userStr_3);

        useSexMaleList.add(id_3);
        userAgeList_25.add(id_3);

        String id_4 = UUID.randomUUID().toString();
        User user_4 = new User(id_4, "zhaoliu", 25, "male");
        String userStr_4 = objToJson(user_4);
        dataMap.put(id_4, userStr_4);
        useSexMaleList.add(id_4);
        userAgeList_25.add(id_4);

        String id_5 = UUID.randomUUID().toString();
        User user_5 = new User(id_5, "Lucy", 25, "female");
        String userStr_5 = objToJson(user_5);
        dataMap.put(id_5, userStr_5);
        userAgeList_25.add(id_4);

        String id_6 = UUID.randomUUID().toString();
        User user_6 = new User(id_6, "Wangcai", 18, "female");
        String userStr_6 = objToJson(user_6);
        dataMap.put(id_6, userStr_6);

        String[] ageStr = userAgeList_25.toArray(new String[]{});
        String[] sexMaleStrArray = useSexMaleList.toArray(new String[]{});

        // 各种数据结构要提前设计，可以利用各种操作，模拟mysql的查询
        jedis.sadd(SET_USER_AGE_25, ageStr);
        jedis.sadd(SET_USER_SEX_MALE, sexMaleStrArray);
        jedis.hmset(USER_TABLE, dataMap);
    }

    /**
     * 要求：查询USER_TABLE缓存中，性别为男，年龄为25岁的所有数据
     */
    @Test
    public void test_3() {
        jedis = new Jedis(REDIS_URL, REDIS_PORT);
        Set<String> sIds = jedis.sinter(SET_USER_AGE_25, SET_USER_SEX_MALE);    // 取交集

        for (Iterator<String> iterator = sIds.iterator(); iterator.hasNext(); ) {
            String uid = iterator.next();
            String userJson = jedis.hget(USER_TABLE, uid);
            System.out.println(userJson);
        }
    }


    public String objToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
