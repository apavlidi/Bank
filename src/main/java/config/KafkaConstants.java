package config;

public interface KafkaConstants {

  String KAFKA_BROKERS = "34.76.156.36:9092";
  String CLIENT_ID = "client";
  String TOPIC_NAME = "bankTest";
  public static Integer MESSAGE_COUNT = 1000;
  public static String GROUP_ID_CONFIG = "consumerGroup1";
  public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;
  public static String OFFSET_RESET_LATEST = "latest";
  public static String OFFSET_RESET_EARLIER = "earliest";
  public static Integer MAX_POLL_RECORDS = 1;
}
