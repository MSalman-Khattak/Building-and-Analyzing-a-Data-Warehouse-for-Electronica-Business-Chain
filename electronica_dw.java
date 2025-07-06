package dataWH;

import java.sql.*;
import java.util.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
public class electronica_dw {

	public static void main(String[] args) {
		  Scanner input = new Scanner(System.in);
	        System.out.println("Enter Your Username to connect with MYSQL: ");
	        String username = input.next();
	        System.out.println("Enter Password: ");
	        String password = input.next();
	        System.out.println("Enter Schema Name: ");
	        String schema = input.next();

	        try {
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, username, password);
	            System.out.println("SUCCESSFULLY CONNECTED TO DATABASE ");
	            System.out.println(" HybridJoin IMPLEMENTING.......KINDLY WAIT");

	            while (true) {
	            	System.out.println("helooooooo ");
	            	
	                int mdPartition = 0;
	                int numberOfPartitions = 10;
	                ArrayBlockingQueue<List<List<String>>> queue = new ArrayBlockingQueue<>(numberOfPartitions);

	                int totalRows = 1000 + 450;
	               
	                MultiValuedMap<String, List> hashmap = new ArrayListValuedHashMap<>();

	                Statement query0 = connection.createStatement();
	                String mdQuery0 = "select count(*) from FACTS";
	                PreparedStatement q0 = connection.prepareStatement(mdQuery0);
	                ResultSet r0 = q0.executeQuery();
	                r0.next();
	                String check = r0.getString("count(*)");
	                if (check.equals("1000")) {
	                    System.out.println("DATA READ FROM TRANSACTION TABLE.......\nNOW esc FROM LOOP");
	                    break;
	                }

	                for (int iter2 = 0; iter2 < totalRows; iter2 = iter2 + 50) {
	                    Statement query = connection.createStatement();
	                    String mdQuery = "SELECT * FROM transactions LIMIT " + iter2 + ", 50";
	                    PreparedStatement q = connection.prepareStatement(mdQuery);
	                    ResultSet r = q.executeQuery();

	                    List<String> s = new ArrayList<>();
	                    List<List<String>> ss = new ArrayList<>();
	                    ss.clear();
	                    s.clear();

	                    while (r.next()) {
	                        s.add(r.getString("OrderID"));
	                        s.add(r.getString("ProductID"));
	                        ss.clear();
	                        ss.add(s);

	                        List<String> s1 = new ArrayList<>();
	                        s1.add(r.getString("OrderID"));
	                        s1.add(r.getString("OrderDate"));
	                        s1.add(r.getString("ProductID"));
	                        s1.add(r.getString("CustomerID"));
	                        s1.add(r.getString("CustomerName"));
	                        s1.add(r.getString("Gender"));
	                        s1.add(r.getString("QuantityOrdered"));
	                        
	                        hashmap.put(r.getString("ProductID"), s1);
	                    }


	                    if (queue.remainingCapacity() > 0) {
	                        queue.offer(ss);
	                    } else {
	                        List<String> temp = new ArrayList<>();
	                        temp = queue.take().get(0);
	                        
	           

	                        for (int ii = 0; ii < 50; ii = ii + 2) {
	                            List<List> rem = new ArrayList<>(hashmap.get(temp.get(ii + 1)));
	                            
	                          
	                            for (int j = 0; j < rem.size(); j++) {
	                                if (rem.get(j).get(0).equals(temp.get(ii))) {
	                                    try {
	                                    	 
	                                        Statement ins = connection.createStatement();
	                                        
	                                        String insQuery = "INSERT INTO customers(CustomerID, CustomerName, Gender) VALUES('"
	                                                + rem.get(j).get(3) + "','" + rem.get(j).get(4) + "','"+rem.get(j).get(5)+"')";
	                                        //System.out.print("First");
	                                        PreparedStatement q1 = connection.prepareStatement(insQuery);
	                                        q1.execute();
	                                        
//	                                        System.out.print("last");
//	                                        System.out.print(rem.get(j).get(3));
	                                    } catch (Exception e) {
	                                    }
	                                    
	                                    try {
	                                    	 
	                                        Statement ins = connection.createStatement();
	                                        
	                                        String insQuery = "INSERT INTO orders(OrderID, OrderDate, QuantityOrdered) VALUES('"
	                                                + rem.get(j).get(0) + "','" + rem.get(j).get(1) + "','"+rem.get(j).get(6)+"')";
	                                       // System.out.print("First");
	                                        PreparedStatement q1 = connection.prepareStatement(insQuery);
	                                        q1.execute();
	                                        
//	                                        System.out.print("last");
//	                                        System.out.print(rem.get(j).get(3));
	                                    } catch (Exception e) {
	                                    }
	                            
	                            
	                                    
	                                    try {
	                                        Statement ins = connection.createStatement();
	                                        String insQuery = "INSERT INTO DATES(DATE_ID) VALUES('"
	                                                + rem.get(j).get(1) + "')";
	                                   
	                                        PreparedStatement q1 = connection.prepareStatement(insQuery);
	                                        q1.execute();
	                                    } catch (Exception e) {
	                                    }
	                                    hashmap.removeMapping(temp.get(ii + 1), rem.get(j));
	                                    break;
	                                }
	                            }
	                        }
	                    }

	                    Statement query1 = connection.createStatement();
	                    String mdQuery1 = "SELECT * FROM masterdata LIMIT " + mdPartition * numberOfPartitions + ","
	                            + numberOfPartitions;
	                    PreparedStatement q1 = connection.prepareStatement(mdQuery1);
	                    ResultSet r1 = q1.executeQuery();

	                    while (r1.next()) {
	                        String match = (r1.getString("productID"));
	                        try {
	                            Statement ins = connection.createStatement();
	                            String insQuery = "INSERT INTO PRODUCTS(productID, productName, productPrice) VALUES('"+ r1.getString("productID") + "','" + r1.getString("productName") + "',"
	                                    + r1.getString("productPrice") + "')";
	                            
	                            ;
	                                                        
	                            
                                 //System.out.print(r1.getString("productID"));
									
									  //System.out.print(r1.getString("productName"));
									  //System.out.print(r1.getString("productPrice"));
									 


	                            PreparedStatement q11 = connection.prepareStatement(insQuery);
	                            q11.execute();
                                //System.out.print("last1111");

	                        } catch (Exception e) {
	                        }
	                        try {
	                            Statement ins = connection.createStatement();
	                            String insQuery = "INSERT INTO suppliers(supplierID, supplierName) VALUES('"
	                                    + r1.getString("supplierID") + "','" + r1.getString("supplierName") + "')";
	                            ;
                             // System.out.print("First1111111111");

	                            PreparedStatement q11 = connection.prepareStatement(insQuery);
	                            q11.execute();

	                        } catch (Exception e) {
	                        }

	                        if (hashmap.get(match).size() > 0) {
	                            List<List> rem = new ArrayList<>(hashmap.get(match));
	                            for (int i = 0; i < hashmap.get(match).size(); i++) {
	                                Float sale;
	                                Float price;
	                                String productPriceString = r1.getString("productPrice").replaceAll("[^\\d.]", ""); // Remove non-numeric characters
	                                price = Float.parseFloat(productPriceString);

	                                sale = Float.parseFloat((String) rem.get(i).get(6)) * price;

	                                String insQuery = "INSERT INTO facts(DATE_ID, ProductID, storeID, supplierID, CustomerID, OrderID, SALE) VALUES('"
	                                		 + r1.getString("ProductID") + "','"+ r1.getString("ProductID") + "','"+ r1.getString("storeID") + "','"+ r1.getString("supplierID") + "','"+ r1.getString("ProductID") + "','"+ r1.getString("ProductID") + "','"+sale + ")";
	                                		 
//	                                        + rem.get(i).get(5)+ "','"+match+"','"
//	                                        + rem.get(i).get(4)+ "','" +r1.getString("supplierID")+ "','"
//	                                        + rem.get(i).get(2)+ "','" +rem.get(i).get(0)+ "'," +rem.get(i).get(0)+ "','"+sale+ ")";
	                                
	                                System.out.print(rem.get(i).get(0));
	                                System.out.print("     ");

	                                try {
	                                    Statement ins = connection.createStatement();
	                                    PreparedStatement q11 = connection.prepareStatement(insQuery);
	                                    q11.execute();
	                                } catch (Exception e) {
	                                    try {
	                                        Statement ins = connection.createStatement();
	                                        PreparedStatement q11 = connection.prepareStatement(insQuery);
	                                        q11.execute();
	                                    } catch (Exception ee) {
	                                    }
	                                }
	                            }
	                        }
	                    }

	                    if (mdPartition < 9) {
	                        mdPartition = mdPartition + 1;
	                    } else {
	                        mdPartition = 0;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
