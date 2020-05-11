package se.kth.IV1350.seminarFour.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.ExternalInventorySystem;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReceiptTest {
    private Receipt instanceToTest;
    private Sale sale;
    private ExternalInventorySystem exInvSys;

    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    public void setUp() {
        sale = new Sale();
        exInvSys = new ExternalInventorySystem();

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    public void tearDown(){
        instanceToTest = null;
        sale = null;

        printoutBuffer = null;
        System.setOut(originalSysOut);
    }

    @Test
    public void testRightPrint(){
        sale.addItemToSale(createItemAndQuantity(1,2));
        sale.addItemToSale(createItemAndQuantity(3,3));
        sale.endSale();
        instanceToTest = new Receipt(sale.payment(1000));

        String printout = instanceToTest.toString();
        String expectedOutput = "3 * Book\t49kr \n2 * Banana\t5kr";
        assertTrue(printout.contains(expectedOutput),
                "Receipt Print was not right" );

    }


    private ItemAndQuantity createItemAndQuantity(int itemID, int quantity){
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        return new ItemAndQuantity(exInvSys.getItemInformation(scannedItem), quantity);
    }

}