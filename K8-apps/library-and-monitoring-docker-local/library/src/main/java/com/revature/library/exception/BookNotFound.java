package com.revature.library.exception;

public class BookNotFound extends RuntimeException{

    public BookNotFound(String message) {
        super(message);
    }
    
}
