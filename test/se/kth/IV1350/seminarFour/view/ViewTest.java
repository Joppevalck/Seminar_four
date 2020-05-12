package se.kth.IV1350.seminarFour.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewTest {
    private View instanceToTest;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;
    private ExternalSystemCreator exSysCreator;

    @BeforeEach
    public void setUp() {
        exSysCreator = new ExternalSystemCreator();
        Controller ctrl = new Controller(exSysCreator);
        instanceToTest = new View(ctrl);

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
    public void testRunFakeExecutionStartSale(){
        instanceToTest.runFakeExecution();
        String printout = printoutBuffer.toString();

        testStartSale();
    }

    @Test
    public void testRunFakeExecutionRegisterItem() throws InvalidItemIdentifierException {
        instanceToTest.runFakeExecution();
        String printout = printoutBuffer.toString();

        testAddedItems();
        testRunningTotal();
    }

    @Test
    public void testRunFakeExecutionEndSale(){
        instanceToTest.runFakeExecution();
        String printout = printoutBuffer.toString();

        testEndSale();
    }

    private void testStartSale(){
        String expectedOutput = "started";
        assertTrue(printoutBuffer.toString().contains(expectedOutput),
                "UI did not start correctly.");
    }

    private void testAddedItems() throws InvalidItemIdentifierException {
        expectedRegItemID(1);
        expectedRegItemID(3);
    }

    private void expectedRegItemID(int itemID) throws InvalidItemIdentifierException {
        ScannedItemDTO scannedItemDTO = new ScannedItemDTO(itemID, 1);
        ItemDTO item = exSysCreator.getExInvSys().getItemInformation(scannedItemDTO);
        assertTrue(printoutBuffer.toString().contains(item.getItemDescription()),
                "Item " + itemID + " did not get added.");
    }

    private void testRunningTotal(){
        expectedRunningPrice(10);
        expectedRunningPrice(157);

    }

    private void expectedRunningPrice(double price){
        String expectedOutput = "Total: " + price + "kr";
        assertTrue(printoutBuffer.toString().contains(expectedOutput),
                "Running Total for price " + price + "kr not correct.");
    }

    private void testEndSale(){
        String expectedOutput = "Amount to pay: ";
        assertTrue(printoutBuffer.toString().contains(expectedOutput),
                "EndSale did not execute properly.");
    }

}