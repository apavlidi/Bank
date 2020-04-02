package service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ui.Console;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private Console console;

  @Mock
  private Clock clock;

  private AccountService accountService;

  @BeforeEach
  void setUp() {
    accountService = new AccountService(console, clock);
  }

  @Test
  public void add_a_deposit_transaction() {
    given(clock.getCurrentDate()).willReturn("10/01/2012");

    accountService.deposit(1000);
    accountService.printStatement();

    verify(console).print("10/01/2012 || 1000   || 1000\n");
  }

  @Test
  public void add_a_withdraw_transaction() {
    given(clock.getCurrentDate()).willReturn("10/01/2012");

    accountService.withdraw(1000);
    accountService.printStatement();

    verify(console).print("10/01/2012 || -1000   || -1000\n");
  }

  @Test
  public void add_two_deposit_transaction() {
    given(clock.getCurrentDate()).willReturn("10/01/2012", "13/01/2012");

    accountService.deposit(1000);
    accountService.deposit(2000);
    accountService.printStatement();

    InOrder inOrder = Mockito.inOrder(console);
    inOrder.verify(console).print("13/01/2012 || 2000   || 3000\n");
    inOrder.verify(console).print("10/01/2012 || 1000   || 1000\n");
  }

  @Test
  public void add_two_deposits_and_a_withdraw_transaction() {
    given(clock.getCurrentDate()).willReturn("10/01/2012", "13/01/2012", "14/01/2012");

    accountService.deposit(1000);
    accountService.deposit(2000);
    accountService.withdraw(500);
    accountService.printStatement();

    InOrder inOrder = Mockito.inOrder(console);
    inOrder.verify(console).print("14/01/2012 || -500   || 2500\n");
    inOrder.verify(console).print("13/01/2012 || 2000   || 3000\n");
    inOrder.verify(console).print("10/01/2012 || 1000   || 1000\n");
  }

  @Test
  public void print_header() {
    accountService.printStatement();

    verify(console).print("Date       || Amount || Balance\n");
  }

}