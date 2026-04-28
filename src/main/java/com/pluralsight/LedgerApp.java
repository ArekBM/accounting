package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

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
                                case "1":
                                        addTransaction(true);
                                        break;
                                case "2":
                                        addTransaction(false);
                                        break;
                                case "3":
                                        showLedgerScreen();
                                        break;
                                case "4":
                                        System.out.println("Goodbye!");
                                        return;
                                default:
                                        System.out.println("Invalid option. Try again.");
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

        private static void showLedgerScreen() {
                while (true) {
                        System.out.println("\n=== Ledger ===");
                        System.out.println("1. All Entries");
                        System.out.println("2. Deposits");
                        System.out.println("3. Payments");
                        System.out.println("4. Reports");
                        System.out.println("5. Home");
                        System.out.println("Choose an option: ");

                        String choice = scanner.nextLine().trim();

                        switch (choice) {
                                case "1":
                                        displayTransactions(getAllTransactions());
                                        break;
                                case "5":
                                        showHomeScreen();
                                default:
                                        System.out.println("Invalid option. Try again.");
                        }
                }
        }

        private static void displayTransactions(ArrayList<Transaction> transactions) {
                if (transactions.isEmpty()) {
                        System.out.println("No transactions found.");
                        return;
                }

                System.out.println("\nDate       | Time     | Description               | Vendor          |Amount");
                System.out.println("---------------------------------------------------------------------------");

                for (Transaction transaction : transactions) {
                        System.out.println(transaction);
                }
        }

        private static ArrayList<Transaction> getAllTransactions() {
                ArrayList<Transaction> transactions = new ArrayList<>();

                try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
                        String line;

                        while ((line = reader.readLine()) != null){
                                if( !line.trim().isEmpty()) {
                                        transactions.add(Transaction.fromCSV((line)));
                                }
                        }
                } catch ( IOException e) {
                        System.out.println("Error reading transactions file.");
                }

                return transactions;
        }


        private static void saveTransaction(Transaction transaction){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                        writer.write(transaction.toCSV());
                        writer.newLine();
                } catch (IOException e) {
                        System.out.println("Error saving transaction.");
                }
        }

        private static void createFileIfMissing() {
                File file = new File(FILE_NAME);

                try {
                        if (file.createNewFile()){
                                System.out.println("Created transaction.csv file.");
                        }
                } catch (IOException e) {
                        System.out.println("Could not create transaction.csv file.");
                }
        }

}
