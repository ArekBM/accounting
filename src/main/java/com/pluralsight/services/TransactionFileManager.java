package com.pluralsight.services;

import com.pluralsight.models.Transaction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TransactionFileManager {
    private static final String FILE_NAME = "transactions.csv";

    public void createFileIfMissing() {
        File file = new File(FILE_NAME);

        try {
            if (file.createNewFile()) {
                System.out.println("Created transactions.csv file.");
            }
        } catch (IOException e) {
            System.out.println("Could not create transactions.csv file.");
        }
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    transactions.add(Transaction.fromCSV(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions file.");
        }

        return transactions;
    }

    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(transaction.toCSV());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction.");
        }
    }
}
