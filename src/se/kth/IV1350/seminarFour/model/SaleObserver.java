package se.kth.IV1350.seminarFour.model;

import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;

/**
 * This interface is to notify classes that there has been an update in the current sale.
 */
public interface SaleObserver {
    /**
     * This will be called when there has been an change in the sale inventory.
     */
    void updatedSaleInventory(ItemAndQuantity lastItem);

    /**
     * This will be called when there has been an change in the sales price.
     */
    void updatedSalePrice(RevenueDTO revenue);

    /**
     * This will be called when the sale has been ended.
     */
    void notifyEndOfSale(RevenueDTO revenue);
}
