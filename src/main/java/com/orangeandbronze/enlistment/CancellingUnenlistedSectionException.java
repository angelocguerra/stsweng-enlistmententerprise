package com.orangeandbronze.enlistment;
/**
 * A custom Exception class to handle the situation when a user attempts to cancel an unenlisted section
 */
public class CancellingUnenlistedSectionException extends RuntimeException {
    /**
     * Creates a CancellingUnenlistedSectionException with a specified message
     * @param message   A detailed message explaining the reason this error is thrown
     */
    public CancellingUnenlistedSectionException(String message) {
        super(message);
    }
}
