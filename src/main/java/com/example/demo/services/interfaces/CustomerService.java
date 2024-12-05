package com.example.demo.services.interfaces;

import com.example.demo.dtos.customer.CustomerRequest;
import com.example.demo.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Long id);
    Customer saveCustomer(CustomerRequest customerRequest);
    Customer updateCustomer(Long id, Customer customer);
    void deleteCustomer(Long id);

}
