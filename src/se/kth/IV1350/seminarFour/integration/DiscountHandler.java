package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.DTOPackage.CustomerID;
import se.kth.IV1350.seminarFour.model.DiscountCalculator;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;

import java.util.HashMap;

/**
 * This class is a hardcoded database handler that handles the discounts.
 */
public class DiscountHandler {
    private static final DiscountHandler discountHandler = new DiscountHandler();
    DiscountCalculator discCalc;

    private DiscountHandler(){}

    /**
     * @return the only instance of the class.
     */
    public static DiscountHandler getDiscountHandler() {
        return discountHandler;
    }

    /**
     * Matches the specified customer identifier with a discount and applies it.
     *
     * @param customerID the customer identifier that will be checked for discounts available.
     * @param itemInventory is the item inventory of the sale.
     *
     * @return the discounts available for the customer identifier.
     *
     * @throws NoDiscountsException if there is no discounts available for the customer identifier.
     */
    public void applyDiscount(CustomerID customerID, HashMap<String, ItemAndQuantity> itemInventory) throws NoDiscountsException {

        switch ( (customerID.getCustomerID()+5)/5 ){
            case 1:
                discCalc = new BananaDiscount();
                break;
            case 2:
                discCalc = new TwoBooksForOneDiscount();
                break;
            case 3:
                discCalc = new CombinedDiscount();
                break;
            case 4:
                discCalc = new AmountOfItemsDiscount();
                break;
            default:
                throw new NoDiscountsException("The given customer ID did not have any discounts available");
        }
        discCalc.calculate(itemInventory);
    }
}
