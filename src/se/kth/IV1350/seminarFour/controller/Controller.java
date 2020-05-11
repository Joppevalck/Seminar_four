package se.kth.IV1350.seminarFour.controller;

import se.kth.IV1350.seminarFour.DTOPackage.ItemDTO;
import se.kth.IV1350.seminarFour.DTOPackage.ScannedItemDTO;
import se.kth.IV1350.seminarFour.integration.*;
import se.kth.IV1350.seminarFour.model.*;
import se.kth.IV1350.seminarFour.view.TotalRevenueView;

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

    @Override
    public String toString() {
        return sale.isSaleActive() ? "Sale is active" : "Sale is not active";
    }

    /**
     * Calls for a new sale to start. Creates an instance of the sale class.
     */
    public void saleStart(){
        TotalRevenueView TotRevView = new TotalRevenueView(this);
        this.sale = new Sale(TotRevView);
    }

    /**
     * Sends a request to add a item with the item identifier and quantity of the item.
     * @param scannedItem contains the item identifier and quantity.
     * @return the updates sale information.
     */
    public SaleInformation registerItem(ScannedItemDTO scannedItem){
        if(sale == null){
            return null;
        }
        else if(sale.isSaleActive()){
            return addNewItem(scannedItem);
        }
        else{
            return sale.getSaleInformation();
        }
    }

    /**
     * Calls the sale to end the sale.
     * @return what the customer has to pay for the item(s).
     */
    public double endSale(){
        return sale.endSale();
    }

    /**
     * Wraps up the sale and updates the sale information of how much the customer paid.
     * @param amountPaid what the customer paid.
     * @return the change for the customer.
     */
    public double payment(int amountPaid){
        CompletedSale completedSale = sale.payment(amountPaid);
        createAndPrintReceipt(completedSale);
        updateExternalAndInternalSystems(completedSale);
        return completedSale.getChange();
    }

    public SaleInformation getSaleInformation(){
        return sale.getSaleInformation();
    }
    public int getRunningTotal() {
        return sale.getRunningTotal();
    }

    private ItemDTO getItem(ScannedItemDTO scannedItem) {
            return exSysCreator.getExInvSys().getItemInformation(scannedItem);
    }

    private ItemAndQuantity mergeItemAndQuantity(ItemDTO item, ScannedItemDTO scannedItem){
        return new ItemAndQuantity(item, scannedItem.getQuantity());
    }

    private SaleInformation addNewItem(ScannedItemDTO scannedItem){
        ItemDTO item = getItem(scannedItem);
        ItemAndQuantity itemAndQuantity = mergeItemAndQuantity(item, scannedItem);
        return sale.addItemToSale(itemAndQuantity);
    }

    private void updateExternalAndInternalSystems(CompletedSale completedSale){
        exSysCreator.getExAccSys().updateAccounting(completedSale);
        exSysCreator.getExInvSys().updateInventory(completedSale);
        register.updateRegisterCash(completedSale);
        saleLog.logCompletedSale(completedSale);
    }

    private void createAndPrintReceipt(CompletedSale completedSale) {
        Receipt receipt = new Receipt(completedSale);
        exSysCreator.getPrinter().printReceipt(receipt);
    }



}
