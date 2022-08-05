package GE.GestoreEventi;

import java.sql.*;

public class Jdbc {
   static final String DB_URL = "jdbc:mysql://localhost:3306";
   static final String USER = "root";
   static final String PASS = "pass";
   static final String QUERY_SELECT_ALL = "SELECT * FROM logs_gestoreeventi.log";
   static final String QUERY_INSERT_LOG = "INSERT INTO logs_gestoreeventi.log ( idlog, dataora )VALUES( DEFAULT, NOW() );";
   
   public void selectAll() {
      // Open a connection
      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(QUERY_SELECT_ALL);) {
         // Extract data from result set
         while (rs.next()) {
            // Retrieve by column name
            System.out.print("ID: " + rs.getInt("idlog"));
            System.out.print(", IP: " + rs.getString("IP"));
            System.out.println(", dataora: " + rs.getString("dataora"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } 
   }
   
   public void insertLog() {
	      // Open a connection
	      try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	         Statement stmt = conn.createStatement();) {
	    	  stmt.execute(QUERY_INSERT_LOG);
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } 
	   }
}