package se.kth.IV1350.seminarFour.model;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * This class gathers all information of the sale that the customer needs. It confirms the sale.
 */
public class Receipt {
    private LocalDateTime saleDateAndTime;
    private StoreLocation storeLocation;
    private ItemNameQuantityPrice[] itemAndPrices;
    private double runningTotal;
    private int amountPaid;
    private double change;
    private double VAT;

    /**
     * Creates an instance of the class Receipt. It stores all information from the completed sale. It creates a array
     * of all the items and prices.
     *
     * @param completedSale the sale that the receipt is going to get the information from.
     */
    public Receipt(CompletedSale completedSale) {
        storeCompletedSaleInformation(completedSale);
        getAllItemInformation(completedSale);
    }

    /**
     * Will print out what is stored in the receipt.
     *
     * @return a string of what the receipt contains.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(saleDateAndTime.toString() + "\n" + storeLocation.toString() + "\n");
        for (ItemNameQuantityPrice itemAndPrice : itemAndPrices)
            sb.append(itemAndPrice).append("\n");
        sb.append("Total price: " + runningTotal + "kr\t" + "VAT: " + VAT + "kr\n" + "Amount paid: " + amountPaid +
                "kr\t" + "Change: " + change + "kr\n");

        return sb.toString();
    }

    private void storeCompletedSaleInformation(CompletedSale compSale){
        this.saleDateAndTime = compSale.getSaleDateAndTime();
        this.storeLocation = compSale.getStoreLocation();
        this.runningTotal = roundOff(compSale.getRunningTotal());
        this.amountPaid = compSale.getAmountPaid();
        this.change = compSale.getChange();
        this.VAT = roundOff(compSale.getVAT());
    }

    private void getAllItemInformation(CompletedSale compSale){
        HashMap<String, ItemAndQuantity> itemInv = compSale.getItemInventory();
        itemAndPrices = new ItemNameQuantityPrice[itemInv.size()];
        int i = 0;
        for(String itemDesc : itemInv.keySet()){
            ItemAndQuantity itemAndQuantity = itemInv.get(itemDesc);
            itemAndPrices[i++] = createItemAndPriceObject(itemAndQuantity);
        }
    }

    private ItemNameQuantityPrice createItemAndPriceObject(ItemAndQuantity itemAndQuantity){
        String name = itemAndQuantity.getItem().getItemDescription();
        int quantity = itemAndQuantity.getQuantity();
        double price = itemAndQuantity.getItem().getPrice();
        return new ItemNameQuantityPrice(name, quantity, price);
    }

    private double roundOff(double price){
        return Math.round(price * 100.0) / 100.0;
    }
}
