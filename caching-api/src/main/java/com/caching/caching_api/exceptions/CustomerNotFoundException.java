package com.caching.caching_api.exceptions;

public class CustomerNotFoundException extends RuntimeException
{
    public CustomerNotFoundException(String message)
    {
        super(message);
    }
}
