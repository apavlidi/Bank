package core.ui;

import static org.mockito.Mockito.verify;

import domain.Transaction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ui.Console;
import ui.PrinterStatement;

@ExtendWith(MockitoExtension.class)
class PrinterStatementTest {

  @Mock
  private Console console;
  private PrinterStatement printerStatement;

  @BeforeEach
  void setUp() {
    printerStatement = new PrinterStatement(console);
  }

  @Test
  public void prints_header() {
    printerStatement.print(Collections.emptyList());

    verify(console).print("Date       || Amount || Balance\n");
  }

  @Test
  public void prints_all_transactions_in_reverse_chroonological_order() {
    List<Transaction> transactions = Arrays.asList(
        new Transaction("10/01/2012", 1000),
        new Transaction("13/01/2012", 2000),
        new Transaction("14/01/2012", -500));

    printerStatement.print(transactions);

    InOrder inOrder = Mockito.inOrder(console);
    inOrder.verify(console).print("Date       || Amount || Balance\n");
    inOrder.verify(console).print("14/01/2012 || -500   || 2500\n");
    inOrder.verify(console).print("13/01/2012 || 2000   || 3000\n");
    inOrder.verify(console).print("10/01/2012 || 1000   || 1000\n");
  }

}