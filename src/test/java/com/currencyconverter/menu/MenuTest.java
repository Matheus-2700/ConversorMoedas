package com.currencyconverter.menu;

import com.currencyconverter.converter.CurrencyConverter;
import com.currencyconverter.history.ConversionHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuTest {

    private Menu menu;
    private CurrencyConverter mockConverter;
    private ConversionHistory mockHistory;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        mockConverter = Mockito.mock(CurrencyConverter.class);
        mockHistory = Mockito.mock(ConversionHistory.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        menu = new Menu(mockConverter, mockHistory);
    }

    @Test
    void run_exitOption() {
        provideInput("0\n");
        menu.run();
        assertTrue(outContent.toString().contains("Obrigado por usar o Conversor de Moedas!"));
    }

    @Test
    void handleOption_invalidInput() {
        provideInput("abc\n0\n"); // Invalid input then exit
        menu.run();
        assertTrue(outContent.toString().contains("Entrada inválida. Por favor, digite um número."));
    }

    @Test
    void handleOption_unsupportedOption() {
        provideInput("99\n0\n"); // Unsupported option then exit
        menu.run();
        assertTrue(outContent.toString().contains("Opção inválida. Por favor, tente novamente."));
    }

    @Test
    void performConversion_success() throws IOException, InterruptedException {
        when(mockConverter.getSupportedCurrencies()).thenReturn(java.util.Arrays.asList("USD", "BRL"));
        when(mockConverter.convert("USD", "BRL", 10.0)).thenReturn(50.0);
        doNothing().when(mockConverter).displayConversion(anyString(), anyString(), anyDouble(), anyDouble());
        doNothing().when(mockHistory).addRecord(Mockito.any());

        provideInput("1\nUSD\nBRL\n10\n0\n");
        menu.run();

        verify(mockConverter, times(1)).convert("USD", "BRL", 10.0);
        verify(mockConverter, times(1)).displayConversion("USD", "BRL", 10.0, 50.0);
        verify(mockHistory, times(1)).addRecord(Mockito.any());
        assertTrue(outContent.toString().contains("Moedas suportadas: [USD, BRL]"));
    }

    @Test
    void performConversion_invalidAmountInput() throws IOException, InterruptedException {
        when(mockConverter.getSupportedCurrencies()).thenReturn(java.util.Arrays.asList("USD", "BRL"));

        provideInput("1\nUSD\nBRL\nabc\n0\n");
        menu.run();

        assertTrue(outContent.toString().contains("Valor inválido. Por favor, digite um número válido."));
        verify(mockConverter, times(0)).convert(anyString(), anyString(), anyDouble());
    }

    @Test
    void performConversion_converterIllegalArgumentException() throws IOException, InterruptedException {
        when(mockConverter.getSupportedCurrencies()).thenReturn(java.util.Arrays.asList("USD", "BRL"));
        doThrow(new IllegalArgumentException("Moeda não suportada."))
                .when(mockConverter).convert(anyString(), anyString(), anyDouble());

        provideInput("1\nXYZ\nBRL\n10\n0\n");
        menu.run();

        assertTrue(outContent.toString().contains("Erro de validação: Moeda não suportada."));
        verify(mockConverter, times(1)).convert("XYZ", "BRL", 10.0);
    }

    @Test
    void performConversion_converterRuntimeException() throws IOException, InterruptedException {
        when(mockConverter.getSupportedCurrencies()).thenReturn(java.util.Arrays.asList("USD", "BRL"));
        doThrow(new RuntimeException("Erro na API: invalid-key"))
                .when(mockConverter).convert(anyString(), anyString(), anyDouble());

        provideInput("1\nUSD\nBRL\n10\n0\n");
        menu.run();

        assertTrue(outContent.toString().contains("Erro na conversão: Erro na API: invalid-key"));
        verify(mockConverter, times(1)).convert("USD", "BRL", 10.0);
    }

    @Test
    void displayHistory_callsHistoryDisplay() {
        provideInput("2\n0\n");
        menu.run();
        verify(mockHistory, times(1)).displayHistory();
    }
}


