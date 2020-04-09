package core.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import domain.Transaction;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.TransactionRepository;
import service.AccountService;
import ui.PrinterStatement;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private TransactionRepository transactionRepository;
  private AccountService accountService;

  @Mock
  private PrinterStatement printerStatement;

  @BeforeEach
  void setUp() {
    accountService = new AccountService(transactionRepository,printerStatement);
  }

  @Test
  public void deposit_an_amount_to_repository() {
    accountService.deposit(1000);

    verify(transactionRepository).addDeposit(1000);
  }

  @Test
  public void withdraw_an_amount_to_repository() {
    accountService.withdraw(1000);

    verify(transactionRepository).addWithdraw(1000);
  }

  @Test
  public void print_all_statements() {
    List<Transaction> transactions = Collections.emptyList();
    given(transactionRepository.getAll()).willReturn(transactions);

    accountService.printStatement();

    verify(printerStatement).print(transactions);
  }

}