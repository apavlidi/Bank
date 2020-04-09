package infrastructure.kafka;

import config.KafkaConstants;
import domain.Transaction;
import infrastructure.kafka.exceptions.KafkaSendException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerRecord;
import repository.TransactionRepository;
import utils.Clock;

public class KafkaTransactionRepository implements TransactionRepository {

  private static final int MAX_NO_MESSAGE_FOUND_COUNT = 100;

  private Clock clock;

  //TODO Try injecting singleton to constructor
  public KafkaTransactionRepository(Clock clock) {
    this.clock = clock;
  }

  @Override
  public void addDeposit(int amount) {
    ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_NAME,
        createTransactionEntry(amount));
    try {
      ProducerSingleton.getInstance().send(record).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new KafkaSendException();
    }

  }

  @Override
  public void addWithdraw(int amount) {
    try {
      ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_NAME,
          createTransactionEntry(-amount));
      ProducerSingleton.getInstance().send(record).get();
    } catch (ExecutionException | InterruptedException e) {
      throw new KafkaSendException();
    }

  }

  @Override
  public List<Transaction> getAll() {
    List<Transaction> transactions = new ArrayList<>();
    int noMessageFound = 0;

    do {
      ConsumerRecords<Long, String> consumerRecords = ConsumerSingleton.getInstance().poll(Duration.of(10, ChronoUnit.MILLIS));
      if (consumerRecords.isEmpty()) {
        noMessageFound++;
      } else {
        appendTransaction(transactions, consumerRecords);
      }
    } while (noMessageFound <= MAX_NO_MESSAGE_FOUND_COUNT);

    ConsumerSingleton.getInstance().commitAsync();
    return transactions;
  }

  private void appendTransaction(List<Transaction> transactions, ConsumerRecords<Long, String> consumerRecords) {
    consumerRecords.forEach(record -> {
      String[] split = record.value().split(" \\|\\| ");
      transactions.add(new Transaction(split[0], Integer.parseInt(split[1])));
    });
  }

  private String createTransactionEntry(int amount) {
    return clock.getCurrentDate() + " || " + amount;
  }
}
