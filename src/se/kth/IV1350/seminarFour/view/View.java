package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;

/**
 * This is a replacement or a faked version for the real view. It contains hardcoded execution to all call all system
 * operation in the controller
 */
public class View {
    private Controller ctrl;
    private String updatedSaleInfo;

    /**
     * Creates an instance of View, keeps track of an instance of the class Controller.
     *
     * @param ctrl is the controller that view is going to call.
     */
    public View(Controller ctrl){
        this.ctrl = ctrl;
    }

    /**
     * Simulates a sale, calls all public methods from the Controller class.
     */
    public void runFakeExecution(){
        runFakeSaleStart();
        runFakeRegisterItem(1, 2);
        runFakeRegisterItem(3, 3);
        runFakeRegisterItem(6,6);
        runFakeRegisterItem(69,69);

        runFakeEndSale();
        runFakeRegisterItem(2, 1);
        runFakePayment(200);

    }

    private void runFakeSaleStart(){
        ctrl.saleStart();
        System.out.println("A new sale has started.\n");
    }

    private void runFakeRegisterItem(int itemID, int quantity){
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        try {
            this.ctrl.registerItem(scannedItem);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void runFakeEndSale(){
        ctrl.endSale();
    }

    private void runFakePayment(int amountPaid){
        double change = ctrl.payment(amountPaid);
        System.out.println("\nChange: " + change);
    }

    private void runFakeDiscountSignal(int customerID){
        ctrl.discount(customerID);
    }
}
