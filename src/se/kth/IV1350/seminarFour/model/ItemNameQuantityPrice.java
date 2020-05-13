package se.kth.IV1350.seminarFour.model;

public class ItemNameQuantityPrice {
    private String name;
    private int quantity;
    private double price;
        ItemNameQuantityPrice(String name, int quantity, double price){
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

    @Override
    public String toString() {
        return quantity + " * " + this.name + "\t" + price + "kr ";
    }
}
