package infrastructure.redis;

import domain.Transaction;
import domain.TransactionFactory;
import java.util.List;
import redis.clients.jedis.Jedis;
import repository.TransactionRepository;
import utils.Clock;

public class RedisTransactionRepository implements TransactionRepository {

  private Clock clock;
  private TransactionFactory transactionFactory;
  public static final String transactionKey = "transactions";

  public RedisTransactionRepository(Clock clock, TransactionFactory transactionFactory) {
    this.clock = clock;
    this.transactionFactory = transactionFactory;
  }

  @Override
  public void addDeposit(int amount) {
    Transaction transaction = transactionFactory.create(clock.getCurrentDate(), amount);
    RedisConnectionSingleton.getInstance().hset(transactionKey,String.valueOf(transaction.id), transaction.date + " || " + transaction.amount);
  }

  @Override
  public void addWithdraw(int amount) {
    Transaction transaction = transactionFactory.create(clock.getCurrentDate(), -amount);
    RedisConnectionSingleton.getInstance().hset(transactionKey,String.valueOf(transaction.id), transaction.date + " || " + transaction.amount);
  }

  @Override
  public List<Transaction> getAll() {
//    RedisConnectionSingleton.getInstance().();
    return null;
  }
}
