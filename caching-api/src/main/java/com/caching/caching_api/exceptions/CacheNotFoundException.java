package com.caching.caching_api.exceptions;

public class CacheNotFoundException extends RuntimeException
{
    public CacheNotFoundException(String message)
    {
        super(message);
    }
}
