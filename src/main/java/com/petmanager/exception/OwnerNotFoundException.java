package com.petmanager.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String msg) {
        super(msg);
    }
}