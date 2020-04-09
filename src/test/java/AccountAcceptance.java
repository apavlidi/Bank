import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import infrastructure.InMemoryTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AccountService;
import ui.Console;
import ui.PrinterStatement;
import utils.Clock;

@ExtendWith(MockitoExtension.class)
public class AccountAcceptance {

  @Mock
  private Console console;

  @Mock
  private Clock clock;

  @Test
  public void print_all_transaction_statements() {
    given(clock.getCurrentDate()).willReturn("10/01/2012", "13/01/2012", "14/01/2012");
    AccountService accountService = new AccountService(new InMemoryTransactionRepository(clock), new PrinterStatement(console));

    accountService.deposit(1000);
    accountService.deposit(2000);
    accountService.withdraw(500);

    accountService.printStatement();

    InOrder inOrder = Mockito.inOrder(console);
    inOrder.verify(console).print("Date       || Amount || Balance\n");
    inOrder.verify(console).print("14/01/2012 || -500   || 2500\n");
    inOrder.verify(console).print("13/01/2012 || 2000   || 3000\n");
    inOrder.verify(console).print("10/01/2012 || 1000   || 1000\n");
  }
}
