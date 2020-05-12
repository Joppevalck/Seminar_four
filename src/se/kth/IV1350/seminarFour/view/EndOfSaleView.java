package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.model.SaleObserver;

/**
 * This class presents the end total for the current sale.
 */
public class EndOfSaleView implements SaleObserver {
    private RevenueDTO revenue;
    private Controller ctrl;

    /**
     * Creates an instance of the class. Sets a controller to get information from.
     *
     * @param ctrl the controller that the class will get information from.
     */
    public EndOfSaleView(Controller ctrl) {
        this.ctrl = ctrl;
    }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void updatedSaleInventory() {

    }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void updatedSalePrice() {

    }

    /**
     * When sale is ended this class will present the end total.
     */
    @Override
    public void notifyEndOfSale() {
        this.revenue = this.ctrl.getRevenue();
        System.out.println("Amount to pay: " + (revenue.getRunningTotal() + revenue.getVAT()) + "kr \n");
    }
}
