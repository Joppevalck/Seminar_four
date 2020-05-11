package se.kth.IV1350.seminarFour.integration;

public class InventorySystemFailureException extends java.lang.RuntimeException {
    private final String message;

    public InventorySystemFailureException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
