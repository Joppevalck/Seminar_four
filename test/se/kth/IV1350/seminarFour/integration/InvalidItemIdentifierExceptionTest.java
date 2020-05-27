package se.kth.IV1350.seminarFour.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.controller.SaleNotStartedException;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class InvalidItemIdentifierExceptionTest {
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
    public void testException() {
        ctrl.saleStart();
        ctrl.endSale();
        assertThrows(InvalidItemIdentifierException.class, () -> {
            ctrl.registerItem(new ScannedItemDTO(42348,2));
        });
    }

}