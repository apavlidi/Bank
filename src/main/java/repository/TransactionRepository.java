package repository;

import domain.Transaction;
import java.util.ArrayList;
import java.util.List;
import utils.Clock;

public class TransactionRepository {

  private List<Transaction> transactions;
  private Clock clock;

  public TransactionRepository(Clock clock) {
    this.clock = clock;
    transactions = new ArrayList<>();
  }

  public void addDeposit(int amount) {
    transactions.add(createTransaction(amount));
  }

  public void addWithdraw(int amount) {
    transactions.add(createTransaction(-amount));
  }

  public List<Transaction> getAll() {
    return transactions;
  }

  private Transaction createTransaction(int amount) {
    return new Transaction(clock.getCurrentDate(), amount);
  }
}
