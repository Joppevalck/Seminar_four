package se.kth.IV1350.seminarFour.DTOPackage;

import java.util.ArrayList;
import java.util.List;

public class DiscountDTO {
    private List<Integer> discountID = new ArrayList<>();

    public DiscountDTO(int discountID){
        this.discountID.add(discountID);
    }

    public List<Integer> getDiscounts() {
        return this.discountID;
    }

    public void addDiscount(int discountID){
        this.discountID.add(discountID);
    }
}
