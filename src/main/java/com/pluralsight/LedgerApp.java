package com.pluralsight;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class LedgerApp {
        private final String FILE_NAME = "transactions.csv";
        private final Scanner scanner = new Scanner(System.in);

        public static void main(String[] args){
                createFileIfMissing();
                showHomeScreen();
        }
}
