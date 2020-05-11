package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.model.Receipt;

/**
 * This is the printer that prints out the receipt. In this seminar it only prints the receipt in the terminal.
 */
public class Printer {

    /**
     * Prints receipt.
     *
     * @param receipt is the receipt that shall be printed.
     */
    public void printReceipt(Receipt receipt){
        System.out.print(receipt);
    }
}
