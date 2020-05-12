package se.kth.IV1350.seminarFour.integration;

/**
 * This exception is thrown when the external inventory system fails.
 */
public class InventorySystemFailureException extends java.lang.RuntimeException {
    private final String messageToDeveloper;
    private final String messageToUser = "Could not get information from database, please check your connection and" +
            " try again\n";

    /**
     * Creates an instance of the class. Sets a message to the developer and prints it.
     * @param messageToDeveloper is the message to the developer.
     */
    public InventorySystemFailureException(String messageToDeveloper) {
        this.messageToDeveloper = messageToDeveloper;
        System.out.println(messageToDeveloper);
    }

    /**
     * @return the message to the user.
     */
    @Override
    public String getMessage() {
        return messageToUser;
    }

    /**
     * @return the message to the developer.
     */
    public String getMessageToDeveloper() {
        return messageToDeveloper;
    }
}
