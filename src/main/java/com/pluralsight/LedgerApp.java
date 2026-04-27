package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class LedgerApp {
        private static final String FILE_NAME = "transactions.csv";
        private static final Scanner scanner = new Scanner(System.in);

        public static void main(String[] args){
                createFileIfMissing();
                showHomeScreen();
        }

        private static void showHomeScreen(){
                while (true) {
                        System.out.println("\n=== Accounting Ledger ===");
                        System.out.println("1. Deposit");
                        System.out.println("2. Make Payment");
                        System.out.println("3. Transactions");
                        System.out.println("4. Exit");
                        System.out.println("Choose an option: ");

                        String choice = scanner.nextLine().trim();

                        switch (choice) {
                        }
                }
        }
        private static void addTransaction(boolean isDeposit) {
                System.out.println("Enter description: ");
                String description = scanner.nextLine();

                System.out.print("Enter vendor: ");
                String vendor = scanner.nextLine();

                System.out.println("Enter amount: ");
                double amount = Double.parseDouble(scanner.nextLine());

                // If not deposit then it is a payment.
                // -> take amount value then force it to be negative
                if (!isDeposit) {
                        amount = -Math.abs(amount);
                }

                Transaction transaction = new Transaction(
                        LocalDate.now(),
                        LocalTime.now(),
                        description,
                        vendor,
                        amount
                );

                saveTransaction(transaction);
                System.out.println("Transaction saved successfully.");

        }

        private static void saveTransaction(Transaction transaction){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                        writer.write(transaction.toCSV());
                        writer.newLine();
                } catch (IOException e) {
                        System.out.println("Error saving transaction.");
                }
        }

}
