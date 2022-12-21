package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
  
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null; 

    try {
      // * We need to use try & catch block as the getConnection() method throws an exception
      connection =  DriverManager.getConnection(db_file);
      System.out.println("We're Connected!");
    } 
    
    catch(SQLException e) {
      /* 
      * We can also print out the stack trace of the exception using printStackTrace() method.
      * This will print out the actual exception that was thrown. 
      */
      e.printStackTrace();
    }

    return connection;
    
  }

  public static void main (String[] args) {
    connect(); 
  }
}
