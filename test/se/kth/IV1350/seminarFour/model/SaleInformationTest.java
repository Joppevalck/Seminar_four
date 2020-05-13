package se.kth.IV1350.seminarFour.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleInformationTest {
    private SaleInformation instanceToTest;
    private ExternalInventorySystem exInvSys;

    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        exInvSys = ExternalInventorySystem.getInventorySystem();
        instanceToTest = new SaleInformation();

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
    public void testToString() throws InvalidItemIdentifierException {

        String expectedOutput = "No item registered";
        assertTrue(instanceToTest.toString().contains(expectedOutput),
                "lastItemAdded was not null.");
        ItemAndQuantity item = createItemAndQuantity(1, 1);
        instanceToTest.addItem(item);
        expectedOutput = "1*" + item.getItem().getItemDescription();
        assertTrue(instanceToTest.toString().contains(expectedOutput),
                "lastItemAdded was null.");
    }

        @Test
    public void testAddAllItems() throws InvalidItemIdentifierException {

        for (int i = 1; i < 6; i++){
            ItemAndQuantity itemAndQuantity = createItemAndQuantity(i,i);
            instanceToTest.addItem(itemAndQuantity);
            assertEquals(itemAndQuantity,instanceToTest.getLastItemAdded(),
                    "Item " + i + " did not get added.");
        }
    }

    @Test @Disabled
    public void testRunningTotal() throws InvalidItemIdentifierException {

        int runningTotal = 0;
        for (int i = 0; i < 15; i++){
            ItemAndQuantity itemAndQuantity = createItemAndQuantity((i%5)+1,(i%5)+1);
            instanceToTest.addItem(itemAndQuantity);
            runningTotal += ((i%5)+1)*itemAndQuantity.getItem().getPrice();
            assertEquals(runningTotal, instanceToTest.getRunningTotal(),
                    "Running Total incorrect after item nr " + (i + 1) + ".");
        }
    }

    private ItemAndQuantity createItemAndQuantity(int itemID, int quantity) throws InvalidItemIdentifierException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return new ItemAndQuantity(exInvSys.getItemInformation(scannedItem), quantity);
    }

}