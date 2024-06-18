package com.caching.caching_api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfiguration
{
    @Bean
    public CacheManager cacheManager()
    {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("customer");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    Caffeine<Object, Object> caffeineCacheBuilder()
    {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(700)
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats();
    }
}
