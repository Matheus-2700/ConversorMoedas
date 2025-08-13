package com.currencyconverter.history;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConversionRecord {
    private LocalDateTime timestamp;
    private String fromCurrency;
    private String toCurrency;
    private double amount;
    private double result;

    public ConversionRecord(String fromCurrency, String toCurrency, double amount, double result) {
        this.timestamp = LocalDateTime.now();
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.result = result;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] %.2f %s -> %.2f %s",
                timestamp.format(formatter),
                amount, fromCurrency.toUpperCase(),
                result, toCurrency.toUpperCase());
    }
}


