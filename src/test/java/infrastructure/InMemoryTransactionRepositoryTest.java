package infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.TransactionRepository;
import utils.Clock;

@ExtendWith(MockitoExtension.class)
class InMemoryTransactionRepositoryTest {

  public static final String TODAY = "10/10/2020";
  @Mock
  private Clock clock;
  private TransactionRepository transactionRepository;

  @BeforeEach
  void setUp() {
    transactionRepository = new InMemoryTransactionRepository(clock);
    given(clock.getCurrentDate()).willReturn(TODAY);
  }

  @Test
  public void create_and_store_a_deposit_transaction() {
    transactionRepository.addDeposit(1000);

    assertEquals(1, transactionRepository.getAll().size());
    assertTransaction(transactionRepository.getAll().get(0), 1000);
  }

  @Test
  public void create_and_store_a_withdraw_transaction() {
    transactionRepository.addWithdraw(1000);

    assertEquals(1, transactionRepository.getAll().size());
    assertTransaction(transactionRepository.getAll().get(0), -1000);
  }

  private void assertTransaction(Transaction transaction, int expected) {
    assertEquals(TODAY, transaction.date);
    assertEquals(expected, transaction.amount);
  }

}