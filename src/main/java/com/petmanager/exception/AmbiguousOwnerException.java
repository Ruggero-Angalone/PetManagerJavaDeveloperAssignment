package com.petmanager.exception;

public class AmbiguousOwnerException extends RuntimeException {
    public AmbiguousOwnerException(String msg) {
        super(msg);
    }
}