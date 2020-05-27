package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.model.CompletedSale;
import java.time.LocalDateTime;


/**
 * This class resembles a database where all the item information of the store lies. In this seminar it will be
 * hardcoded with a few items.
 */
public class ExternalInventorySystem {
    private static final ExternalInventorySystem exInvSys = new ExternalInventorySystem();

    private final ItemDTO[] items = { new ItemDTO("Banana", 1, 5, 0.12),
            new ItemDTO("Soda", 2, 15, 0.12),
            new ItemDTO("Book", 3, 49, 0.06),
            new ItemDTO("Frying Pan", 4, 299, 0.25),
            new ItemDTO("Bread", 5, 29, 0.12)};

    private ExternalInventorySystem(){}

    /**
     * This will get the iteminformation from the hardcoded database.
     *
     * @param scannedItem contains the item identifier which will select which item information will return.
     *
     * @return resembles an item and the information about it.
     *
     * @throws InvalidItemIdentifierException is thrown when an invalid item identifier is registered.
     */
    public ItemDTO getItemInformation(ScannedItemDTO scannedItem) throws InvalidItemIdentifierException {
        int itemID = scannedItem.getItemID();
        if(itemID == 69){
            throw new InventorySystemFailureException("To developer: Could not get item information from inventory " +
                    "database, unable to connect. ItemID: " + itemID + " Date and time: " + LocalDateTime.now() + "\n");
        }
        try{
            return new ItemDTO(this.items[itemID - 1]);
        }catch(ArrayIndexOutOfBoundsException Ae){
            throw new InvalidItemIdentifierException(scannedItem.getItemID());
        }
    }
    
    /**
     * @return the only instance of the class.
     */
    public static ExternalInventorySystem getInventorySystem(){
        return exInvSys;
    }

    private boolean checkItemID(ScannedItemDTO scannedItem){
        return scannedItem.getItemID() > 0 && scannedItem.getItemID() < (items.length+1);
    }

    /**
     * This will update the item inventory after a completed sale.
     * @param completedSale contains the completed sale information.
     */
    public void updateInventory(CompletedSale completedSale) {
        // Code that updates the inventory system.
    }
}
