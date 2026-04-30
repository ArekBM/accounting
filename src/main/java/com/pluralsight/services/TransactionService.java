package com.pluralsight.services;

import com.pluralsight.models.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionService {
    private final TransactionFileManager fileManager;

    public TransactionService(TransactionFileManager fileManager) {
        this.fileManager = fileManager;
    }

    public ArrayList<Transaction> filterDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for (Transaction transaction : fileManager.getAllTransactions()) {
            if (transaction.isDeposit()) {
                deposits.add(transaction);
            }
        }

        return deposits;
    }

    public ArrayList<Transaction> filterPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();

        for (Transaction transaction : fileManager.getAllTransactions()) {
            if (transaction.isPayment()) {
                payments.add(transaction);
            }
        }

        return payments;
    }

    public ArrayList<Transaction> getTransactionsBetween(LocalDate start, LocalDate end) {
        ArrayList<Transaction> results = new ArrayList<>();

        for (Transaction transaction : fileManager.getAllTransactions()) {
            boolean isAfterStart = transaction.getDate().isEqual(start) || transaction.getDate().isAfter(start);
            boolean isBeforeEnd = transaction.getDate().isEqual(end) || transaction.getDate().isBefore(end);

            if (isAfterStart && isBeforeEnd) {
                results.add(transaction);
            }
        }

        return results;
    }

    public ArrayList<Transaction> searchByVendor(String vendorSearch) {
        ArrayList<Transaction> results = new ArrayList<>();

        for (Transaction transaction : fileManager.getAllTransactions()) {
            if (transaction.getVendor().toLowerCase().contains(vendorSearch.toLowerCase())) {
                results.add(transaction);
            }
        }

        return results;
    }

    public ArrayList<Transaction> customSearch(
            String startDateInput,
            String endDateInput,
            String descriptionInput,
            String vendorInput,
            String amountInput
    ) {
        ArrayList<Transaction> results = new ArrayList<>();

        for (Transaction transaction : fileManager.getAllTransactions()) {
            if (!startDateInput.isEmpty() && transaction.getDate().isBefore(LocalDate.parse(startDateInput))) {
                continue;
            }

            if (!endDateInput.isEmpty() && transaction.getDate().isAfter(LocalDate.parse(endDateInput))) {
                continue;
            }

            if (!descriptionInput.isEmpty() &&
                    !transaction.getDescription().toLowerCase().contains(descriptionInput.toLowerCase())) {
                continue;
            }

            if (!vendorInput.isEmpty() &&
                    !transaction.getVendor().toLowerCase().contains(vendorInput.toLowerCase())) {
                continue;
            }

            if (!amountInput.isEmpty() && transaction.getAmount() != Double.parseDouble(amountInput)) {
                continue;
            }

            results.add(transaction);
        }

        return results;
    }
}
