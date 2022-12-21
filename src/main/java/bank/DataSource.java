package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

  // An object that represent a specific customer from the database
  public static Customer getCustomer(String username) {
    // get customer database

    /*  
      * We can append the username String to the SQL query but we should never send a raw user input as part of a database query. 
      * Because this could be a malicious input used in an SQL injection attack that is meant to steal data.
      * Instead we used a question mark (?) as a placeholder for the username.
    */
    String sql = "SELECT * FROM customers WHERE username = ?";
    Customer customer = null;
    try (Connection connection = connect();

    // Because we have a placeholder on our sql query the object for this is PreparedStatement which is part of the java.sql package.
    // PreparedStatement are AutoCloseable so we can use the try-with-resources statement to automatically close the connection.
    PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username); // We use the setString() method to set the value of the first placeholder to the username String.

      // ResultSet are also AutoCloseable so we can use it again with the try-with-resources statement to automatically close the connection.
      try(ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
          resultSet.getInt("id"), 
          resultSet.getString("name"), 
          resultSet.getString("username"),
          resultSet.getString("password"),
          resultSet.getInt("account_Id"));
      } 

    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    return customer;
  }

  // Construct account from data source
  public static Account getAccount(int account_id) {
    // get account database
    String sql = "SELECT * FROM accounts WHERE id = ?";
    Account account = null; 

    try(Connection connection = connect();

    PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setInt(1, account_id);

      try(ResultSet resultSet = statement.executeQuery()) {
        account = new Account(
          resultSet.getInt("id"), 
          resultSet.getString("type"), 
          resultSet.getDouble("balance"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return account;
    
  }
  

  public static void main (String[] args) {
   Customer customer = getCustomer("clillea8@nasa.gov"); 
   System.out.println(customer.getName());

   Account account = getAccount(customer.getAccountId()); 
   System.out.println(account.getBalance());        
  }

}
