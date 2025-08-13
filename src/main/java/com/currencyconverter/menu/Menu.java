package com.currencyconverter.menu;

import com.currencyconverter.converter.CurrencyConverter;
import com.currencyconverter.history.ConversionHistory;
import com.currencyconverter.history.ConversionRecord;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    private CurrencyConverter converter;
    private ConversionHistory history;

    public Menu(CurrencyConverter converter, ConversionHistory history) {
        this.scanner = new Scanner(System.in);
        this.converter = converter;
        this.history = history;
    }

    public void run() {
        int option = -1;
        while (option != 0) {
            displayMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());
                handleOption(option);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
        }
        System.out.println("Obrigado por usar o Conversor de Moedas!");
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n*** Conversor de Moedas ***");
        System.out.println("1. Converter Moeda");
        System.out.println("2. Ver Histórico de Conversões");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void handleOption(int option) {
        switch (option) {
            case 1:
                performConversion();
                break;
            case 2:
                history.displayHistory();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida. Por favor, tente novamente.");
        }
    }

    private void performConversion() {
        System.out.println("\nMoedas suportadas: " + converter.getSupportedCurrencies());
        String fromCurrency;
        String toCurrency;
        double amount;

        System.out.print("Digite a moeda de origem (ex: USD): ");
        fromCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Digite a moeda de destino (ex: BRL): ");
        toCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Digite o valor a ser convertido: ");
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Por favor, digite um número válido.");
            return;
        }

        try {
            double result = converter.convert(fromCurrency, toCurrency, amount);
            converter.displayConversion(fromCurrency, toCurrency, amount, result);
            history.addRecord(new ConversionRecord(fromCurrency, toCurrency, amount, result));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Erro na conversão: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado durante a conversão: " + e.getMessage());
        }
    }
}


