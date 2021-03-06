package se.kth.IV1350.seminarFour.model;

import se.kth.IV1350.seminarFour.DTOPackage.CustomerID;
import se.kth.IV1350.seminarFour.DTOPackage.RevenueDTO;
import se.kth.IV1350.seminarFour.integration.NoDiscountsException;

import java.util.List;

/**
 * This is the main operator for the sale, all calls regarding the sale goes through this class.
 */
public class Sale {
    private SaleInformation saleInfo;
    private boolean saleActive;
    private DiscountCalculator discountCalculator;

    /**
     * Initializes a new sale. Creates a new instance of sale.
     */
    public Sale(){
        initSale();
    }

    /**
     * Adds a new item to the sale information and the inventory.
     *
     * @param itemAndQuantity is the item the quantity that is going to be added.
     */
    public void addItemToSale(ItemAndQuantity itemAndQuantity) throws SaleNotActiveException {
        if(saleActive)
            saleInfo.addItem(itemAndQuantity);
        else{
            throw new SaleNotActiveException();
        }
    }

    /**
     * Checks if the sale is active.
     * @return the state of the sale.
     */
    public boolean isSaleActive() {
        return saleActive;
    }

    /**
     * Gets the sale's information.
     * @return the sale's information.
     */
    public SaleInformation getSaleInformation(){
        return saleInfo;
    }

    /**
     * Calls to end the sale, notifies the sale information class and inactivates the sale.
     */
    public void endSale(){
        saleActive = false;
        saleInfo.setLastItemAddedToNull();
        saleInfo.notifyEndOfSale();
    }

    /**
     * Calls to inform that the customer has paid and wraps up the sale. It prints the receipt and updates all system
     * associated to the completed sale.
     * @param amountPaid is the amount that the customer paid.
     * @return the completed sales information.
     */
    public CompletedSale payment(int amountPaid){
        return saleInfo.completeSale(amountPaid);
    }

    /**
     * @return the running total and VAT total for the sale.
     */
    public RevenueDTO getRevenue() {
        return saleInfo.getRevenue();
    }

    /**
     * Adds a specified observer to observe changes in this class.
     *
     * @param observer the observer that is going to get notified when there has been a change in the sale.
     */
    public void addObserver(SaleObserver observer){
        saleInfo.addObserver(observer);
    }

    /**
     * Adds multiple observers to observe changes in this class.
     *
     * @param observers the observers that is going to get notified when there has been a change in the sale.
     */
    public void addObservers(List<SaleObserver> observers) {
        for(SaleObserver observer : observers)
            saleInfo.addObserver(observer);
    }

    private void initSale(){
        this.saleInfo = new SaleInformation();
        this.saleActive = true;
    }

    /**
     * @return the last item scanned and added to the sale inventory.
     */
    public ItemAndQuantity getLastItem() {
        return getSaleInformation().getLastItemAdded();
    }

    /**
     * Applies a discount for the specified customer ID if there is any.
     *
     * @param customerID is the specefied customer ID.1
     *
     * @throws NoDiscountsException is thrown when there is no discount to be applied.
     * @throws SaleActiveException is thrown when the sale is still active and not able to apply the discount.
     */
    public void discount(CustomerID customerID) throws NoDiscountsException, SaleActiveException {

        if(!saleActive) {
            this.saleInfo.discount(customerID);
            this.saleInfo.notifyEndOfSale();
        }else{
            throw new SaleActiveException();
        }
    }
}
