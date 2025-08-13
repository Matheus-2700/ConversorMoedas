package com.currencyconverter.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private final String API_KEY;
    private final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private HttpClient httpClient;

    public ApiService(String apiKey) {
        this.API_KEY = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    // Construtor para injeção de dependência para testes
    public ApiService(String apiKey, HttpClient httpClient) {
        this.API_KEY = apiKey;
        this.httpClient = httpClient;
    }

    public JsonObject getExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            if (jsonResponse.get("result").getAsString().equals("success")) {
                return jsonResponse.getAsJsonObject("conversion_rates");
            } else {
                throw new RuntimeException("Erro na API: " + jsonResponse.get("error-type").getAsString());
            }
        } else {
            throw new RuntimeException("Erro ao conectar à API: Status " + response.statusCode());
        }
    }
}


