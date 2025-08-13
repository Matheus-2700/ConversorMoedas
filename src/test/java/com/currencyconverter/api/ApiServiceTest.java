package com.currencyconverter.api;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiServiceTest {

    private ApiService apiService;
    private HttpClient mockHttpClient;
    private HttpResponse<String> mockHttpResponse;

    @BeforeEach
    void setUp() {
        mockHttpClient = mock(HttpClient.class);
        mockHttpResponse = mock(HttpResponse.class);
        apiService = new ApiService("test_api_key", mockHttpClient);
    }

    @Test
    void getExchangeRates_success() throws IOException, InterruptedException {
        String jsonResponse = "{\"result\":\"success\",\"conversion_rates\":{\"USD\":1,\"BRL\":5.0}}";
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        JsonObject rates = apiService.getExchangeRates("USD");

        assertNotNull(rates);
        assertTrue(rates.has("USD"));
        assertTrue(rates.has("BRL"));
        assertEquals(1.0, rates.get("USD").getAsDouble());
        assertEquals(5.0, rates.get("BRL").getAsDouble());
    }

    @Test
    void getExchangeRates_apiError() throws IOException, InterruptedException {
        String jsonResponse = "{\"result\":\"error\",\"error-type\":\"invalid-key\"}";
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            apiService.getExchangeRates("USD");
        });
        assertTrue(thrown.getMessage().contains("Erro na API: invalid-key"));
    }

    @Test
    void getExchangeRates_httpError() throws IOException, InterruptedException {
        when(mockHttpResponse.statusCode()).thenReturn(404);
        when(mockHttpResponse.body()).thenReturn("Not Found");
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            apiService.getExchangeRates("USD");
        });
        assertTrue(thrown.getMessage().contains("Erro ao conectar à API: Status 404"));
    }

    @Test
    void getExchangeRates_ioException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Network error"));

        IOException thrown = assertThrows(IOException.class, () -> {
            apiService.getExchangeRates("USD");
        });
        assertTrue(thrown.getMessage().contains("Network error"));
    }
}


