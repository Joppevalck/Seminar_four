package se.kth.IV1350.seminarFour.model;

import se.kth.IV1350.seminarFour.DTOPackage.CustomerID;
import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.integration.DiscountHandler;
import se.kth.IV1350.seminarFour.integration.NoDiscountsException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * SaleInformation contains all Information of a sale.
 */
public class SaleInformation {
    private LocalDateTime saleDateAndTime;
    private StoreLocation storeLocation;
    private HashMap<String, ItemAndQuantity> itemInventory;
    private ItemAndQuantity lastItemAdded;
    private double runningTotal;
    private double VAT;

    private List<SaleObserver> saleObservers = new ArrayList<>();

    /**
     * Creates an instance of SaleInformation. Initializes the sale information.
     */
    SaleInformation(){
        setTimeOfSale();
        setStoreLocationOfSale("nameOfStore", "addressOfStore" );
        createItemInventory();
        initMoneyVariables();

    }

    /**
     * Prints the running total and last item's that was registered description and price.
     *
     * @return String formated to print the running total and the item's description and price.
     */
    @Override
    public String toString() {
        return lastItemAdded == null ? "Total price: " + runningTotal + "kr \nNo item registered": "Total price: " + runningTotal +
                "kr\n" + lastItemAdded.getQuantity() + "*" + lastItemAdded.getItem().getItemDescription() + "\t" +
                lastItemAdded.getQuantity() + "*" + lastItemAdded.getItem().getPrice() + "kr \tVAT: "+
                lastItemAdded.getItem().getVAT()*100 + "%";
    }

    /**
     * Notifies the observers that the sale has ended.
     */
    public void notifyEndOfSale() {
        for(SaleObserver observer : this.saleObservers){
            observer.notifyEndOfSale();
        }
    }

    void discount(CustomerID customerID) throws NoDiscountsException {
        DiscountHandler.getDiscountHandler().applyDiscount(customerID, this.itemInventory);
        updatePrice();
    }

    void addObserver(SaleObserver observer){
        this.saleObservers.add(observer);
    }

    void addItem(ItemAndQuantity itemAndQuantity){
        addItemAndQuantity(itemAndQuantity);
        this.lastItemAdded = itemAndQuantity;
        updateInventoryObserver();
        updatePrice();
        updatePriceObserver();
    }

    RevenueDTO getRevenue(){
        return new RevenueDTO(this.runningTotal, this.VAT);
    }

    void setLastItemAddedToNull(){
        lastItemAdded = null;
    }

    double getRunningTotal(){
        return runningTotal;
    }

    CompletedSale completeSale(int amountPaid){
        return  new CompletedSale(this, amountPaid);
    }

    private void setTimeOfSale(){
        this.saleDateAndTime = LocalDateTime.now();
    }

    private void setStoreLocationOfSale(String nameOfStore, String addressOfStore){
        this.storeLocation = new StoreLocation(nameOfStore, addressOfStore);
    }

    private void createItemInventory(){
        this.itemInventory = new HashMap<String, ItemAndQuantity>();
    }

    private void initMoneyVariables(){
        this.runningTotal = 0;
    }

    private void addItemAndQuantity(ItemAndQuantity itemAndQuantity){
        String itemDescription = itemAndQuantity.getItem().getItemDescription();

        if(this.itemInventory.containsKey(itemDescription)){
            ItemAndQuantity prevAddedItem = this.itemInventory.get(itemDescription);
            prevAddedItem.addQuantity(itemAndQuantity.getQuantity());
        }
        else{
            this.itemInventory.put(itemDescription, itemAndQuantity);
        }

    }

    private void updatePrice(){
        ItemAndQuantity itemAndQuantity;
        this.runningTotal = 0;
        for(String itemDescription : this.itemInventory.keySet() ){
            itemAndQuantity = this.itemInventory.get(itemDescription);
            this.runningTotal += itemAndQuantity.getQuantity()*itemAndQuantity.getItem().getPrice();
        }
        calculateVAT();
    }
    private void calculateVAT(){
        this.VAT = 0;
        for (String itemDesc : itemInventory.keySet()){
            this.VAT += getVATfromItem(itemDesc);
        }
    }

    private double getVATfromItem(String itemDesc){
        double VATRate = itemInventory.get(itemDesc).getItem().getVAT();
        double itemPrice = itemInventory.get(itemDesc).getItem().getPrice();
        int quantity = itemInventory.get(itemDesc).getQuantity();
        return itemPrice*VATRate*quantity;
    }

    LocalDateTime getSaleDateAndTime() {
        return saleDateAndTime;
    }

    StoreLocation getStoreLocation() {
        return storeLocation;
    }

    ItemAndQuantity getLastItemAdded() {
        return lastItemAdded;
    }

    double getVAT() {
        return VAT;
    }

    HashMap<String, ItemAndQuantity> getItemInventory() {
        return itemInventory;
    }

    private void updatePriceObserver(){
        for(SaleObserver observer : this.saleObservers){
            observer.updatedSalePrice();
        }
    }
    private void updateInventoryObserver() {
        for(SaleObserver observer : this.saleObservers){
            observer.updatedSaleInventory();
        }
    }
}
