package com.currencyconverter.converter;

import com.currencyconverter.api.ApiService;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class CurrencyConverter {
    private ApiService apiService;
    private final List<String> SUPPORTED_CURRENCIES = Arrays.asList("ARS", "BOB", "BRL", "CLP", "COP", "USD");

    public CurrencyConverter(ApiService apiService) {
        this.apiService = apiService;
    }

    public double convert(String fromCurrency, String toCurrency, double amount) throws IOException, InterruptedException {
        if (!SUPPORTED_CURRENCIES.contains(fromCurrency.toUpperCase()) || !SUPPORTED_CURRENCIES.contains(toCurrency.toUpperCase())) {
            throw new IllegalArgumentException("Moeda não suportada. As moedas suportadas são: " + SUPPORTED_CURRENCIES);
        }
        if (amount < 0) {
            throw new IllegalArgumentException("O valor a ser convertido não pode ser negativo.");
        }

        JsonObject rates = apiService.getExchangeRates(fromCurrency.toUpperCase());

        if (rates.has(toCurrency.toUpperCase())) {
            double rate = rates.get(toCurrency.toUpperCase()).getAsDouble();
            return amount * rate;
        } else {
            throw new RuntimeException("Não foi possível obter a taxa de conversão para " + toCurrency.toUpperCase());
        }
    }

    public void displayConversion(String fromCurrency, String toCurrency, double amount, double result) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        System.out.println(String.format("%.2f %s equivale a %s %s", amount, fromCurrency.toUpperCase(), df.format(result), toCurrency.toUpperCase()));
    }

    public List<String> getSupportedCurrencies() {
        return SUPPORTED_CURRENCIES;
    }
}


