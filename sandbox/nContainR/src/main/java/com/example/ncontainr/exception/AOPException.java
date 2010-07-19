package com.example.ncontainr.exception;

public class AOPException extends RuntimeException {

    public AOPException(final String message) {
        super(message);
    }

    public AOPException(final Exception message) {
        super(message);
    }

    public AOPException(final String message, final Exception ex) {
        super(message, ex);
    }
}

