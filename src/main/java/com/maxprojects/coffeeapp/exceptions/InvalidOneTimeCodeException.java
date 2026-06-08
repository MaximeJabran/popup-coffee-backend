package com.maxprojects.coffeeapp.exceptions;

public class InvalidOneTimeCodeException extends RuntimeException {

    public InvalidOneTimeCodeException() {
        super("Invalid or expired membership code");
    }
}
