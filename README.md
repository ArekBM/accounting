# 🏦 Gringotts Accounting Ledger

This is a command-line Java app I built to track financial transactions. You can log deposits/payments, store them in a CSV file, and filter/search through them.

This was my capstone project, so I focused on making it structured, readable, and actually usable — not just something that “works.”

---

## 📌 What it does

- Add deposits (money in)
- Add payments (money out)
- Save everything to a CSV file
- View all transactions
- Filter deposits vs payments
- Run reports (monthly, yearly, etc.)
- Search by vendor or do a custom search

All data is stored in:

```text
transactions.csv
```

Each transaction looks like:

```text
date|time|description|vendor|amount
```

---

## 🚀 Features

### Home Screen
- Add Deposit  
- Make Payment  
- View Ledger  
- Exit  

### Ledger
- View all entries  
- Deposits only  
- Payments only  
- Reports  

### Reports
- Month to date  
- Previous month  
- Year to date  
- Previous year  
- Search by vendor  
- Custom search  

### Custom Search
You can filter by:
- Start date  
- End date  
- Description  
- Vendor  
- Amount  

If you leave a field blank, it just skips that filter.

---

## 🛠️ Tech I used

- Java  
- Maven  
- File I/O (BufferedReader / BufferedWriter)  
- ArrayLists  
- LocalDate / LocalTime  

---

## 📂 Project Structure

```text
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
```

---

## ▶️ How to run it

### Option 1 (IDE)
1. Open the project  
2. Make sure `transactions.csv` is in the root  
3. Run `LedgerApp.java`

---

### Option 2 (Maven)

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.pluralsight.LedgerApp"
```

---

## 💡 What I focused on

Originally I had everything in one file, which got messy fast.

So I broke it down into:

- `ui/` → menus + user input  
- `services/` → logic (filtering, searching)  
- `models/` → data structure  
- file manager → handles CSV  

This made it way easier to debug and understand.

---

## 🎯 What this project shows

- I can build a CLI app end-to-end  
- I understand file handling and persistence  
- I can structure a real project (not just one big class)  
- I can implement filtering/search logic  

---

## 🔮 If I kept going

- Add input validation  
- Sort transactions (newest first)  
- Add categories  
- Turn it into a web app  

---

## 👍 Final thoughts

This project helped me connect a lot of concepts together. It’s simple, but it feels like something real I could build on.
