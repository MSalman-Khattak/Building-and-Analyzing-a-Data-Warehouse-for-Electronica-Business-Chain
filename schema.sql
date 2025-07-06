-- create schema ELECTRONICA_DW;
use ELECTRONICA_DW;
CREATE TABLE MASTERDATA (
    productID INT NOT NULL,
    productName VARCHAR(255) NOT NULL,
    productPrice varchar(255) NOT NULL,
    supplierID INT NOT NULL,
    supplierName VARCHAR(255),
    storeID INT NOT NULL,
    storeName VARCHAR(255)
);

-- Create the Orders table
CREATE TABLE TRANSACTIONDATA (
    OrderID INT,
    OrderDate TIMESTAMP NOT NULL,
    ProductID INT,
    CustomerID INT,
    CustomerName VARCHAR(255),
    Gender VARCHAR(10),
    QuantityOrdered INT
);






