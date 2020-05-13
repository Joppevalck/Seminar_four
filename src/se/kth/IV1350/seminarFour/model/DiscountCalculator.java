package se.kth.IV1350.seminarFour.model;

import java.util.HashMap;

public interface DiscountCalculator {

    public void calculate(HashMap<String, ItemAndQuantity> itemInventory);

}
