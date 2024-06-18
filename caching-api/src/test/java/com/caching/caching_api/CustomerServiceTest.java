package com.caching.caching_api;

import com.caching.caching_api.entity.Customer;
import com.caching.caching_api.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static java.util.Optional.ofNullable;

@ExtendWith (SpringExtension.class)
@SpringBootTest (classes = CachingApiApplication.class)
public class CustomerServiceTest
{
    @Autowired
    CacheManager cacheManager;

    @Autowired
    CustomerService customerService;

    @BeforeEach
    void setUp()
    {
        Customer customer = new Customer(3, "John", "USA");
        customerService.saveCustomer(customer);
        customer = new Customer(4, "Joe", "USA");
        customerService.saveCustomer(customer);
    }

    @Test
    void givenCustomerThatShouldBeCached_whenFindById_thenResultShouldBePutInCache()
    {
        Customer joe = customerService.getCustomerById(3);
        assertEquals(joe, getCachedCustomer(3).get());
    }

    private Optional<Customer> getCachedCustomer(Integer customerId)
    {
        return ofNullable(cacheManager.getCache("customer"))
                .map(c -> c.get(customerId, Customer.class));
    }
}
