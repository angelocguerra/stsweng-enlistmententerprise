package com.orangeandbronze.enlistment;

public class DuplicateSubjectEnlistmentException extends RuntimeException {
    DuplicateSubjectEnlistmentException(String message) {
        super(message);
    }
}