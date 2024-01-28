package com.orangeandbronze.enlistment;

public class ScheduleConflictException extends RuntimeException {
    ScheduleConflictException(String msg) {
        super(msg);
    }
}
