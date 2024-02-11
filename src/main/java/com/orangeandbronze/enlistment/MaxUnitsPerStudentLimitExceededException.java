package com.orangeandbronze.enlistment;

public class MaxUnitsPerStudentLimitExceededException extends RuntimeException {
    MaxUnitsPerStudentLimitExceededException(String message) {
        super(message);
    }
}
