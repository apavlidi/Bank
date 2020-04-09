package domain;

public class TransactionFactory {

  public Transaction create(String date, int amount) {
    return new Transaction(UniqueIdGenerator.generate(), date, amount);
  }

  private static class UniqueIdGenerator {

    private static int sequence;

    public static int generate() {
      return sequence++;
    }
  }
}
