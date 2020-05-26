package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;
import se.kth.IV1350.seminarFour.model.SaleObserver;

/**
 * This class presents the end total for the current sale.
 */
public class EndOfSaleView implements SaleObserver {
    private RevenueDTO revenue;

    /**
     * Creates an instance of the class. Sets a controller to get information from.
     */
    EndOfSaleView() {
            }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void updatedSaleInventory(ItemAndQuantity lastItem) {

    }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void updatedSalePrice(RevenueDTO revenue) {

    }

    /**
     * When sale is ended this class will present the end total.
     */
    @Override
    public void notifyEndOfSale(RevenueDTO revenue) {
        this.revenue = revenue;
        double totalAmount = revenue.getRunningTotal() + revenue.getVAT();
        double roundOff = Math.round(totalAmount * 100.0) / 100.0;
        System.out.println("Amount to pay: " + roundOff + "kr \n");
    }
}
