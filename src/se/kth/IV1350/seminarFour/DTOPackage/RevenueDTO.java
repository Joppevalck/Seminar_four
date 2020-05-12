package se.kth.IV1350.seminarFour.DTOPackage;

public class RevenueDTO {
    private double runningTotal;
    private double VAT;

    public RevenueDTO(double runningTotal, double VAT){
        this.runningTotal = runningTotal;
        this.VAT = VAT;
    }

    public double getRunningTotal() {
        return runningTotal;
    }

    public double getVAT() {
        return VAT;
    }
}
