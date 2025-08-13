package com.currencyconverter.converter;

import com.currencyconverter.api.ApiService;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CurrencyConverterTest {

    private ApiService mockApiService;
    private CurrencyConverter currencyConverter;

    @BeforeEach
    void setUp() {
        mockApiService = Mockito.mock(ApiService.class);
        currencyConverter = new CurrencyConverter(mockApiService);
    }

    @Test
    void convert_success() throws IOException, InterruptedException {
        JsonObject mockRates = new JsonObject();
        mockRates.addProperty("USD", 1.0);
        mockRates.addProperty("BRL", 5.0);
        mockRates.addProperty("ARS", 100.0);

        when(mockApiService.getExchangeRates("USD")).thenReturn(mockRates);

        double convertedValue = currencyConverter.convert("USD", "BRL", 10.0);
        assertEquals(50.0, convertedValue, 0.001);
    }

    @Test
    void convert_unsupportedCurrency() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            currencyConverter.convert("XYZ", "BRL", 10.0);
        });
        assertTrue(thrown.getMessage().contains("Moeda não suportada"));
    }

    @Test
    void convert_negativeAmount() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            currencyConverter.convert("USD", "BRL", -10.0);
        });
        assertTrue(thrown.getMessage().contains("O valor a ser convertido não pode ser negativo."));
    }

    @Test
    void convert_missingTargetCurrencyRate() throws IOException, InterruptedException {
        JsonObject mockRates = new JsonObject();
        mockRates.addProperty("USD", 1.0);
        // BRL is intentionally missing

        when(mockApiService.getExchangeRates("USD")).thenReturn(mockRates);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            currencyConverter.convert("USD", "BRL", 10.0);
        });
        assertTrue(thrown.getMessage().contains("Não foi possível obter a taxa de conversão para BRL"));
    }

    @Test
    void getSupportedCurrencies_returnsCorrectList() {
        List<String> supported = currencyConverter.getSupportedCurrencies();
        assertNotNull(supported);
        assertFalse(supported.isEmpty());
        assertTrue(supported.contains("USD"));
        assertTrue(supported.contains("BRL"));
        assertEquals(6, supported.size());
    }
}


