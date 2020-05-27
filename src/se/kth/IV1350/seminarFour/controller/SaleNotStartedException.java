package se.kth.IV1350.seminarFour.controller;

/**
 * This exception is thrown when there is no sale that has started and the user has tried to modify a non existing sale.
 */
public class SaleNotStartedException extends Throwable {
    /**
     * Creates an instance of this class.
     */
    SaleNotStartedException() {
    }
}
