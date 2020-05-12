package se.kth.IV1350.seminarFour.startup;

import se.kth.IV1350.seminarFour.controller.Controller;
import se.kth.IV1350.seminarFour.integration.ExternalSystemCreator;
import se.kth.IV1350.seminarFour.view.TotalRevenueView;
import se.kth.IV1350.seminarFour.view.View;

/**
 * This is the start sequence for the application. It contains the main method to initialize the application.
 */
public class Main {

    /**
     * The main method, that is used to initialize the application. Creates the View and controller layer.
     *
     * @param args The application does not take any initial parameters.
     */
    public static void main(String[] args){
        ExternalSystemCreator exSysCreator = new ExternalSystemCreator();
        Controller ctrl = new Controller(exSysCreator);
        View view = new View(ctrl);
        view.runFakeExecution();
    }
}
