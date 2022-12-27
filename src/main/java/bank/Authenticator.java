package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {

  public static Customer login(String username, String password) throws LoginException {
    /* 
     * Get the getCustomer from the DataSource class
     * Because the method is static we don't want to create an instance of a data source object to access the method.
     * We can use the class name and the dot operator to access the getCustomer method.
     */
   Customer customer = DataSource.getCustomer(username);
   if (customer == null) {
    throw new LoginException("Username not found");
   }


   // * We use here the equals method to compare the password rather than equal equal operator that compares the memory location. Because when comparing objects it's safer to use the equals method as it compares the value of the objects.
   if (password.equals(customer.getPassword())) {
      customer.setAuthenticated(true);
      return customer;
   } 

   else throw new LoginException("Invalid password");

  }

  // The logout method accept the customer object as a parameter
  public static void logout(Customer customer) {
    customer.setAuthenticated(false); 
  }
}
