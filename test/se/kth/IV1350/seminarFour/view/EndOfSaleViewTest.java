package se.kth.IV1350.seminarFour.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.controller.SaleNotStartedException;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;
import se.kth.IV1350.seminarFour.model.SaleObserver;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EndOfSaleViewTest {
    private List<SaleObserver> observers;
    private Controller ctrl;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;
    private ExternalSystemCreator exSysCreator;

    @BeforeEach
    public void setUp() {
        exSysCreator = new ExternalSystemCreator();
        ctrl = new Controller(exSysCreator);
        observers = new ArrayList<>();
        observers.add(new EndOfSaleView());

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    public void tearDown(){
        ctrl = null;
        observers = null;
        exSysCreator = null;

        printoutBuffer = null;
        System.setOut(originalSysOut);
    }

    @Test
    public void testAmountToPay() throws InvalidItemIdentifierException, SaleNotActiveException,
            SaleNotStartedException {
        double total = 0;
        ctrl.saleStart(observers);
        total += registerNewItemTotal(2,4);
        total += registerNewItemTotal(3,8);
        total += registerNewItemTotal(1,10);


        ctrl.endSale();
        String printout = printoutBuffer.toString();
        assertTrue(printout.contains("Amount to pay: " + roundOff(total) ), "Wrong amount to pay. Expected Amount: " +
                total + " Printed: " + printout);
    }

    @Test
    public void testEndOfSaleNoticed() throws InvalidItemIdentifierException, SaleNotActiveException {
        ctrl.saleStart(observers);
        ctrl.endSale();
        String printout = printoutBuffer.toString();
        String expectedPrintout = "Amount to pay: ";
        assertTrue(printout.contains(expectedPrintout), "End of sale did not notify observer.");
    }

    private ItemDTO getItemInformation(ScannedItemDTO scannedItem) throws InvalidItemIdentifierException {
        return ExternalInventorySystem.getInventorySystem().getItemInformation(scannedItem);
    }

    private double registerNewItemTotal(int itemID, int quantity) throws InvalidItemIdentifierException,
            SaleNotActiveException, SaleNotStartedException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID,quantity);
        ctrl.registerItem(scannedItem);
        ItemDTO item = getItemInformation(scannedItem);
        return itemTotalPrice(item, quantity) + itemTotalVAT(item, quantity);
    }

    private double itemTotalPrice(ItemDTO item, int quantity) {
        return item.getPrice() * quantity;
    }

    private double itemTotalVAT(ItemDTO item, int quantity) {
        return item.getVAT() * item.getPrice() * quantity;
    }

    private double roundOff(double price){
        return Math.round(price * 100.0) / 100.0;
    }

}