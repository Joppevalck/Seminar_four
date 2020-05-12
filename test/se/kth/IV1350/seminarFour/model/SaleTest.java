package se.kth.IV1350.seminarFour.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleTest {
    private Sale instanceToTest;
    private ExternalInventorySystem exInvSys;

    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        exInvSys = new ExternalInventorySystem();
        instanceToTest = new Sale();

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    public void tearDown(){
        instanceToTest = null;
        exInvSys = null;

        printoutBuffer = null;
        System.setOut(originalSysOut);
    }

    @Test
    public void testAddAllItems() throws SaleNotActiveException, InvalidItemIdentifierException {
        for (int i = 1; i < 6; i++){
            ItemAndQuantity itemAndQuantity = createItemAndQuantity(i,i);
            instanceToTest.addItemToSale(itemAndQuantity);
            ItemAndQuantity item = instanceToTest.getLastItem();

            assertTrue(checkLastItem(item, i),
                    "Item " + i + " did not get added.");
        }
    }

    @Test
    public void testEndSale() throws SaleNotActiveException, InvalidItemIdentifierException {
        ItemAndQuantity itemAndQuantity = createItemAndQuantity(1,1);
        instanceToTest.addItemToSale(itemAndQuantity);
        instanceToTest.endSale();
        String printout = instanceToTest.getSaleInformation().toString();
        String expectedOutput = "No item registered";
        assertTrue(printout.contains(expectedOutput),
                "EndSale did not null lastItemAdded." );
    }

    private ItemAndQuantity createItemAndQuantity(int itemID, int quantity) throws InvalidItemIdentifierException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return new ItemAndQuantity(exInvSys.getItemInformation(scannedItem), quantity);
    }
    private boolean checkLastItem(ItemAndQuantity item, int i){
        return item.getItem().getItemID() == i && item.getQuantity() == i;
    }
}