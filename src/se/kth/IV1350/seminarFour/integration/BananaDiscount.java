package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.model.DiscountCalculator;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;

import java.util.HashMap;

/**
 * This class calculates the discount that the sale is going to give.
 */
public class BananaDiscount implements DiscountCalculator {

    BananaDiscount(){}

    /**
     * Discounts Banana items with 70%.
     * @param itemInventory is the item inventory that will get a discount
     */
    @Override
    public void calculate(HashMap<String, ItemAndQuantity> itemInventory) {

        if(itemInventory.containsKey("Banana")){
            ItemAndQuantity item = itemInventory.get("Banana");
            item.getItem().applyDiscountPercent(0.7);
        }
    }
}
