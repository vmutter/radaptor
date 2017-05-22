package com.vmutter.exception;

public class RadaptorException extends RuntimeException {

    public RadaptorException(final String message) {
        super(message);
    }

    public RadaptorException(final String message, final Throwable e) {
        super(message, e);
    }
}
