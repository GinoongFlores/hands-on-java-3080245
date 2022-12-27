package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {

  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Flores Bank!");

    // We need to create an instance of scanner to access the methods of the Scanner class. 
    Menu menu = new Menu(); 

    // instantiate the scanner by calling its constructor
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if(customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }

    menu.scanner.close(); 
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username: ");
    String username = scanner.next(); 

    System.out.println("Please enter your password: ");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch(LoginException e) {
      System.out.println("There was an error" + e.getMessage());
    }

    return customer;
  }

  public void showMenu(Customer customer, Account account) {
    
    int selection = 0;

    /* 
     * User interfaces are 
     * 1. Deposit 
     * 2. Withdraw
     * 3. Check Balance
     * 4. Exit
     */

    // We want to let the user to make as many transaction as they want to. Therefore, the best option is a while loop. To keep the user in the menu until they choose to exit.

    while(selection != 4 && customer.isAuthenticated()) {
      System.out.println("==================================");
      System.out.println("Please select an option: ");

      System.out.println("1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
      System.out.println("==================================");

      selection = scanner.nextInt();
      double amount = 0;

      switch(selection) {
        case 1:
          System.out.println("How much you would like to deposit?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch(AmountException e) {
            System.out.println("Please try again");
          }
          break;

        case 2: 
          System.out.println("How much you would like to withdraw?");
          amount = scanner.nextDouble();
          account.withdraw(amount);
          break;  
        
        case 3: 
          System.out.println("Current balance: " + account.getBalance());
          break;

        case 4: 
          Authenticator.logout(customer);
          System.out.println("Thanks for banking at Flores Bank!");
        
        default: 
          System.out.println("Invalid selection. Please try again.");

      }

    }

  }
}
