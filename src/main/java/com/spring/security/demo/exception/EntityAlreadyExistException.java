package com.spring.security.demo.exception;

public class EntityAlreadyExistException extends RuntimeException
{
    public EntityAlreadyExistException()
    {
        super();
    }

    public EntityAlreadyExistException(String message)
    {
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EntityAlreadyExistException(Throwable cause)
    {
        super(cause);
    }
}
