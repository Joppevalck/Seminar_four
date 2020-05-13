package se.kth.IV1350.seminarFour.integration;

import se.kth.IV1350.seminarFour.model.DiscountCalculator;
import se.kth.IV1350.seminarFour.model.ItemAndQuantity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Composite class that combines two discounts, in this seminar it is hardcoded to discount one and two.
 */
public class CombinedDiscount implements DiscountCalculator {
    private ArrayList<DiscountCalculator> calculators = new ArrayList<>();

    CombinedDiscount() {
        calculators.add(new BananaDiscount());
        calculators.add(new TwoBooksForOneDiscount());
    }

    /**
     * Combines two calculators, Banana and Books discount, and applies the discounts.
     * @param itemInventory is the sale's items that is going to get a discount.
     */
    @Override
    public void calculate(HashMap<String, ItemAndQuantity> itemInventory) {

        for (DiscountCalculator discCalc : calculators)
            discCalc.calculate(itemInventory);
    }
}
