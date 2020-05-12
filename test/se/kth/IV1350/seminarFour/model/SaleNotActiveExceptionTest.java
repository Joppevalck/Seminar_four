package se.kth.IV1350.seminarFour.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class SaleNotActiveExceptionTest {
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
    public void testRightPrintout() throws InvalidItemIdentifierException {
        ctrl.saleStart();
        ctrl.endSale();
        String expectedOutput = "The sale is not active";
        try {
            ctrl.registerItem(new ScannedItemDTO(1, 1));
        }catch(SaleNotActiveException e){
            assertTrue(e.getMessage().contains(expectedOutput), "Wrong exception message");
        }
    }

    @Test
    public void testException() {
        ctrl.saleStart();
        ctrl.endSale();
        assertThrows(SaleNotActiveException.class, () -> {
            ctrl.registerItem(new ScannedItemDTO(2,2));
        });
    }

}