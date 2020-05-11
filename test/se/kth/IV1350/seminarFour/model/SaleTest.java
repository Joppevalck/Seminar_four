package se.kth.IV1350.seminarFour.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;

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
    public void testAddAllItems(){
        for (int i = 1; i < 6; i++){
            ItemAndQuantity itemAndQuantity = createItemAndQuantity(i,i);
            String printout = instanceToTest.addItemToSale(itemAndQuantity).toString();
            String expectedOutput = i + "*";
            assertTrue(printout.contains(expectedOutput),
                    "Item " + i + " did not get added.");
        }
    }

    @Test
    public void testEndSale(){
        ItemAndQuantity itemAndQuantity = createItemAndQuantity(1,1);
        instanceToTest.addItemToSale(itemAndQuantity).toString();
        instanceToTest.endSale();
        String printout = instanceToTest.getSaleInformation().toString();
        String expectedOutput = "No item registered";
        assertTrue(printout.contains(expectedOutput),
                "EndSale did not null lastItemAdded." );
    }

    private ItemAndQuantity createItemAndQuantity(int itemID, int quantity){
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return new ItemAndQuantity(exInvSys.getItemInformation(scannedItem), quantity);
    }

}