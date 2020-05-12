package se.kth.IV1350.seminarFour.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class InventorySystemFailureExceptionTest {
    private Controller ctrl;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        ExternalSystemCreator exSysCreator = new ExternalSystemCreator();
        ctrl = new Controller(exSysCreator);

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    public void tearDown(){
        ctrl = null;
        printoutBuffer = null;
        System.setOut(originalSysOut);
    }

    @Test
    public void testRightPrintout() throws SaleNotActiveException, InvalidItemIdentifierException {
        ctrl.saleStart();
        ctrl.endSale();
        ScannedItemDTO scannedItem = new ScannedItemDTO(69, 1);
        String expectedOutputToDev = "To developer: Could not get item information from inventory";
        String expectedOutputToUser = "Could not get information from database, please check your connection and try" +
                " again";
        try {
            ctrl.registerItem(scannedItem);
        }catch(InventorySystemFailureException e){
            assertTrue(e.getMessageToDeveloper().contains(expectedOutputToDev), "Wrong exception message");
            assertTrue(e.getMessage().contains(expectedOutputToUser), "Wrong exception message");
        }
    }

    @Test
    public void testException() {
        ctrl.saleStart();
        ctrl.endSale();
        assertThrows(InventorySystemFailureException.class, () -> {
            ctrl.registerItem(new ScannedItemDTO(69,2));
        });
    }


}