package com.pluralsight.ui;

import com.pluralsight.models.Transaction;
import com.pluralsight.services.TransactionFileManager;
import com.pluralsight.services.TransactionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final TransactionFileManager fileManager = new TransactionFileManager();
    private final TransactionService transactionService = new TransactionService(fileManager);

    public UserInterface() {
        fileManager.createFileIfMissing();
    }

    public void showHomeScreen() {
        while (true) {
            printLogo();

            System.out.println("\n=== Gringotts Accounting System ===");
            System.out.println("1. Deposit");
            System.out.println("2. Make Payment");
            System.out.println("3. Gold Tracker");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

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

    private void printLogo() {
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
    }

    private void addTransaction(boolean isDeposit) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

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

        fileManager.saveTransaction(transaction);
        System.out.println("Transaction saved successfully.");
    }

    private void showLedgerScreen() {
        while (true) {
            System.out.println("\n=== Gold Tracker ===");
            System.out.println("1. All Entries");
            System.out.println("2. Deposits");
            System.out.println("3. Payments");
            System.out.println("4. Reports");
            System.out.println("5. Home");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    displayTransactions(fileManager.getAllTransactions());
                    break;
                case "2":
                    displayTransactions(transactionService.filterDeposits());
                    break;
                case "3":
                    displayTransactions(transactionService.filterPayments());
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

    private void showReportsScreen() {
        while (true) {
            System.out.println("\n=== Reports ===");
            System.out.println("1. Month To Date");
            System.out.println("2. Previous Month");
            System.out.println("3. Year To Date");
            System.out.println("4. Previous Year");
            System.out.println("5. Search By Vendor");
            System.out.println("6. Custom Search");
            System.out.println("7. Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
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

    private void retrieveCurrentMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        displayTransactions(transactionService.getTransactionsBetween(startOfMonth, today));
    }

    private void retrievePreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayPreviousMonth = today.withDayOfMonth(1).minusDays(1);
        displayTransactions(transactionService.getTransactionsBetween(firstDayPreviousMonth, lastDayPreviousMonth));
    }

    private void retrieveCurrentYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.withDayOfYear(1);
        displayTransactions(transactionService.getTransactionsBetween(startOfYear, today));
    }

    private void retrievePreviousYear() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayPreviousYear = today.minusYears(1).withDayOfYear(1);
        LocalDate lastDayPreviousYear = today.withDayOfYear(1).minusDays(1);
        displayTransactions(transactionService.getTransactionsBetween(firstDayPreviousYear, lastDayPreviousYear));
    }

    private void searchByVendor() {
        System.out.print("Enter vendor name: ");
        String vendorSearch = scanner.nextLine();

        ArrayList<Transaction> results = transactionService.searchByVendor(vendorSearch);
        displayTransactions(results);
    }

    private void customSearch() {
        System.out.print("Start date yyyy-mm-dd, or leave blank: ");
        String startDateInput = scanner.nextLine();

        System.out.print("End date yyyy-mm-dd, or leave blank: ");
        String endDateInput = scanner.nextLine();

        System.out.print("Description, or leave blank: ");
        String descriptionInput = scanner.nextLine();

        System.out.print("Vendor, or leave blank: ");
        String vendorInput = scanner.nextLine();

        System.out.print("Amount, or leave blank: ");
        String amountInput = scanner.nextLine();

        ArrayList<Transaction> results = transactionService.customSearch(
                startDateInput,
                endDateInput,
                descriptionInput,
                vendorInput,
                amountInput
        );

        displayTransactions(results);
    }

    private void displayTransactions(ArrayList<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nDate       | Time     | Description               | Vendor          | Amount");
        System.out.println("---------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
