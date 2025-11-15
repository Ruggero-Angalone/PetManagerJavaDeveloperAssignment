package com.petmanager.exception;

public class OwnerHasPetsException extends RuntimeException {
    public OwnerHasPetsException(String msg) {
        super(msg);
    }
}