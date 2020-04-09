package infrastructure;

import static infrastructure.redis.RedisTransactionRepository.transactionKey;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

import domain.Transaction;
import domain.TransactionFactory;
import infrastructure.redis.RedisConnectionSingleton;
import infrastructure.redis.RedisTransactionRepository;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import utils.Clock;

@ExtendWith(MockitoExtension.class)
public class RedisTransactionRepositoryTest {

  public static final String TODAY = "10/10/2020";

  @Mock
  private Clock clock;

  @Mock
  private TransactionFactory transactionFactory;

  private RedisTransactionRepository redisTransactionRepository;

  private static Jedis redis;

  @BeforeEach
  void setUp() {
    redisTransactionRepository = new RedisTransactionRepository(clock, transactionFactory);
    given(clock.getCurrentDate()).willReturn(TODAY);
    redis = RedisConnectionSingleton.getInstance();
  }

  @AfterAll
  static void tearDown() {
    RedisConnectionSingleton.getInstance().del(transactionKey);
    redis.disconnect();
    redis.close();
  }

  @Test
  public void add_deposit_transaction_to_redis() throws IOException {
    int amount = 100;
    Transaction transaction = new Transaction(1, TODAY, amount);
    given(transactionFactory.create(TODAY, amount)).willReturn(transaction);

    redisTransactionRepository.addDeposit(amount);

    assertRedisValue(transaction);
  }

  @Test
  public void add_withdraw_transaction_to_redis() throws IOException {
    int amount = 100;
    Transaction transaction = new Transaction(1, TODAY, -amount);
    given(transactionFactory.create(TODAY, -amount)).willReturn(transaction);

    redisTransactionRepository.addWithdraw(amount);

    assertRedisValue(transaction);
  }

  @Test
  public void get_all_transactions_from_redis() throws IOException {
    int amount = 100;
    Transaction transaction = new Transaction(1, TODAY, -amount);
    given(transactionFactory.create(TODAY, -amount)).willReturn(transaction);

    redisTransactionRepository.addWithdraw(amount);
    List<Transaction> receivedTransactions = redisTransactionRepository.getAll();

    for (Transaction receivedTransaction : receivedTransactions) {
      assertRedisValue(receivedTransaction);
    }
  }

  private void assertRedisValue(Transaction transaction) throws IOException {
    String transactionJson = RedisConnectionSingleton.getInstance().hget(transactionKey, String.valueOf(transaction.id));
    Transaction retrievedTransaction = Transaction.createFromJson(transactionJson);
    assertEquals(retrievedTransaction.date, transaction.date);
    assertEquals(retrievedTransaction.amount, transaction.amount);
    assertEquals(retrievedTransaction.id, transaction.id);
  }

}
