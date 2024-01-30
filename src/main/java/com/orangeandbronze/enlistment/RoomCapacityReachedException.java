package com.orangeandbronze.enlistment;
/**
 * A custom Exception class to handle the situation when a user attempts to enlist in a section whose room is already full
 */
public class RoomCapacityReachedException extends RuntimeException {
    /**
     * Creates a RoomCapacityReachedException with a specified message
     * @param message   A detailed message explaining the reason this error is thrown
     */
    public RoomCapacityReachedException(String message) {
        super(message);
    }
}
