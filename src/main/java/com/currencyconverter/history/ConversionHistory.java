package com.currencyconverter.history;

import java.util.ArrayList;
import java.util.List;

public class ConversionHistory {
    private List<ConversionRecord> records;

    public ConversionHistory() {
        this.records = new ArrayList<>();
    }

    public void addRecord(ConversionRecord record) {
        this.records.add(record);
    }

    public List<ConversionRecord> getRecords() {
        return records;
    }

    public void displayHistory() {
        if (records.isEmpty()) {
            System.out.println("Nenhuma conversão registrada ainda.");
        } else {
            System.out.println("\n*** Histórico de Conversões ***");
            for (int i = 0; i < records.size(); i++) {
                System.out.println((i + 1) + ". " + records.get(i));
            }
        }
    }
}


