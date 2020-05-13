package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.model.DiscountCalculator;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;

import java.util.HashMap;

/**
 * This class calculates a discount based on the quantity of books.
 */
public class TwoBooksForOneDiscount implements DiscountCalculator {

    TwoBooksForOneDiscount(){}

    /**
     * Calculates the discount and applies it based on the quantity of books. Two books for the price of one.
     * @param itemInventory
     */
    @Override
    public void calculate(HashMap<String, ItemAndQuantity> itemInventory) {

        if(itemInventory.containsKey("Book")){
            ItemAndQuantity item = itemInventory.get("Book");
            double discountPrice = getItemDiscount(item);
            item.getItem().applyDiscountPrice(discountPrice);
        }
    }
    private double getItemDiscount(ItemAndQuantity item){
        return (item.getItem().getPrice() * (double)(item.getQuantity()/2))/item.getQuantity();
    }
}
