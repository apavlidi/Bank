package infrastructure;

import domain.Transaction;
import java.util.ArrayList;
import java.util.List;
import repository.TransactionRepository;
import utils.Clock;

public class InMemoryTransactionRepository implements TransactionRepository {

  private List<Transaction> transactions;
  private Clock clock;

  public InMemoryTransactionRepository(Clock clock) {
    this.clock = clock;
    transactions = new ArrayList<>();
  }

  @Override
  public void addDeposit(int amount) {
    transactions.add(createTransaction(amount));
  }

  @Override
  public void addWithdraw(int amount) {
    transactions.add(createTransaction(-amount));
  }

  @Override
  public List<Transaction> getAll() {
    return transactions;
  }

  private Transaction createTransaction(int amount) {
    return new Transaction(clock.getCurrentDate(), amount);
  }
}
