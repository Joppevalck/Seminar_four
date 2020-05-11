package se.kth.IV1350.seminarFour.model;


/**
 * This is the main operator for the sale, all calls regarding the sale goes through this class.
 */
public class Sale {
    private SaleInformation saleInfo;
    private boolean saleActive;

    /**
     * Initializes a new sale. Creates a new instance of sale.
     */
    public Sale(){
        initSale();
    }

    /**
     * Initializes a new sale. Creates a new instance of sale and adds an observer.
     *
     * @param observer is the class that observes sale.
     */
    public Sale(Observer observer){
        initSale();
        saleInfo.addObserver(observer);
    }


    /**
     * Adds a new item to the sale information and the inventory.
     * @param itemAndQuantity is the item the quantity that is going to be added.
     * @return an updated sale information.
     */
    public SaleInformation addItemToSale(ItemAndQuantity itemAndQuantity){
        if(saleActive)
            return saleInfo.addItem(itemAndQuantity);
        return saleInfo;
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
     * Calls to end the sale, notices the sale information class and inactivates the sale.
     * @return the amount that the customers has to pay for the items.
     */
    public double endSale(){
        saleActive = false;
        saleInfo.setLastItemAddedToNull();
        return saleInfo.getAmountToPay();
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

    public int getRunningTotal() {
        return saleInfo.getRunningTotal();
    }

    private void initSale(){
        this.saleInfo = new SaleInformation();
        this.saleActive = true;
    }


}
