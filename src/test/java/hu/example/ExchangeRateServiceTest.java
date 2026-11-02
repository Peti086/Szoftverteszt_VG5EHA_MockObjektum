package hu.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExchangeRateServiceTest {

    private ExchangeRateService rateService;

    @BeforeEach
    void setUp() {
        rateService = mock(ExchangeRateService.class);
    }

    @Test
    @DisplayName("A mock a beállított árfolyamot adja vissza")
    void returnsStubbedRate() {
        when(rateService.getRate("EUR", "HUF")).thenReturn(400.0);

        double rate = rateService.getRate("EUR", "HUF");

        assertEquals(400.0, rate, 0.001);
        verify(rateService).getRate("EUR", "HUF");
    }

    @Test
    @DisplayName("Be nem állított paraméterekre a mock az alapértéket (0.0) adja")
    void returnsDefaultForUnstubbedArguments() {
        when(rateService.getRate("EUR", "HUF")).thenReturn(400.0);

        assertEquals(0.0, rateService.getRate("USD", "HUF"), 0.001);
    }
}
