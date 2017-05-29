package com.vmutter.radaptor.exception;

public class RadaptorException extends RuntimeException {

    public RadaptorException(final String message) {
        super(message);
    }

    public RadaptorException(final Throwable e) {
        super(e);
    }

    public RadaptorException(final String message, final Throwable e) {
        super(message, e);
    }
}
