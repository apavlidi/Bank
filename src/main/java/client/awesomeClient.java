package client;

import config.KafkaConstants;
import infrastructure.kafka.ProducerSingleton;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class awesomeClient {

  public static void main(String[] args) {
//    Producer<String, String> producer = ProducerSingleton.getInstance();
//    for (int index = 0; index < 10; index++) {
//      try {
//        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_NAME,
//            "This is record " + index);
//        RecordMetadata metadata = producer.send(record).get();
//        System.out.println("Record sent with key " + index + " to partition " + metadata.partition()
//            + " with offset " + metadata.offset());
//      } catch (ExecutionException | InterruptedException e) {
//        System.out.println("Error in sending record");
//        System.out.println(e);
//      }
//    }

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
