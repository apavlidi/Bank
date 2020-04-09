package infrastructure.redis;

import config.RedisConstants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionSingleton {

  private static final Jedis jedis = establishConnection();

  private RedisConnectionSingleton() {
  }

  public static Jedis getInstance() {
    return jedis;
  }

  private static Jedis establishConnection() {
    JedisPoolConfig poolCfg = new JedisPoolConfig();
    JedisPool jedisPool = new JedisPool(poolCfg, RedisConstants.HOSTNAME, RedisConstants.PORT, RedisConstants.TIMEOUT,
        RedisConstants.PASSWORD, RedisConstants.SSL);
    return jedisPool.getResource();
  }

}
