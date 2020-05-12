package se.kth.IV1350.seminarFour.integration;

public class NoDiscountsException extends Exception {
    private final String message;

    public NoDiscountsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
