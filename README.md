# Electronica Data Warehouse – HybridJoin ETL Implementation (Java)

This project implements a **near-real-time ETL system** using Java for a simulated retail business chain, *Electronica*. The ETL system reads transactional data, enriches it using master data, and loads the output into a star-schema-based data warehouse (`ELECTRONICA_DW`) using the **HYBRIDJOIN** algorithm.

---

## 📦 Project Overview

### 🎯 Objective
To simulate a real-time data warehousing pipeline using Java and MySQL that:

1. Extracts transactional data from a staging table (`transactions`)
2. Transforms it by enriching with master data (`masterdata`) using a **partition-based hybrid join**
3. Loads the data into dimensional and fact tables under a **star schema**

---

## 🧠 Key Features

### 🔁 HybridJoin Algorithm (Java)
The program mimics near-real-time joining by:
- Reading transactional data in **50-record chunks** into a queue.
- Matching transactions to `productID` values from master data.
- Calculating `SALE = Quantity × Price`
- Inserting enriched data into:
  - `CUSTOMERS`, `ORDERS`, `PRODUCTS`, `SUPPLIERS`, `DATES` tables (if new)
  - `FACTS` table (fact table with foreign key references)

### ⚙️ Multithread-Safe Structures Used
- `ArrayBlockingQueue` for simulating streaming
- `MultiValuedMap` from Apache Commons Collections for hash joins
- Fallback handling if queue is full

---

## 🛠️ Setup Instructions

### 📌 Prerequisites
- Java JDK 8 or above
- MySQL Server running locally
- Apache Commons Collections v4
- Eclipse IDE (recommended) or any Java IDE

---

### 🧱 Steps to Run

1. **Create the Schema**
   - Use the `createDW.sql` file to create all dimension and fact tables under schema `ELECTRONICA_DW`.

2. **Load Raw Data**
   - Load `transactions.csv` into a staging table named `transactions`.
   - Load `master_data.csv` into a staging table named `masterdata`.

3. **Compile and Run**
   - Open project in Eclipse.
   - Add Apache Commons Collections v4 JAR file to the project build path.
   - Run the `electronica_dw.java` file.
   - Enter:
     - MySQL **username**
     - **password**
     - **schema name** (should be `ELECTRONICA_DW`)

> The system will read 1000+ records, enrich them with master data in partitions, and load all enriched tuples into the DW schema.

---

## 📊 Data Warehouse Design

### 🟨 Fact Table
- `FACTS`: Holds enriched sales transactions with references to dimension tables

### 🟦 Dimension Tables
- `PRODUCTS`, `CUSTOMERS`, `ORDERS`, `SUPPLIERS`, `DATES`, `STORES`

---

## 📈 OLAP Queries

Once the data is loaded, OLAP queries (in `queriesDW.sql`) allow analysis such as:
- Supplier-wise quarterly sales
- Most popular products
- Roll-up, drill-down, slicing, and dicing by store/product/date
- Materialized views for performance

---

## 📁 Project Structure

