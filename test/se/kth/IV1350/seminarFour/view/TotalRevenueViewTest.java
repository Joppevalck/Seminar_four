package se.kth.IV1350.seminarFour.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TotalRevenueViewTest {
    private Controller ctrl;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;
    private ExternalSystemCreator exSysCreator;

    @BeforeEach
    public void setUp() {
        exSysCreator = new ExternalSystemCreator();
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
    public void testRunningTotal() throws InvalidItemIdentifierException, SaleNotActiveException {
        double runningTotal = 0;
        ctrl.saleStart();

        runningTotal += registerNewItemRunningTotal(2,4);
        checkRunningTotal(runningTotal);
        runningTotal += registerNewItemRunningTotal(3,8);
        checkRunningTotal(runningTotal);
        runningTotal += registerNewItemRunningTotal(1,10);
        checkRunningTotal(runningTotal);
    }

    @Test
    public void testTotalVAT() throws InvalidItemIdentifierException, SaleNotActiveException {
        double VAT = 0;
        ctrl.saleStart();

        VAT += registerNewItemVAT(2,4);
        checkTotalVAT(VAT);
        VAT += registerNewItemVAT(3,8);
        checkTotalVAT(VAT);
        VAT += registerNewItemVAT(1,10);
        checkTotalVAT(VAT);
    }

    private ItemDTO specificScannedItem(int itemID, int quantity) throws InvalidItemIdentifierException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return ExternalInventorySystem.getInventorySystem().getItemInformation(scannedItem);
    }

    private ItemDTO getItemInformation(ScannedItemDTO scannedItem) throws InvalidItemIdentifierException {
        return ExternalInventorySystem.getInventorySystem().getItemInformation(scannedItem);
    }

    private double registerNewItemRunningTotal(int itemID, int quantity) throws InvalidItemIdentifierException,
            SaleNotActiveException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID,quantity);
        ctrl.registerItem(scannedItem);
        return getItemInformation(scannedItem).getPrice() * quantity;
    }

    private void checkRunningTotal(double runningTotal){
        String expectedOutput = "Total: " + runningTotal;
        assertTrue(printoutBuffer.toString().contains(expectedOutput),
                "Total was wrong, expected total: " + runningTotal + " printed: \n" +
                        printoutBuffer.toString());
    }

    private double registerNewItemVAT(int itemID, int quantity) throws InvalidItemIdentifierException,
            SaleNotActiveException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID,quantity);
        ctrl.registerItem(scannedItem);
        ItemDTO item = getItemInformation(scannedItem);
        return item.getVAT() * item.getPrice() * quantity;
    }

    private void checkTotalVAT(double VAT){
        String expectedOutput = "VAT Total: " + VAT;
        assertTrue(printoutBuffer.toString().contains(expectedOutput),
                "Total was wrong, expected total VAT: " + VAT + " printed: \n" +
                        printoutBuffer.toString());
    }
}