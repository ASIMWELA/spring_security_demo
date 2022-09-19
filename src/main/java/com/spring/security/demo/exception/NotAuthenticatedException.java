package com.spring.security.demo.exception;

public class NotAuthenticatedException extends RuntimeException{
    public NotAuthenticatedException()
    {
        super();
    }

    public NotAuthenticatedException(String message)
    {
        super(message);
    }
}
