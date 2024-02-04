package com.orangeandbronze.enlistment;
/**
 * A custom Exception class to handle the situation when a user attempts to enlist in a section with schedule conflict
 */
public class ScheduleConflictException extends RuntimeException {
    /**
     * Creates a ScheduleConflictException with a specified message
     * @param msg   A detailed message explaining the reason this error is thrown
     */
    ScheduleConflictException(String msg) {
        super(msg);
    }
}
