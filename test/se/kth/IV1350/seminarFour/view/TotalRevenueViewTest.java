package se.kth.IV1350.seminarFour.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;
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
        ctrl.saleStart();

        ctrl.registerItem(new ScannedItemDTO(2,4));
        printour
        assertTrue();
    }

    private ItemDTO specificScannedItem(int itemID, int quantity) throws InvalidItemIdentifierException {
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return exSysCreator.getExInvSys().getItemInformation(scannedItem);
    }

}