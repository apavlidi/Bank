package service;

import static java.util.Collections.reverse;

import java.util.ArrayList;
import java.util.List;
import ui.Console;

public class AccountService {

  private Console console;
  private Clock clock;
  private List<String> transactions;
  private int balance;

  public AccountService(Console console, Clock clock) {
    this.console = console;
    this.clock = clock;
    transactions = new ArrayList<>();
  }

  public void deposit(int amount) {
    balance += amount;
    persist(amount);
  }

  public void withdraw(int amount) {
    balance -= amount;
    persist(-amount);
  }

  private void persist(int amount) {
    transactions.add(clock.getCurrentDate() + " || " + amount + "   || " + balance + "\n");
  }

  public void printStatement() {
    printHeader();
    printAllTransactions();
  }

  private void printAllTransactions() {
    List<String> transactionsCopy = new ArrayList<>(transactions);
    reverse(transactionsCopy);
    transactionsCopy.forEach(console::print);
  }

  private void printHeader() {
    console.print("Date       || Amount || Balance\n");
  }

}
