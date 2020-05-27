package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.DTOPackage.CustomerID;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.controller.SaleNotStartedException;
import se.kth.IV1350.seminarFour.integration.InvalidItemIdentifierException;
import se.kth.IV1350.seminarFour.integration.InventorySystemFailureException;
import se.kth.IV1350.seminarFour.integration.NoDiscountsException;
import se.kth.IV1350.seminarFour.model.SaleActiveException;
import se.kth.IV1350.seminarFour.model.SaleNotActiveException;
import se.kth.IV1350.seminarFour.model.SaleObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a replacement or a faked version for the real view. It contains hardcoded execution to all call all system
 * operation in the controller
 */
public class View {
    private Controller ctrl;
    private List<SaleObserver> observers = new ArrayList<>();

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
    public void runFakeExecution() {
        runFakeSaleStart();
        runFakeRegisterItem(1, 2);
        runFakeRegisterItem(3, 3);
        runFakeRegisterItem(6,6);
        runFakeRegisterItem(69,69);

        runFakeEndSale();

        runFakeRegisterItem(2, 1);
        runFakePayment(200);


    }
    /**
     * Simulates a sale, calls all public methods from the Controller class, including the discount feature.
     */
    public void runFakeExecutionWithDiscount() {
        runFakeSaleStart();
        runFakeRegisterItem(1, 2);
        runFakeRegisterItem(3, 3);
        runFakeRegisterItem(6,6);
        runFakeRegisterItem(69,69);

        runFakeEndSale();

        runFakeDiscountSignal(11);

        runFakeRegisterItem(2, 1);
        runFakePayment(200);


    }

    private void runFakeSaleStart(){
        addObservers();
        ctrl.saleStart(observers);
        System.out.println("A new sale has started.\n");
    }

    private void addObservers() {
        observers.add(new TotalRevenueView());
        observers.add(new ScannedItemView());
        observers.add(new EndOfSaleView());
    }

    private void runFakeRegisterItem(int itemID, int quantity){
        ScannedItemDTO scannedItem = new ScannedItemDTO(itemID, quantity);
        try {
            this.ctrl.registerItem(scannedItem);
        }catch (SaleNotActiveException e) {
            System.out.println("The sale is not active\n");
        }catch (SaleNotStartedException e){
            System.out.println("Sale not started, can not add items yet\n");
        }catch (InvalidItemIdentifierException e){
            System.out.println("Item ID ("+e.getItemID()+") is invalid. \n");
        }catch (InventorySystemFailureException e){
            System.out.println(e.getMessageToDeveloper());
            System.out.println("Could not get information from database, please check your connection and try again\n");
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
        try {
            ctrl.discount(new CustomerID(customerID));
        }catch(SaleActiveException e) {
            System.out.println("Sale is active, can not apply discount.\n");
        }catch(NoDiscountsException e){
            System.out.println("The given customer ID did not have any discounts available.\n");
        }
    }
}
