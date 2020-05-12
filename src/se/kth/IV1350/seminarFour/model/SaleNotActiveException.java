package se.kth.IV1350.seminarFour.model;

/**
 * This exception is thrown when the user is trying to call some functionality to the sale when it is not active.
 */
public class SaleNotActiveException extends Exception {
    private String message;

    /**
     * Creates an instance of the class and stores an exception message for the user.
     * @param message is the message to the user.
     */
    public SaleNotActiveException(String message) {
        this.message = message;
    }

    /**
     * @return the message to the user.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
