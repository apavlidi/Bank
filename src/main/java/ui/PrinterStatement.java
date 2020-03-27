package ui;

import static java.util.Collections.reverse;

import domain.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PrinterStatement {

  private Console console;

  public PrinterStatement(Console console) {
    this.console = console;
  }

  public void print(List<Transaction> transactions) {
    printHeader();
    printTransactionsInReverse(transactions);
  }

  private void printTransactionsInReverse(List<Transaction> transactions) {
    List<String> statements = createStatements(transactions);
    reverse(statements);
    statements.forEach(console::print);
  }

  private List<String> createStatements(List<Transaction> transactions) {
    AtomicInteger balance = new AtomicInteger(0);
    return transactions.stream().map(t -> createStatementLine(balance, t)).collect(Collectors.toList());
  }

  private String createStatementLine(AtomicInteger balance, Transaction t) {
    balance.addAndGet(t.amount);
    return t.date +
        " || " +
        t.amount +
        "   || " +
        balance +
        "\n";
  }

  private void printHeader() {
    console.print("Date       || Amount || Balance\n");
  }
}
