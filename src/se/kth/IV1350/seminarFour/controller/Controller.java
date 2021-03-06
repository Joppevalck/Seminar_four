package se.kth.IV1350.seminarFour.controller;

import se.kth.IV1350.seminarFour.DTOPackage.CustomerID;
import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.*;
import se.kth.IV1350.seminarFour.model.*;
import se.kth.IV1350.seminarFour.view.EndOfSaleView;
import se.kth.IV1350.seminarFour.view.ScannedItemView;
import se.kth.IV1350.seminarFour.view.TotalRevenueView;

import java.util.ArrayList;
import java.util.List;

/**
 *  This is the applications controller. All calls to the model goes through this class.
 */
public class Controller {
    private ExternalSystemCreator exSysCreator;

    private SaleLog saleLog;
    private Register register;
    private Sale sale;

    /**
     * Creates a new instance of the class Controller. It also creates an instance of SaleLog and Register.
     *
     * @param exSysCreator Contains all external systems that Controller needs.
     */
    public Controller(ExternalSystemCreator exSysCreator){
        this.exSysCreator = exSysCreator;
        this.saleLog = new SaleLog();
        this.register = new Register();

    }

    /**
     * Calls for a new sale to start. Creates an instance of the sale class. Adds the given observers.
     *
     * @param observers list of observers that are observing the sale started.
     */
    public void saleStart(List<SaleObserver> observers){
        this.sale = new Sale();
        this.sale.addObservers(observers);
    }

    /**
     * Calls for a new sale to start. Creates an instance of the sale class.
     */
    public void saleStart(){
        this.sale = new Sale();
    }

    /**
     * Sends a request to add a item with the item identifier and quantity of the item.
     *
     * @param scannedItem contains the item identifier and quantity.
     * @throws SaleNotActiveException is throw when there is no active sale to modify.
     * @throws InvalidItemIdentifierException is thrown when an invalid item identifier is registered.
     * @throws SaleNotStartedException is thrown when the sale has not started.
     */
    public void registerItem(ScannedItemDTO scannedItem) throws SaleNotActiveException, InvalidItemIdentifierException,
            SaleNotStartedException {
        try{
            addNewItem(scannedItem);
        } catch (NullPointerException Ne){
            throw new SaleNotStartedException();
        }

    }

    /**
     * Calls the sale to end the sale.
     */
    public void endSale(){
        sale.endSale();
    }

    /**
     * Wraps up the sale and updates the sale information of how much the customer paid.
     *
     * @param amountPaid what the customer paid.
     * @return the change for the customer.
     */
    public double payment(int amountPaid){
        CompletedSale completedSale = sale.payment(amountPaid);
        createAndPrintReceipt(completedSale);
        updateExternalAndInternalSystems(completedSale);
        return completedSale.getChange();
    }

    /**
     * @return the running total and VAT total for the sale.
     */
    public RevenueDTO getRevenue() {
        return sale.getRevenue();
    }

    /**
     * @return the last scanned and added item to the current sale.
     */
    public ItemAndQuantity getLastItem() {
        return this.sale.getLastItem();
    }

    /**
     * @return the state of the sale.
     */
    public boolean isSaleActive(){
        return sale.isSaleActive();
    }

    private ItemDTO getItem(ScannedItemDTO scannedItem) throws InvalidItemIdentifierException {
            return ExternalInventorySystem.getInventorySystem().getItemInformation(scannedItem);
    }

    private ItemAndQuantity mergeItemAndQuantity(ItemDTO item, ScannedItemDTO scannedItem){
        return new ItemAndQuantity(item, scannedItem.getQuantity());
    }

    private void addNewItem(ScannedItemDTO scannedItem) throws SaleNotActiveException, InvalidItemIdentifierException {
        ItemDTO item = getItem(scannedItem);
        ItemAndQuantity itemAndQuantity = mergeItemAndQuantity(item, scannedItem);
        sale.addItemToSale(itemAndQuantity);
    }

    private void updateExternalAndInternalSystems(CompletedSale completedSale){
        exSysCreator.getExAccSys().updateAccounting(completedSale);
        ExternalInventorySystem.getInventorySystem().updateInventory(completedSale);
        register.updateRegisterCash(completedSale);
        saleLog.logCompletedSale(completedSale);
    }

    private void createAndPrintReceipt(CompletedSale completedSale) {
        Receipt receipt = new Receipt(completedSale);
        exSysCreator.getPrinter().printReceipt(receipt);
    }

    /**
     * Applies the discounts available for the specified customer ID.
     *
     * @param customerID the customer ID that will show which discounts that are available.
     *
     * @throws NoDiscountsException if the given customer ID does not have any discounts available.
     * @throws SaleActiveException if the sale is active and not discount applicable.
     */
    public void discount(CustomerID customerID) throws NoDiscountsException, SaleActiveException {
            sale.discount(customerID);
    }
}
