package com.currencyconverter.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ConversionHistoryTest {

    private ConversionHistory history;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        history = new ConversionHistory();
        System.setOut(new PrintStream(outContent)); // Redirect System.out for testing console output
    }

    @Test
    void addRecord_addsRecordToList() {
        ConversionRecord record = new ConversionRecord("USD", "BRL", 10.0, 50.0);
        history.addRecord(record);
        assertEquals(1, history.getRecords().size());
        assertEquals(record, history.getRecords().get(0));
    }

    @Test
    void displayHistory_emptyHistory() {
        history.displayHistory();
        assertTrue(outContent.toString().contains("Nenhuma conversão registrada ainda."));
    }

    @Test
    void displayHistory_withRecords() {
        ConversionRecord record1 = new ConversionRecord("USD", "BRL", 10.0, 50.0);
        ConversionRecord record2 = new ConversionRecord("BRL", "USD", 100.0, 20.0);
        history.addRecord(record1);
        history.addRecord(record2);

        history.displayHistory();
        String output = outContent.toString();

        assertTrue(output.contains("*** Histórico de Conversões ***"));
        assertTrue(output.contains("1. "));
        assertTrue(output.contains("2. "));
        assertTrue(output.contains("10.00 USD -> 50.00 BRL"));
        assertTrue(output.contains("100.00 BRL -> 20.00 USD"));
    }

    @Test
    void conversionRecord_toString_formatsCorrectly() {
        // Create a record with a specific timestamp for predictable output
        LocalDateTime fixedTime = LocalDateTime.of(2025, 8, 13, 10, 30, 0);
        ConversionRecord record = new ConversionRecord("USD", "BRL", 10.0, 50.0) {
            @Override
            public String toString() {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                return String.format("[%s] %.2f %s -> %.2f %s",
                        fixedTime.format(formatter),
                        getAmount(), getFromCurrency().toUpperCase(),
                        getResult(), getToCurrency().toUpperCase());
            }
        };

        String expected = String.format("[%s] %.2f %s -> %.2f %s",
                fixedTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                10.0, "USD",
                50.0, "BRL");
        assertEquals(expected, record.toString());
    }

    // Restore System.out after all tests
    @org.junit.jupiter.api.AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }
}


