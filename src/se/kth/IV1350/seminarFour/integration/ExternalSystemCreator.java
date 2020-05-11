package se.kth.IV1350.seminarFour.integration;

/**
 * The External System Creator that creates the external systems. It contains the ExternalAccountingSystem, the
 * ExternalInventorySystem and the printer.
 */
public class ExternalSystemCreator {
    private ExternalAccountingSystem exAccSys;
    private ExternalInventorySystem exInvSys;

    private Printer printer;

    /**
     * Creates a new instance of ExternalSystemCreator. It creates all external systems used for the application. All
     * systems are hardcoded.
     */
    public ExternalSystemCreator(){
        this.exAccSys = new ExternalAccountingSystem();
        this.exInvSys = new ExternalInventorySystem();

        this.printer = new Printer();
    }

    public ExternalAccountingSystem getExAccSys() {
        return exAccSys;
    }

    public ExternalInventorySystem getExInvSys() {
        return exInvSys;
    }

    public Printer getPrinter() {
        return printer;
    }
}
