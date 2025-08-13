package com.currencyconverter;

import com.currencyconverter.api.ApiService;
import com.currencyconverter.menu.Menu;
import com.currencyconverter.converter.CurrencyConverter;
import com.currencyconverter.history.ConversionHistory;

public class Main {
    public static void main(String[] args) {

        String apiKey = "ae028e8c2dbaf9eb23dccf16";

        ApiService apiService = new ApiService(apiKey);
        CurrencyConverter converter = new CurrencyConverter(apiService);
        ConversionHistory history = new ConversionHistory();
        Menu menu = new Menu(converter, history);
        menu.run();
    }
}


