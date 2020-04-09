package service;


import repository.TransactionRepository;
import ui.PrinterStatement;

public class AccountService {

  private TransactionRepository transactionRepository;
  private PrinterStatement printerStatement;

  public AccountService(TransactionRepository transactionRepository, PrinterStatement printerStatement) {
    this.transactionRepository = transactionRepository;
    this.printerStatement = printerStatement;
  }

  public void deposit(int amount) {
    transactionRepository.addDeposit(amount);
  }

  public void withdraw(int amount) {
    transactionRepository.addWithdraw(amount);
  }

  public void printStatement() {
    printerStatement.print(transactionRepository.getAll());
  }
}
