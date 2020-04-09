package infrastructure.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Transaction;
import domain.TransactionFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    try {
      RedisConnectionSingleton.getInstance().hset(transactionKey, String.valueOf(transaction.id),
          new ObjectMapper().writeValueAsString(transaction));
    } catch (JsonProcessingException e) {
      throw new RedisJsonSeriliazationException();
    }
  }

  @Override
  public void addWithdraw(int amount) {
    Transaction transaction = transactionFactory.create(clock.getCurrentDate(), -amount);
    try {
      RedisConnectionSingleton.getInstance().hset(transactionKey, String.valueOf(transaction.id),
          new ObjectMapper().writeValueAsString(transaction));
    } catch (JsonProcessingException e) {
      throw new RedisJsonSeriliazationException();
    }
  }

  @Override
  public List<Transaction> getAll() {
    ArrayList<Transaction> transactions = new ArrayList<>();
    Map<String, String> jsonTransactions = RedisConnectionSingleton.getInstance().hgetAll(transactionKey);

    for (String jsonTransaction : jsonTransactions.values()) {
      try {
        transactions.add(Transaction.createFromJson(jsonTransaction));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return transactions;
  }
}
