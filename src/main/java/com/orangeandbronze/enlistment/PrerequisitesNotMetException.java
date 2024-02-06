package com.orangeandbronze.enlistment;

public class PrerequisitesNotMetException extends RuntimeException{
    PrerequisitesNotMetException(String message) {
        super(message);
    }
}
