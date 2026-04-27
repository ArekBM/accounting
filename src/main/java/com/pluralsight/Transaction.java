package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isDeposit() {
        return amount > 0;
    }

    public boolean isPayment() {
        return amount < 0;
    }

    public String toCSV() {
        return date + "|" + time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "|" + description + "|" + vendor + "|" + amount;
    }

    public static Transaction fromCSV(String line) {
        String[] parts = line.split("\\|");

        LocalDate date = localDate.parse(parts[0]);
        LocalTime time = localTime.parse(parts[1]);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);

    }
}

