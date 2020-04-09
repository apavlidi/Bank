package infrastructure;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import domain.Transaction;
import infrastructure.kafka.ConsumerSingleton;
import infrastructure.kafka.KafkaTransactionRepository;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.Clock;

@ExtendWith(MockitoExtension.class)
class KafkaTransactionRepositoryTest {

  public static final String TODAY = "10/10/2020";

  @Mock
  private Clock clock;

  private KafkaTransactionRepository kafkaTransactionRepository;
  private static Consumer<Long, String> consumer;

  @BeforeEach
  void setUp() {
    kafkaTransactionRepository = new KafkaTransactionRepository(clock);
    consumer = ConsumerSingleton.getInstance();
    given(clock.getCurrentDate()).willReturn(TODAY);
  }

  @AfterAll
  static void tearDown() {
    consumer.close();
  }

  @Test
  public void create_and_push_a_deposit_transaction() {
    int amount = 100;
    kafkaTransactionRepository.addDeposit(amount);

    List<Transaction> transactions = kafkaTransactionRepository.getAll();

    assertEquals(1, transactions.size());
    assertEquals(amount, transactions.get(0).amount);
    assertEquals(TODAY, transactions.get(0).date);
  }

  @Test
  public void create_and_push_a_withdraw_transaction() {
    int amount = 100;
    kafkaTransactionRepository.addWithdraw(amount);

    List<Transaction> transactions = kafkaTransactionRepository.getAll();

    assertEquals(1, transactions.size());
    assertEquals(-amount, transactions.get(0).amount);
    assertEquals(TODAY, transactions.get(0).date);
  }

  @Test
  public void retrieve_all_transaction_messages_from_kafka() {
    int amount = 100;
    kafkaTransactionRepository.addWithdraw(amount);

    List<Transaction> transactions = kafkaTransactionRepository.getAll();

    assertEquals(1, transactions.size());
    assertEquals(-amount, transactions.get(0).amount);
    assertEquals(TODAY, transactions.get(0).date);
  }


}