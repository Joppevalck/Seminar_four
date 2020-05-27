package se.kth.IV1350.seminarFour.integration;

/**
 * This exception is thrown when the item identifier does not correspond to any existing items identifier in the
 * external inventory system.
 */
public class InvalidItemIdentifierException extends Exception {
    private final int itemID;

    /**
     * Creates a new instance of the class and stores a item identifier.
     * @param itemID is the identifier that was invalid
     */
    InvalidItemIdentifierException(int itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the invalid item ID.
     */
    public int getItemID() {
        return itemID;
    }
}
