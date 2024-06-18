package com.caching.caching_api.service;

import com.caching.caching_api.entity.Customer;
import com.caching.caching_api.exceptions.CacheNotFoundException;
import com.caching.caching_api.exceptions.CustomerNotFoundException;
import com.caching.caching_api.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService
{
    private static final Logger LOG = LoggerFactory.getLogger(CustomerService.class.getName());
    private static final String CACHE_NAME = "customer";
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    CacheManager cacheManager;

    public Customer saveCustomer(Customer customer)
    {
        LOG.info("Customer Record Saved");
        return customerRepository.save(customer);
    }

    @Cacheable (value = CACHE_NAME, key = "#id")
    public Customer getCustomerById(Integer id)
    {
        LOG.info("Getting Customer record with id :" + id);
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer does not exist with id: " + id));
    }

    @CachePut (value = CACHE_NAME, key = "#customer.customerId")
    public Customer updateCustomer(Customer customer)
    {
        LOG.info("Updating Customer Record : " + customer.getCustomerId());
        return customerRepository.save(customer);
    }

    @CacheEvict (value = CACHE_NAME, key = "#id")
    public void deleteCustomer(Integer id)
    {
        customerRepository.deleteById(id);
        LOG.info("Delete Customer Record: " + id);
        invalidateCacheKey(CACHE_NAME, id);
    }

    public void invalidateCache(String cacheName)
    {
        Cache customerCache = cacheManager.getCache(cacheName);
        if (customerCache == null)
        {
            throw new CacheNotFoundException("Cache does not exist with name " + cacheName);
        }
        customerCache.invalidate();
        LOG.info(cacheName + " get invalidated!");
    }

    public void invalidateCacheKey(String cacheName, Integer key)
    {
        final Cache customerCache = cacheManager.getCache(cacheName);
        if (customerCache == null)
        {
            throw new CacheNotFoundException("Cache does not exist with name " + cacheName);
        }
        customerCache.evict(key);
        LOG.info(key + " get invalidated in the " + cacheName);
    }
}
