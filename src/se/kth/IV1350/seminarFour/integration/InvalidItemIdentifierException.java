package se.kth.IV1350.seminarFour.integration;

/**
 * This exception is thrown when the item identifier does not correspond to any existing items identifier in the
 * external inventory system.
 */
public class InvalidItemIdentifierException extends Exception {
    private final String message;

    /**
     * Creates a new instance of the class and stores a message to the user.
     * @param message the message to the user.
     */
    InvalidItemIdentifierException(String message) {
        this.message = message;
    }

    /**
     * @return the exception message.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
