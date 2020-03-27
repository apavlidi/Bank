package domain;

public class Transaction {

  public final int amount;
  public final String date;

  public Transaction(String date, int amount) {
    this.amount = amount;
    this.date = date;
  }
}
