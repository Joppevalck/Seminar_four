package se.kth.IV1350.seminarFour.model;

public class StoreLocation {
    private String addressOfStore;
    private String nameOfStore;

    public StoreLocation(String addressOfStore, String nameOfStore){
        this.addressOfStore = addressOfStore;
        this.nameOfStore = nameOfStore;
    }

    @Override
    public String toString() {
        return nameOfStore + "\t" + addressOfStore;
    }

    String getAddressOfStore() {
        return addressOfStore;
    }

    String getNameOfStore() {
        return nameOfStore;
    }
}
