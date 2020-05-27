package se.kth.IV1350.seminarFour.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.CustomerID;
import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.controller.SaleNotStartedException;
import se.kth.IV1350.seminarFour.model.SaleActiveException;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BananaDiscountTest {
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
    public void testRightPrice() throws InvalidItemIdentifierException, SaleNotActiveException, SaleNotStartedException,
            NoDiscountsException, SaleActiveException {
        ctrl.saleStart();
        ctrl.registerItem(new ScannedItemDTO(1,3));
        ctrl.registerItem(new ScannedItemDTO(2,4));
        ctrl.registerItem(new ScannedItemDTO(1,7));
        ctrl.endSale();
        double totalBefore = getTotal();
        ctrl.discount(new CustomerID(0));
        double totalAfter = getTotal();
        assertEquals((totalBefore - (banana(10) *  0.7)), totalAfter,
                "Not right amount after calculation of discount");
    }

    private double getTotal(){
        RevenueDTO revenue = ctrl.getRevenue();
        return revenue.getRunningTotal() + revenue.getVAT();
    }

    private double banana(int quantity) throws InvalidItemIdentifierException {
        return 5 * (1 + 0.12) * quantity;
    }

    private ItemDTO getItem(int itemID, int quantity) throws InvalidItemIdentifierException {

        return ExternalInventorySystem.getInventorySystem().getItemInformation(new ScannedItemDTO(itemID, quantity));
    }
}