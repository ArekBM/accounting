package com.pluralsight;

import java.io.*;
import java.sql.Array;
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
                        System.out.println("  /$$$$$$            /$$                                 /$$     /$$             \n" +
                                " /$$__  $$          |__/                                | $$    | $$             \n" +
                                "| $$  \\__/  /$$$$$$  /$$ /$$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$ /$$$$$$   /$$$$$$$\n" +
                                "| $$ /$$$$ /$$__  $$| $$| $$__  $$ /$$__  $$ /$$__  $$|_  $$_/|_  $$_/  /$$_____/\n" +
                                "| $$|_  $$| $$  \\__/| $$| $$  \\ $$| $$  \\ $$| $$  \\ $$  | $$    | $$   |  $$$$$$ \n" +
                                "| $$  \\ $$| $$      | $$| $$  | $$| $$  | $$| $$  | $$  | $$ /$$| $$ /$$\\____  $$\n" +
                                "|  $$$$$$/| $$      | $$| $$  | $$|  $$$$$$$|  $$$$$$/  |  $$$$/|  $$$$//$$$$$$$/\n" +
                                " \\______/ |__/      |__/|__/  |__/ \\____  $$ \\______/    \\___/   \\___/ |_______/ \n" +
                                "                                   /$$  \\ $$                                     \n" +
                                "                                  |  $$$$$$/                                     \n" +
                                "                                   \\______/                                      ");
                        System.out.println("\n=== Gringotts Accounting System ===");
                        System.out.println("1. Deposit");
                        System.out.println("2. Make Payment");
                        System.out.println("3. Gold Tracker");
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

        // addTransaction expects one boolean which checks if we are adding money or subtracting
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
                        System.out.println("\n=== Gold Tracker ===");
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
                                case "2":
                                        displayTransactions(filterDeposits());
                                        break;
                                case "3":
                                        displayTransactions(filterPayments());
                                        break;
                                case "4":
                                        showReportsScreen();
                                        break;
                                case "5":
                                        return;
                                default:
                                        System.out.println("Invalid option. Try again.");
                        }
                }
        }

        private static void showReportsScreen() {
                while (true) {
                        System.out.println("\n=== Reports ===");
                        System.out.println("1. Month To Date");
                        System.out.println("2. Previous Month");
                        System.out.println("3. Year To Date");
                        System.out.println("4. Previous Year");
                        System.out.println("5. Search By Vendor");
                        System.out.println("6. Custom Search");
                        System.out.println("7. Back");
                        System.out.println("Choose an option: ");

                        String choice = scanner.nextLine().trim();

                        switch(choice) {
                                case "1":
                                        retrieveCurrentMonth();
                                        break;
                                case "2":
                                        retrievePreviousMonth();
                                        break;
                                case "3":
                                        retrieveCurrentYear();
                                        break;
                                case "4":
                                        retrievePreviousYear();
                                        break;
                                case "5":
                                        searchByVendor();
                                        break;
                                case "6":
                                        customSearch();
                                        break;
                                case "7":
                                        return;
                                default:
                                        System.out.println("Invalid option. Try again.");
                        }
                }
        }

        private static void retrieveCurrentMonth() {
                LocalDate today = LocalDate.now();
                LocalDate startOfMonth = today.withDayOfMonth(1);
                displayBetweenDates(startOfMonth, today);
        }

        private static void retrievePreviousMonth() {
                LocalDate today = LocalDate.now();
                //Gets first day of the last month
                LocalDate firstDayPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
                //Gets last day of the last month
                LocalDate lastDayPreviousMonth = today.withDayOfMonth(1).minusDays(1);
                //Shows from 1-31 of last month
                displayBetweenDates(firstDayPreviousMonth, lastDayPreviousMonth);
        }

        private static void retrieveCurrentYear() {
                LocalDate today = LocalDate.now();
                LocalDate startOfYear = today.withDayOfYear(1);
                displayBetweenDates(startOfYear, today);
        }

        private static void retrievePreviousYear() {
                LocalDate today = LocalDate.now();
                LocalDate firstDayPreviousYear = today.minusYears(1).withDayOfYear(1);
                LocalDate lastDayPreviousYear = today.withDayOfYear(1).minusDays(1);
                displayBetweenDates(firstDayPreviousYear, lastDayPreviousYear);
        }

        private static void searchByVendor() {
                System.out.println("Enter vendor name: ");
                String vendorSearch = scanner.nextLine().toLowerCase();

                ArrayList<Transaction> results = new ArrayList<>();

                for (Transaction transaction : getAllTransactions()) {
                        if (transaction.getVendor().toLowerCase().contains(vendorSearch)) {
                                ;
                                results.add(transaction);
                        }
                }
                displayTransactions(results);
        }

        private static void displayBetweenDates(LocalDate start, LocalDate end) {
                ArrayList<Transaction> results = new ArrayList<>();

                for (Transaction transaction : getAllTransactions()) {
                        boolean isAfterStart = transaction.getDate().isEqual(start) || transaction.getDate().isAfter(start);
                        boolean isBeforeEnd = transaction.getDate().isEqual(end) || transaction.getDate().isBefore(end);

                        if (isAfterStart && isBeforeEnd){
                                results.add(transaction);
                        }

                }
                displayTransactions(results);
        }

        private static ArrayList<Transaction> filterDeposits() {
                ArrayList<Transaction> deposits = new ArrayList<>();

                for (Transaction transaction : getAllTransactions()){
                        if (transaction.isDeposit()){
                                deposits.add(transaction);
                        }
                }
                return deposits;
        }

        private static ArrayList<Transaction> filterPayments() {
                ArrayList<Transaction> payments = new ArrayList<>();

                for (Transaction transaction : getAllTransactions()){
                        if(transaction.isPayment()){
                                payments.add(transaction);
                        }
                }
                return payments;
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

        private static void customSearch() {
                System.out.println("Start date yyyy-mm-dd, or leave blank: ");
                String startDateInput = scanner.nextLine();

                System.out.println("End date yyyy-mm-dd, or leave blank: ");
                String endDateInput = scanner.nextLine();

                System.out.println("Description, or leave blank: ");
                String descriptionInput = scanner.nextLine();

                System.out.println("Vendor, or leave blank: ");
                String vendorInput = scanner.nextLine();

                System.out.println("Amount, or leave blank: ");
                String amountInput = scanner.nextLine();

                ArrayList<Transaction> results = new ArrayList<>();

                //Anything that doesnt match we skip
                for (Transaction transaction : getAllTransactions()){
                        if (!startDateInput.isEmpty() && transaction.getDate().isBefore(LocalDate.parse(startDateInput))){
                                System.out.println("test");
                                continue;
                        }
                        if (!endDateInput.isEmpty() && transaction.getDate().isAfter(LocalDate.parse(endDateInput))){
                                continue;
                        }
                        if (!descriptionInput.isEmpty() && !transaction.getDescription().toLowerCase().contains(descriptionInput.toLowerCase())){
                                continue;
                        }
                        if (!vendorInput.isEmpty() && transaction.getVendor().toLowerCase().contains(vendorInput.toLowerCase())){
                                continue;
                        }
                        if (!amountInput.isEmpty() && transaction.getAmount() != Double.parseDouble(amountInput)){
                                continue;
                        }

                        //Keep the matched results
                        results.add(transaction);

                }

                displayTransactions(results);
        }

}
