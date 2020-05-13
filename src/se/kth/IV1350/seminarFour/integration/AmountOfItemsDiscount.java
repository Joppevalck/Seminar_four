package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.model.DiscountCalculator;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;

import java.util.HashMap;

/**
 * This class calculates a discount based on the amount of items.
 */
public class AmountOfItemsDiscount implements DiscountCalculator {

    AmountOfItemsDiscount(){}

    /**
     * Calculates discount of sale based on amount of items. 0-9 items equals 0%, 10-19 equals 10%, 20-29 equals 20%
     * and above 30 equals 30% discount.
     *
     * @param itemInventory is the sale inventory that will be given a discount.
     */
    @Override
    public void calculate(HashMap<String, ItemAndQuantity> itemInventory) {

        int totalQuantity = 0;
        for (ItemAndQuantity item : itemInventory.values())
            totalQuantity += item.getQuantity();

        double discountPercentage;
        switch (totalQuantity/10){
            case 0:
                discountPercentage = 0;
                break;
            case 1:
                discountPercentage = 0.1;
                break;
            case 2:
                discountPercentage = 0.2;
                break;
            default:
                discountPercentage = 0.3;
                break;
        }

        for (ItemAndQuantity item : itemInventory.values())
            item.getItem().applyDiscountPercent(discountPercentage);

    }
}
