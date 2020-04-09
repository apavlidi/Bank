package domain;

public class Transaction {

  public int id;
  public final int amount;
  public final String date;

  public Transaction(String date, int amount) {
    this.amount = amount;
    this.date = date;
  }

  public Transaction(int id,String date, int amount) {
    this.id = id;
    this.amount = amount;
    this.date = date;
  }

}
