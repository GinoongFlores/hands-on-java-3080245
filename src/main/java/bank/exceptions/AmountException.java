package bank.exceptions;

// Extend or inherit a custom exception on java's built-in exceptions.
public class AmountException extends Exception {
  
  public AmountException(String message) {
    super(message);
  }
  
}
