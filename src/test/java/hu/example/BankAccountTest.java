package hu.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount();
    }

    @Test
    void testNewAccountBalanceIsZero() {
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    void testDeposit1000ResultsIn1000Balance() {
        account.deposit(1000.0);
        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    void testMultipleDepositsSumUpCorrectly() {
        account.deposit(500.0);
        account.deposit(250.5);
        account.deposit(100.0);
        assertEquals(850.5, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawDecreasesBalanceAfterDeposit() {
        account.deposit(1000.0);
        account.withdraw(400.0);
        assertEquals(600.0, account.getBalance(), 0.001);
    }

    @Test
    void testEntireBalanceCanBeWithdrawn() {
        account.deposit(1000.0);
        account.withdraw(1000.0);
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    void testWithdrawTooLargeThrowsException() {
        account.deposit(500.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(500.1));
        assertEquals(500.0, account.getBalance(), 0.001);
    }

    @Test
    void testNegativeDepositThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0));
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    void testZeroDepositThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
        assertEquals(0.0, account.getBalance(), 0.001);
    }

    @Test
    void testNegativeWithdrawThrowsException() {
        account.deposit(500.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50.0));
        assertEquals(500.0, account.getBalance(), 0.001);
    }

    @Test
    void testZeroWithdrawThrowsException() {
        account.deposit(500.0);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
        assertEquals(500.0, account.getBalance(), 0.001);
    }
}
