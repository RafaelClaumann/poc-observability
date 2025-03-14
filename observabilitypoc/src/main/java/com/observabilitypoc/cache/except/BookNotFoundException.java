package com.observabilitypoc.cache.except;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(final String message) {
        super(message);
    }
}
