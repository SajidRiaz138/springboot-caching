package com.caching.caching_api.controller;

import com.caching.caching_api.entity.Customer;
import com.caching.caching_api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/customers")
public class CustomerController
{
    @Autowired
    CustomerService customerService;

    @PostMapping ("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer)
    {
        Customer entity = customerService.saveCustomer(customer);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping ("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer)
    {
        return new ResponseEntity<>(customerService.updateCustomer(customer), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable ("id") Integer id)
    {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable ("id") Integer id)
    {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping ("/{cacheName}")
    public ResponseEntity<String> invalidateCache(@PathVariable ("cacheName") String cacheName)
    {
        customerService.invalidateCache(cacheName);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping ("/{cacheName}/{key}")
    public ResponseEntity<String> invalidateCacheKey(@PathVariable ("cacheName") String cacheName, @PathVariable ("key") Integer key)
    {
        customerService.invalidateCacheKey(cacheName, key);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
