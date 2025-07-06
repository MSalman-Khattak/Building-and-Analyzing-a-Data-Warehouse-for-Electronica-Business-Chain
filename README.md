# Electronica Data Warehouse (ELECTRONICA_DW)

This project implements a **near-real-time data warehouse** solution for the Electronica electronics retail chain. The system ingests transactional data, transforms and enriches it using the **HYBRIDJOIN** algorithm, and loads it into a **star-schema-based** MySQL data warehouse. It also supports complex OLAP analysis and materialized views for business intelligence.

---

## 📦 Project Components

### 1. Data Warehouse Schema

- **Database:** `ELECTRONICA_DW`
- **Tables:**
  - **Dimensions:** `PRODUCTS`, `CUSTOMERS`, `SUPPLIERS`, `STORES`, `DATES`, `ORDERS`
  - **Fact Table:** `FACTS` (central table joining dimensions and holding sales data)

### 2. ETL Process (Java, Multi-threaded)

- **Thread 1: `StreamGenerator`** – Generates a batch stream of transactional records.
- **Thread 2: `HybridJoin`** – Enriches transactional data by joining it with master data using the HYBRIDJOIN algorithm.
- **Thread 3: `Controller`** – Monitors ingestion and processing rates, controls flow speed.

> The enriched data is inserted into the `FACTS` table with relevant foreign keys, while also updating dimensions.

---


---

## 🛠️ Setup Instructions

### 1. Import Schema and Data

- Create the `ELECTRONICA_DW` schema in MySQL.
- Run the provided SQL script to create dimension and fact tables.
- Load data from `transactions.csv` and `master_data.csv` into temporary tables (`TRANSACTIONDATA` and `MASTERDATA`).

### 2. Run ETL

- Run the Java ETL program (`ETL.java`) using Eclipse or IntelliJ.
- On launch, it will:
  - Ask for MySQL DB credentials.
  - Load data from the source tables.
  - Perform enrichment using `HYBRIDJOIN`.
  - Insert enriched records into `FACTS`.

> Ensure that all external libraries (e.g., Apache MultiHashMap) are added to your Java project.

---

## 📊 OLAP Analysis

Several analytical SQL queries are performed on the DW:

### 🔍 Sample Queries

- **Q1. Drill-down:**  
  Total sales per supplier by quarter and month using `GROUPING SETS`.

- **Q2. Roll-up & Dicing:**  
  Sales by product/month filtered for supplier "DJI" using `ROLLUP`.

- **Q3. Top 5 Products on Weekends:**  
  Sales grouped by product for `DAYOFWEEK` = 1 or 7.

- **Q4. Quarterly and Yearly Product Sales:**  
  Uses `SUM(...) OVER(PARTITION BY ...)`.

- **Q5. Anomaly Detection:**  
  Identify outliers based on unexpected sales patterns.

- **Materialized Views:**
  - `STOREANALYSIS_MV`: Product-wise sales by store.
  - `SUPPLIER_PERFORMANCE_MV`: Supplier performance over months.
  - `CUSTOMER_STORE_SALES_MV`: Monthly sales by customer/store.

---

## 📌 Notes

- Ensure correct data types when importing CSVs.
- For date operations (`YEAR`, `MONTH`, `DAYOFWEEK`), the `DATE_ID` must be in a parsable date format.
- OLAP operations require well-indexed tables for performance.

---

## 📚 Technologies Used

- **Database:** MySQL
- **ETL Language:** Java (Multithreaded)
- **SQL Features:** Star-schema design, `GROUPING SETS`, `ROLLUP`, `WINDOW FUNCTIONS`, `MATERIALIZED VIEWS`

---

## 🧠 Learning Outcomes

- Implemented real-time ETL using a custom algorithm.
- Designed and normalized a data warehouse schema.
- Practiced advanced SQL aggregation and multidimensional analysis.
- Understood tradeoffs in real-time vs batch processing pipelines.

---

## 📁 Project Submission

- `createDW.sql` – Schema + table creation script
- `ETL/` – Java source code implementing multithreaded ETL
- `queriesDW.sql` – All OLAP queries
- `report.docx` – Full project write-up
- `readMe.txt` – Step-by-step instructions


