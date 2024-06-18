package com.caching.caching_api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.caching.caching_api.controller.CustomerController;
import com.caching.caching_api.entity.Customer;
import com.caching.caching_api.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest (CustomerController.class)
public class CustomerControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveCustomer() throws Exception
    {
        Customer customer = new Customer(1, "John Doe", "johndoe@example.com");

        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.address").value(customer.getAddress()));
    }

    @Test
    void testUpdateCustomer() throws Exception
    {
        Customer customer = new Customer(1, "John Doe", "johndoe@example.com");

        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/customers/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.address").value(customer.getAddress()));
    }

    @Test
    void testGetCustomer() throws Exception
    {
        Customer customer = new Customer(1, "John Doe", "johndoe@example.com");

        when(customerService.getCustomerById(customer.getCustomerId())).thenReturn(customer);

        mockMvc.perform(get("/customers/{id}", customer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andExpect(jsonPath("$.address").value(customer.getAddress()));
    }

    @Test
    void testDeleteCustomer() throws Exception
    {
        mockMvc.perform(delete("/customers/delete/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
