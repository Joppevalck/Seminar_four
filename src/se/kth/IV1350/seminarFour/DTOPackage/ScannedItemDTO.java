package se.kth.IV1350.seminarFour.DTOPackage;

public class ScannedItemDTO {
    private int itemID;
    private int quantity;

    public ScannedItemDTO(int itemID, int quantity){
        this.itemID = itemID;
        this.quantity = quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public int getQuantity() {
        return quantity;
    }


}
