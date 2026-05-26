package com.rikkei.bai5.exception;

public class WishLimitExceededException extends RuntimeException {
    public WishLimitExceededException(String message) {
        super(message);
    }
}
