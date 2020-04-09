package domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;

public class Transaction implements Serializable {

  public int id;
  public int amount;
  public String date;

  public Transaction() {
  }

  public static Transaction createFromJson(String jsonString) throws IOException {
    return new ObjectMapper().readValue(jsonString, Transaction.class);
  }

  public Transaction(String date, int amount) {
    this.amount = amount;
    this.date = date;
  }

  public Transaction(int id, String date, int amount) {
    this.id = id;
    this.amount = amount;
    this.date = date;
  }

}
