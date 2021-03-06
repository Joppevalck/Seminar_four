package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;
import se.kth.IV1350.seminarFour.model.SaleObserver;

/**
 * This class presents information of the last scanned item in the current sale.
 */
public class ScannedItemView implements SaleObserver {
    private int quantity;
    private ItemDTO item;

    /**
     * Creates an instance of the class and sets the controller that the class will get information from.
     */
    ScannedItemView(){
    }

    /**
     * Presents the last scanned item description, price, quantity and VAT in the UI.
     */
    @Override
    public void updatedSaleInventory(ItemAndQuantity lastItem){

        this.item = lastItem.getItem();
        this.quantity = lastItem.getQuantity();
        printLastItemAdded();
    }

    private void printLastItemAdded() {
        String separator = "=======================\n";
        System.out.println(separator +
                this.quantity + " * " + this.item.getItemDescription() + "\t" + this.quantity + " * " +
                this.item.getPrice() + "kr\t" + this.quantity * this.item.getPrice() + "kr\n" +
                "VAT: " + this.item.getVAT()*100 + "%\n");
    }


    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void updatedSalePrice(RevenueDTO revenue) {
    }

    /**
     * Null block, will get notified but will not do anything.
     */
    @Override
    public void notifyEndOfSale(RevenueDTO revenue) {
    }
}
