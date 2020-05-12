package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.DTOPackage.DiscountDTO;

/**
 * This class is a hardcoded database handler that handles the discounts.
 */
public class DiscountHandler {
    private static final DiscountHandler discountHandler = new DiscountHandler();

    private DiscountHandler(){}

    /**
     * @return the only instance of the class.
     */
    public static DiscountHandler getDiscountHandler() {
        return discountHandler;
    }

    /**
     * Matches the specified customer identifier with a discount.
     *
     * @param customerID the customer identifier that will be checked for discounts available.
     *
     * @return the discounts available for the customer identifier.
     *
     * @throws NoDiscountsException if there is no discounts available for the customer identifier.
     */
    public DiscountDTO getDiscountID(int customerID) throws NoDiscountsException {
        switch ( (customerID+3)/3 ){
            case 1:
                return new DiscountDTO(1);
            case 2:
                return new DiscountDTO(2);
            case 3:
                DiscountDTO discountDTO = new DiscountDTO(3);
                discountDTO.addDiscount(2);
                return discountDTO;
            default:
                throw new NoDiscountsException("The given customer ID did not have any discounts available");
        }
    }
}
