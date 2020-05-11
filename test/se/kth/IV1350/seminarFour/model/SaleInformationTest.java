package se.kth.IV1350.seminarFour.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleInformationTest {
    private SaleInformation instanceToTest;
    private ExternalInventorySystem exInvSys;

    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        exInvSys = new ExternalInventorySystem();
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
    public void testToString() {

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
    public void testAddAllItems(){

        for (int i = 1; i < 6; i++){
            ItemAndQuantity itemAndQuantity = createItemAndQuantity(i,i);
            String printout = instanceToTest.addItem(itemAndQuantity).toString();
            String expectedOutput = i + "*" + itemAndQuantity.getItem().getItemDescription();
            assertTrue(printout.contains(expectedOutput),
                    "Item " + i + " did not get added.");
        }
    }

    @Test
    public void testRunningTotal(){

        int runningTotal = 0;
        for (int i = 0; i < 15; i++){
            ItemAndQuantity itemAndQuantity = createItemAndQuantity((i%5)+1,(i%5)+1);
            String printout = instanceToTest.addItem(itemAndQuantity).toString();
            runningTotal += ((i%5)+1)*itemAndQuantity.getItem().getPrice();
            assertTrue(printout.contains(runningTotal + ""),
                    "Running Total incorrect after item nr " + i + ".");
        }
    }

    private ItemAndQuantity createItemAndQuantity(int itemID, int quantity){
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return new ItemAndQuantity(exInvSys.getItemInformation(scannedItem), quantity);
    }

}