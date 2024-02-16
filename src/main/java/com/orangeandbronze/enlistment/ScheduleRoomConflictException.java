package com.orangeandbronze.enlistment;

public class ScheduleRoomConflictException extends RuntimeException {
    ScheduleRoomConflictException(String msg) {
        super(msg);
    }
}