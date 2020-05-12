package se.kth.IV1350.seminarFour.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;
import se.kth.IV1350.seminarFour.model.SaleInformation;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller instanceToTest;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        ExternalSystemCreator exSysCreator = new ExternalSystemCreator();
        instanceToTest = new Controller(exSysCreator);

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    public void tearDown(){
        instanceToTest = null;
        printoutBuffer = null;
        System.setOut(originalSysOut);
    }


    @Test
    public void testSaleStart(){
        instanceToTest.saleStart();

        assertTrue(instanceToTest.isSaleActive(),
                "Sale did not start correctly");
    }

    @Test
    public void testAddAllItemID() throws InvalidItemIdentifierException, SaleNotActiveException {

        instanceToTest.saleStart();
        for (int i = 1; i < 6; i++){
            ScannedItemDTO scannedItem = new ScannedItemDTO(i, i);
            instanceToTest.registerItem(scannedItem);
            ItemAndQuantity item = instanceToTest.getLastItem();

            assertTrue(checkLastItem(item, i),
                    "Item " + i + " did not get added.");
        }
    }
    @Test
    public void testRegisterItemWithoutSaleStart() throws InvalidItemIdentifierException, SaleNotActiveException {

        ScannedItemDTO scannedItem = new ScannedItemDTO(1, 1);
        instanceToTest.registerItem(scannedItem);
        String printout = printoutBuffer.toString();
        String expectedOutput = "Sale not started";
        assertTrue(printout.contains(expectedOutput),
                "RegisterItem went through without a saleStart.");
    }

    @Test
    public void testInvalidItemID(){
        instanceToTest.saleStart();
        specificScannedItem(0,1);
        specificScannedItem(6,1);
        specificScannedItem(Integer.MAX_VALUE, 1);
        specificScannedItem(Integer.MIN_VALUE, 1);
    }

    @Test
    public void testEndSale(){
        instanceToTest.saleStart();
        instanceToTest.endSale();

        assertFalse(instanceToTest.isSaleActive(),
                "Sale did not end correctly");
    }

    @Test
    public void testEndSaleWithoutStartingASale(){
        Assertions.assertThrows(NullPointerException.class, () -> {
            instanceToTest.endSale();;
        });
    }
    private void specificScannedItem(int itemID, int quantity){
        Assertions.assertThrows(InvalidItemIdentifierException.class, () -> {
            ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
            instanceToTest.registerItem(scannedItem);
        });
    }

    private boolean checkLastItem(ItemAndQuantity item, int i){
        return item.getItem().getItemID() == i && item.getQuantity() == i;
    }
}