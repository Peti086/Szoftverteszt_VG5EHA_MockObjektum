package hu.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BankServiceTest {

    private NotificationService notificationService;
    private BankService bankService;
    private BankAccount account;

    @BeforeEach
    void setUp() {
        // Mock: nem küld valódi értesítést, csak rögzíti a hívásokat
        notificationService = mock(NotificationService.class);
        bankService = new BankService(notificationService);
        account = new BankAccount();
    }

    @Test
    @DisplayName("1. A befizetés növeli a számla egyenlegét")
    void depositIncreasesBalance() {
        bankService.deposit(account, 1000);

        assertEquals(1000.0, account.getBalance(), 0.001);
    }

    @Test
    @DisplayName("2. Sikeres befizetés után meghívódik a send() metódus")
    void successfulDepositCallsSend() {
        bankService.deposit(account, 1000);

        verify(notificationService, times(1)).send(anyString());
    }

    @Test
    @DisplayName("3. A megfelelő értesítés kerül elküldésre")
    void successfulDepositSendsCorrectMessage() {
        bankService.deposit(account, 1000);

        verify(notificationService).send("Deposit successful");
    }

    @ParameterizedTest(name = "összeg = {0}")
    @ValueSource(doubles = {0.0, -100.0})
    @DisplayName("4. Sikertelen befizetés esetén nem történik értesítés")
    void failedDepositSendsNoNotification(double amount) {
        assertThrows(IllegalArgumentException.class,
                () -> bankService.deposit(account, amount));

        verify(notificationService, never()).send(anyString());
        assertEquals(0.0, account.getBalance(), 0.001);
    }
}
