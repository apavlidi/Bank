package repository;

import domain.Transaction;
import java.util.List;

public interface TransactionRepository {

  void addDeposit(int amount);

  void addWithdraw(int amount);

  List<Transaction> getAll();
}
