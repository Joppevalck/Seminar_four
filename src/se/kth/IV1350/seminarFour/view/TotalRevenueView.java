package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.model.SaleObserver;


/**
 * This is the UI for the running total. Displays the running total and total VAT of the current sale.
 */
public class TotalRevenueView implements SaleObserver {
    private double runningTotal;
    private double VAT;
    private Controller ctrl;

    /**
     * Creates an instance of the class. Sets all values to zero and applies a controller to get information from.
     *
     * @param ctrl is the controller that the class will get information from.
     */
    public TotalRevenueView(Controller ctrl){
        this.ctrl = ctrl;
        this.runningTotal = 0;
        this.VAT = 0;
    }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void updatedSaleInventory() {
    }

    /**
     * This method will update the presented running total in the UI. Displays running total and VAT total.
     */
    @Override
    public void updatedSalePrice() {
        RevenueDTO revenue = ctrl.getRevenue();
        this.runningTotal = revenue.getRunningTotal();
        this.VAT = revenue.getVAT();
        String separator = "=======================\n";
        System.out.println("Total: " + this.runningTotal + "kr\tVAT Total: " + this.VAT + "kr\n" +
                separator);
    }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void notifyEndOfSale() {

    }
}
