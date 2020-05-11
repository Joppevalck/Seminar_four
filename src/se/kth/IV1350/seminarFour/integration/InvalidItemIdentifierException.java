package se.kth.IV1350.seminarFour.integration;

public class InvalidItemIdentifierException extends java.lang.RuntimeException {
    private final String message;

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
