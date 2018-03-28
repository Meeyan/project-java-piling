package com.std.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 使用集群-redis
 * @author zhaojy
 * @create-time 2018-03-28
 */
public class D2_RedisClusterDemo {

    public static void main(String[] args) throws IOException {
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        jedisClusterNode.add(new HostAndPort("192.168.199.248", 7001));
        jedisClusterNode.add(new HostAndPort("192.168.199.248", 7002));
        jedisClusterNode.add(new HostAndPort("192.168.199.248", 7003));
        jedisClusterNode.add(new HostAndPort("192.168.199.248", 7004));
        jedisClusterNode.add(new HostAndPort("192.168.199.248", 7005));
        jedisClusterNode.add(new HostAndPort("192.168.199.248", 7006));

        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(100);
        cfg.setMaxIdle(20);
        cfg.setMaxWaitMillis(-1);
        cfg.setTestOnBorrow(true);

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNode, 6000, 100, cfg); // 节点，失效时间，最多尝试，连接池
        System.out.println(jedisCluster.set("sex", "male"));
        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("sex"));

        jedisCluster.close();

    }

}
