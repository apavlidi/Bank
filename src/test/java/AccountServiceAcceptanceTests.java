import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.AccountService;
import service.Clock;
import ui.Console;

@ExtendWith(MockitoExtension.class)
public class AccountServiceAcceptanceTests {

  @Mock
  private Console console;

  @Mock
  private Clock clock;

  @Test
  public void print_all_statements() {
    given(clock.getCurrentDate()).willReturn("10/01/2012", "13/01/2012", "14/01/2012");
    AccountService accountService = new AccountService(console, clock);

    accountService.deposit(1000);
    accountService.deposit(2000);
    accountService.withdraw(500);

    accountService.printStatement();

    verify(console).print("Date       || Amount || Balance\n");
    verify(console).print("14/01/2012 || -500   || 2500\n");
    verify(console).print("13/01/2012 || 2000   || 3000\n");
    verify(console).print("10/01/2012 || 1000   || 1000\n");
  }

}
