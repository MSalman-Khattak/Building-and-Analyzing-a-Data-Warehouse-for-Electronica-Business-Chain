SELECT 
  s.supplierID, 
  d.DATE_ID,
  SUM(f.SALE) AS TotalSales
FROM
  SUPPLIERS s
  JOIN FACTS f ON s.supplierID = f.supplierID
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
GROUP BY 
  GROUPING SETS ((s.supplierID, d.Quarter, d.Month), (s.supplierID, d.Quarter), (s.supplierID));
  
  
  
  
  SELECT 
  p.productID, 
  d.DATE_ID,
  SUM(f.SALE) AS TotalSales
FROM 
  PRODUCTS p
  JOIN FACTS f ON p.productID = f.productID
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
  JOIN SUPPLIERS s ON f.supplierID = s.supplierID
WHERE 
  s.supplierName = 'DJI' AND YEAR(d.DATE_ID) = 2019
GROUP BY 
  ROLLUP (p.productID, d.Month);




SELECT 
  p.productID,
  p.productName,
  SUM(f.SALE) AS TotalSales
FROM 
  PRODUCTS p
  JOIN FACTS f ON p.productID = f.productID
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
WHERE 
  DAYOFWEEK(d.DATE_ID) IN (1, 7)  
GROUP BY 
  p.productID, p.productName
ORDER BY 
  TotalSales DESC
LIMIT 5;



SELECT 
  p.productID, 
  d.Quarter,
  SUM(f.SALE) AS QuarterlySales,
  SUM(SUM(f.SALE)) OVER (PARTITION BY p.productID) AS YearlySales
FROM 
  PRODUCTS p
  JOIN FACTS f ON p.productID = f.productID
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
WHERE 
  YEAR(d.DATE_ID) = 2019
GROUP BY 
  p.productID, d.Quarter
ORDER BY 
  p.productID, d.Quarter;


CREATE MATERIALIZED VIEW STOREANALYSIS_MV AS
SELECT 
  s.storeID,
  f.productID,
  SUM(f.SALE) AS STORE_TOTAL
FROM 
  STORES s
  JOIN FACTS f ON s.storeID = f.storeID
GROUP BY 
  s.storeID, f.productID;



SELECT 
  d.DATE_ID,
  SUM(f.SALE) AS TotalSales
FROM 
  STORES s
  JOIN FACTS f ON s.storeID = f.storeID
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
WHERE 
  s.storeName = 'Tech Haven'
GROUP BY 
  d.DATE_ID;



SELECT 
  f.CustomerID,
  COUNT(DISTINCT f.productID) AS UniqueProducts,
  SUM(f.SALE) AS TotalSales
FROM 
  FACTS f
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
WHERE 
  YEAR(d.DATE_ID) = 2019
GROUP BY 
  f.CustomerID
ORDER BY 
  TotalSales DESC
LIMIT 5;



CREATE MATERIALIZED VIEW CUSTOMER_STORE_SALES_MV AS
SELECT 
  s.storeID,
  f.CustomerID,
  d.DATE_ID,
  SUM(f.SALE) AS MonthlySales
FROM 
  STORES s
  JOIN FACTS f ON s.storeID = f.storeID
  JOIN DATES d ON f.DATE_ID = d.DATE_ID
GROUP BY 
  s.storeID, f.CustomerID, d.DATE_ID;

