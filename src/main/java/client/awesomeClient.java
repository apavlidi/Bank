package client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class awesomeClient {

  public static void main(String[] args) {
    JedisPoolConfig poolCfg = new JedisPoolConfig();
    poolCfg.setMaxTotal(3);

    JedisPool jedisPool = new JedisPool(poolCfg, "redis-10058.c92.us-east-1-3.ec2.cloud.redislabs.com", 10058, 500,
        "3nFCjSBY2guurmwAxCRWbSsa7FdOGbgj", false);
    try (Jedis jedis = jedisPool.getResource()) {
      System.out.println("hi");
      jedis.set("key1", "some value");
      System.out.println(jedis.get("key1"));
    }

  }

}
