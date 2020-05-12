package se.kth.IV1350.seminarFour.DTOPackage;

/**
 * This class resembles an item and contains the information about it.
 */
public class ItemDTO {
    private String itemDescription;
    private int itemID;
    private int price;
    private double VAT;

    /**
     * Creates an instance of the class. It sets the attribute's value.
     *
     * @param itemDescription is the items name.
     * @param itemID is the item identifier.
     * @param price is the price of the item.
     * @param VAT is the VAT rate of the item.
     */
    public ItemDTO(String itemDescription, int itemID, int price, double VAT){
        this.itemDescription = itemDescription;
        this.itemID = itemID;
        this.price = price;
        this.VAT = VAT;
    }

    /**
     * Gets the item's description
     *
     * @return the item's description
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Gets the item's identifier.
     * @return the item's identifier.
     */
    public int getItemID() {
        return itemID;
    }
    /**
     * Gets the item's price.
     * @return the item's price
     */
    public int getPrice() {
        return price;
    }
    /**
     * Gets the item's VAT rate.
     * @return the item's VAT rate.
     */
    public double getVAT() {
        return VAT;
    }
}
