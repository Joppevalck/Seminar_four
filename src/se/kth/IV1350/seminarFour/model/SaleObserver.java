package se.kth.IV1350.seminarFour.model;

/**
 * This interface is to notify classes that there has been an update in the current sale.
 */
public interface SaleObserver {
    /**
     * This will be called when there has been an change in the sale inventory.
     */
    public void updatedSaleInventory();

    /**
     * This will be called when there has been an change in the sales price.
     */
    public void updatedSalePrice();

    /**
     * This will be called when the sale has been ended.
     */
    public void notifyEndOfSale();
}
