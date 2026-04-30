🏦 Gringotts Accounting Ledger

This is a command-line Java app I built to track financial transactions. You can log deposits/payments, store them in a CSV file, and filter/search through them.

This was my capstone project, so I focused on making it structured, readable, and actually usable — not just something that “works.”

📌 What it does
Add deposits (money in)
Add payments (money out)
Save everything to a CSV file
View all transactions
Filter deposits vs payments
Run reports (monthly, yearly, etc.)
Search by vendor or do a custom search

All data is stored in:

transactions.csv

Each transaction looks like:

date|time|description|vendor|amount
🚀 Features
Home Screen
Add Deposit
Make Payment
View Ledger
Exit
Ledger
View all entries
Deposits only
Payments only
Reports
Reports
Month to date
Previous month
Year to date
Previous year
Search by vendor
Custom search
Custom Search

You can filter by:

Start date
End date
Description
Vendor
Amount

If you leave a field blank, it just skips that filter.

🛠️ Tech I used
Java
Maven (project structure + build)
File I/O (BufferedReader / BufferedWriter)
ArrayLists
LocalDate / LocalTime
📂 Project Structure
accounting-master/
│
├── src/main/java/com/pluralsight/
│   ├── LedgerApp.java
│
│   ├── models/
│   │   └── Transaction.java
│
│   ├── services/
│   │   ├── TransactionFileManager.java
│   │   └── TransactionService.java
│
│   └── ui/
│       └── UserInterface.java
│
├── transactions.csv
├── pom.xml
└── README.md
▶️ How to run it
Option 1 (IntelliJ / IDE)
Open the project
Make sure transactions.csv is in the root
Run:
LedgerApp.java

💡 What I focused on

Originally I had everything in one file… which got messy fast.

So I broke it down into layers:

ui/ → handles menus + user input
services/ → handles logic (filtering, searching, reports)
models/ → represents the data (Transaction)
file manager → handles reading/writing CSV

This made debugging way easier and the code actually makes sense now when you read it.

🎯 What this project shows
I can build a full CLI app end-to-end
I understand file handling and persistence
I can structure a project like a real application (not just one big class)
I can implement filtering/search logic cleanly
🔮 If I kept going
Add input validation (right now it trusts the user a bit too much 😅)
Sort transactions (newest first)
Add categories or tags
Turn it into a web app (React + backend)
👍 Final thoughts

This project helped me connect a lot of concepts together — especially structuring code properly instead of just getting something working.

It’s simple, but it feels like something real I could actually build on.
