package se.kth.IV1350.seminarFour.model;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * This contains all information from a completed sale.
 */
public class CompletedSale {

    private LocalDateTime saleDateAndTime;
    private StoreLocation storeLocation;
    private HashMap<String, ItemAndQuantity> itemInventory;
    private double runningTotal;
    private int amountPaid;
    private double change;
    private double VAT;


    /**
     * Creates an instance of CompletedSale, which resembles a completed version of the sale.
     *
     * @param saleInfo is the completed sale that CompletedSale will resemble.
     * @param amountPaid is what the customer paid.
     */
    CompletedSale(SaleInformation saleInfo, int amountPaid) {
        this.saleDateAndTime = saleInfo.getSaleDateAndTime();
        this.storeLocation = saleInfo.getStoreLocation();
        this.itemInventory = saleInfo.getItemInventory();
        this.runningTotal = saleInfo.getRunningTotal();
        this.amountPaid = amountPaid;
        this.change = (int)(this.amountPaid-(this.runningTotal+this.VAT) + 0.5);
        this.VAT = saleInfo.getVAT();

    }



    LocalDateTime getSaleDateAndTime() {
        return saleDateAndTime;
    }

    StoreLocation getStoreLocation() {
        return storeLocation;
    }

    HashMap<String, ItemAndQuantity> getItemInventory() {
        return itemInventory;
    }

    double getRunningTotal() {
        return runningTotal;
    }

    int getAmountPaid() {
        return amountPaid;
    }

    /**
     * Returns the calculated change for the customer.
     * @return the change.
     */
    public double getChange() {
        return change;
    }

    double getVAT() {
        return VAT;
    }
}
