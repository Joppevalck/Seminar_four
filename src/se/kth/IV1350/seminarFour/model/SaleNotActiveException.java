package se.kth.IV1350.seminarFour.model;

public class SaleNotActiveException extends Exception {
    private String message;

    public SaleNotActiveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
