package se.kth.IV1350.seminarFour.view;

import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.model.Observer;

public class TotalRevenueView implements Observer {
    private int runningTotal;
    private Controller ctrl;

    public TotalRevenueView(Controller ctrl){
        this.ctrl = ctrl;
        this.runningTotal = 0;
    }

    @Override
    public void newPrice() {
        this.runningTotal = ctrl.getRunningTotal();
        System.out.println("=======================\nTotalRevenueView: "+runningTotal+"\n=======================\n");
    }
}
